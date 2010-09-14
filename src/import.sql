-- Datos iniciales - MYSQL
INSERT INTO grupo (id, nombre, calle, numero, codigoPostal, idProvincia, provincia, idMunicipio, municipio, telefono1, email, asociacion, jpa_version) VALUES ('UP', 'Decimo de la Isla de la Educación', 'Plaza de los Valores', 72, 46073, 46, 'Valencia', 250, 'Valencia', '685643219', 'chanclillas@isladeningunsitio.org', 1, 0);

insert into users (username, enabled, nombreCompleto, password, idGrupo) values ('cudu', true, 'Cuenta de Administrador', 'cudu', 'UP');
insert into authorities (authority, username) values ('ROLE_ADMIN', 'cudu'), ('ROLE_USER', 'cudu');

INSERT INTO users (username, password, nombreCompleto, idGrupo, enabled ) VALUES ('baden', 'bp', 'Baden Powell', 'UP', true);
INSERT INTO authorities (username, authority) VALUES ('baden', 'ROLE_USER');

---- Demo data - Borrar ----


