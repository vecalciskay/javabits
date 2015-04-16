package laberinto.gui;

import estructuras.Grafo;

import estructuras.Pila;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import laberinto.LaberintoEdd;

import org.apache.log4j.Logger;

public class VentanaLaberinto extends JFrame {
    private BorderLayout layoutMain = new BorderLayout();
    private PanelDibujo panelCenter;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JMenuItem menuFileCargarNodos = new JMenuItem();
    private JMenuItem menuFileCargarArcos = new JMenuItem();
    private JMenu menuGrafo = new JMenu();
    private JMenuItem menuGrafoBFS = new JMenuItem();
    private JMenuItem menuGrafoDijkstra = new JMenuItem();
    
    private DibujoGrafo elDibujoGrafo;

    private static final Logger logger = 
        Logger.getLogger(VentanaLaberinto.class);

    public VentanaLaberinto() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(layoutMain);
       
        this.setSize(new Dimension(400, 300));
        this.setTitle("Laberinto - Estructura de Datos");
        menuFile.setText("Archivo");
        menuFileExit.setText("Salir");
        menuFileExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        fileExit_ActionPerformed(ae);
                    }
                });
        menuHelp.setText("Ayuda");
        menuHelpAbout.setText("Sobre");
        menuHelpAbout.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        helpAbout_ActionPerformed(ae);
                    }
                });
        statusBar.setText("");
        menuFileCargarNodos.setText("Cargar Nodos");
        menuFileCargarNodos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        menuFileCargarNodos_actionPerformed(e);
                    }
                });
        menuFileCargarArcos.setText("Cargar Arcos");
        menuFileCargarArcos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        menuFileCargarArcos_actionPerformed(e);
                    }
                });
        menuGrafo.setText("Grafo");
        menuGrafoBFS.setText("Camino Mas Corto BFS");
        menuGrafoBFS.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        menuGrafoBFS_actionPerformed(e);
                    }
                });
        menuGrafoDijkstra.setText("Camino Mas Corto Dijkstra");
        menuGrafoDijkstra.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        menuGrafoDijkstra_actionPerformed(e);
                    }
                });
        menuFile.add(menuFileCargarNodos);
        menuFile.add(menuFileCargarArcos);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        menuGrafo.add(menuGrafoBFS);
        menuGrafo.add(menuGrafoDijkstra);
        menuBar.add(menuGrafo);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        
        panelCenter = new PanelDibujo();
        panelCenter.setLayout(null);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
        
        inicializarGrafo();
    }
    
    public void inicializarGrafo() {
        Grafo<DibujoNodo> grafo = new Grafo<DibujoNodo>();
        grafo.addObserver(panelCenter);
        this.elDibujoGrafo = new DibujoGrafo(grafo);
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, 
                                      new VentanaLaberinto_AboutBoxPanel1(), 
                                      "About", JOptionPane.PLAIN_MESSAGE);
    }

    private void menuFileCargarNodos_actionPerformed(ActionEvent e) {
        logger.info("Llama a la ventana para cargar el archivo de nodos");

        JFileChooser aFileChooser = new JFileChooser("./");
        int response = aFileChooser.showOpenDialog(this);
        if (response != JFileChooser.APPROVE_OPTION) {
            logger.warn("No eligio ningun archivo o algun error se produjo");
            return;
        }
        File aFile = aFileChooser.getSelectedFile();
        logger.info("Obtiene el archivo: " + aFile.getName());

        try {
            inicializarGrafo();
            elDibujoGrafo.leerNodosLaberinto(aFile);
        } catch (Exception err) {
            logger.error("No pudo leer los nodos del archivo", err);
            JOptionPane.showMessageDialog(this, "El archivo no se puede leer o no esta en el formato correcto");
        }
    }

    private void menuFileCargarArcos_actionPerformed(ActionEvent e) {
        logger.info("Llama a la ventana para cargar el archivo de arcos");

        JFileChooser aFileChooser = new JFileChooser("./");
        int response = aFileChooser.showOpenDialog(this);
        if (response != JFileChooser.APPROVE_OPTION) {
            logger.warn("No eligio ningun archivo o algun error se produjo");
            return;
        }
        File aFile = aFileChooser.getSelectedFile();
        logger.info("Obtiene el archivo: " + aFile.getName());

        try {
            elDibujoGrafo.leerArcosLaberinto(aFile);
        } catch (Exception err) {
            logger.error("No pudo leer los nodos del archivo", err);
            JOptionPane.showMessageDialog(this, "El archivo no se puede leer o no esta en el formato correcto");
        }
        
        elDibujoGrafo.calcularParedes();
    }

    private void menuGrafoBFS_actionPerformed(ActionEvent e) {
        String desde = JOptionPane.showInputDialog("Desde el nodo:");
        String hasta = JOptionPane.showInputDialog("Hasta el nodo:");
        
        int respuesta = elDibujoGrafo.getGrafo().caminoMasCorto(desde, hasta);
        
        JOptionPane.showMessageDialog(this, "El numero de pasos minimo necesario es de: " + respuesta);
    }

    private void menuGrafoDijkstra_actionPerformed(ActionEvent e) {
        String desde = JOptionPane.showInputDialog("Desde el nodo:");
        String hasta = JOptionPane.showInputDialog("Hasta el nodo:");
        
        Pila<Grafo.Nodo<DibujoNodo>> camino = elDibujoGrafo.getGrafo().caminoMasCortoDijkstra(desde, hasta);
        
        elDibujoGrafo.setUltimoCamino(camino);
        panelCenter.repaint();
    }
}
