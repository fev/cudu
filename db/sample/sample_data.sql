\connect cudu

-- Datos de prueba
INSERT INTO grupo (id, nombre, calle, numero, codigoPostal, idProvincia, provincia, idMunicipio, municipio, telefono1, email, asociacion)
VALUES ('UP', 'Decimo de la Isla de la Educación', 'Plaza de los Valores', 72, 46073, 46, 'Valencia', 250, 'Valencia', '685643219', 'chanclillas@isladeningunsitio.org', 1);

INSERT INTO pagocuotas (idgrupo, "año", cantidad)
VALUES ('UP', 2009, 690.45);

INSERT INTO users VALUES ('baden', 'bp', 'Baden Powell', 'UP', true);
INSERT INTO authorities VALUES ('baden', 'ROLE_USER');

