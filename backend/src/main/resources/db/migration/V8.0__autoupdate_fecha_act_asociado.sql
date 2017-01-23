CREATE OR REPLACE FUNCTION nueva_fecha_actualizacion()	
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_actualizacion = now();
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER actualizar_fecha_asociado 
BEFORE UPDATE ON asociado FOR EACH ROW 
EXECUTE PROCEDURE nueva_fecha_actualizacion();
