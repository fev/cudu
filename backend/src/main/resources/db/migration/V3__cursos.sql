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
  c.id as cargo_id,
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

create or replace view dto_miembros_curso as
select
  ac.curso_id,
  a.id as id,
  ac.tipo as tipo_miembro,
  ac.secuencia_inscripcion,
  a.tipo,
  concat_ws(' ', a.nombre, a.apellidos) as nombre_completo,
  g.nombre as nombre_grupo,
  coalesce(a.telefono_movil, a.telefono_casa) as telefono,
  a.email,
  a.fecha_nacimiento
from asociado a
inner join (
  (select f.curso_id, 'F' as tipo, id, 0 as secuencia_inscripcion from asociado a inner join curso_formador f on a.id = f.asociado_id)
  union
  (select p.curso_id, 'P' as tipo, id, p.secuencia_inscripcion from asociado a inner join curso_participante p on a.id = p.asociado_id)
) ac on a.id = ac.id
left join grupo g on a.grupo_id = g.id
order by ac.curso_id, ac.secuencia_inscripcion;

