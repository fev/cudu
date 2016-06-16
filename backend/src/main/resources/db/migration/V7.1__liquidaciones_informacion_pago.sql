
create table informacion_pago (
  asociacionId integer NOT NULL,
  titular varchar(50) NOT NULL,
  iban varchar(29) NOT NULL,
  precioPorAsociado numeric NOT NULL,
  CONSTRAINT pk_informacion_pago PRIMARY KEY (asociacionId)
);

insert into informacion_pago (asociacionId, titular, iban, precioPorAsociado) values
  (0, 'Scouts de Alicante', 'ES00 0000 0000 0000 0000 0000', 5),
  (1, 'Scouts de Castelló', 'ES00 0000 0000 0000 0000 0000', 3),
  (2, 'Moviment Escolta de València', 'ES00 0000 0000 0000 0000 0000', 5);
