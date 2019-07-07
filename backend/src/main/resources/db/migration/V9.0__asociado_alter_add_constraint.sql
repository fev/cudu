ALTER TABLE asociado ADD CONSTRAINT email_notnull_if_voluntarios CHECK (
  NOT((email_contacto IS NULL AND NOT (tipo = 'J')))
) NOT VALID
