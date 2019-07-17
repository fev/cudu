ALTER TABLE asociado DROP CONSTRAINT email_notnull_if_voluntarios;

ALTER TABLE asociado ADD CONSTRAINT email_notnull_if_voluntarios CHECK (
  NOT((email_contacto IS NULL AND (tipo = 'K' OR tipo = 'C')))
) NOT VALID;
