delete from inscripcionCurso;
delete from monograficos_en_cursos;
delete from monografico where id >5;
delete from curso where id >5;

insert into curso (id, nombre, acronimo, anyo,  precio, descripcion)
values		  (6,  'Formación Contínua', 'FC', 2010, 20, 'curso de FC');

insert into curso (id, nombre, acronimo, anyo, precio, descripcion)
values		  (7,  'Animador Juvenil', 'AJ', 2010, 20, 'curso de AJ');

insert into curso (id, nombre, acronimo, anyo, precio, descripcion)
values		  (8,  'Manipulador de Alimentos', 'MA',  2011, 20, 'curso de MA');

insert into curso (id, nombre, acronimo,  anyo, precio, descripcion)
values		  (9,  'Monitor de Tiempo Libre', 'MTL',  2011, 20, 'curso de MTL');

insert into curso (id, nombre, acronimo, anyo, precio, descripcion)
values		  (10,  'Formador de Animadores', 'FA',  2011, 20, 'curso de FA');

insert into curso (id, nombre, acronimo, anyo, precio, descripcion)
values		  (11,  'Animador Juvenil', 'AJ', 2011, 20, 'curso de AJ');

insert into curso (id, nombre, acronimo, anyo, precio, descripcion)
values		  (12,'Formación Contínua', 'FC', 2011, 20, 'curso de FC');

----
insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (6,'Manipulador de alimentos', '02/02/2011','03/02/2011',21,'MANIPULADOR DE ALIMENTOS',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(8,6,'Manipulador de alimentos','T','T');

			     
-----
insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (7,'Monitor de Tiempo Libre', '02/02/2011','03/02/2011',21,'Monitor de Tiempo Libre',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(9,7,'Monitor de Tiempo Libre','T','T');

			     
-----
insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (8,'TF Tutores de Formación)', '02/02/2011','03/02/2011',21,'TF Tutores de Formación)',10,10,0,'Casa de Paco');

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (9,'Seminarios de Formación', '02/02/2011','03/02/2011',21,'Seminarios de Formación',10,10,0,'Casa de Paco');

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (10,'ADF (ADJUNTO DE FORMACIÓN)', '02/02/2011','03/02/2011',21,'ADF (ADJUNTO DE FORMACIÓN)',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(10,8,'Formador de Animadores','T','F');
insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(10,9,'Formador de Animadores','T','F');			     
insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(10,10,'Formador de Animadores','T','F');			     



			     
--FC Y AJ 2011

	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (11,'Educación para la Salud', '02/02/2011','03/02/2011',21,'Educación para la Salud',10,10,0,'Casa de Paco');


	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(11,11,'Bloque Optativas','T','F');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(12,11,'Bloque Rebrot','F','F');	


insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (12,'Educar en la Fe', '02/02/2011','03/02/2011',21,'Educar en la Fe',10,10,0,'Casa de Paco');


insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(11,12,'Bloque Optativas','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(12,12,'Bloque Rebrot','F','F');
			     


	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (13,'Naturaleza en la C. Valenciana', '02/02/2011','03/02/2011',21,'Naturaleza en la C. Valenciana',10,10,0,'Casa de Paco');


	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(11,13,'Bloque Optativas','F','F');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(12,13,'Bloque Rebrot','F','F');

			     
insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (14,'Educación para el Desarrollo', '02/02/2011','03/02/2011',21,'Educación para el Desarrollo',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(11,14,'Bloque Optativas','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(12,14,'Bloque Rebrot','F','F');


	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (15,'Recursos para la Animación', '02/02/2011','03/02/2011',21,'Recursos para la Animación',10,10,0,'Casa de Paco');


	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(11,15,'Bloque Optativas','F','F');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(12,15,'Bloque Técnicas','F','F');
			     

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (16,'Trabajo y Consumo', '02/02/2011','03/02/2011',21,'Trabajo y Consumo',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(11,16,'Bloque Optativas','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(12,16,'Bloque Técnicas','F','F');

			     
	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (17,'Dinámica de Grupos', '02/02/2011','03/02/2011',21,'Dinámica de Grupos',10,10,0,'Casa de Paco');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(11,17,'Bloque Optativas','F','F');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(12,17,'Bloque Técnicas','F','F');

			     
insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (18,'Habilidades Sociales', '02/02/2011','03/02/2011',21,'Habilidades Sociales',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(11,18,'Bloque Optativas','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(12,18,'Bloque Técnicas','F','F');

				     
	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (19,'Monográfico Gestión de Campamentos', '02/02/2011','03/02/2011',21,'Monográfico Gestión de Campamentos',10,10,0,'Casa de Paco');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(11,19,'Bloque Obligatorio','T','F');
		     

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (20,'Monográfico Vida en la Naturaleza', '02/02/2011','03/02/2011',21,'Monográfico Vida en la Naturaleza',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(11,20,'Bloque Obligatorio','T','F');

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (21,'Cambio Social', '02/02/2011','03/02/2011',21,'Cambio Social',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(11,21,'Bloque Obligatorio','T','F');

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (22,'Participación Asociativa', '02/02/2011','03/02/2011',21,'Participación Asociativa',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(11,22,'Bloque Obligatorio','T','F');				     				     

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (23,'Curso de Ramas', '02/02/2011','03/02/2011',21,'Curso de Ramas',10,10,0,'Casa de Paco');


insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(11,23,'Rama','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(12,23,'Rama','F','F');






		     
--FC Y AJ 2010

	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (24,'Educación para la Salud', '02/02/2010','03/02/2010',21,'Educación para la Salud',10,10,0,'Casa de Paco');


	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(6,24,'Bloque Optativas','T','F');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(7,24,'Bloque Rebrot','F','F');	


insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (25,'Educar en la Fe', '02/02/2010','03/02/2010',21,'Educar en la Fe',10,10,0,'Casa de Paco');


insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(6,25,'Bloque Optativas','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(7,25,'Bloque Rebrot','F','F');
			     


	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (26,'Naturaleza en la C. Valenciana', '02/02/2010','03/02/2010',21,'Naturaleza en la C. Valenciana',10,10,0,'Casa de Paco');


	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(6,26,'Bloque Optativas','F','F');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(7,26,'Bloque Rebrot','F','F');

			     
insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (27,'Educación para el Desarrollo', '02/02/2010','03/02/2010',21,'Educación para el Desarrollo',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(6,27,'Bloque Optativas','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(7,27,'Bloque Rebrot','F','F');


	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (28,'Recursos para la Animación', '02/02/2010','03/02/2010',21,'Recursos para la Animación',10,10,0,'Casa de Paco');


	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(6,28,'Bloque Optativas','F','F');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(7,28,'Bloque Técnicas','F','F');
			     

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (29,'Trabajo y Consumo', '02/02/2010','03/02/2010',21,'Trabajo y Consumo',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(6,29,'Bloque Optativas','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(7,29,'Bloque Técnicas','F','F');

			     
	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (30,'Dinámica de Grupos', '02/02/2010','03/02/2010',21,'Dinámica de Grupos',10,10,0,'Casa de Paco');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(6,30,'Bloque Optativas','F','F');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(7,30,'Bloque Técnicas','F','F');

			     
insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (31,'Habilidades Sociales', '02/02/2010','03/02/2010',21,'Habilidades Sociales',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(6,31,'Bloque Optativas','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(7,31,'Bloque Técnicas','F','F');

				     
	insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
	values (32,'Monográfico Gestión de Campamentos', '02/02/2010','03/02/2010',21,'Monográfico Gestión de Campamentos',10,10,0,'Casa de Paco');

	insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(6,32,'Bloque Obligatorio','T','F');
		     

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (33,'Monográfico Vida en la Naturaleza', '02/02/2010','03/02/2010',21,'Monográfico Vida en la Naturaleza',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(6,33,'Bloque Obligatorio','T','F');

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (34,'Cambio Social', '02/02/2010','03/02/2010',21,'Cambio Social',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(6,34,'Bloque Obligatorio','T','F');

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (35,'Participación Asociativa', '02/02/2010','03/02/2010',21,'Participación Asociativa',10,10,0,'Casa de Paco');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
				     values(6,35,'Bloque Obligatorio','T','F');				     				     

insert into monografico (id, nombre, fechainicio,fechafin,precio,descripcion,plazasdisponibles,plazastotales,listaespera,lugarprevisto)
values (36,'Curso de Ramas', '02/02/2010','03/02/2010',21,'Curso de Ramas',10,10,0,'Casa de Paco');


insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(6,36,'Rama','F','F');

insert into monograficos_en_cursos (idcurso,idmonografico,bloque,obligatorio,bloqueunico) 
			     values(7,36,'Rama','F','F');



-------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------_
-------------------------------------------------------------------------------------------------------------------_
insert into inscripcionCurso (idasociado, idcurso, idmonografico, fechaInscripcion, pagoRealizado, trabajo, fecha_entrega_trabajo, calificacion)

 values(20, 6, 24,
	'10/10/2010',
	'T',
	'A',
	'11/11/2010',
	'A');
insert into inscripcionCurso (idasociado, idcurso, idmonografico, fechaInscripcion, pagoRealizado, trabajo, fecha_entrega_trabajo, calificacion)
values(20, 6, 25,
	'10/10/2010',
	'T',
	'A',
	'11/11/2010',
	'A');

----------------------------

insert into inscripcionCurso (idasociado, idcurso, idmonografico, fechaInscripcion, pagoRealizado, trabajo, fecha_entrega_trabajo, calificacion)

 values(20, 11, 14,
	'10/10/2011',
	'T',
	'A',
	'11/11/2011',
	'A');
insert into inscripcionCurso (idasociado, idcurso, idmonografico, fechaInscripcion, pagoRealizado, trabajo, fecha_entrega_trabajo, calificacion)
values(20, 12, 15,
	'10/10/2011',
	'T',
	'A',
	'11/11/2011',
	'A');

insert into faltamonografico(idasociado,idcurso,idmonografico,fechafalta)
values (20,6,25,'12/12/2010');
insert into faltamonografico(idasociado,idcurso,idmonografico,fechafalta)
values (20,6,25,'11/12/2010');
insert into faltamonografico(idasociado,idcurso,idmonografico,fechafalta)
values (20,6,24,'12/12/2010');



select * from curso;
--select * from inscripcioncurso;