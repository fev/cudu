import groovy.sql.Sql
import groovy.grape.Grape

@Grapes([
    @Grab('postgresql:postgresql:8.4-701.jdbc3'),
    @GrabConfig(systemClassLoader=true)
])

def db = Sql.newInstance("jdbc:postgresql://localhost/cudu", "postgres", "test", "org.postgresql.Driver")

def reset = """
	delete from liquidacion; 
	update asociado set activo = false where id in (1,3,6, 11,12); 
	update asociado set activo = true where id in (20,21);"""

def liquidar(db, fecha) {
	def statement = """
	--- Altas
	insert into liquidacion (ejercicio, fecha, idasociado, caracter, asociacion)
	select 2011, ${fecha}, id, 'A', 1 from asociado
	   where asociacion = 1
	     and activo = true
	     and id not in (select idasociado from liquidacion where ejercicio = 2011 and caracter = 'A');
	-- Bajas
	insert into liquidacion (ejercicio, fecha, idasociado, caracter, asociacion)
	select 2011, ${fecha}, id, 'B', 1 from asociado
	    where asociacion = 1
	      and activo = false
	      and id in (select idasociado from liquidacion where ejercicio = 2011 and caracter = 'A')
	      and id not in (select idasociado from liquidacion where ejercicio = 2011 and caracter = 'B');
	"""
	db.execute(statement)
}

db.execute(reset)
liquidar(db, java.sql.Date.valueOf( "2011-01-01" ))
db.execute("update asociado set activo = true where id in (11,12);")
db.execute("update asociado set activo = false where id in (20);")
liquidar(db, java.sql.Date.valueOf( "2011-02-12" ))
db.execute("update asociado set activo = false where id in (21);")
liquidar(db, java.sql.Date.valueOf( "2011-03-23" ))

db.eachRow("select * from liq_resumen order by fecha asc") {
    println "${it.ejercicio}\t${it.fecha}\t${it.altas}\t${it.bajas}\t${it.asociacion}"
}




