/* database.groovy - does some database operations over postgresql databases
 * Copyright (c) 2015, Luis Belloch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import groovy.sql.*
import groovy.transform.*

@GrabConfig(systemClassLoader=true)
@Grab(group='org.postgresql', module='postgresql', version='9.4-1202-jdbc42')
@Grab(group='org.flywaydb', module='flyway-core', version='3.2.1')
import org.flywaydb.core.Flyway;

@Grab(group='org.jooq', module='jooq-codegen', version='3.6.2')
import org.jooq.util.GenerationTool

final String defaultUserName = System.getProperty("user.name")
final String defaultDatabase = "playground_${UUID.randomUUID().toString().substring(0, 7)}"

def cli = new CliBuilder(usage: 'database.groovy <operation> [args]')
cli.with {
  m longOpt: 'migrate', args: 1, required: false, optionalArg: true, 'Migrates database. If name is not set it will create a new database.'
  j longOpt: 'jooq', args: 1, required: false, optionalArg: false, 'Recreates jOOQ metadata classes, you have to provide the namespace for generated classes. Runs existing migrations first over a temporary database.'
  r longOpt: 'drop', required: false, args: 1, 'Drops databases matching a like clause.'
  u longOpt: 'username', args: 1, required: false, "User name. Defaults to '${defaultUserName}'."
  p longOpt: 'password', args: 1, required: false, 'Password. Defaults to empty'
}

def options = cli.parse(args)
if (!options || (!options.m && !options.j && !options.r)) {
  cli.usage()
  return
}

if (options.r) {
  Database database = new Database(name: defaultDatabase, user: options.u ?: defaultUserName, password: options.p)
  database.drop(options.r)
  return
}

if (options.m) {
  String name = (options.m instanceof Boolean) ? defaultDatabase : options.m
  Database database = new Database(name: name, user: options.u ?: defaultUserName, password: options.p)
  database.createIfNotExists()
  database.migrate()
  return
}

if (options.j) {
  Database database = new Database(name: defaultDatabase, user: options.u ?: defaultUserName, password: options.p)
  Jooq jooq = new Jooq(packageName: options.j, outputFolder: 'src/main/java', database: database)
  jooq.generate()
  return
}

@Immutable
@ToString
class Database {
  String name
  int port = 5432
  String server = "localhost"
  String user
  String password
  String jdbc = "jdbc:postgresql://${server}:${port}/${name}"
  String jdbcMaster = "jdbc:postgresql://${server}:${port}/postgres"

  @Memoized
  boolean exists() {
    boolean exists = false
    withMaster() { sql ->
      def row = sql.firstRow "select count(datname) from pg_catalog.pg_database where datname = ?", name
      exists = row.count == 1
    }
    return exists
  }

  void createIfNotExists() {
    if (!exists()) {
      println "Creating database ${name}"
      withMaster({ s -> s.execute 'CREATE DATABASE ' + escape(name) })
    }
  }

  void drop(String pattern) {
    def row =
    withMaster({ sql ->
      sql.eachRow("select datname as name from pg_catalog.pg_database where datname like ?", [pattern]) { r ->
        println "Dropping database ${r.name}..."
        sql.execute 'DROP DATABASE ' + escape(r.name)
      }
    })
  }

  void migrate() {
    Flyway flyway = new Flyway();
    flyway.setDataSource(jdbc, user, password);
    flyway.setLocations('src/main/resources/db/migration')
    flyway.migrate();
  }

  void withMaster(Closure c) {
    Sql.withInstance(jdbcMaster, user, password, "org.postgresql.Driver") { sql -> c(sql) }
  }

  String escape(String token) {
    boolean isQuoted  = token[0] == '"' && token.charAt(name.length()-1) == '"'
    String unquoted = isQuoted ? token[1..-2] : token // Drop start-end quotes, if any
    '"' + unquoted.replaceAll('"', '\"') + '"' // Escape the rest
  }

  String jdbc(String nameOverride = name) { "jdbc:postgresql://${server}:${port}/${nameOverride}" }

  // TODO Function to drop all connections
}

class Jooq {
  String packageName
  String outputFolder
  Database database

  void generate() {
    database.createIfNotExists()
    database.migrate()
    try {
      def writer = new StringWriter()
      new groovy.xml.MarkupBuilder(writer)
      .configuration() {
        jdbc() {
          driver('org.postgresql.Driver')
          url(database.jdbc)
          user(database.user)
          password(database.password)
        }
        generator() {
          name('org.jooq.util.DefaultGenerator')
          database() {
            name('org.jooq.util.postgres.PostgresDatabase')
            includes('.*')
            excludes('')
            inputSchema('public')
          }
          generate() {
            pojos(false)
            immutablePojos(true)
            interfaces(false)
            jpaAnnotations(false)
            validationAnnotations(false)
            daos(false)
          }
          target() {
            packageName(packageName)
            directory(outputFolder)
          }
        }
      }
      GenerationTool.generate(writer.toString())
    } catch (Exception e) {
      e.printStackTrace()
    } finally {
      database.drop(database.name)
    }
  }
}
