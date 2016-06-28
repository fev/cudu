drop view if exists valores_federativos;
drop view if exists valores_por_asociacion;
drop view if exists valores_por_liquidacion;

create or replace view valores_por_liquidacion as
select g.nombre as ambito, 30 as especificidad, la.*
from (
  select
    sum(case when tipo = 'J' then 1 else 0 end) as joven,
    sum(case when tipo = 'K' then 1 else 0 end) as kraal,
    sum(case when tipo = 'C' then 1 else 0 end) as comite,
    sum(case when rama_colonia then 1 else 0 end)      as colonia,
    sum(case when rama_manada then 1 else 0 end)       as manada,
    sum(case when rama_exploradores then 1 else 0 end) as exploradores,
    sum(case when rama_expedicion then 1 else 0 end)   as expedicion,
    sum(case when rama_ruta then 1 else 0 end)         as ruta,
    count(*) as total,
    liquidacion_id
  from liquidacion_asociado la
  inner join liquidacion l on l.id = la.liquidacion_id
  group by liquidacion_id) la
inner join liquidacion l on la.liquidacion_id = l.id
inner join grupo g on l.grupo_id = g.id;

create or replace view valores_por_asociacion as
select
  case
    when g.asociacion = 0 then 'SdC'
    when g.asociacion = 1 then 'SdA'
    when g.asociacion = 2 then 'MEV'
  end::text as ambito,
  20                              as especificidad,
  sum(case when tipo = 'J' then 1 else 0 end) as joven,
  sum(case when tipo = 'K' then 1 else 0 end) as kraal,
  sum(case when tipo = 'C' then 1 else 0 end) as comite,
  sum(case when rama_colonia then 1 else 0 end)      as colonia,
  sum(case when rama_manada then 1 else 0 end)       as manada,
  sum(case when rama_exploradores then 1 else 0 end) as exploradores,
  sum(case when rama_expedicion then 1 else 0 end)   as expedicion,
  sum(case when rama_ruta then 1 else 0 end)         as ruta,
  count(a.id)                     as total,
  g.asociacion                    as asociacionId
from asociado a
inner join grupo g on a.grupo_id = g.id
where a.activo = true and g.id not in ('UP')
group by g.asociacion;

create or replace view valores_federativos as
select
  'FEV'::text                  as ambito,
  10                              as especificidad,
  sum(case when tipo = 'J' then 1 else 0 end) as joven,
  sum(case when tipo = 'K' then 1 else 0 end) as kraal,
  sum(case when tipo = 'C' then 1 else 0 end) as comite,
  sum(case when rama_colonia then 1 else 0 end)      as colonia,
  sum(case when rama_manada then 1 else 0 end)       as manada,
  sum(case when rama_exploradores then 1 else 0 end) as exploradores,
  sum(case when rama_expedicion then 1 else 0 end)   as expedicion,
  sum(case when rama_ruta then 1 else 0 end)         as ruta,
  count(a.id)                     as total
from asociado a
inner join grupo g on a.grupo_id = g.id
where a.activo = true and g.id not in ('UP');

