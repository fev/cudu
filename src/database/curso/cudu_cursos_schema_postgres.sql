drop view miscursos;
drop table faltaMonografico;
drop table inscripcionCurso;
drop table monograficos_en_cursos;
drop table monografico;
drop table curso;

CREATE TABLE curso(
  id 		integer		NOT NULL,
  nombre	varchar(50)	NOT NULL,
  acronimo	varchar(6)	NOT NULL,
  anyo		integer		NOT NULL,
  precio	float,
  descripcion	varchar(100),
  CONSTRAINT	pk_curso PRIMARY KEY (id),
  CONSTRAINT	uk_curso_nombre_anyo	UNIQUE	(nombre,anyo),
  CONSTRAINT	uk_curso_acronimo_anyo	UNIQUE	(acronimo,anyo)
);
  
CREATE TABLE monografico (
  id 		integer 	NOT NULL,
  nombre	varchar(50)	NOT NULL,
  fechaInicio	date		NOT NULL,
  fechaFin	date		NOT NULL,
  precio	float,
  descripcion	varchar(100),
  plazasDisponibles integer	NOT NULL,
  plazasTotales	integer		NOT NULL,
  listaEspera	integer		NOT NULL,
  lugarPrevisto	varchar(100),
  CONSTRAINT	pk_monografico				PRIMARY KEY (id),
  CONSTRAINT	uk_monografico_nomsbre_Fechainicio	UNIQUE(NOMBRE,fechaInicio),
  CONSTRAINT	uk_monografico_nombre_FechaFin		UNIQUE(NOMBRE,fechaInicio)
);

create table monograficos_en_cursos(

	idcurso integer,
	idmonografico integer,
	bloque char(100),
	obligatorio boolean,
	bloqueunico boolean,
		
	constraint pk_id_monograficos_en_cursos PRIMARY KEY ( idcurso,idmonografico),
	constraint fk_curso_monograficos_en_cursos FOREIGN KEY ( idcurso) REFERENCES CURSO,
	constraint fk_monografico_monograficos_en_cursos FOREIGN KEY ( idmonografico) REFERENCES MONOGRAFICO
);


CREATE TABLE inscripcionCurso (
  idasociado	integer,
  idcurso	integer,		
  idmonografico	integer,
  
  fechaInscripcion date		NOT NULL,
  pagoRealizado	boolean		NOT NULL,
  trabajo 	character(1) 	not null,--aqui se recogerá el estado 'PENDIENTE, APTO,NO APTO ó O -> no está inscrito.
  fecha_entrega_trabajo date,
  calificacion 	character(1)	not null  DEFAULT 'N',--aqui se recogerá el estado 'LISTA ESPERA, ACEPTADO EN EL CURSO, APTO,NO APTO.
  
  CONSTRAINT pk_inscripcionCurso		PRIMARY KEY(idasociado,idmonografico,idcurso),
  CONSTRAINT fk_inscripcionCurso_asociado	FOREIGN KEY (idasociado) REFERENCES asociado(id),
  CONSTRAINT fk_inscripcionCurso_cuso		FOREIGN KEY (idcurso,idmonografico) REFERENCES monograficos_en_cursos(idcurso,idmonografico),
  CONSTRAINT ck_enum_trabajo_inscripcioncurso CHECK (trabajo IN ('N','P','A','O')),
  CONSTRAINT ck_enum_calificacion_inscripcioncurso CHECK (calificacion IN ('E','A','N','P','O'))
);
  
  
CREATE TABLE faltaMonografico (
  idasociado 	integer,
  idmonografico	integer,
  idcurso	integer,
  fechaFalta	date,
  CONSTRAINT	pk_faltaMonografico	PRIMARY KEY (idasociado, idmonografico, fechaFalta),
  CONSTRAINT	fk_faltaMonografico_inscripcion	FOREIGN KEY (idasociado,idmonografico,idcurso)  REFERENCES inscripcionCurso(idasociado,idmonografico,idcurso) 
);
--restruiccion en monografico -> fecha inicio < fecha fin.
--Numero de gente inscrita -> se tiene que calcular con un count.
-- precio no puede ser negativo.
-- plazas disponibles no puede ser valor negativo.
--inscritos no puede ser más que numero total.
--la fecha de inscripcionCurso a partir de septiembre
--las faltas tienen que estar comprendidas entre las fechas en las que se realiza el mon

