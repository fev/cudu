create TABLE "ficha" (
    "id" serial not null,
    "lenguaje" varchar(3) not null,
    "nombre" varchar(200) not null,
    "plantilla" varchar(200) not null,
    "tipo_entidad" int4,
    "tipo_ficha" int4,
    PRIMARY KEY ("id", "lenguaje")
);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (4, 'es', 'Autorización fotografias mayor de edad', 'FEV_Autorizacion_fotografias_mayor_de_edad.pdf', 0, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (6, 'es', 'Autorización fotografias menor de edad', 'FEV_Autorizacion_fotografias_menor_de_edad.pdf', 0, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (14, 'ca', 'Autoritzacio fotografies major d''edat', 'FEV_Autoritzacio_fotografies_major_d''edat.pdf', 0, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (15, 'ca', 'Autoritzacio fotografies menor d''edat', 'FEV_Autoritzacio_fotografies_menor_d''edat.pdf', 0, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (16, 'ca', 'Autoritzacio menor ausentar-se activitat', 'FEV_Autoritzacio_menor_ausentar-se_activitat.pdf', 1, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (17, 'ca', 'Autoritzacio menor regresar a soles activitat', 'FEV_Autoritzacio_menor_regresar_a_soles_activitat.pdf', 1, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (18, 'ca', 'Autoritzacio menor viatge vehicle privat', 'FEV_Autoritzacio_menor_viatge_vehicle_privat.pdf', 1, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (19, 'ca', 'Declaracio exclusio responsabilitat', 'FEV_declaracio_exclusio_responsabilitat.pdf', 0, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (20, 'ca', 'Fulla d''inscripcio a l''agrupament escolta', 'FEV_Fulla_d''inscripcio_a_l''agrupament_escolta.pdf', 0, 0);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (10, 'es', 'Declaración exclusión responsabilidad', 'FEV_declaración_exclusion_responsabilidad.pdf', 0, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (3, 'es', 'Ficha sanitaria', 'FEV_ficha_sanitaria.pdf', 0, 0);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (11, 'es', 'Ficha inscripcion grupo scout', 'FEV_Ficha_inscripcion_grupo_scout.pdf', 0, 0);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (13, 'ca', 'Fitxa Sanitaria', 'FEV_fitxa_sanitaria.pdf', 0, 0);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (7, 'es', 'Autorización menor ausentarse actividad', 'FEV_Autorizacion_menor_ausentarse_actividad.pdf', 1, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (8, 'es', 'Autorización menor regresar solo actividad', 'FEV_Autorizacion_menor_regresar_solo_actividad.pdf', 1, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (9, 'es', 'Autorización menor viaje vehiculo privado', 'FEV_Autorizacion_menor_viaje_vehiculo_privado.pdf', 1, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (0, 'es', 'Participacion en actividades', 'FEV_Autorizacion_participacion_en_actividades.pdf', 1, 1);
INSERT INTO "ficha"(id, lenguaje, nombre, plantilla, tipo_entidad, tipo_ficha) VALUES (12, 'ca', 'Participació en activitats', 'FEV_Autoritzacio_participacio_en_activitats.pdf', 1, 1);

create TABLE "impresion" (
    "fichero" varchar(100) not null,
    "usuario_id" int4 not null,
    "fecha" timestamp default now(),
    PRIMARY KEY ("fichero", "usuario_id")
);

