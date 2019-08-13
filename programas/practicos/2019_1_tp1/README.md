# Practico 1
Se debe desarrollar dos programas. Un programa servidor y un programa cliente.
La fecha de presentación de este práctico es **Martes 19 de Marzo de 2019**.
## Servidor
El programa servidor es un programa que se conecta por sockets con sus clientes y les responde lo que los clientes preguntan.
## Cliente
El cliente es un programa que se conecta por sockets con el servidor y le consulta algo. Al recibir la respuesta la muestra en una interfaz gráfica.

El cliente recibe la lista de números que debe ordenar a partir de un archivo que se carga desde la interfaz grafica. El formato del archivo es sencillo, solamente un numero por linea de archivo. Ejemplo:
```sh
2
5
6
84
5462
1
0
57
-65
2
```

## Protocolo
El protocolo es la lista de cosas que un cliente puede consultarle al servidor y cómo le responde el servidor a los clientes. Para este practico el protocolo es el siguiente:
1. El cliente envia una linea para ordenar
```sh
SORT SIMPLE 2,5,6,84,5462,1,0,57,-65,2 
```
2. El servidor responde con la siguiente linea
```sh
10 -65,0,1,2,2,5,6,57,84,5462
```
## Logs obligatorios
Para todas las clases del proyecto es obligatorio el uso de logs con la libreria log4j. La recomendación para escribir logs es la siguiente:
 - Ponga mensajes claros en cada log
 - Si es un mensaje de debug significa que es más un mensaje para el desarrollador para control interno.
 - Si es un mensaje de info significa que es más un mensaje para el usuario final.
 - Si es un warning significa que ha habido algun problema pero que el programa puede seguir.
 - Si es un error significa que el programa no puede seguir y se muestra el mensaje al usuario.
 - Concatenar el mensaje de log con toda la información de contexto que sea pertinente

