insert into asociado (tipo, idGrupo, nombre, primerApellido, segundoApellido, sexo, fechaNacimiento, calle, 
patio, codigopostal, idprovincia, provincia, idmunicipio, municipio, telefonoCasa)
values ('N', 'AK', 'Boo', 'Wazowski', 'Sullivan', 'F', '12/03/1997', 'De la Rúe', 
'3', 46341, 46, 'Valencia', 12, 'Rocafort', '972691732');

insert into asociado (tipo, idGrupo, nombre, primerApellido, segundoApellido, sexo, fechaNacimiento, calle, 
patio, codigopostal, idprovincia, provincia, idmunicipio, municipio, telefonoCasa)
values ('S', 'AK', 'Luis', 'Belloch', 'Gómez', 'M', '31/01/1982', 'Jorge Comín', 
'6', 46015, 46, 'Valencia', 250, 'Valencia', '953478858');


insert into asociado_rama values (1, 'P');
insert into asociado_rama values (2, 'P');
insert into asociado_rama values (2, 'R');

-- select * from grupo;
-- select * from asociado;
-- select * from asociado_rama;