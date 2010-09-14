-- Datos iniciales - MYSQL
insert into users (username, enabled, fullname, password) values ('cudu', true, 'Cuenta de Administrador', 'cudu');
insert into authorities (authority, username) values ('ROLE_ADMIN', 'cudu'), ('ROLE_USER', 'cudu');

