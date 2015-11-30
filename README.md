# Cudú

[![Build Status](https://travis-ci.org/fev/cudu.svg?branch=master)](https://travis-ci.org/fev/cudu)

Cudú es una aplicación web que permite almacenar y gestionar de forma segura y centralizada datos de agrupaciones juveniles. Cudú ha sido desarrollado por la [Federació d'Escoltisme Valencià](http://www.scoutsfev.org) y legalmente se distribuye bajo [licencia AGPLv3](http://www.gnu.org/licenses/agpl-3.0.html).

## Primeros pasos

Para generar el `frontend` necesitas tener instalado [Compass](http://compass-style.org/) y [Bower](http://bower.io/). Hemos incluido los archivos necesarios para que puedas obtener las dependencias con [npm](https://www.npmjs.org) y [bundler](http://bundler.io):

	$ cd frontend
	$ npm install
	$ bundler install
	$ bower install

Una vez tengas todo disponible necesitas generar las traducciones de las vistas, el CSS resultante de Sass/Compass y una vez completado puedes arrancar el `frontend`:

	$ node traducir.js
	$ compass compile
	$ node devserver.js

Pasados unos segundos Cudú estará disponible con datos de prueba en `http://localhost:3000`. El `frontend` se ha desarrollado usando [AngularJS](https://angularjs.org), [TypeScript](http://typescriptlang.org) y [Sass](http://sass-lang.com/), entre otros. 

El servidor de desarrollo es una aplicación [Node.js](http://nodejs.org/) con [Express](http://expressjs.com/) creada para emular la configuración del entorno de producción, pero no es estrictamente necesaria. Cualquier servidor como Nginx o Apache puede utilizarse para servir el `frontend`.

Para arrancar el `backend` únicamente necesitas tener instalado [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), el resto de dependencias y librerías se descargarán automáticamente en la primera ejecución. Tres sencillos pasos:

	$ cd backend
	$ ./gradlew build
	$ java -jar ./build/libs/cudu-2.0.7.war

Recuerda que los resultados de las pruebas se generar en `build/reports/tests`.

Una vez arrancado el API REST escucha peticiones en `http://localhost:8080`. El pequeño servidor de [Node.js](http://nodejs.org/) que hemos utilizado antes enrutará las peticiones realizadas a `http://localhost:3000/api` a los servicios del backend en el puerto `8080`. Puedes comprobar que ambos funcionan mediate:

	$ curl localhost:8080/health		
	$ curl localhost:3000/api/health
	
Ambas peticiones deberían devolver `{"status":"UP"}`.

El servicio REST está desarrollado utilizando [Spring](http://projects.spring.io/spring-framework) y [JPA](http://projects.spring.io/spring-data-jpa). Aunque se soportan múltiples bases de datos, recomendamos utilizar [PostgreSQL](http://www.postgresql.org). Por defecto viene configurado para usar [HSQLDB](http://hsqldb.org) en memoria y crear todas las tablas necesarias al inicio.

Recuerda que puedes generar archivos de proyecto para distintos IDEs:

	$ ./gradlew idea
	$ ./gradlew eclipse

## Base de datos

Puedes generar una base de datos desde cero usando dos scripts en groovy que hemos preparado:

	$ groovy database.groovy -m cudu_playground
	$ groovy populate.groovy -d cudu_playground

### Migraciones de la base de datos

Cudú utiliza [Flyway](http://flywaydb.org/) para mantener las actualizaciones de la base de datos. Es posible ejecutarlas directamente desde Gradle:

	$ createdb cudu_dev
	$ ./gradlew flywayMigrate -Dflyway.url=jdbc:postgresql://localhost:5432/cudu_dev
	
Los scripts de migración siguen las [convenciones de nombres](http://flywaydb.org/documentation/migration/sql.html) de Flyway y residen en la carpeta `backend/src/main/resources/db/migration`.

Para comprobar el estado de una base de datos existente puedes utilizar la opción `flywayInfo` en lugar de `flywayMigrate`. Hay más información en la documentación de [Flyway para Gradle](http://flywaydb.org/documentation/gradle/).

### jOOQ

Para algunas operaciones utilizamos [jOOQ](http://www.jooq.org/). Si necesitas regenerar el metamodelo a partir de las migraciones de bases de datos, puedes ejecutar:

	$ groovy database.groovy -j org.scoutsfev.cudu.dd

Se creará una base de datos temporal sobre la que ejecutar las migraciones con Flyway. La base de datos se elimina una vez completa el proceso.

## Contacto

Federació d'Escoltisme Valencià  
c/ Balmes, 17. 46001 València, SPAIN  
Télefono +34 963 153 240  
[http://www.scoutsfev.org](http://www.scoutsfev.org)  
Siguenos en Twitter [@scoutsfev](https://twitter.com/scoutsfev)
