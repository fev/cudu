-- drop type tipoAsociado;
-- drop type sexo;
-- drop type rama;

-- create type sexo as ENUM ('F', 'M');
-- create type rama as ENUM ('C', 'M', 'U', 'E', 'R');
-- create type tipoAsociado as ENUM ('J', 'K', 'C', 'V');

drop trigger menorSoloEnUnaUnidad on asociado_rama;
drop function comprobarCantidadRamasMenor();
drop table asociado_rama;
drop table asociado;

create table asociado (
    id serial NOT NULL,
    tipo tipoAsociado NOT NULL CONSTRAINT df_asociado_tipo DEFAULT ('J'),
    idGrupo varchar(20) NOT NULL,
    nombre varchar(30) NOT NULL,
    primerApellido varchar(50) NOT NULL,
    segundoApellido varchar(50) NOT NULL,
    sexo sexo NOT NULL,
    fechaNacimiento date NOT NULL,
    dni varchar(9) NULL,
    seguridadSocial varchar(12) NULL, -- son 13?? 46 10504687 15T
    tieneSeguroPrivado boolean NOT NULL CONSTRAINT DF_Asociado_SegPriv DEFAULT (false),

    calle varchar(100) NOT NULL,
    numero varchar(3) NOT NULL,
    escalera varchar(3) NULL,
    puerta varchar(3) NULL,
    codigoPostal int NOT NULL,

    /* Necesidades, IDEA se mantiene un par de datos que permite tanto:
    - Actualizaciones masivas de datos (update idprovincia...)
    - Preservar integridad documental (en origen se especificó X)
    */

    IdProvincia smallint NOT NULL,    -- se especifica como fk para actualizaciones masivas
    Provincia varchar(100) NOT NULL, -- si cambia id en acc. de bbdd, se mantiene esta
    IdMunicipio int NOT NULL,
    Municipio varchar(100) NOT NULL,

    -- IDEA 1 podría hacerse como en google, que añaden los que quieren con el icono al lado
    --        ¿cómo los nombra nokia o google?
    -- IDEA 2 check a la derecha para marcar el predeterminado, para listados
    -- IDEA 3 nombrar como telefono 1, telefono 2 o "telefono principal"
    telefonoCasa varchar(15) NULL,
    telefonoMovil varchar(15) NULL,

    email varchar(100) NULL,

    -- Casilla "Tiene tutor(es) legales", que al marcarla quite la leyenda
    -- de "Datos del padre" o "Datos de la madre" y cambiar por "Tutor 1", "Tutor 2"
    -- Campo "Información adicional"
    tieneTutorLegal boolean NOT NULL CONSTRAINT df_asociado_tutor DEFAULT (false),
    padre_nombre varchar(250) NULL,
    padre_telefono varchar(15) NULL,
    padre_email varchar(100) NULL,

    madre_nombre varchar(250) NULL,
    madre_telefono varchar(15) NULL,
    madre_email varchar(100) NULL,
    
    fechaAlta timestamp NOT NULL CONSTRAINT df_asociado_fechaalta DEFAULT CURRENT_DATE,
    fechaBaja timestamp NULL, -- si !null, está activo, se muestra en listados

    -- performance opt
    -- rama rama NULL, 
    -- check (this.rama != null) && (asociado.rama.Count = 1)
    
    CONSTRAINT pk_asociado PRIMARY KEY (id),
    CONSTRAINT fk_asociado_grupo FOREIGN KEY (idGrupo) REFERENCES grupo(id)
);

CREATE TABLE asociado_rama (
    idAsociado int NOT NULL,
    rama rama NOT NULL,
    CONSTRAINT pk_asociado_rama PRIMARY KEY (idasociado, rama),
    CONSTRAINT fk_asociado_rama_id FOREIGN KEY (idAsociado) 
        REFERENCES asociado(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Un menor (tipo J) no puede estar en más de una rama a la vez
CREATE FUNCTION comprobarCantidadRamasMenor() RETURNS trigger AS $comprobarCantidadRamasMenor$
DECLARE
    existentes int;
    tipo tipoAsociado;
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


