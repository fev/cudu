
delete from historico_asociado;

-- ALTAS
insert into historico_asociado ()
select fecha_alta as h_fecha, 0 as h_operacion, '(migración)' as h_usuario, row_to_json(*) from asociado;

-- Normalizar valores activo/fecha_baja que vienen de 1.0
update asociado set fecha_baja = fecha_actualizacion where fecha_baja is null and activo = false;
update asociado set fecha_baja = fecha_baja + interval '1' day where fecha_baja = fecha_alta;

-- BAJAS
insert into historico_asociado
select fecha_baja as h_fecha, 2 as h_operacion, '(migración)' as h_usuario, * from asociado where activo = false;

