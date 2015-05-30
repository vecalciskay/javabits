# Pequeños y muy simples programas en Java

Aquí se pueden ver programas sencillos que sirven para que nos podamos guiar en algunas tareas sencillas 
de Java.

## Paquete vcy.ej1

En una lista de enteros cuántas veces aparece un número en la lista?

El output del programa es:
```
El 47 aparece 19 veces en la lista
```

## Paquete vcy.ej2

Observer para indicar cuándo una cuenta de banco tiene un premio por depósito o no.

El output del programa es:
```
Se han depositado 1500.0
La cuenta tiene: 1600.0
Se han retirado 200.0
La cuenta tiene: 1400.0
Se han depositado 400.0
La cuenta tiene: 1800.0
------------- Con Premio ---------------
Se han depositado 800.0
Se ha dado a la cuenta un premio de 26.0 por el deposito de 800.0
La cuenta tiene: 2626.0
Se han depositado 300.0
No hay premio por un deposito pequeno
La cuenta tiene: 2926.0
```

## Paquete vcy.ej3

Cómo hacer para dibujar un círculo lleno con letras. Se ve cómo utilizar la fórmula de círculo y hacer la conversión
entre sistemas de coordenadas

El output del programa es:
```
0000000000
0001111100
0011111110
0111111111
0111111111
0111111111
0111111111
0111111111
0011111110
0001111100
```

## Paquete vcy.ej4

El patrón de diseño Strategy se ve implementado en cómo crear mejoras para el motor de un auto.

El output del programa es:
```
1. Vel max: 250
2. Vel max: 252
3. Vel max: 251
4. Vel max: 253
```

## Paquete vcy.ej5

Es un programa extremadamente simple para crear objetos a partir de familias de arreglos paralelos.

El output del programa es:
```
--------- 0 ----------
Toro, Hugo
oguH
El email de la persona es nombre@corporacion.com
--------- 1 ----------
Gonzalez, Paco
ocaP
La direccion no tiene ningun email valido
--------- 2 ----------
Guardia, Luis
siuL
El email de la persona es nombres@corporacion.com
--------- 3 ----------
Zenzano, Daisy
ysiaD
El email de la persona es gallo.pato@microsoft.com
--------- 4 ----------
Claros, Donald
dlanoD
La direccion no tiene ningun email valido
```

## Paquete vcy.ej6

Es un programa para ver el uso de expresiones regulares en Java. El programa avisa si un texto tiene
un mail. 

El output del programa es:
```
Este ejercicio muestra las posibilidades de regex (Expresiones regulares)
Para revisar si una expresión es un email por ejemplo: [a-zA-Z0-9\._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}
vladimir.calderon@gmail.com ----> OK
www.yahoo.com ----> ERROR
gabriel@hotmail.com ----> OK

PAra revisar si un texto lleva un correo electrónico:
-----------------------------------------------------------
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque et magna nec augue malesuada
posuere vel vitae eros. Ut nec vladimir.calderon@gmail.com est a est semper vestibulum eget  
Integer iaculis ullamcorper metus, quis blandit dolor commodo a. danielVelasquez@cs.math.edu 
purus venenatis congue. Aenean eu neque lorem. Morbi convallis, mi nec elementum laoreet, 
elit sapien accumsan orci, eu finibus arcu ex eu neque. Nam eu eros quis ante sollicitudin 
semper. Etiam federico56@hotmail.com nulla lectus mattis ac mauris. Morbi elementum molestie
lorem, eu commodo ex dapibus vel.
-----------------------------------------------------------
Email Nro 1
Desde posicion: 125 hasta: 152
Mail: vladimir.calderon@gmail.com
Email Nro 2
Desde posicion: 253 hasta: 280
Mail: danielVelasquez@cs.math.edu
Email Nro 3
Desde posicion: 479 hasta: 501
Mail: federico56@hotmail.com
```
