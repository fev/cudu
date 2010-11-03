CREATE DATABASE cudu WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'es_ES' LC_CTYPE = 'es_ES';
ALTER DATABASE cudu OWNER TO postgres;

\connect cudu

CREATE PROCEDURAL LANGUAGE plpgsql;
ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;


CREATE TABLE grupo (
    id character varying(20) NOT NULL,
    nombre varchar(50) NOT NULL,

    direccion varchar(300) NOT NULL DEFAULT('(desconocida)'),
    codigopostal integer NOT NULL,
    idProvincia integer NOT NULL DEFAULT(0),
    provincia varchar(100) NOT NULL,
    idMunicipio integer NOT NULL DEFAULT(0),
    municipio varchar(100) NOT NULL,
  
    aniversario date,
    telefono1 varchar(15) NOT NULL DEFAULT('(desconocido)'),
    telefono2 varchar(15) NULL,
    email varchar(100) NOT NULL,
    web varchar(300),
    entidadpatrocinadora varchar(100),
    asociacion smallint NOT NULL,
    
    jpa_version int NOT NULL DEFAULT(0),

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
    CONSTRAINT pk_authorities PRIMARY KEY (username, authority),
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
);    

CREATE TABLE pagocuotas (
    idgrupo character varying(20) NOT NULL,
    "año" integer NOT NULL,
    cantidad numeric(7,2) NOT NULL,
    fechapago date NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notas varchar(250) NULL,
    jpa_version int NOT NULL DEFAULT(0),
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
    dni varchar(10) NULL,
    seguridadSocial varchar(12) NULL,
    tieneSeguroPrivado boolean NOT NULL CONSTRAINT DF_Asociado_SegPriv DEFAULT (false),

    calle varchar(100) NOT NULL,
    numero varchar(5) NOT NULL,
    escalera varchar(3) NULL,
    puerta varchar(3) NULL,
    codigoPostal int NOT NULL,

    telefonoCasa varchar(15) NULL,
    telefonoMovil varchar(15) NULL,
    email varchar(100) NULL,

    idProvincia smallint NOT NULL CONSTRAINT df_asociado_idprovincia DEFAULT (0),
    provincia varchar(100) NOT NULL,
    idMunicipio int NOT NULL CONSTRAINT df_asociado_idmunicipio DEFAULT (0),
    municipio varchar(100) NOT NULL,

    tieneTutorLegal boolean NOT NULL CONSTRAINT df_asociado_tutor DEFAULT (false),
    padresDivorciados boolean NOT NULL CONSTRAINT df_asociado_divorcio DEFAULT (false),
    padre_nombre varchar(250) NULL,
    padre_telefono varchar(15) NULL,
    padre_email varchar(100) NULL,
    madre_nombre varchar(250) NULL,
    madre_telefono varchar(15) NULL,
    madre_email varchar(100) NULL,
    
    fechaAlta timestamp NOT NULL CONSTRAINT df_asociado_fechaalta DEFAULT CURRENT_TIMESTAMP,
    fechaBaja timestamp NULL, -- si !null, está activo, se muestra en listados
    fechaActualizacion timestamp NOT NULL CONSTRAINT df_asociado_fechaactualizacion DEFAULT CURRENT_TIMESTAMP,

    -- Códigos de las ramas separados por comas, para permitir la importación
    -- Considerar utilizar para los listados de asociados.
    ramas varchar(10) NULL DEFAULT(''),
    
    rama_colonia boolean NOT NULL CONSTRAINT df_asociado_rama_colonia DEFAULT (false),
    rama_manada boolean NOT NULL CONSTRAINT df_asociado_rama_manada DEFAULT (false),
    rama_exploradores boolean NOT NULL CONSTRAINT df_asociado_rama_exploradores DEFAULT (false),
    rama_pioneros boolean NOT NULL CONSTRAINT df_asociado_rama_pioneros DEFAULT (false),
    rama_rutas boolean NOT NULL CONSTRAINT df_asociado_rama_rutas DEFAULT (false),
    
    jpa_version int NOT NULL DEFAULT(0),

    CONSTRAINT pk_asociado PRIMARY KEY (id),
    CONSTRAINT fk_asociado_grupo FOREIGN KEY (idGrupo) REFERENCES grupo(id),
    CONSTRAINT ck_enum_asociado_sexo CHECK (sexo IN ('M', 'F')),
    CONSTRAINT ck_enum_asociado_tipo CHECK (tipo IN ('J', 'K', 'C', 'V'))
);

-- TRIGGER
-- Un menor (tipo J) no puede estar en más de una rama a la vez
CREATE OR REPLACE FUNCTION comprobarCantidadRamasMenor() RETURNS trigger AS $comprobarCantidadRamasMenor$
DECLARE
    existentes int;
BEGIN
    if new.tipo <> 'J' then
        return new;
    end if;

    existentes := 0;
    if new.rama_colonia = true then existentes := existentes + 1; end if;
    if new.rama_manada = true then existentes := existentes + 1; end if;
    if new.rama_exploradores = true then existentes := existentes + 1; end if;
    if new.rama_pioneros = true then existentes := existentes + 1; end if;
    if new.rama_rutas = true then existentes := existentes + 1; end if;
    
    if existentes > 1 then
        raise exception 'Un niño no puede estar en más de dos ramas simultáneamente.';
        return null;
    else
        return new;
    end if;
END;
$comprobarCantidadRamasMenor$ LANGUAGE plpgsql;

CREATE TRIGGER menorSoloEnUnaUnidad
    BEFORE INSERT OR UPDATE ON asociado FOR EACH ROW
    EXECUTE PROCEDURE comprobarCantidadRamasMenor();


-- TRIGGER
-- Auditoría sobre los datos de los asociados
-- Considerar el uso de reglas http://www.postgresql.org/docs/8.4/interactive/rules-update.html#AEN46440
CREATE FUNCTION actualizarDatosAuditoriaAsociado() RETURNS trigger as $actualizarDatosAuditoriaAsociado$
BEGIN
    new.fechaActualizacion = CURRENT_TIMESTAMP;
    return new;
END;
$actualizarDatosAuditoriaAsociado$ LANGUAGE plpgsql;

CREATE TRIGGER auditAsociado
    BEFORE INSERT OR UPDATE ON asociado FOR EACH ROW
    EXECUTE PROCEDURE actualizarDatosAuditoriaAsociado();

-- Índices
CREATE UNIQUE INDEX ix_authorities ON authorities (username,authority);

-- Datos iniciales
INSERT INTO users VALUES ('cudu', 'cudu', 'Cuenta de Administación', NULL, true);
INSERT INTO authorities VALUES ('cudu', 'ROLE_USER');
INSERT INTO authorities VALUES ('cudu', 'ROLE_ADMIN');




