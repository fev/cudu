INSERT INTO cargo (id, etiqueta, ambito, unico, puntos)
SELECT 70, 'fev.colaborador_lluerna', 'F', false, 3
WHERE NOT EXISTS (SELECT * FROM cargo WHERE id = 70);
