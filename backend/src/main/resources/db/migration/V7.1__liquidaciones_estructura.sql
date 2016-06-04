DROP TABLE historico_asociado;

CREATE TABLE historico_asociado (
  id        INTEGER                  NOT NULL,
  grupo_id  CHARACTER(3)             NULL,
  fecha     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  operacion SMALLINT                 NOT NULL,
  usuario   VARCHAR(100)             NOT NULL,
  datos     JSONB                    NOT NULL,
  CONSTRAINT pk_historico_asociado PRIMARY KEY (id, fecha),
  CONSTRAINT chk_historico_asociado_operacion CHECK (operacion IN (0, 1, 2))
);

insert into historico_asociado (id, fecha, operacion, usuario, datos)
select id, fecha_alta as h_fecha, 0 as h_operacion, 'cudu@scoutsfev.org' as h_usuario, row_to_json(a)::JSONB from asociado a;

-- Normalizar valores activo/fecha_baja que vienen de 1.0
update asociado set fecha_baja = fecha_actualizacion where fecha_baja is null and activo = false;
update asociado set fecha_baja = fecha_baja + interval '1' day where fecha_baja = fecha_alta;

-- BAJAS
insert into historico_asociado (id, fecha, operacion, usuario, datos)
select id, fecha_baja as h_fecha, 2 as h_operacion, 'cudu@scoutsfev.org' as h_usuario, row_to_json(a)::JSONB from asociado a where activo = false;


select count(*) from historico_asociado group by operacion;




--drop FUNCTION marcarTodoComoNullable(text, text);

-- CREATE OR REPLACE FUNCTION marcarTodoComoNullable(_schema TEXT, _table TEXT)
--   RETURNS INTEGER
-- LANGUAGE plpgsql
-- AS
-- $$
-- DECLARE
--   r RECORD;
--   columnas INTEGER := 0;
--   drop_stm TEXT;
-- BEGIN
--   FOR r IN
--   SELECT column_name
--   FROM information_schema.columns
--   WHERE table_schema = _schema AND table_name = _table AND is_nullable = 'NO'
--   LOOP
--     drop_stm := format('ALTER TABLE %s ALTER COLUMN %s DROP NOT NULL;', quote_ident(_schema) || '.' || quote_ident(_table), quote_ident(r.column_name));
--     RAISE INFO 'Marcando columna ''%'' como nulable.', r.column_name;
--     RAISE INFO '%', drop_stm;
--     EXECUTE drop_stm;
--     columnas := columnas + 1;
--   END LOOP;
--   RETURN columnas;
-- END;
-- $$;
--
-- SELECT marcarTodoComoNullable('public', 'historico_asociado');

-- ALTER TABLE historico_asociado
--   ALTER COLUMN id SET NOT NULL,
--   ALTER COLUMN h_fecha SET NOT NULL,
--   ALTER COLUMN h_operacion SET NOT NULL,
--   ALTER COLUMN h_usuario SET NOT NULL,
--   ADD CONSTRAINT pk_historico_asociado PRIMARY KEY (id, h_fecha),
--   ADD CONSTRAINT chk_historico_asociado_operacion CHECK (h_operacion IN (0, 1, 2));

