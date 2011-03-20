delete from pago;
delete from liquidacion;

-- Generación de liquidación principal para el 2011 (enero)
-- Todos los no pagados del 2011 que no estén dados de baja
insert into liquidacion values (2011, 2, '01/01/2011');
insert into pago select id, 2011 as "ejercicio", asociacion, '01/01/2011' from asociado 
    where asociacion = 2 
    and id not in (select idasociado from pago where ejercicio = 2011) 
    and fechabaja is null
and ramas = 'C' and idgrupo = 'AK' order by id; -- solo para pruebas

-- Liquidación secundaria 2010 (marzo)
-- Altas asociados 313 y 314
insert into liquidacion values (2011, 2, '01/03/2011');
insert into pago select id, 2011 as "ejercicio", asociacion, '01/03/2011' from asociado
    where asociacion = 2
    and id not in (select idasociado from pago where ejercicio = 2011)
    and fechabaja is null
and id in (313, 314);

-- update asociado set fechabaja = CURRENT_TIMESTAMP where id = 315;
-- select fechabaja, * from asociado where ramas = 'C' and idgrupo = 'AK';

-- select * from liquidacion;
-- select * from pago;
select count(idasociado) as "N", count(idasociado) * 15.00 as "importe", fecha from pago group by fecha;

-- Descuentos para el 2011
-- Dados de baja que hayan sido pagados ya