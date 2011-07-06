-- Patch para incluir el filtro de asociaciones dentro de la tabla
-- de asociado, en lugar de provocar un join con grupo en cada consulta.
-- git commit 6f065a4ba3ac3f760d0da57b18c0157337a2562a

alter table asociado add column asociacion smallint; -- NOT NULL;
update asociado set asociacion = 0 where idgrupo in (select id from grupo where asociacion = 0);
update asociado set asociacion = 1 where idgrupo in (select id from grupo where asociacion = 1);
update asociado set asociacion = 2 where idgrupo in (select id from grupo where asociacion = 2);
alter table asociado alter column asociacion set not null;

-- TRIGGER
-- Optimización para evitar el join al filtrar asociados por asociación
-- a través del grupo al que pertenece (from asociacion inner join grupo g on a.idgrupo = g.id)
CREATE FUNCTION actualizarFiltroAsociacion() RETURNS trigger as $actualizarFiltroAsociacion$
BEGIN
    -- RAISE WARNING 'actualizarFiltroAsociacion %', new.id;
	-- tail -f /Library/PostgreSQL/8.4/data/pg_log/postgresql-2010-11-18_194723.log
	new.asociacion = (SELECT asociacion from grupo where id = new.idgrupo limit 1);
	return new;
END;
$actualizarFiltroAsociacion$ LANGUAGE plpgsql;

CREATE FUNCTION actualizarFiltroAsociacionGrupo() RETURNS trigger as $actualizarFiltroAsociacionGrupo$
BEGIN
	UPDATE asociado SET asociacion = new.asociacion WHERE asociado.idgrupo = new.id;
	return new;
END;
$actualizarFiltroAsociacionGrupo$ LANGUAGE plpgsql;

-- Al crear el asociado, actualizar el grupo
CREATE TRIGGER actualizarAsociacion
	BEFORE INSERT ON asociado FOR EACH ROW
	EXECUTE PROCEDURE actualizarFiltroAsociacion();

-- Al actualizar el grupo, actualizar asociados
CREATE TRIGGER actualizarAsociacionGrupo
	AFTER UPDATE ON grupo FOR EACH ROW
	EXECUTE PROCEDURE actualizarFiltroAsociacionGrupo();

