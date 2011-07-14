
--ALTER TABLE historico_asociados  DROP CONSTRAINT u_historico_asociado_usuario;

ALTER TABLE historico_asociados  DROP COLUMN profesion ;
ALTER TABLE historico_asociados  DROP COLUMN cargos;

ALTER TABLE ASOCIADO DROP COLUMN profesion ;
ALTER TABLE ASOCIADO DROP COLUMN cargos;
--ALTER TABLE ASOCIADO drop CONSTRAINT ck_enum_asociado_cargo;


ALTER TABLE ASOCIADO ADD COLUMN profesion varchar(200);
alter table asociado add column cargos character varying(16) DEFAULT ''::character varying;
--ALTER TABLE ASOCIADO ADD CONSTRAINT ck_enum_asociado_cargo CHECK (cargo = ANY (ARRAY['P'::bpchar, 'S'::bpchar, 'T'::bpchar, 'I'::bpchar, 'C'::bpchar, 'N'::bpchar, 'V'::bpchar, 'O'::bpchar]));

ALTER TABLE ASOCIADO DROP COLUMN cargo_Presidencia;
ALTER TABLE ASOCIADO DROP COLUMN cargo_Tesoreria;
ALTER TABLE ASOCIADO DROP COLUMN cargo_Cocina ;
ALTER TABLE ASOCIADO DROP COLUMN cargo_Otro;
ALTER TABLE ASOCIADO DROP COLUMN cargo_Secretaria;
ALTER TABLE ASOCIADO DROP COLUMN cargo_Consiliario;
ALTER TABLE ASOCIADO DROP COLUMN cargo_intendencia;
ALTER TABLE ASOCIADO DROP COLUMN cargo_vocal;


ALTER TABLE ASOCIADO ADD COLUMN cargo_Presidencia boolean NOT NULL DEFAULT false;
ALTER TABLE ASOCIADO ADD COLUMN cargo_Tesoreria boolean NOT NULL DEFAULT false;
ALTER TABLE ASOCIADO ADD COLUMN cargo_Cocina boolean NOT NULL DEFAULT false;
ALTER TABLE ASOCIADO ADD COLUMN cargo_Otro boolean NOT NULL DEFAULT false;
ALTER TABLE ASOCIADO ADD COLUMN cargo_Secretaria boolean NOT NULL DEFAULT false;
ALTER TABLE ASOCIADO ADD COLUMN cargo_Consiliario boolean NOT NULL DEFAULT false;
ALTER TABLE ASOCIADO ADD COLUMN cargo_vocal boolean NOT NULL DEFAULT false;
ALTER TABLE ASOCIADO ADD COLUMN cargo_intendencia boolean NOT NULL DEFAULT false;


ALTER TABLE historico_asociados ADD COLUMN profesion varchar(200);
alter table historico_asociados add column cargos character varying(16) DEFAULT ''::character varying;



ALTER TABLE historico_asociados DROP COLUMN cargo_Presidencia;
ALTER TABLE historico_asociados DROP COLUMN cargo_Tesoreria;
ALTER TABLE historico_asociados DROP COLUMN cargo_Cocina ;
ALTER TABLE historico_asociados DROP COLUMN cargo_Otro;
ALTER TABLE historico_asociados DROP COLUMN cargo_Secretaria;
ALTER TABLE historico_asociados DROP COLUMN cargo_Consiliario;
ALTER TABLE historico_asociados DROP COLUMN cargo_intendencia;
ALTER TABLE historico_asociados DROP COLUMN cargo_vocal;




ALTER TABLE historico_asociados ADD COLUMN cargo_Presidencia boolean NOT NULL DEFAULT false;
ALTER TABLE historico_asociados ADD COLUMN cargo_Tesoreria boolean NOT NULL DEFAULT false;
ALTER TABLE historico_asociados ADD COLUMN cargo_Cocina boolean NOT NULL DEFAULT false;
ALTER TABLE historico_asociados ADD COLUMN cargo_Otro boolean NOT NULL DEFAULT false;
ALTER TABLE historico_asociados ADD COLUMN cargo_Secretaria boolean NOT NULL DEFAULT false;
ALTER TABLE historico_asociados ADD COLUMN cargo_Consiliario boolean NOT NULL DEFAULT false;
ALTER TABLE historico_asociados ADD COLUMN cargo_vocal boolean NOT NULL DEFAULT false;
ALTER TABLE historico_asociados ADD COLUMN cargo_intendencia boolean NOT NULL DEFAULT false;


--ramas character varying(10) DEFAULT ''::character varying;
update asociado SET PROFEsion='';
update asociado SET cargos='O';


select cargos, profesion,id, nombre from asociado;


