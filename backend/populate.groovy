String username = System.getProperty("user.name")

def cli = new CliBuilder(usage: 'testdata.groovy -d [DATABASE_NAME] [OPTS]')
cli.with {
  h longOpt: 'help', 'Mostrar información sobre el uso'
  d longOpt: 'database', args: 1, required: true, 'Nombre de la base de datos a rellenar'
  u longOpt: 'username', args: 1, required: false, 'Usuario (por defecto: ' + username + ')'
  p longOpt: 'password', args: 1, required: false, 'Password (por defecto en blanco)'
}

def options = cli.parse(args)
if (!options) {
	return
}

if (options.h) {
	cli.usage()
	return
}

@Grab(group='org.slf4j', module='slf4j-simple', version='1.7.7')
@Grab(group='io.codearte.jfairy', module='jfairy', version='0.5.0')
import io.codearte.jfairy.Fairy
import io.codearte.jfairy.producer.BaseProducer
import io.codearte.jfairy.producer.person.Person
import io.codearte.jfairy.producer.company.Company

//Fairy fairy = Fairy.create(Locale.forLanguageTag("es"));
Fairy fairy = Fairy.create();

String jdbcUrl = "jdbc:postgresql://localhost:5432/${options.d}"
username = options.u ?: username
String password = options.p ?: "wackamole3"

println "Base de datos: ${options.d}"

@Grab(group='org.postgresql', module='postgresql', version='9.4-1202-jdbc42')
@GrabConfig(systemClassLoader=true)
import groovy.sql.*
import io.codearte.jfairy.producer.text.TextProducer

import java.sql.Timestamp
import java.time.ZoneOffset
import java.time.LocalDate
import java.time.LocalDateTime

class Generador {

    static final String passwordPorDefecto = "wackamole"
    static final int cargoFormadorLluerna = 34;
    static int indiceParticipantes = 0;

    Sql sql
    Fairy fairy

    Generador(Sql sql, Fairy fairy) {
        this.sql = sql
        this.fairy = fairy
    }

    String grupo(String grupoId = null) {
        String codigo = grupoId ?: fairy.textProducer().randomString(3).toUpperCase()
        Company company = fairy.company();
        insert("grupo", [id: codigo, nombre: company.name(), codigo_postal: 46015, municipio: "Valencia", asociacion: 0])
        return codigo
    }

    def asociado(grupoId, tipoAsociado, usuarioActivo, email = null, ambitoEdicion = null) {
        Person person = fairy.person();
        def ambito = ambitoEdicion ?: (tipoAsociado == 'J' ? 'P' : 'G');
        def sexo = person.female ? 'F' : 'M';
        def datos = [
                grupo_id: grupoId,
                activo: true,
                usuario_activo: usuarioActivo,
                password: usuarioActivo ? passwordPorDefecto : null,
                email: usuarioActivo ? (email ?: person.email()) : null,
                email_contacto: person.email(),
                nombre: person.firstName(),
                apellidos: person.lastName(),
                tipo: tipoAsociado,
                ambito_edicion: ambito,
                sexo: sexo,
                direccion: person.address.street(),
                fecha_nacimiento: new Timestamp(person.dateOfBirth().getMillis())
        ]
        def insertado = insert("asociado", datos)
        datos.id = insertado[0][0]
        return datos
    }

    def asociados(int numero, grupoId, tipoAsociado, usuarioActivo = false) {
        def lista = []
        for (int i = 0; i < numero; i++) {
            lista << asociado(grupoId, tipoAsociado, usuarioActivo)
        }
        return lista
    }

    def curso(boolean abierto, boolean visible = true) {
        TextProducer textProducer = fairy.textProducer();
        BaseProducer baseProducer = fairy.baseProducer();
        LocalDateTime fechaFinInstcripcion = LocalDateTime.now().plusDays(abierto ? 10 : -10);
        def datos = [
                titulo: textProducer.sentence(3),
                fecha_inicio_inscripcion: toSqlTimestamp(LocalDateTime.now().minusDays(baseProducer.randomBetween(1, 10))),
                fecha_fin_inscripcion: toSqlTimestamp(fechaFinInstcripcion),
                fecha_nacimiento_minima: toSqlTimestamp(LocalDate.now().minusYears(18)),
                plazas: baseProducer.randomBetween(10, 25),
                descripcion_fechas: textProducer.sentence(),
                descripcion_lugar: textProducer.sentence(),
                visible: visible
        ];
        def r = insert("curso", datos)
        datos.id = r[0][0]

        formadores(2, "UP", datos.id);

        final int epg = datos.plazas * 0.5;
        indiceParticipantes++;
        sql.executeInsert "insert into curso_participante (curso_id, asociado_id) select ?, id from asociado where tipo = 'K' order by nombre limit ? offset ?", [datos.id, epg, indiceParticipantes * epg];
    }

    def cursos(int numero, boolean abierto) {
        for (int i = 0; i < numero; i++)
            curso(abierto)
    }

    def formadores(int numero, String grupoId, cursoId) {
        def lista = [];
        for (int i = 0; i < numero; i++) {
            def a = asociado(grupoId, 'K', true)
            lista << a.id
            sql.executeInsert "insert into cargo_asociado (cargo_id, asociado_id) VALUES (?, ?)", [cargoFormadorLluerna, a.id]
        }
        for (int id : lista) {
            sql.executeInsert "insert into curso_formador (curso_id, asociado_id) VALUES (?, ?)", [cursoId, id]
        }
    }

    static def toSqlTimestamp(LocalDateTime date) {
        new Timestamp(date.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    static def toSqlTimestamp(LocalDate date) {
        new Timestamp(date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    def insert(String tabla, LinkedHashMap obj) {
        def columnas = obj.keySet().join(", ")
        def valores = obj.values().toArray()
        def markers = (1..(valores.length)).collect { "?" }.join(",")
        String sqlStatement = "INSERT INTO ${tabla} (${columnas}) VALUES (${markers})"
        sql.executeInsert sqlStatement, valores
    }
}

Sql sql = Sql.newInstance(jdbcUrl, username, password, "org.postgresql.Driver")
Generador generador = new Generador(sql, fairy)

// TODO Opcion para hacer drop de datos?
sql.execute "delete from curso_formador"
sql.execute "delete from curso_participante"
sql.execute "delete from curso"
sql.execute "delete from asociado"
sql.execute "delete from grupo"

generador.grupo("UP")
generador.asociados(70, "UP", 'J')
generador.asociados(7, "UP", 'K')
generador.asociados(3, "UP", 'C')

def g1 = generador.grupo()
generador.asociados(20, g1, 'J')
generador.asociados(2, g1, 'K')
generador.asociados(1, g1, 'K', true)

def g2 = generador.grupo()
generador.asociados(60, g2, 'J')
generador.asociados(10, g2, 'K')

generador.asociado('UP', 'K', true, "baden@example.com", 'G')
generador.asociado('UP', 'K', true, "jack@example.com", 'P')
generador.asociado(null, 'T', true, "fev@example.com", 'F')
generador.asociado(null, 'T', true, "lluerna@example.com", 'E')

generador.cursos(5, true)
generador.cursos(2, false)

println "\nGRUPOS"
sql.eachRow("select id, nombre from grupo")  {
    println "${it.id} ${it.nombre}"
}

println "\nUSUARIOS ACTIVOS\nPassword: ${generador.passwordPorDefecto}"
sql.eachRow("SELECT id, tipo, ambito_edicion, grupo_id, email, nombre, apellidos FROM asociado WHERE usuario_activo = true order by id") {
    println it
}
