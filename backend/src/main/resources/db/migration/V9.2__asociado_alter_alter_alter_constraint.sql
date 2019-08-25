ALTER TABLE asociado DROP CONSTRAINT email_notnull_if_voluntarios;

ALTER TABLE asociado ADD CONSTRAINT email_notnull_if_voluntarios_activos CHECK (
  NOT((email_contacto IS NULL OR email_contacto = '') AND (tipo = 'K' OR tipo = 'C') AND activo = TRUE)
) NOT VALID;
