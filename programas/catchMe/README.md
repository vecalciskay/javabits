# Catch Me

Este es un programa que es un juego en el que el usuario debe atrapar con el mouse
escurridizas pelotas que están rebotando en la interfase.

<img src="img/practico5.gif" width="450" />

Este programa hace uso de las interfases para controlar el clic del mouse. 
Es un buen ejemplo para ver cómo funcionan estas interfases.

Por otro lado, también se hace uso de multiprocesos (threads) para el movimiento
de las figuras en el plano.

Finalmente, el uso del polimorfismo es evidente para poder mostrar diferentes tipos
de figuras en el mismo plano con las mismas operaciones.

## Diagrama de clases

<img src="img/CatchMeCD.png" width="800" />

### Polimorfismo

La clase ``Figure`` hace de superclase para las clases ``Circle`` y ``Square``. Eso significa que estas
dos clases heredan todo lo que se define en ``Figure``.

Las cosas en común que tienen son la velocidad, posición y la funcionalidad de moverse. Cómo se muevan dependerá de cada uno pero puede
colocarse aquí una forma de moverse por defecto y así evitar determinarlo en las subclases.

Las funcionalidades que son propias de cada objeto son: ``draw()`` y ``isInside()``. Estos dos métodos son visiblemente muy diferentes
entre las diferentes clases, por ello es que cada uno debe definir estos dos métodos para ver cómo se implementa con esa figura en 
particular.

Un método interesante de ambos es probablemente ``isInside``, aquí se ve el método del ``Square`` (cuadrado):

```
	@Override
	public boolean isInside(int x, int y) {
		if (x > (this.x - size) &&
				x < (this.x + size) &&
				y > (this.y - size) &&
				y < (this.y + size))
			return true;
		return false;
	}
```

### Observer

Es muy díficil pensar en hacer este programa sin aplicar correctamente el patrón Observer. El objeto
observado de este programa es el ``GameBoard``. Cada vez que la ubicación de las figuras de la ``Cadena``
cambia de posición entonces se avisa al observador para que este pinte el objeto completo, el tablero completo
con todas las figuras en el lugar en el que estén.

Se puede ver el codigo donde se llama al obserador. Es justamente despues de que todas las figuras se han
moido un poco:

```
	public void move() {
		Iterator<Figure> iter = figures.iterator();
		while(iter.hasNext()) {
			Figure f = iter.next();
			f.moveAlongDirection(this);
		}
		this.setChanged();
		this.notifyObservers();
	}
```

### Estructura de Datos

Para este ejercicio se ha utilizado la estructura de datos de una Cadena (o Lista). Esta cadena se ha realizado con 
generics y tiene la funcionalidad base para poder ingresar figuras y eliminarlas.
