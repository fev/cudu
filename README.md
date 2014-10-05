# ¿Qué es Cudú?

Cudú es una aplicación web que permite almacenar y gestionar de forma segura y centralizada datos de agrupaciones juveniles. Cudú ha sido desarrollado con el apoyo de la [Federació d'Escoltisme Valencià](http://www.scoutsfev.org) y un grupo desinteresado de voluntarios.

Cudú es software libre, lo que significa que no has de pagar por su uso y que puedes modificar el programa. La única restricción es que si realizas modificaciones, estas sean tambíén compartidas. Legalmente Cudú se distribuye bajo [licencia AGPLv3](http://www.gnu.org/licenses/agpl-3.0.html).


## ¿Como puedo empezar a usarlo?

Para generar el `frontend` necesitas tener instalado [npm](https://www.npmjs.org/). Una vez lo tengas disponible, puedes lanzar la generación mediante [Gulp](http://gulpjs.com/):

	$ cd frontend
	$ npm install
	$ gulp

Para el `backend` necesitas tener instalado GIT y JDK 7. Tres sencillos pasos:

	$ cd backend
	$ ./gradlew build
	$ java -jar ./build/libs/cudu-2.0.0.war

Pasados unos segundos el sitio de prueba estará disponible en `http://localhost:8080`.

## API de datos

### Autenticación

Comenzaremos por obtener la lista de asociados del usuario `mike`:

	$ curl -i -u mike:whatever localhost:8080/asociado

En la respuesta veremos una cookie:

	Set-Cookie: JSESSIONID=19tdbbwad92s31b7yvidtbi5r8;Path=/

Podemos utilizar ese valor para evitar enviar el usuario y contraseña en cada petición:

	$ curl -b JSESSIONID=19tdbbwad92s31b7yvidtbi5r8 localhost:8080/asociado

### Otras operaciones

Listar asociados de un grupo

	$ curl -i -u mike:whatever localhost:8080/asociado

Listar información sobre el grupo `AK`

	$ curl -i -u mike:whatever localhost:8080/grupo/AK

Obtener el asociado 42

	$ curl -i -u mike:whatever localhost:8080/asociado/42

Crear un nuevo asociado a partir de datos en json

	$ curl -u mike:whatever -w '\n' -i -H "Content-Type: application/json" -X POST --data @nuevo_asociado.json localhost:8080/asociado

Editar el asociado 42 utilizando datos de un archivo externo `asociado42.json`

	$ curl -u mike:whatever -w '\n' -i -H "Content-Type: application/json" -X PUT --data @asociado42.json localhost:8080/asociado/42

Activar un asociado

	$ curl -i -X PUT -u mike:whatever localhost:8080/asociado/1/activar

Para desactivar un asociado cambia el fragmento final de la URL por `desactivar`.


## Contacto

Federació d'Escoltisme Valencià  
c/ Balmes, 17. 46001 València, SPAIN  
Télefono +34 963 153 240  
[http://www.scoutsfev.org](http://www.scoutsfev.org)  
Siguenos en Twitter [@scoutsfev](https://twitter.com/scoutsfev)