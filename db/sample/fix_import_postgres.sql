\connect cudu

-- Actualizar secuencia
SELECT setval('public.asociado_id_seq', 201, true);

-- Corregir teléfonos
update asociado set telefonoCasa = regexp_replace(telefonoCasa, '[^0-9]', '', 'g');
update asociado set telefonoMovil = regexp_replace(telefonoMovil, '[^0-9]', '', 'g');
update asociado set padre_telefono = regexp_replace(padre_telefono, '[^0-9]', '', 'g');
update asociado set madre_telefono = regexp_replace(madre_telefono, '[^0-9]', '', 'g');

-- Ramas
update asociado set ramas = 'E' where ramas = 'T';
update asociado set rama_colonia = true where ramas like '%C%';
update asociado set rama_manada = true where ramas like '%M%';
update asociado set rama_exploradores = true where ramas like '%E%';
update asociado set rama_pioneros = true where ramas like '%P%';
update asociado set rama_rutas = true where ramas like '%R%';


