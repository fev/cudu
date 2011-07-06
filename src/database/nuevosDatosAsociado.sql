
--ALTER TABLE historico_asociados  DROP CONSTRAINT u_historico_asociado_usuario;
ALTER TABLE historico_asociados  DROP COLUMN profesion ;
ALTER TABLE historico_asociados  DROP COLUMN cargo;

ALTER TABLE ASOCIADO DROP COLUMN profesion ;
ALTER TABLE ASOCIADO DROP COLUMN cargo;
--ALTER TABLE ASOCIADO drop CONSTRAINT ck_enum_asociado_cargo;


ALTER TABLE ASOCIADO ADD COLUMN profesion varchar(200);
ALTER TABLE ASOCIADO ADD COLUMN cargo character(1);
--ALTER TABLE ASOCIADO ADD CONSTRAINT ck_enum_asociado_cargo CHECK (cargo = ANY (ARRAY['P'::bpchar, 'S'::bpchar, 'T'::bpchar, 'I'::bpchar, 'C'::bpchar, 'N'::bpchar, 'V'::bpchar, 'O'::bpchar]));


ALTER TABLE historico_asociados ADD COLUMN profesion varchar(200);
ALTER TABLE historico_asociados ADD COLUMN cargo character(1);

update asociado SET PROFEsion='';
update asociado SET cargo='O';


select cargo, profesion,id, nombre from asociado;