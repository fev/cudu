create table curso (
  id SERIAL NOT NULL,
  titulo VARCHAR(128) NOT NULL,
  fecha_inicio_inscripcion TIMESTAMP NOT NULL,
  fecha_fin_inscripcion TIMESTAMP NOT NULL,
  fecha_nacimiento_minima DATE NOT NULL,
  plazas INTEGER NOT NULL,
  descripcion_fechas TEXT NOT NULL,
  descripcion_lugar TEXT NOT NULL,
  visible BOOLEAN NOT NULL DEFAULT false,
  coordinador_id int NULL,
  CONSTRAINT pk_curso PRIMARY KEY (id),
  CONSTRAINT fk_curso_coordinadorId_asociado FOREIGN KEY (coordinador_id) REFERENCES asociado(id)
);

create table curso_participante (
  curso_id INTEGER NOT NULL,
  asociado_id INTEGER NOT NULL,
  secuencia_inscripcion SERIAL NOT NULL,
  fecha_inscripcion TIMESTAMP DEFAULT now() NOT NULL,
  CONSTRAINT pk_curso_participante PRIMARY KEY (curso_id, asociado_id),
  CONSTRAINT fk_curso_participante_curso FOREIGN KEY (curso_id) REFERENCES curso(id),
  CONSTRAINT fk_curso_participante_participante FOREIGN KEY (asociado_id) REFERENCES asociado(id)
);

create table curso_formador (
  curso_id INTEGER NOT NULL,
  asociado_id INTEGER NOT NULL,
  CONSTRAINT pk_curso_formador PRIMARY KEY (curso_id, asociado_id),
  CONSTRAINT fk_curso_formador_curso FOREIGN KEY (curso_id) REFERENCES curso(id),
  CONSTRAINT fk_curso_formador_formador FOREIGN KEY (asociado_id) REFERENCES asociado(id)
);

create or replace view dto_miembros_escuela AS
  select
  a.id as id,
  concat_ws(' ', a.nombre, a.apellidos) AS nombre_completo, g.nombre as nombre_grupo,
  c.etiqueta,
  c.id as cargo_id,
  (select count(1) > 0 from cargo_asociado ca where ca.asociado_id = a.id and ca.cargo_id = 37) as mesa_pedagogica,
  coalesce(a.telefono_movil, a.telefono_casa) as telefono, a.email, a.fecha_nacimiento
from asociado a
left join grupo g on g.id = a.grupo_id
inner join cargo_asociado ca on ca.asociado_id = a.id
inner join cargo c on ca.cargo_id = c.id
where c.id in (34, 70, 36);

