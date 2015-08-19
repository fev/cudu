
CREATE TABLE cargo (
  id integer NOT NULL,
  etiqueta varchar(50) NOT NULL,
  ambito char(1) NOT NULL DEFAULT 'P',
  unico boolean NOT NULL DEFAULT false,
  puntos int NOT NULL DEFAULT 0,
  CONSTRAINT pk_cargo PRIMARY KEY (id)
);

ALTER TABLE cargo ADD CONSTRAINT chk_cargo_puntos_positivos CHECK (puntos >= 0);
ALTER TABLE cargo ADD CONSTRAINT chk_cargo_ambito CHECK (ambito IN ('P', 'K', 'C', 'F'));

CREATE INDEX idx_cargo_ambito ON cargo(ambito);

CREATE TABLE cargo_asociado (
  cargo_id INT NOT NULL,
  asociado_id INT NOT NULL,
  PRIMARY KEY (cargo_id, asociado_id)
);

insert into cargo_asociado (cargo_id, asociado_id) VALUES
  (1, 86),
  (12, 86),
  (14, 86),
  (34, 86),
  (39, 86);

CREATE VIEW dto_cargo_asociado AS
select ca.asociado_id, c.* from cargo c
inner join cargo_asociado ca on c.id = ca.cargo_id;

COPY cargo (id, etiqueta, ambito, unico, puntos) FROM stdin WITH DELIMITER ',';
1,grupo.jefe_de_grupo,K,true,5
2,grupo.subjefe_de_grupo,K,false,4
3,grupo.tesorero,K,true,2
4,grupo.secretario,K,true,4
5,grupo.dinamizador_de_kraal,K,false,4
6,grupo.responsable_de_progresion_personal,K,false,4
7,grupo.responsable_de_material,K,false,3
8,grupo.consiliario,K,false,3
9,comite.presidencia,C,true,0
10,comite.secretaria,C,true,0
11,comite.tesoreria,C,true,0
12,comite.vocal,C,false,0
13,fev.coordinador_equipo_fe,F,true,4
14,fev.voluntario_equipo_fe,F,false,2
15,fev.coordinador_equipo_salud,F,true,4
16,fev.voluntario_equipo_salud,F,false,2
17,fev.coordinador_equipo_social,F,true,4
18,fev.voluntario_equipo_social,F,false,2
19,fev.coordinador_equipo_internacional,F,true,4
20,fev.voluntario_equipo_internacional,F,false,2
21,fev.coordinador_equipo_desarrollo,F,true,4
22,fev.voluntario_equipo_desarrollo,F,false,2
23,fev.coordinador_equipo_participacion,F,true,4
24,fev.voluntario_equipo_participacion,F,false,2
25,fev.coordinador_equipo_promocion_cultural,F,true,4
26,fev.voluntario_equipo_promocion_cultural,F,false,2
27,fev.coordinador_equipo_medio_ambiente,F,true,4
28,fev.voluntario_equipo_medio_ambiente,F,false,2
29,fev.voluntario_creequip,F,false,2
30,fev.voluntario_equipo_animacion_pedagogica,F,false,2
31,fev.voluntario_equipo_comunicacion,F,false,2
32,fev.coordinador_equipo_4vents,F,true,4
33,fev.voluntario_equipo_4vents,F,false,2
34,fev.formador,F,false,2
35,fev.coordinador_de_curso,F,true,3
36,fev.tutor_de_formacion,F,false,3
37,fev.miembro_de_la_mesa_pedagogica,F,false,3
38,fev.direccion_escola_lluerna,F,true,5
39,fev.presidencia_fev,F,true,5
40,fev.vicepresidencia_fev,F,true,5
41,fev.secretaria_fev,F,true,5
42,fev.tesoreria_fev,F,true,5
43,fev.consiliario_fev,F,true,5
44,fev.responsable_de_comunicacion,F,true,5
45,fev.responsable_de_crecimiento_y_equipo_humano,F,true,5
46,fev.coordinador_de_equipos_de_voluntarios,F,true,5
47,fev.responsable_de_animacion_pedagogica,F,true,5
48,fev.vocal_fev,F,false,5
49,fev.presidencia_sdc,F,true,5
50,fev.vicepresidencia_sdc,F,true,5
51,fev.secretaria_sdc,F,true,5
52,fev.tesoreria_sdc,F,true,5
53,fev.consiliario_sdc,F,true,5
54,fev.vocal_sc,F,false,5
55,fev.presidencia_mev,F,true,5
56,fev.vicepresidencia_mev,F,true,5
57,fev.secretaria_mev,F,true,5
58,fev.tesoreria_mev,F,true,5
59,fev.consiliario_mev,F,true,5
60,fev.vocal_mev,F,false,5
61,fev.presidencia_sda,F,true,5
62,fev.vicepresidencia_sda,F,true,5
63,fev.secretaria_sda,F,true,5
64,fev.tesoreria_sda,F,true,5
65,fev.consiliario_sda,F,true,5
66,fev.vocal_sda,F,false,5
67,fev.mev_coordinacion_de_comarca,F,false,3
68,fev.mev_secretaria_de_comarca,F,false,2
69,fev.mev_tesoreria_de_comarca,F,false,2
70,fev.colaborador_lluerna,F,false,3
\.

CREATE SEQUENCE cargo_id_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE cargo_id_seq OWNED BY asociado.id;
ALTER TABLE ONLY cargo ALTER COLUMN id SET DEFAULT nextval('cargo_id_seq');