drop table authorities;
drop table users;

CREATE TABLE users (
   username varchar(200) NOT NULL,
   password varchar(200) NOT NULL,   
   fullname varchar(200) NOT NULL,
   idGrupo character varying(20) NULL,   
   enabled boolean NOT NULL,
   CONSTRAINT pk_users PRIMARY KEY (username),
   CONSTRAINT pk_users_grupo FOREIGN KEY (idGrupo) REFERENCES Grupo(id) ON UPDATE CASCADE
);

CREATE TABLE authorities (
   username varchar(200) NOT NULL,
   authority varchar(50) NOT NULL,
   CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users VALUES ('cudu', 'cudu', 'Cuenta de Administación', NULL, true);
INSERT INTO users VALUES ('xuano', 'xu', 'Xuano Vidal Tomás', 'AK', true);

INSERT INTO authorities VALUES ('cudu', 'ROLE_USER');
INSERT INTO authorities VALUES ('cudu', 'ROLE_ADMIN');
INSERT INTO authorities VALUES ('xuano', 'ROLE_USER');

