package tresenrayadecision.objects;

import org.apache.log4j.Logger;

import tresenrayadecision.TresEnRayaException;

public class Jugada {

    private static Logger logger = Logger.getRootLogger();

    /**
     * La referencia al tablero se debe hacer por fila y columna
     */
    private int[][] tablero;

    /**
     * Constructor vacio, crea una jugada sin movimientos
     */
    public Jugada() {
        tablero = new int[3][3];
    }

    /**
     * Marca una jugada con la marca del jugador en el lugar indicado. 
     * @param fila
     * @param columna
     * @param jugador
     */
    public void marcar(int fila, int columna, int jugador) throws TresEnRayaException {
        if (tablero[fila][columna] != 0)
            throw new TresEnRayaException("No se puede colocar una marca donde ya hay una");
        tablero[fila][columna] = jugador;
    }

    /**
     * Tal como se indica devuelve un duplicado de la jugada.
     * @return
     */
    public Jugada clonar() {
        Jugada clon = new Jugada();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                clon.setTablero(i, j, this.getTablero(i, j));

        return clon;
    }

    /**
     * Devuelve el valor del tablero (jugada) en la coordenada especificada.
     * @param fila
     * @param columna
     * @return el valor puede ser JUGADOR, COMPUTADORA o 0
     */
    public int getTablero(int fila, int columna) {
        return tablero[fila][columna];
    }

    /**
     * Coloca el valor indicado por el parametro jugador en la coordenada fila, columna
     * @param fila
     * @param columna
     * @param jugador
     */
    public void setTablero(int fila, int columna, int jugador) {
        tablero[fila][columna] = jugador;
    }

    /**
     * Indica si la configuracion del tablero actual indica que el jugador pasado en parametro
     * ha ganado la partida. Se revisa si tiene 3 posiciones seguidas a lo vertical, horizontal o 
     * en las 2 diagonales.
     * @param jugador
     * @return
     */
    public boolean haGanado(int jugador) {
        // revisa las filas
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == jugador && tablero[i][1] == jugador && 
                tablero[i][2] == jugador)
                return true;
        }

        // revisa las columnas
        for (int i = 0; i < 3; i++) {
            if (tablero[0][i] == jugador && tablero[1][i] == jugador && 
                tablero[2][i] == jugador)
                return true;
        }

        // y las diagonales
        if (tablero[0][0] == jugador && tablero[1][1] == jugador && 
            tablero[2][2] == jugador)
            return true;

        if (tablero[0][2] == jugador && tablero[1][1] == jugador && 
            tablero[2][0] == jugador)
            return true;

        return false;
    }

    /**
     * Dada una jugada inmediatamente posterior a la actual, el objetivo del metodo
     * es obtener las coordenadas que nos llevaran de la configuracion actual 
     * a la configuracion pasada en parametro.
     * @param objetivo
     * @return
     */
    public int[] coordenadaParaLlegarA(Jugada objetivo) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (objetivo.getTablero(i, j) > tablero[i][j]) {
                    int[] resultado = new int[2];
                    resultado[0] = i;
                    resultado[1] = j;

                    return resultado;
                }
            }
        int[] resultado = new int[2];
        resultado[0] = -1;
        resultado[1] = -1;
        return resultado;
    }

    /**
     * Solamente se obtienen las posibles jugadas cuando el anterior jugador no esta
     * en posicion de haber ganado
     * @param jugador
     * @return
     */
    public Cadena<Jugada> getPosiblesJugadas(int jugador) {
        Cadena<Jugada> result = new Cadena<Jugada>();
        if (haGanado(jugador == JuegoTresEnRaya.COMPUTADORA ? JuegoTresEnRaya.JUGADOR : JuegoTresEnRaya.COMPUTADORA)) {
            return result;
        }
                
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == 0) {
                    Jugada nueva = this.clonar();

                    try {
                        nueva.marcar(i,j, jugador);
                    } catch (TresEnRayaException e) {
                        logger.error("Esto nunca puede pasar ya que hemos probado antes en el if", e);
                    }
                    result.insertar(nueva);
                }
            }
        
        return result;
    }
    
    public String toString() {
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res.append(" " + tablero[i][j] + " ");
            }
            res.append("\n");
        }
        
        return res.toString();
    }
}
