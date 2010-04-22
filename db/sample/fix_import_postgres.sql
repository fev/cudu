\connect cudu

-- Actualizar secuencia
SELECT setval('public.asociado_id_seq', 201, true);

-- Corregir teléfonos
update asociado set telefonoCasa = regexp_replace(telefonoCasa, '[^0-9]', '', 'g');
update asociado set telefonoMovil = regexp_replace(telefonoMovil, '[^0-9]', '', 'g');
update asociado set padre_telefono = regexp_replace(padre_telefono, '[^0-9]', '', 'g');
update asociado set madre_telefono = regexp_replace(madre_telefono, '[^0-9]', '', 'g');


