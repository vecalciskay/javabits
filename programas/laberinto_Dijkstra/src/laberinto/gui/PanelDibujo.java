package laberinto.gui;

import estructuras.Grafo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * El panel donde se muestra todo el dibujo del fractal. Este panel lo unico que
 * hace es pintar el dibujo que se encuentra en el modelo, al cual accede a 
 * traves del controlador.
 */
public class PanelDibujo extends JPanel implements Observer {
    
    private final static Logger logger = 
        Logger.getLogger(PanelDibujo.class);
    
    /**
     * La referencia a la ventana superior 
     */
    private VentanaLaberinto padre;
    
    private DibujoGrafo elDibujoGrafo;

    /**
     * El constructor.
     * @param p
     */
    public PanelDibujo() {
        this.padre = null;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicializa el objeto sin niung layout ya que solamente se mostrara una figura.
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setLayout( null );
    }
    
    /**
     * Sobre escribiomos el metodo de getPreferredSize para que cuando se despliegue
     * el panel dibujo los componentes se arreglen para dar el tamano que se necesite
     * @return
     */
    public Dimension getPreferredSize() {
        
        return new Dimension(400, 400);
    }

    /**
     * Este es el metodo al que llama cuando alguno de los modelos cambia
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        if (o instanceof Grafo)
            elDibujoGrafo = new DibujoGrafo((Grafo<DibujoNodo>)o);
        repaint();
    }
    
    /**
     * Dibuja el fractal y si necesita dibuja el cuadro que muestra la seleccion
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        logger.debug("Pinta la imagen del grafo");
        
        if (elDibujoGrafo == null) {
            logger.warn("No existe grafo cargado.");
            return;
        }
        
        elDibujoGrafo.dibujar(g);
    }
}