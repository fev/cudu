CREATE DATABASE cudu WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'es_ES' LC_CTYPE = 'es_ES';
ALTER DATABASE cudu OWNER TO postgres;

\connect cudu

CREATE PROCEDURAL LANGUAGE plpgsql;
ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;


CREATE TABLE grupo (
    id character varying(20) NOT NULL,
    nombre varchar(50) NOT NULL,

    calle varchar(300) NOT NULL,
    numero varchar(3) NOT NULL,
    puerta varchar(3),
    escalera varchar(3),
    codigopostal integer NOT NULL,
    idProvincia integer NOT NULL,
    provincia varchar(100) NOT NULL,
    idMunicipio integer NOT NULL,
    municipio varchar(100) NOT NULL,
  
    aniversario date,
    telefono1 varchar(15) NOT NULL,
    telefono2 varchar(15) NULL,
    email varchar(100) NOT NULL,
    web varchar(300),
    entidadpatrocinadora varchar(100),
    asociacion smallint NOT NULL,

    CONSTRAINT pk_grupo PRIMARY KEY (id)
);

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

create table asociado (
    id serial NOT NULL,
    tipo char(1) NOT NULL CONSTRAINT df_asociado_tipo DEFAULT ('J'),
    idGrupo varchar(20) NOT NULL,
    nombre varchar(30) NOT NULL,
    primerApellido varchar(50) NOT NULL,
    segundoApellido varchar(50) NULL,
    sexo char(1) NOT NULL,
    fechaNacimiento date NOT NULL,
    dni varchar(9) NULL,
    seguridadSocial varchar(12) NULL,
    tieneSeguroPrivado boolean NOT NULL CONSTRAINT DF_Asociado_SegPriv DEFAULT (false),

    calle varchar(100) NOT NULL,
    numero varchar(3) NOT NULL,
    escalera varchar(3) NULL,
    puerta varchar(3) NULL,
    codigoPostal int NOT NULL,

    telefonoCasa varchar(15) NULL,
    telefonoMovil varchar(15) NULL,
    email varchar(100) NULL,

    idProvincia smallint NOT NULL,
    provincia varchar(100) NOT NULL,
    idMunicipio int NOT NULL,
    municipio varchar(100) NOT NULL,

    tieneTutorLegal boolean NOT NULL CONSTRAINT df_asociado_tutor DEFAULT (false),
    padre_nombre varchar(250) NULL,
    padre_telefono varchar(15) NULL,
    padre_email varchar(100) NULL,
    madre_nombre varchar(250) NULL,
    madre_telefono varchar(15) NULL,
    madre_email varchar(100) NULL,
    
    fechaAlta timestamp NOT NULL CONSTRAINT df_asociado_fechaalta DEFAULT CURRENT_TIMESTAMP,
    fechaBaja timestamp NULL, -- si !null, está activo, se muestra en listados
    fechaActualizacion timestamp NOT NULL CONSTRAINT df_asociado_fechaactualizacion DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_asociado PRIMARY KEY (id),
    CONSTRAINT fk_asociado_grupo FOREIGN KEY (idGrupo) REFERENCES grupo(id),
    CONSTRAINT ck_enum_asociado_sexo CHECK (sexo IN ('M', 'F')),
    CONSTRAINT ck_enum_asociado_tipo CHECK (tipo IN ('J', 'K', 'C', 'V'))
);

CREATE TABLE asociado_rama (
    idAsociado int NOT NULL,
    rama char(1) NOT NULL,
    CONSTRAINT pk_asociado_rama PRIMARY KEY (idasociado, rama),
    CONSTRAINT fk_asociado_rama_id FOREIGN KEY (idAsociado) 
        REFERENCES asociado(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT ck_enum_asociado_rama_rama CHECK (rama IN ('C', 'M', 'E', 'P', 'R'))
);

-- TRIGGER
-- Un menor (tipo J) no puede estar en más de una rama a la vez
CREATE FUNCTION comprobarCantidadRamasMenor() RETURNS trigger AS $comprobarCantidadRamasMenor$
DECLARE
    existentes int;
    tipo char(1);
BEGIN
    select into tipo a.tipo from asociado a where a.id = new.idasociado;
    
    if tipo <> 'J' then
        return new;
    end if;

    select into existentes COUNT(*)
    from asociado_rama a 
    where a.idasociado = new.idasociado;

    if existentes = 0 then
        return new;
    else
        raise exception 'Un niño no puede estar en más de dos ramas simultáneamente.';
        return null;
    end if;
END;
$comprobarCantidadRamasMenor$ LANGUAGE plpgsql;

CREATE TRIGGER menorSoloEnUnaUnidad
    BEFORE INSERT OR UPDATE ON asociado_rama FOR EACH ROW
    EXECUTE PROCEDURE comprobarCantidadRamasMenor();


-- TRIGGER
-- Auditoría sobre los datos de los asociados
CREATE FUNCTION actualizarDatosAuditoriaAsociado() RETURNS trigger as $actualizarDatosAuditoriaAsociado$
BEGIN
    new.fechaActualizacion = CURRENT_TIMESTAMP;
    return new;
END;
$actualizarDatosAuditoriaAsociado$ LANGUAGE plpgsql;

CREATE TRIGGER auditAsociado
    BEFORE INSERT OR UPDATE ON asociado FOR EACH ROW
    EXECUTE PROCEDURE actualizarDatosAuditoriaAsociado();


-- Datos iniciales
INSERT INTO users VALUES ('cudu', 'cudu', 'Cuenta de Administación', NULL, true);
INSERT INTO authorities VALUES ('cudu', 'ROLE_USER');
INSERT INTO authorities VALUES ('cudu', 'ROLE_ADMIN');


-- Datos de prueba
INSERT INTO grupo (id, nombre, calle, numero, codigoPostal, idProvincia, provincia, idMunicipio, municipio, telefono1, email, asociacion)
VALUES ('AK','Ain-Karen', 'Diputado Lluís Lucia', 21, 46015, 46, 'Valencia', 250, 'Valencia', '656897645', 'ainkaren@gmail.com', 1);

INSERT INTO grupo (id, nombre, calle, numero, codigoPostal, idProvincia, provincia, idMunicipio, municipio, telefono1, email, asociacion)
VALUES ('UP', 'Decimo de la Isla de la Educación', 'Plaza de los Valores', 72, 46073, 46, 'Valencia', 250, 'Valencia', '685643219', 'chanclillas@isladeningunsitio.org', 1);

INSERT INTO pagocuotas (idgrupo, "año", cantidad)
VALUES ('AK', 2009, 690.45);

INSERT INTO users VALUES ('xuano', 'xu', 'Xuano Vidal Tomás', 'AK', true);
INSERT INTO authorities VALUES ('xuano', 'ROLE_USER');

INSERT INTO users VALUES ('baden', 'bp', 'Baden Powell', 'UP', true);
INSERT INTO authorities VALUES ('baden', 'ROLE_USER');

INSERT INTO asociado (tipo, idGrupo, nombre, primerApellido, segundoApellido, sexo, fechaNacimiento, calle, 
numero, codigopostal, idprovincia, provincia, idmunicipio, municipio, telefonoCasa)
VALUES ('J', 'UP', 'Boo', 'Wazowski', 'Sullivan', 'F', '12/03/1997', 'De la Rúe', '3', 46341, 46, 'Valencia', 12, 'Rocafort', '972691732');

INSERT INTO asociado (tipo, idGrupo, nombre, primerApellido, segundoApellido, sexo, fechaNacimiento, calle, 
numero, codigopostal, idprovincia, provincia, idmunicipio, municipio, telefonoCasa)
VALUES ('K', 'UP', 'John', 'Doe', '', 'M', '31/01/1982', '5th ave', '212', 46015, 46, 'Valencia', 250, 'Valencia', '983562345');

INSERT INTO asociado_rama VALUES (1, 'P');
INSERT INTO asociado_rama VALUES (2, 'P');
INSERT INTO asociado_rama VALUES (2, 'R');




