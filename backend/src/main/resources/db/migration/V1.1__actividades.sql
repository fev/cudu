CREATE TABLE actividad (
  id SERIAL PRIMARY KEY NOT NULL,
  grupo_id VARCHAR(3) NOT NULL,
  nombre VARCHAR(255) NOT NULL,
  fecha_inicio date NOT NULL,
  fecha_fin date NULL,
  creada_por VARCHAR(130) NOT NULL,
  lugar VARCHAR(100),
  precio VARCHAR(20),
  responsable VARCHAR(130),
  notas TEXT,
  rama_colonia BOOL DEFAULT false NOT NULL,
  rama_expedicion BOOL DEFAULT false NOT NULL,
  rama_exploradores BOOL DEFAULT false NOT NULL,
  rama_manada BOOL DEFAULT false NOT NULL,
  rama_ruta BOOL DEFAULT false NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT now() NOT NULL,
  fecha_baja TIMESTAMP NULL,
  FOREIGN KEY (grupo_id) REFERENCES grupo (id)
);

CREATE TABLE asistente_actividad (
    actividad_id INT NOT NULL,
    asociado_id INT NOT NULL,
    estado char(1) NOT NULL,
    PRIMARY KEY (asociado_id, actividad_id),
    FOREIGN KEY (actividad_id) REFERENCES actividad (id) ON DELETE CASCADE,
    FOREIGN KEY (asociado_id) REFERENCES asociado (id) ON DELETE CASCADE
);

CREATE OR REPLACE VIEW dto_actividad_detalle
  AS
SELECT
  a1.grupo_id, a2.actividad_id, a2.estado AS estado_asistente,
  a1.id as asociado_id, concat_ws(' ', a1.nombre, a1.apellidos) AS nombre_completo, a1.tipo,
  a1.rama_colonia, a1.rama_manada, a1.rama_exploradores, a1.rama_expedicion, a1.rama_ruta,
  coalesce(a1.telefono_movil, a1.telefono_casa, a1.madre_telefono || ' (madre)', a1.padre_telefono || ' (padre)') AS telefono
FROM asociado a1
  INNER JOIN asistente_actividad a2 ON a1.id = a2.asociado_id
WHERE a1.activo = TRUE;