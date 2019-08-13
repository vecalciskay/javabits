# Practico 1
Se debe desarrollar un programa de ordenamiento mejorado quicksort. 
La fecha de presentación de este práctico es **Viernes 23 de Agosto de 2019**.

## Ordenamiento mejorado quicksort
Este tipo de ordenamiento tiene por base un quicksort pero también
incorpora otros algoritmos para los casos en los cuales quicksort
no es tan efectivo.

## Programa principal
El programa principal debe realizar las siguientes operaciones:

1. Crear un arreglo de 1000000 (un millón) de enteros con números al azar.
2. Clonar este arreglo una vez para tener 2 variables de tipo int[] con
exactamente la misma información.
3. Ejecutar el quicksort tradicional con una de las variables e indicar
el tiempo que tomó la ejecución de la función.
4. Ejecutar el quicksort mejorado con la otra variable e indicar el tiempo
que tomó la ejecución de la función.
5. Indicar las diferencias entre ambos.
6. Tener alguna función que compruebe que lo recibido está correcto 
(ya sea botón o una opción del menú)

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
