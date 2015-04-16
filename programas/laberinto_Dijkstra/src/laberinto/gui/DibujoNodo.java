package laberinto.gui;

import java.awt.Color;
import java.awt.Graphics;

import java.util.StringTokenizer;

public class DibujoNodo {
    
    private String nombre;
    private int fila;
    private int columna;
    
    private int hayVecino;
    
    private int direccion;
    
    public static final int TOP = 0x0008;
    public static final int BOTTOM = 0x0004;
    public static final int LEFT = 0x0002;
    public static final int RIGHT = 0x0001;
    
    public static final int START = 0x0010;
    public static final int END = 0x00F0;
    
    public DibujoNodo(String n, int f, int c) {
        nombre = n;
        fila = f;
        columna = c;
        hayVecino = 0x0000;
        direccion = 0;
    }
    
    /**
     * Lee un Dibujo Nodo a partir de una linea de un archivo de nodos.
     * El primer token del argumento es el id
     * El segundo token es forzosamente un entero
     * El tercer token es forzosamente un entero
     * @param linea
     */
    public DibujoNodo(String linea) throws Exception {
        StringTokenizer lectorTokens = new StringTokenizer(linea);
        
        nombre = lectorTokens.nextToken();
        fila = Integer.parseInt(lectorTokens.nextToken());
        columna = Integer.parseInt(lectorTokens.nextToken());
    }

    public String getNombre() {
        return nombre;
    }

    public void setFila(int newfila) {
        this.fila = newfila;
    }

    public int getFila() {
        return fila;
    }

    public void setColumna(int newcolumna) {
        this.columna = newcolumna;
    }

    public int getColumna() {
        return columna;
    }
    
    /**
     * Coloca con bitiwse el lugar en el que hay un vecino. Si se da lugar = 0 entonces
     * es como hacer un reset y no hay vecinos en ingun lugar.
     * @param lugar
     */
    public void hayVecinoEn(int lugar) {
        if (lugar == 0)
            hayVecino = 0;
        else 
            hayVecino = hayVecino | lugar;
    }
    
    public void dibujar(Graphics gc, int ancho) {
        gc.setColor(Color.white);
        gc.fillRect((this.columna - 1) * ancho, (this.fila - 1) * ancho, ancho, ancho);
        gc.setColor(Color.gray);
        gc.drawString(this.nombre, (this.columna - 1) * ancho + 5, (this.fila - 1) * ancho + 15);
        
        dibujarParedes(gc, ancho);
        
        if (direccion != 0)
            dibujarDireccion(gc, ancho);
    }


    private void dibujarParedes(Graphics gc, int ancho) {
        // Se lee: si NO hayVecino en TOP
        if ((hayVecino & TOP) == 0) {
            gc.setColor(Color.black);
            gc.drawLine((this.columna - 1) * ancho, (this.fila - 1) * ancho, 
                        (this.columna - 1) * ancho + ancho, (this.fila - 1) * ancho);
        }
        
        if ((hayVecino & BOTTOM) == 0) {
            gc.setColor(Color.black);
            gc.drawLine((this.columna - 1) * ancho, (this.fila - 1) * ancho + ancho, 
                        (this.columna - 1) * ancho + ancho, (this.fila - 1) * ancho + ancho);
        }
        
        if ((hayVecino & LEFT) == 0) {
            gc.setColor(Color.black);
            gc.drawLine((this.columna - 1) * ancho, (this.fila - 1) * ancho, 
                        (this.columna - 1) * ancho, (this.fila - 1) * ancho + ancho);
        }
        
        if ((hayVecino & RIGHT) == 0) {
            gc.setColor(Color.black);
            gc.drawLine((this.columna - 1) * ancho + ancho, (this.fila - 1) * ancho, 
                        (this.columna - 1) * ancho + ancho, (this.fila - 1) * ancho + ancho);
        }
    }

    public void setDireccion(int newdireccion) {
        this.direccion = newdireccion;
    }

    public int getDireccion() {
        return direccion;
    }

    private void dibujarDireccion(Graphics gc, int ancho) {
        gc.setColor(Color.red);
        if (direccion == TOP) {
            gc.drawLine((this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + 2, 
                        (this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + 2 + ancho/2);
            gc.drawLine((this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + 2,
                        (this.columna - 1) * ancho + ancho - 2, (this.fila - 1) * ancho + 4);
            gc.drawLine((this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + 2,
                        (this.columna - 1) * ancho + ancho - 6, (this.fila - 1) * ancho + 4);
        }
        
        if (direccion == BOTTOM) {
            gc.drawLine((this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + ancho - 2, 
                        (this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + ancho - 2 - ancho/2);
            gc.drawLine((this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + ancho - 2,
                        (this.columna - 1) * ancho + ancho - 2, (this.fila - 1) * ancho + ancho - 4);
            gc.drawLine((this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + ancho - 2,
                        (this.columna - 1) * ancho + ancho - 6, (this.fila - 1) * ancho + ancho - 4);
        }
        
        if (direccion == LEFT) {
            gc.drawLine((this.columna - 1) * ancho + 2, (this.fila - 1) * ancho + ancho - 4, 
                        (this.columna - 1) * ancho + 2 + ancho/2, (this.fila - 1) * ancho + ancho - 4);
            gc.drawLine((this.columna - 1) * ancho + 2, (this.fila - 1) * ancho + ancho - 4,
                        (this.columna - 1) * ancho + 4, (this.fila - 1) * ancho + ancho - 2);
            gc.drawLine((this.columna - 1) * ancho + 2, (this.fila - 1) * ancho + ancho - 4,
                        (this.columna - 1) * ancho + 4, (this.fila - 1) * ancho + ancho - 6);
        }
        
        if (direccion == RIGHT) {
            gc.drawLine((this.columna - 1) * ancho + ancho - 2, (this.fila - 1) * ancho + ancho - 4, 
                        (this.columna - 1) * ancho + ancho - 2 - ancho/2, (this.fila - 1) * ancho + ancho - 4);
            gc.drawLine((this.columna - 1) * ancho + ancho - 2, (this.fila - 1) * ancho + ancho - 4,
                        (this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + ancho - 2);
            gc.drawLine((this.columna - 1) * ancho + ancho - 2, (this.fila - 1) * ancho + ancho - 4,
                        (this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + ancho - 6);
        }
        
        if (direccion == END) {
            gc.drawLine((this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + ancho - 4, 
                        (this.columna - 1) * ancho + ancho - 4, (this.fila - 1) * ancho + ancho - 4 - 4);
            gc.drawLine((this.columna - 1) * ancho + ancho - 6, (this.fila - 1) * ancho + ancho - 6,
                        (this.columna - 1) * ancho + ancho - 2, (this.fila - 1) * ancho + ancho - 6);
        }
    }
}
