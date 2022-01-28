ALTER TABLE asociado ADD COLUMN documentacion_extrangera varchar (20);
ALTER TABLE asociado ADD CONSTRAINT valor_unico_documentacion_extrangera UNIQUE (documentacion_extrangera);
