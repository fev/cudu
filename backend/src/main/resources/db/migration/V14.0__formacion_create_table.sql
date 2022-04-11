 CREATE TABLE formacion_tl(
     id SERIAL PRIMARY KEY NOT NULL,
     titulo VARCHAR(50),
     centro_formativo VARCHAR(50),
     fecha_inicio DATE,
     fecha_final DATE,
     CONSTRAINT fk_formacion_tl_asociado FOREIGN KEY(id) REFERENCES asociado(id)
 );
 