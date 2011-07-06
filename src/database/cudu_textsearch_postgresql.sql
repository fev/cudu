/* Clean up -------------------------------------------------
DROP INDEX idx_busqueda_asociado;
DROP TRIGGER actualizarIndiceBusquedaAsociado ON asociado;
ALTER TABLE asociado DROP COLUMN textsearchindex;
*/


/* Query de ejemplo -----------------------------------------
select id, nombre, primerapellido, textsearchindex, padre_nombre, madre_nombre from asociado
where textsearchindex @@ to_tsquery('valentine');
*/

ALTER TABLE asociado ADD COLUMN textsearchindex tsvector;

UPDATE asociado SET textsearchindex =
    to_tsvector('spanish', nombre || ' ' || primerapellido || coalesce(segundoapellido, '') 
                           || municipio || coalesce(padre_nombre, '') || coalesce(madre_nombre, '') );

CREATE INDEX idx_busqueda_asociado ON asociado USING gin(textsearchindex);

CREATE TRIGGER actualizarIndiceBusquedaAsociado BEFORE INSERT OR UPDATE
ON asociado FOR EACH ROW EXECUTE PROCEDURE
tsvector_update_trigger(textsearchindex, 'pg_catalog.spanish', nombre, primerapellido, segundoapellido);


