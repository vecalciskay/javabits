# Practico 7
Se debe presentar el practico el martes 18 de junio de 2019.
## Funcionalidad
El programa debe ser un programa Java de escritorio que muestra una tabla con los datos de una lista de personas. Cada persona debe tener los siguientes campos:
* id: entero unico que se usa como llave
* nombre: nombre completo hasta 200 caracteres
* fechaNacimiento: de tipo fecha
* peso: de tipo decimal 

En Java la interfaz grafica para usar tablas es generalmente JTable de la libreria Swing.

El programa debe permitir el ABM de los datos dentro de la tabla usando el patron de diseño DAO de Java.

Implementar alguna solucion para cuando la tabla tiene 100.000 items para evitar una mala experiencia de usuario.

### ABM
La tabla JTable muestra todos los datos de la tabla en base de datos.
Cuando se hace clic en alguno de los items de la tabla entonces aparece un popup con la informacion de la persona para su edicion y un boton para confirmar los cambios.

En el mismo popup y solamente en modo edicion se puede colocar un boton para eliminar ese item. Es importante que la interfaz exija confirmar la accion cuando se elige eliminar.

En la parte superior de la tabla se debe colocar un boton que permita insertar un nuevo registro. Al hacer clic en este boton el programa abre el mismo popup pero sin datos para que el usuario pueda insertar el nuevo registro.

## Patron DAO
Todos los accesos a la base de datos deben hacerse usando el patron de diseño DAO con la implementacion de los patrones Singleton y Abstract Factory.

## Logs obligatorios
Para todas las clases del proyecto es obligatorio el uso de logs con 
la libreria log4j en versión 2. Si el estudiante no tiene los logs 
la nota será de 0. La recomendación para escribir logs es la siguiente:
 - Ponga mensajes claros en cada log
 - Si es un mensaje de debug significa que es más un mensaje para el desarrollador para control interno.
 - Si es un mensaje de info significa que es más un mensaje para el usuario final.
 - Si es un warning significa que ha habido algun problema pero que el programa puede seguir.
 - Si es un error significa que el programa no puede seguir y se muestra el mensaje al usuario.
 - Concatenar el mensaje de log con toda la información de contexto que sea pertinente

