--drop view miscursinsos;
drop view miscursos ;
create view miscursos as
(
select ic.idasociado, 
	m.id as idmonografico,
	c.id as idcurso,
	
	m.fechainicio as ronda,
	c.nombre as formacion,
	ic.trabajo,
	ic.fecha_entrega_trabajo,
	m.nombre as curso,
	ic.calificacion,
	count(fm.fechafalta) as faltas
from 
		      curso c,
     monograficos_en_cursos mc, 
		monografico m,
	   inscripcioncurso ic

     left outer join 	faltamonografico fm on
			(
			 ic.idmonografico = fm.idmonografico AND
			 ic.idcurso  = fm.idcurso 	     AND
			 ic.idasociado = fm.idasociado
			)
    
where m.id = ic.idmonografico  
  and c.id = ic.idcurso

  and c.id = mc.idcurso
  and m.id = mc.idmonografico
  

  group by ic.idasociado, m.fechainicio,c.nombre,m.nombre,ic.calificacion,m.id,c.id,ic.trabajo,ic.fecha_entrega_trabajo
  order by idasociado,m.id,c.id
  )
;
select * from miscursos;
--select * from inscripcionCurso;
select * from faltamonografico;