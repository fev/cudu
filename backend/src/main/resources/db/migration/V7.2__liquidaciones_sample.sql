delete from liquidacion_asociado;
delete from liquidacion;

--select crear_liquidacion('UP'::varchar(3), 2015::SMALLINT, 'jack.sparow@example.com'::varchar(100));

-- insert into liquidacion (grupo_id, ronda_id, fecha, precio_unitario, creado_por)
-- VALUES ('AK', 2015, CURRENT_DATE, 3, '');
-- insert into liquidacion_asociado (liquidacion_id, asociado_id, alta)
-- select 1, id, true from asociado a where a.grupo_id = 'AK' and a.activo = true limit 115;
--
-- insert into liquidacion (grupo_id, ronda_id, fecha, precio_unitario, creado_por)
-- VALUES ('AK', 2015, CURRENT_DATE + interval '4' month, 3, '');
-- insert into liquidacion_asociado (liquidacion_id, asociado_id, alta)
-- select 2, id, true from asociado a where a.grupo_id = 'AK' and a.activo = true limit 118;
--
-- insert into liquidacion (grupo_id, ronda_id, fecha, precio_unitario, creado_por)
-- VALUES ('AK', 2015, CURRENT_DATE + interval '6' month, 3, '');
-- insert into liquidacion_asociado (liquidacion_id, asociado_id, alta)
-- select 3, id, true from asociado a where a.grupo_id = 'AK' and a.activo = true limit 110;
--
--
-- insert into liquidacion (grupo_id, ronda_id, fecha, precio_unitario, creado_por)
-- VALUES ('UP', 2015, CURRENT_DATE, 10, '');
--
-- insert into liquidacion_asociado (liquidacion_id, asociado_id, alta)
-- select 4, id, true from asociado a where a.grupo_id = 'UP' and a.activo = true limit 8;
--
-- insert into liquidacion (grupo_id, ronda_id, fecha, precio_unitario, creado_por)
-- VALUES ('UP', 2015, CURRENT_DATE + interval '1' month, 10, '');
--
-- insert into liquidacion_asociado (liquidacion_id, asociado_id, alta)
-- select 5, id, true from asociado a where a.grupo_id = 'UP' and a.activo = true limit 7;
--
-- insert into liquidacion (grupo_id, ronda_id, fecha, precio_unitario, creado_por)
-- VALUES ('UP', 2015, CURRENT_DATE + interval '2' month, 10, '');
--
-- insert into liquidacion_asociado (liquidacion_id, asociado_id, alta)
-- select 6, id, true from asociado a where a.grupo_id = 'UP' and a.activo = true limit 10;

-- LIQUIDACION DE AJUSTE
-- insert into liquidacion (grupo_id, ronda_id, fecha, precio_unitario, creado_por)
-- VALUES ('UP', 2015, CURRENT_DATE + interval '3' month, 10, '');

-- update liquidacion set borrador = false;