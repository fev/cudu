CREATE TABLE grupo (
    id varchar(20) NOT NULL,
    nombre varchar(50) NOT NULL,
    codigo_postal integer NOT NULL,
    municipio varchar(100) NOT NULL,
    aniversario date,
    telefono1 varchar(15),
    telefono2 varchar(15),
    email varchar(100),
    web varchar(300),
    entidad_patrocinadora varchar(100),
    asociacion integer NOT NULL,
    direccion varchar(300) DEFAULT '(desconocida)' NOT NULL,
    CONSTRAINT pk_grupo PRIMARY KEY (id)
);

CREATE TABLE asociado (
    id integer NOT NULL,
    tipo char(1) NOT NULL,
    grupo_id varchar(3),
    nombre varchar(30) NOT NULL,
    apellidos varchar(100) NOT NULL,
    sexo char(1),
    fecha_nacimiento date NOT NULL,
    dni varchar(10),
    seguridad_social varchar(12),
    tiene_seguro_privado boolean DEFAULT false NOT NULL,
    direccion varchar(100),
    codigo_postal integer,
    telefono_casa varchar(15),
    telefono_movil varchar(15),
    email varchar(100),
    municipio varchar(100),
    tiene_tutor_legal boolean DEFAULT false NOT NULL,
    padres_divorciados boolean DEFAULT false NOT NULL,
    padre_nombre varchar(250),
    padre_telefono varchar(15),
    padre_email varchar(100),
    madre_nombre varchar(250),
    madre_telefono varchar(15),
    madre_email varchar(100),
    fecha_alta timestamp without time zone DEFAULT now() NOT NULL,
    fecha_baja timestamp without time zone,
    fecha_actualizacion timestamp without time zone DEFAULT now() NOT NULL,
    rama_colonia boolean DEFAULT false NOT NULL,
    rama_manada boolean DEFAULT false NOT NULL,
    rama_exploradores boolean DEFAULT false NOT NULL,
    rama_expedicion boolean DEFAULT false NOT NULL,
    rama_ruta boolean DEFAULT false NOT NULL,
    activo boolean NOT NULL,
    usuario_activo boolean DEFAULT false NOT NULL,
    password varchar(255),
    requiere_captcha boolean DEFAULT false NOT NULL,
    lenguaje varchar(3),
    ambito_edicion char(1) NOT NULL,
    restriccion_asociacion integer,
    no_puede_editar_datos_del_grupo boolean DEFAULT false NOT NULL,
    no_puede_editar_otras_ramas boolean DEFAULT false NOT NULL,
    solo_lectura boolean DEFAULT false NOT NULL,
    estudios varchar(128),
    profesion varchar(128),
    hermanos_en_el_grupo boolean DEFAULT false NOT NULL,
    fecha_usuario_creado timestamp without time zone,
    fecha_usuario_visto timestamp without time zone,
    usuario_creado_por_id integer,
    usuario_creado_por_nombre varchar(130),
    notas text,
    email_contacto varchar(100),
    CONSTRAINT pk_asociado PRIMARY KEY (id),
    CONSTRAINT fk_asociado_grupo FOREIGN KEY (grupo_id) REFERENCES grupo(id)
);

CREATE SEQUENCE asociado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE asociado_id_seq OWNED BY asociado.id;
ALTER TABLE ONLY asociado ALTER COLUMN id SET DEFAULT nextval('asociado_id_seq');

CREATE UNIQUE INDEX uk_asociado_email ON asociado USING btree (email);

CREATE INDEX typeahead_asociado_nombre_completo
ON asociado (lower(nombre) varchar_pattern_ops, lower(apellidos) varchar_pattern_ops);

CREATE TABLE token (
    token varchar(26) NOT NULL,
    creado timestamp without time zone,
    duracion_en_segundos bigint NOT NULL,
    email varchar(100) NOT NULL,
    CONSTRAINT token_pkey PRIMARY KEY (token)
);

CREATE UNIQUE INDEX uk_token_email ON token USING btree (email);

