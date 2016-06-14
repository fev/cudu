/* drop view liquidacion_balance cascade; */
/* drop view liquidacion_calculo cascade; */
/* drop table liquidacion_asociado cascade; */
/* drop table liquidacion cascade; */
/* drop table if exists ronda; */

create table ronda (
  id smallint NOT NULL,
  etiqueta varchar(9) NOT NULL,
  rango daterange NOT NULL,
  CONSTRAINT pk_ronda PRIMARY KEY (id),
  EXCLUDE USING GIST (rango WITH &&)
);

insert into ronda (id, etiqueta, rango)
select extract(isoyear from lower(rango))::smallint as id, to_char(lower(rango), 'YYYY') || '-' || to_char(upper(rango), 'YYYY') etiqueta, rango
from (
  select daterange(ptr::date, ((ptr + interval '1' year) - interval '1' day)::date) as rango
  from generate_series('2015-09-01'::DATE, '2030-09-01', '1 year') with ordinality as t(ptr, id)) rangos;

create table liquidacion (
  id SERIAL NOT NULL,
  grupo_id character varying(3) NOT NULL,
  ronda_id smallint NOT NULL,
  fecha date NOT NULL DEFAULT CURRENT_TIMESTAMP,
  borrador boolean NOT NULL DEFAULT (true),
  ajuste_manual numeric NULL,
  pagado numeric NOT NULL DEFAULT(0),
  fecha_pago timestamp with time zone NULL,
  precio_unitario numeric NOT NULL,
  creado_por varchar(100) NOT NULL,
  creado_en timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
  observaciones TEXT NULL,
  CONSTRAINT pk_liquidacion PRIMARY KEY (id),
  CONSTRAINT uq_liquidacion UNIQUE (grupo_id, fecha),
  CONSTRAINT fk_liquidacion_ronda FOREIGN KEY (ronda_id) REFERENCES ronda(id)
);

create table liquidacion_asociado (
  liquidacion_id int NOT NULL,
  asociado_id int NOT NULL,
  alta BOOLEAN NOT NULL DEFAULT (true),
  creado_en timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_liquidacion_asociado PRIMARY KEY (liquidacion_id, asociado_id)
);

create or replace view liquidacion_calculo as
  select grupo_id, ronda_id, liquidacion_id, activos,
    activos - coalesce(lag(activos) over w, 0) as diferencia,
    subtotal - coalesce(lag(subtotal) over w, 0) as subtotal
  from (
    select grupo_id, ronda_id, liquidacion_id,
      count(*) activos,
      sum(precio_unitario) as subtotal
    from liquidacion l
    inner join liquidacion_asociado a on a.liquidacion_id = l.id
    where l.borrador = false
    group by grupo_id, ronda_id, liquidacion_id
    order by liquidacion_id
  ) t
  window w as (partition by grupo_id, ronda_id order by liquidacion_id);

create or replace view liquidacion_balance as
  select
    l.grupo_id,
    l.ronda_id,
    l.id as liquidacion_id,
    c.activos,
    c.diferencia,
    l.precio_unitario,
    c.subtotal,
    l.ajuste_manual,
    l.pagado,
    l.pagado - coalesce(l.ajuste_manual, c.subtotal) as balance
  from liquidacion l
  left join liquidacion_calculo c on l.id = c.liquidacion_id
  order by ronda_id desc, grupo_id, liquidacion_id;

-- last y last_agg son para obtener el último elemento de un agregado
-- mantener el order by en liquidacion_balance para que funcione correctamente
create or replace function public.last_agg (anyelement, anyelement)
returns anyelement language sql immutable strict as $$
  select $2;
$$;

create aggregate public.last (
  sfunc    = public.last_agg,
  basetype = anyelement,
  stype    = anyelement
);

create or replace view liquidacion_grupos AS
  select
    gr.ronda_etiqueta,
    gr.ronda_id,
    gr.id as grupo_id,
    gr.nombre,
    gc.activos,
    gr.asociacion,
    bg.balance,
    coalesce(bg.num_liquidaciones, 0) as num_liquidaciones,
    bg.activos_ultima
  from (
    select r.id as ronda_id, r.etiqueta as ronda_etiqueta, g.*
    from grupo g cross join (select * from ronda order by id limit 5) r
  ) gr
  inner join (
    select grupo_id, count(activo) as activos from asociado a where grupo_id is not null and a.activo = true group by grupo_id
  ) gc on gc.grupo_id = gr.id
  left join (
      select grupo_id, ronda_id, sum(balance) as balance, count(c.liquidacion_id) as num_liquidaciones, last(c.activos) activos_ultima
      from liquidacion_balance c
      group by grupo_id, ronda_id
  ) bg on bg.grupo_id = gr.id and bg.ronda_id = gr.ronda_id
