# Practico 3
Se debe desarrollar dos programas. Un programa servidor y un programa 
cliente. La fecha de presentación de este práctico es 
**Martes 16 de Abril de 2019**.

### Servidor
El programa servidor es un programa que se conecta por sockets con sus 
clientes y les responde lo que los clientes preguntan.

### Cliente
El cliente es un programa que se conecta por sockets con el servidor 
y le consulta algo. Al recibir la respuesta la muestra en una interfaz 
gráfica.

## Funcionalidad
El programa servidor debe realizar las siguientes operaciones:

1. Comenzar el servidor
2. Leer la consulta que llega del cliente
3. En la consulta está la RUTA del archivo con la imagen a procesar.
4. En la consulta está el proceso a realizar sobre el archivo
5. Leer el archivo, realizar el proceso indicado sobre la imagen
6. Enviar la imagen al cliente

El programa cliente debe realizar las siguientes operaciones:

1. Conectar con el servidor (ya sea un botón o una opción del menú)
2. El programa debe indicar que ya se ha conectado con el servidor
3. Deben haber 3 menús: Normal, Gris, Floyd Steinberg
4. En cada uno de los menús hay 3 ítems: rhino, palestina y trump. El
item rhino en el menú Gris debe llamar el servicio para devolver esa 
imagen en tonos de gris. Así se tendrá tres funcionalidades para cada
una de las 3 imágenes.

### Los archivos imagen

Cada una de las 3 imágenes se encuentra también en este repositorio:

<img src="img/rhino.png" width="200" />
<img src="img/palestina.png" width="200" />
<img src="img/trump.png" width="200" />

En la carpeta img usted puede bajarlas. Las imágenes tienen todas el
mismo tamaño de 800x500.

## Protocolo

### Consulta
El cliente debe enviar la consulta de la siguiente manera:

```
GET /rhino.png?operacion=gris
```

En operación se puede utilizar las palabras gris, normal y fs

### Respuesta
La respuesta del servidor debe ser bien específica. Si es correcta debe 
ser la siguiente:

```
HTTP/1.1 200 OK
Content-Type: image/png

bytes de la imagen
```
Para poder enviar esta información en exactamente ese formato se puede 
usar el código de ejemplo del programa server2017tpImagen:

```java
String statusLine = "HTTP/1.1 200 OK";
String contentType = "Content-Type: image/png";

out.writeBytes(statusLine);
out.writeBytes(CRLF);
out.writeBytes(contentType);
out.writeBytes(CRLF);

// Linea vacia
out.writeBytes(CRLF);

// Aqui el archivo y/o imagen
BufferedImage bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
WritableRaster raster = (WritableRaster)bi.getRaster();

int[] rasterPixels = transformarPuntos();
raster.setPixels(0, 0, ancho, alto, rasterPixels);

try {
	ImageIO.write(bi, "png", out);
} catch (IOException e) {
	e.printStackTrace();
}
```
Fijese que para tener la imagen en la variable bi se usa el transformarPuntos()
que se encuentra en el programa VerySimpleImageWriter.

De igual manera fijarse que se escrib directamente en el socket con el
ImageIO.write(..)

El cliente, cuando lee la respuesta debe ser capaz de sacar los primeros bytes
hasta que encuentre dos \r\n seguidos (es hasta la linea vacía) y luego tiene que 
leer los bytes desde donde comienza el archivo que el servidor envía.

El cliente, habiendo identificado el archivo, ya puede reemplazarlo a la
imagen actual y repintar el cuadro para que se vea la nueva imagen
cargada.

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

