drop sequence serialhistaso ;
CREATE SEQUENCE serialhistaso START 1;
drop table historico_asociados;
create table historico_asociados(
	idHistorico serial NOT NULL,
	fecha timestamp,
	id INTEGER NOT NULL,
	  tipo character(1) NOT NULL DEFAULT 'J'::bpchar,
	  idgrupo character varying(20) NOT NULL,
	  nombre character varying(30) NOT NULL,
	  primerapellido character varying(50) NOT NULL,
	  segundoapellido character varying(50),
	  sexo character(1) NOT NULL,
	  fechanacimiento date NOT NULL,
	  dni character varying(10),
	  seguridadsocial character varying(12),
	  tieneseguroprivado boolean NOT NULL DEFAULT false,
	  calle character varying(100) NOT NULL,
	  numero character varying(5) NOT NULL,
	  escalera character varying(3),
	  puerta character varying(3),
	  codigopostal integer NOT NULL,
	  telefonocasa character varying(15),
	  telefonomovil character varying(15),
	  email character varying(100),
	  idprovincia smallint NOT NULL DEFAULT 0,
	  provincia character varying(100) NOT NULL,
	  idmunicipio integer NOT NULL DEFAULT 0,
	  municipio character varying(100) NOT NULL,
	  tienetutorlegal boolean NOT NULL DEFAULT false,
	  padresdivorciados boolean NOT NULL DEFAULT false,
	  padre_nombre character varying(250),
	  padre_telefono character varying(15),
	  padre_email character varying(100),
	  madre_nombre character varying(250),
	  madre_telefono character varying(15),
	  madre_email character varying(100),
	  fechaalta timestamp without time zone NOT NULL DEFAULT now(),
	  fechabaja timestamp without time zone,
	  fechaactualizacion timestamp without time zone NOT NULL DEFAULT now(),
	  ramas character varying(10) DEFAULT ''::character varying,
	  rama_colonia boolean NOT NULL DEFAULT false,
	  rama_manada boolean NOT NULL DEFAULT false,
	  rama_exploradores boolean NOT NULL DEFAULT false,
	  rama_pioneros boolean NOT NULL DEFAULT false,
	  rama_rutas boolean NOT NULL DEFAULT false,
	  jpa_version integer NOT NULL DEFAULT 0,
	  textsearchindex tsvector,
	  asociacion smallint NOT NULL,
	  activo boolean NOT NULL DEFAULT true,
	  usuario character varying(200),
	  last_updated timestamp without time zone,
	  CONSTRAINT pk_historico_asociado PRIMARY KEY (idHistorico),
	  CONSTRAINT fk_historico_asociado_grupo FOREIGN KEY (idgrupo)
	      REFERENCES grupo (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_historico_asociado_users FOREIGN KEY (usuario)
	      REFERENCES users (username) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT u_historico_asociado_usuario UNIQUE (usuario),
	  CONSTRAINT ck_historico_enum_asociado_sexo CHECK (sexo = ANY (ARRAY['M'::bpchar, 'F'::bpchar])),
	  CONSTRAINT ck_historico_enum_asociado_tipo CHECK (tipo = ANY (ARRAY['J'::bpchar, 'K'::bpchar, 'C'::bpchar, 'V'::bpchar]))
	)
	WITH (
	  OIDS=FALSE
	);



	
-- Function: stamp_updated()
-- CAMBIAR NOMBRE DE STAMP_UPDATED
-- DROP FUNCTION stamp_updated();

CREATE OR REPLACE FUNCTION insertar_filas_asociado()
  RETURNS trigger AS
$BODY$

DECLARE 
	yearAct int;
	monthAct int;
	dayAct int;


BEGIN




yearAct		:= extract(YEAR  from now());
monthAct	:= extract(MONTH from now());
dayAct		:= extract(DAY   from now());


	insert into historico_asociados(
	idHistorico,id,
	fecha,	tipo,	idgrupo,	nombre,	primerapellido,	segundoapellido,
	  sexo,	fechanacimiento,	dni,	seguridadsocial,	tieneseguroprivado,
	  calle,	numero,	escalera,	puerta,	codigopostal,	telefonocasa,	telefonomovil,
	  email,
	  idprovincia,	provincia,idmunicipio,	municipio,	tienetutorlegal,
	  padresdivorciados,	padre_nombre,	padre_telefono,	padre_email,
	 madre_nombre,	madre_telefono,	madre_email,	fechaalta,	fechabaja,	fechaactualizacion,
	  ramas,	rama_colonia,	rama_manada,	rama_exploradores,		rama_pioneros,
	  rama_rutas,	jpa_version,	textsearchindex,	asociacion,
	  activo,	usuario)
SELECT 
	nextval('serialhistaso'),id,last_updated,	  tipo,	idgrupo,
	  nombre,
	  primerapellido,
	  segundoapellido,
	  sexo,
	  fechanacimiento,
	  dni,
	  seguridadsocial,
	  tieneseguroprivado,
	  calle,
	  numero,
	  escalera,
	  puerta,
	  codigopostal,
	  telefonocasa,
	  telefonomovil,
	  email,
	  idprovincia,
	  provincia,
	  idmunicipio,
	  municipio,
	  tienetutorlegal,
	  padresdivorciados,
	  padre_nombre,
	  padre_telefono,
	  padre_email,
	  madre_nombre,
	  madre_telefono,
	  madre_email,
	  fechaalta,
	  fechabaja,
	  fechaactualizacion,
	  ramas,
	  rama_colonia,
	  rama_manada,
	  rama_exploradores,
	  rama_pioneros,
	  rama_rutas,
	  jpa_version,
	  textsearchindex,
	  asociacion,
	  activo,
	  usuario
	  from asociado
	  
	WHERE   extract(YEAR  from last_updated)=yearAct AND
		extract(MONTH  from last_updated)=monthAct AND
		extract(DAY  from last_updated)=dayAct;

return new;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


ALTER FUNCTION insertar_filas_asociado() OWNER TO postgres;

	
--CREATE OR REPLACE TRIGGER actualizar_historico_asociado
--  BEFORE INSERT OR UPDATE
--  ON asociado
--  EXECUTE PROCEDURE insertar_filas_asociado();
