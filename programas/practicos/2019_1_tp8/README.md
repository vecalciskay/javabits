# Practico 8
Se debe presentar el practico el viernes 12 de julio de 2019.
## Funcionalidad
El programa debe ser un programa Java de escritorio que permite crear y administrar un arbol y guardar y recargar el arbol desde una base de datos. Cada objeto dentro del arbol deberia tener al menos los siguientes campos:
* id: entero unico que se usa como llave
* nombre: nombre completo hasta 200 caracteres

El programa debe permitir el ABM de los elementos dentro del arbol.

### Tabla para guardar el arbol
Los atributos que debe tener el arbol estan en una sola tabla tbl_arbol:

* id: entero unico que se usa como llave
* nombre: nombre completo hasta 200 caracteres
* idpadre: puede ser nulo y cuando no es nulo contiene el id al padre

Para obtener los hijos del nodo 4:
``` 
SELECT id, nombre, idpadre 
FROM tbl_arbol
WHERE idpadre = 4
```
Para obtener la raiz:
``` 
SELECT id, nombre, idpadre 
FROM tbl_arbol
WHERE idpadre IS NULL
```


## Test
Al principio el programa y la base de datos estan vacias.

Con el programa s epueden crear nodos e hijos (los que se deseen). Luego el programa permite guardar en la base de datos.

Al volver a cargar la aplicacion se puede cargar el arbol creado anteriormente desde la base de datos

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

