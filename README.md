# Demo ActiveJDBC

Proyecto para presentar la funcionalidad de ActiveJDBC con Java. ActiveJDBC implementa
el patrón de arquitectura llamado Active Record, llamado así por [Martin Fowler](https://es.wikipedia.org/wiki/Martin_Fowler).
Los Active Record, proporcionan funcionalidades para el manejo del CRUD (Create, Read, Update y Delete) de los datos de datos
con la características que las propiedades de las clases están asociadas directamente con los campos de la base de datos.

La libraría que estamos utilizando es [ActiveJDBC](https://javalite.io/activejdbc), desarrollada por [Javalite](ttps://javalite.io),
de una forma muy simple nos permite implementar el patrón. La ventajas del uso de ActiveJDBC son:

- No es necesario aprender un lenguaje para consulta.
- No es necesario tener un mapeo de los campos de la base de datos con las propiedades del objeto.
- No es necesario de utilizar sesiones.

Para inicializar la aplicación:

`gradlew run`

Para probar la funcionalidad:

http://localhost:7000/api/estudiante/