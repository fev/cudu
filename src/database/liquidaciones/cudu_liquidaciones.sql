-- Patch para la gestión de liquidaciones
-- 2011-01-02

drop view liq_resumen;
drop view liq_detalle_grupo;
drop table liquidacion;

create table liquidacion (
	-- id serial NOT NULL,
	ejercicio smallint NOT NULL,
	fecha date NOT NULL DEFAULT CURRENT_TIMESTAMP,
	idasociado integer NOT NULL,
	caracter char(1) NOT NULL CONSTRAINT df_liquidacion_caracter DEFAULT ('A'),
	asociacion smallint NOT NULL,

	borrador boolean NOT NULL CONSTRAINT df_liquidacion_borrador DEFAULT (true),

    -- auditoria_usuario varchar(200) NOT NULL,
	-- auditoria_tiempo timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	CONSTRAINT pk_liquidacion PRIMARY KEY (ejercicio, fecha, idasociado),
    -- CONSTRAINT pk_liquidacion PRIMARY KEY (id),
	-- CONSTRAINT uq_liquidacion UNIQUE (ejercicio, fecha, idasociado),
	CONSTRAINT fk_liquidacion_asociado FOREIGN KEY (idasociado) REFERENCES asociado (id),
    CONSTRAINT ck_enum_liquidacion_caracter CHECK (caracter IN ('A', 'B'))
);

-- TODO Revisar Índice, interesa por Id + Asociación
-- CREATE UNIQUE INDEX ix_liq_asociacion ON liquidacion (asociacion);

create or replace view liq_resumen as
with  
  altas as (select fecha, asociacion, count(caracter) as "na" from liquidacion where caracter = 'A' group by fecha, asociacion),
  bajas as (select fecha, asociacion, count(caracter) as "nb" from liquidacion where caracter = 'B' group by fecha, asociacion)
select distinct Q.ejercicio, Q.fecha, coalesce(A.na, 0) as "altas", coalesce(B.nb, 0) as "bajas", Q.asociacion
from liquidacion Q
left join altas A on A.fecha = Q.fecha and A.asociacion = Q.asociacion
left join bajas B on B.fecha = Q.fecha and B.asociacion = Q.asociacion
order by asociacion, fecha desc;

create or replace view liq_detalle_grupo
as
select l.ejercicio, l.fecha, g.nombre, l.caracter, count(*) as "cantidad", l.asociacion from liquidacion l
inner join asociado a on a.id = l.idasociado
inner join grupo g on a.idgrupo = g.id
group by l.ejercicio, l.fecha, g.nombre, l.caracter, l.asociacion;
