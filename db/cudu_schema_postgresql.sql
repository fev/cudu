CREATE TABLE grupo (
  id character varying(20) NOT NULL,
  nombre varchar(50) NOT NULL,
  
  calle varchar(300) NOT NULL,
  numero integer NOT NULL,
  puerta integer,
  escalera varchar(2),
  codigopostal integer NOT NULL,
  idprovincia integer NOT NULL,
  idmunicipio integer NOT NULL,
  
  aniversario date,
  telefono integer,
  movil integer,
  mail varchar(100) NOT NULL,
  web varchar(300),
  entidadpatrocinadora character varying(100),
  asociacion smallint NOT NULL,

  CONSTRAINT pk_grupo PRIMARY KEY (id),
  CONSTRAINT grupo_asociacion_check CHECK (asociacion >= 0),
  CONSTRAINT grupo_codigopostal_check CHECK (codigopostal >= 0),
  CONSTRAINT grupo_movil_check CHECK (movil >= 0),
  CONSTRAINT grupo_numero_check CHECK (numero >= 0),
  CONSTRAINT grupo_puerta_check CHECK (puerta >= 0),
  CONSTRAINT grupo_telefono_check CHECK (telefono >= 0)
);

CREATE TABLE pagocuotas (
  idgrupo character varying(20) NOT NULL,
  "año" integer NOT NULL,
  cantidad numeric(7,2) NOT NULL,
  fechapago date NOT NULL DEFAULT CURRENT_TIMESTAMP,
  notas varchar(250) NULL,
  CONSTRAINT pk_pagocuotas PRIMARY KEY (idgrupo, "año"),
  CONSTRAINT fk_pagocuotas_grupo FOREIGN KEY (idgrupo)
      REFERENCES grupo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "pagocuotas_año_check" CHECK ("año" >= 0)
);


-- Datos de prueba
insert into grupo (id, nombre, calle, numero, codigoPostal, idProvincia, idMunicipio, mail, asociacion)
values ('AK','Ain-Karen', 'Diputado Lluís Lucia', 21, 46015, 46, 250, 'ainkaren@gmail.com', 1);

insert into pagocuotas (idgrupo, "año", cantidad)
values ('AK', 2009, 690.45);

