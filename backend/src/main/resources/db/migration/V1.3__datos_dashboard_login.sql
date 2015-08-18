CREATE OR REPLACE VIEW dto_datos_rama_tipo
AS
  SELECT
    tipo = 'J'                    AS es_joven,
    sum(rama_colonia :: INT)      AS colonia,
    sum(rama_manada :: INT)       AS manada,
    sum(rama_exploradores :: INT) AS exploradores,
    sum(rama_expedicion :: INT)   AS expedicion,
    sum(rama_ruta :: INT)         AS ruta,
    count(id)                     AS total
  FROM asociado a
  WHERE a.activo = TRUE AND grupo_id <> 'UP'
  GROUP BY tipo = 'J';

CREATE OR REPLACE VIEW dto_datos_periodo_tipo
AS
  SELECT
    extract(YEAR FROM CURRENT_TIMESTAMP) :: INT AS periodo,
    coalesce(sum((tipo =  'J') :: INT), 0)      AS jovenes,
    coalesce(sum((tipo <> 'J') :: INT), 0)      AS voluntarios,
    count(1)                                    AS total
  FROM asociado
  WHERE activo = TRUE AND grupo_id <> 'UP'
union
select 2005 as periodo, 4004 as joven, 893  as voluntario, 4897 as total union
select 2006 as periodo, 3493 as joven, 809  as voluntario, 4302 as total union
select 2007 as periodo, 3409 as joven, 758  as voluntario, 4167 as total union
select 2008 as periodo, 3825 as joven, 1057 as voluntario, 4882 as total union
select 2009 as periodo, 3656 as joven, 953  as voluntario, 4609 as total union
select 2010 as periodo, 4097 as joven, 1048 as voluntario, 5145 as total union
select 2011 as periodo, 4172 as joven, 1013 as voluntario, 5185 as total union
select 2012 as periodo, 4276 as joven, 1067 as voluntario, 5343 as total union
select 2013 as periodo, 4509 as joven, 1134 as voluntario, 5643 as total union
select 2014 as periodo, 4880 as joven, 1222 as voluntario, 6102 as total
order by periodo;

CREATE OR REPLACE VIEW dto_datos_grupo_rama
AS
  SELECT
    a.grupo_id,
    sum(rama_colonia :: INT)      AS colonia,
    sum(rama_manada :: INT)       AS manada,
    sum(rama_exploradores :: INT) AS exploradores,
    sum(rama_expedicion :: INT)   AS expedicion,
    sum(rama_ruta :: INT)         AS ruta
  FROM asociado a
  WHERE a.activo = TRUE AND grupo_id IS NOT NULL
  GROUP BY a.grupo_id;

CREATE OR REPLACE VIEW dto_datos_grupo_tipo
AS
  SELECT
    a.grupo_id,
    sum((tipo = 'J') :: INT) AS joven,
    sum((tipo = 'K') :: INT) AS kraal,
    sum((tipo = 'C') :: INT) AS comite
  FROM asociado a
  WHERE a.activo = TRUE AND grupo_id IS NOT NULL  AND tipo IN ('J', 'K', 'C')
  GROUP BY a.grupo_id;