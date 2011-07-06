-- Function: stamp_updated()
-- CAMBIAR NOMBRE DE STAMP_UPDATED
-- DROP FUNCTION stamp_updated();

CREATE OR REPLACE FUNCTION stamp_updated()
  RETURNS trigger AS
$BODY$
BEGIN
  NEW.last_updated := now();
  RETURN NEW;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION stamp_updated() OWNER TO postgres;



usuario->  last_updated timestamp without time zone,
CREATE TRIGGER asociado_stamp_updated
  BEFORE INSERT OR UPDATE
  ON asociado
  FOR EACH ROW
  EXECUTE PROCEDURE stamp_updated();


/*
-- Table: asociado

-- DROP TABLE asociado;

CREATE TABLE asociado
(
  id serial NOT NULL,
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
  CONSTRAINT pk_asociado PRIMARY KEY (id),
  CONSTRAINT fk_asociado_grupo FOREIGN KEY (idgrupo)
      REFERENCES grupo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_asociado_users FOREIGN KEY (usuario)
      REFERENCES users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT u_asociado_usuario UNIQUE (usuario),
  CONSTRAINT ck_enum_asociado_sexo CHECK (sexo = ANY (ARRAY['M'::bpchar, 'F'::bpchar])),
  CONSTRAINT ck_enum_asociado_tipo CHECK (tipo = ANY (ARRAY['J'::bpchar, 'K'::bpchar, 'C'::bpchar, 'V'::bpchar]))
)
WITH (
  OIDS=FALSE
);
ALTER TABLE asociado OWNER TO postgres;

-- Index: idx_busqueda_asociado

-- DROP INDEX idx_busqueda_asociado;

CREATE INDEX idx_busqueda_asociado
  ON asociado
  USING gin
  (textsearchindex);


-- Trigger: actualizarasociacion on asociado

-- DROP TRIGGER actualizarasociacion ON asociado;

CREATE TRIGGER actualizarasociacion
  BEFORE INSERT
  ON asociado
  FOR EACH ROW
  EXECUTE PROCEDURE actualizarfiltroasociacion();

-- Trigger: actualizarindicebusquedaasociado on asociado

-- DROP TRIGGER actualizarindicebusquedaasociado ON asociado;

CREATE TRIGGER actualizarindicebusquedaasociado
  BEFORE INSERT OR UPDATE
  ON asociado
  FOR EACH ROW
  EXECUTE PROCEDURE tsvector_update_trigger('textsearchindex', 'pg_catalog.spanish', 'nombre', 'primerapellido', 'segundoapellido');

-- Trigger: asociado_stamp_updated on asociado

-- DROP TRIGGER asociado_stamp_updated ON asociado;

CREATE TRIGGER asociado_stamp_updated
  BEFORE INSERT OR UPDATE
  ON asociado
  FOR EACH ROW
  EXECUTE PROCEDURE stamp_updated();

-- Trigger: auditasociado on asociado

-- DROP TRIGGER auditasociado ON asociado;

CREATE TRIGGER auditasociado
  BEFORE INSERT OR UPDATE
  ON asociado
  FOR EACH ROW
  EXECUTE PROCEDURE actualizardatosauditoriaasociado();

-- Trigger: menorsoloenunaunidad on asociado

-- DROP TRIGGER menorsoloenunaunidad ON asociado;

CREATE TRIGGER menorsoloenunaunidad
  BEFORE INSERT OR UPDATE
  ON asociado
  FOR EACH ROW
  EXECUTE PROCEDURE comprobarcantidadramasmenor();
*/








grupo->  last_updated timestamp without time zone,

-- Trigger: grupo_stamp_updated on grupo

-- DROP TRIGGER grupo_stamp_updated ON grupo;

CREATE TRIGGER grupo_stamp_updated
  BEFORE INSERT OR UPDATE
  ON grupo
  FOR EACH ROW
  EXECUTE PROCEDURE stamp_updated();



/*
-- Table: grupo

-- DROP TABLE grupo;

CREATE TABLE grupo
(
  id character varying(20) NOT NULL,
  nombre character varying(50) NOT NULL,
  codigopostal integer NOT NULL,
  idprovincia integer NOT NULL DEFAULT 0,
  provincia character varying(100) NOT NULL,
  idmunicipio integer NOT NULL DEFAULT 0,
  municipio character varying(100) NOT NULL,
  aniversario date,
  telefono1 character varying(15),
  telefono2 character varying(15),
  email character varying(100),
  web character varying(300),
  entidadpatrocinadora character varying(100),
  asociacion smallint NOT NULL,
  jpa_version integer NOT NULL DEFAULT 0,
  direccion character varying(300) NOT NULL DEFAULT '(desconocida)'::character varying,
  last_updated timestamp without time zone,
  CONSTRAINT pk_grupo PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE grupo OWNER TO postgres;

-- Trigger: actualizarasociaciongrupo on grupo

-- DROP TRIGGER actualizarasociaciongrupo ON grupo;

CREATE TRIGGER actualizarasociaciongrupo
  AFTER UPDATE
  ON grupo
  FOR EACH ROW
  EXECUTE PROCEDURE actualizarfiltroasociaciongrupo();

-- Trigger: grupo_stamp_updated on grupo

-- DROP TRIGGER grupo_stamp_updated ON grupo;

CREATE TRIGGER grupo_stamp_updated
  BEFORE INSERT OR UPDATE
  ON grupo
  FOR EACH ROW
  EXECUTE PROCEDURE stamp_updated();

*/

