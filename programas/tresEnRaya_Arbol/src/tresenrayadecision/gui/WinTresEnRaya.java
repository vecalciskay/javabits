package tresenrayadecision.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import tresenrayadecision.TresEnRayaException;

import tresenrayadecision.objects.JuegoTresEnRaya;
import tresenrayadecision.objects.Jugada;

public class WinTresEnRaya extends JFrame implements Observer {
    
    private static Logger logger = Logger.getRootLogger();
    
    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JButton tab0x0 = new JButton();
    private JButton tab0x1 = new JButton();
    private JButton tab0x2 = new JButton();
    private JButton tab1x0 = new JButton();
    private JButton tab1x1 = new JButton();
    private JButton tab1x2 = new JButton();
    private JButton tab2x0 = new JButton();
    private JButton tab2x1 = new JButton();
    private JButton tab2x2 = new JButton();
    private JMenuItem menuFileNuevo = new JMenuItem();

    private JuegoTresEnRaya objJuegoTresEnRaya = null;
    private JLabel numNodosLabel = new JLabel();
    private JLabel numNodosResultLabel = new JLabel();
    private JLabel quienGanoLabel = new JLabel();
    private JLabel quienGanoResultLabel = new JLabel();

    public WinTresEnRaya() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(layoutMain);
        panelCenter.setLayout(null);
        this.setSize(new Dimension(495, 224));
        this.setTitle("Tres En Raya - Arbol de Decision");
        menuFile.setText("Archivo");
        menuFileExit.setText("Salir");
        menuFileExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        fileExit_ActionPerformed(ae);
                    }
                });
        menuHelp.setText("Help");
        menuHelpAbout.setText("About");
        menuHelpAbout.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        helpAbout_ActionPerformed(ae);
                    }
                });
        statusBar.setText("");
        tab0x0.setBounds(new Rectangle(5, 5, 55, 50));
        tab0x0.setFont(new Font("Tahoma", 1, 30));
        tab0x0.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab0x0_actionPerformed(e);
                    }
                });
        tab0x1.setBounds(new Rectangle(65, 5, 55, 50));
        tab0x1.setFont(new Font("Tahoma", 1, 30));
        tab0x1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab0x1_actionPerformed(e);
                    }
                });
        tab0x2.setBounds(new Rectangle(125, 5, 55, 50));
        tab0x2.setFont(new Font("Tahoma", 1, 30));

        tab0x2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab0x2_actionPerformed(e);
                    }
                });
        tab1x0.setBounds(new Rectangle(5, 60, 55, 50));
        tab1x0.setFont(new Font("Tahoma", 1, 30));
        tab1x0.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab1x0_actionPerformed(e);
                    }
                });
        tab1x1.setBounds(new Rectangle(65, 60, 55, 50));
        tab1x1.setFont(new Font("Tahoma", 1, 30));
        tab1x1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab1x1_actionPerformed(e);
                    }
                });
        tab1x2.setBounds(new Rectangle(125, 60, 55, 50));
        tab1x2.setFont(new Font("Tahoma", 1, 30));

        tab1x2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab1x2_actionPerformed(e);
                    }
                });
        tab2x0.setBounds(new Rectangle(5, 115, 55, 50));
        tab2x0.setFont(new Font("Tahoma", 1, 30));
        tab2x0.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab2x0_actionPerformed(e);
                    }
                });
        tab2x1.setBounds(new Rectangle(65, 115, 55, 50));
        tab2x1.setFont(new Font("Tahoma", 1, 30));
        tab2x1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab2x1_actionPerformed(e);
                    }
                });
        tab2x2.setBounds(new Rectangle(125, 115, 55, 50));
        tab2x2.setFont(new Font("Tahoma", 1, 30));
        tab2x2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tab2x2_actionPerformed(e);
                    }
                });
        menuFileNuevo.setText("Nuevo");
        menuFileNuevo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        menuFileNuevo_actionPerformed(e);
                    }
                });
        numNodosLabel.setText("Numero de nodos en ultimo arbol:");
        numNodosLabel.setBounds(new Rectangle(195, 10, 170, 25));
        numNodosResultLabel.setBounds(new Rectangle(375, 10, 105, 25));
        numNodosResultLabel.setFont(new Font("Tahoma", 1, 14));
        quienGanoLabel.setText("Quien gano?");
        quienGanoLabel.setBounds(new Rectangle(200, 55, 115, 25));
        quienGanoResultLabel.setBounds(new Rectangle(355, 55, 95, 25));
        menuFile.add(menuFileNuevo);
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        panelCenter.add(quienGanoResultLabel, null);
        panelCenter.add(quienGanoLabel, null);
        panelCenter.add(numNodosResultLabel, null);
        panelCenter.add(numNodosLabel, null);
        panelCenter.add(tab0x0, null);
        panelCenter.add(tab0x1, null);
        panelCenter.add(tab0x2, null);

        panelCenter.add(tab1x0, null);
        panelCenter.add(tab1x1, null);
        panelCenter.add(tab1x2, null);

        panelCenter.add(tab2x0, null);
        panelCenter.add(tab2x1, null);
        panelCenter.add(tab2x2, null);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);

        nuevoJuego();
        mostrarTablero();
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new WinTresEnRaya_AboutBoxPanel1(), 
                                      "About", JOptionPane.PLAIN_MESSAGE);
    }

    private void tab0x0_actionPerformed(ActionEvent e) {
        movimientoJugador(0, 0);
    }

    private void tab0x1_actionPerformed(ActionEvent e) {
        movimientoJugador(0, 1);
    }

    private void tab0x2_actionPerformed(ActionEvent e) {
        movimientoJugador(0, 2);
    }

    private void tab1x0_actionPerformed(ActionEvent e) {
        movimientoJugador(1, 0);
    }

    private void tab1x1_actionPerformed(ActionEvent e) {
        movimientoJugador(1, 1);
    }

    private void tab1x2_actionPerformed(ActionEvent e) {
        movimientoJugador(1, 2);
    }

    private void tab2x0_actionPerformed(ActionEvent e) {
        movimientoJugador(2, 0);
    }

    private void tab2x1_actionPerformed(ActionEvent e) {
        movimientoJugador(2, 1);
    }

    private void tab2x2_actionPerformed(ActionEvent e) {
        movimientoJugador(2, 2);
    }

    /**
     * Este es el metodo que se ejecuta cuando se crea un nuevo juego cada vez
     * Se puede llamar a este metodo en cualquier momento asi que sireve tambien
     * para resetear el juego
     * @param e
     */
    private void menuFileNuevo_actionPerformed(ActionEvent e) {
        nuevoJuego();

        mostrarTablero();
    }

    /**
     * 
     * @param fila
     * @param columna
     */
    private void movimientoJugador(int fila, int columna) {
        try {
            objJuegoTresEnRaya.nuevoMovimiento(fila, columna, JuegoTresEnRaya.JUGADOR);
        } catch (TresEnRayaException e) {
            logger.error("Error al crear el nuevo movimiento", e);
            JOptionPane.showMessageDialog(this,"El jugador No pudo crear el movimiento");
        }
        
        if (objJuegoTresEnRaya.estaTerminado())
            return;
        
        // ahora juega la computadora
        int[] nuevoMovimiento = objJuegoTresEnRaya.elegirMovimiento(JuegoTresEnRaya.COMPUTADORA);
        try {
            objJuegoTresEnRaya.nuevoMovimiento(nuevoMovimiento[0], nuevoMovimiento[1],
                          JuegoTresEnRaya.COMPUTADORA);
        } catch (TresEnRayaException e) {
            logger.error("Error al crear el nuevo movimiento", e);
            JOptionPane.showMessageDialog(this, "La computadora No pudo crear el movimiento");
        }
    }

    /**
     * Este metodo hace un nuevo juego
     */
    private void nuevoJuego() {       

        objJuegoTresEnRaya = new JuegoTresEnRaya();
        objJuegoTresEnRaya.addObserver(this);
        objJuegoTresEnRaya.setQuienComienza(JuegoTresEnRaya.JUGADOR);
        objJuegoTresEnRaya.setQuienJuega(JuegoTresEnRaya.JUGADOR);
        
    }

    /**
     * Este metodo muestra el tablero del juego
     */
    private void mostrarTablero() {
        Jugada objJugada = null;
        if (! this.objJuegoTresEnRaya.getMovimientos().estaVacia())
            objJugada = this.objJuegoTresEnRaya.getMovimientos().ultimo();

        if (objJugada == null) {
            tab0x0.setEnabled(true);
            tab0x1.setEnabled(true);
            tab0x2.setEnabled(true);
            
            tab1x0.setEnabled(true);
            tab1x1.setEnabled(true);
            tab1x2.setEnabled(true);
            
            tab2x0.setEnabled(true);
            tab2x1.setEnabled(true);
            tab2x2.setEnabled(true);
            
            tab0x0.setText("");
            tab0x1.setText("");
            tab0x2.setText("");
            
            tab1x0.setText("");
            tab1x1.setText("");
            tab1x2.setText("");
            
            tab2x0.setText("");
            tab2x1.setText("");
            tab2x2.setText("");
            return;
        }
        
        if (objJugada.getTablero(0, 0) > 0) {
            tab0x0.setEnabled(false);
            tab0x0.setText(getTexto(objJugada.getTablero(0, 0)));
        }
        if (objJugada.getTablero(0, 1) > 0) {
            tab0x1.setEnabled(false);
            tab0x1.setText(getTexto(objJugada.getTablero(0, 1)));
        }
        if (objJugada.getTablero(0, 2) > 0) {
            tab0x2.setEnabled(false);
            tab0x2.setText(getTexto(objJugada.getTablero(0, 2)));
        }

        if (objJugada.getTablero(1, 0) > 0) {
            tab1x0.setEnabled(false);
            tab1x0.setText(getTexto(objJugada.getTablero(1, 0)));
        }
        if (objJugada.getTablero(1, 1) > 0) {
            tab1x1.setEnabled(false);
            tab1x1.setText(getTexto(objJugada.getTablero(1, 1)));
        }
        if (objJugada.getTablero(1, 2) > 0) {
            tab1x2.setEnabled(false);
            tab1x2.setText(getTexto(objJugada.getTablero(1, 2)));
        }

        if (objJugada.getTablero(2, 0) > 0) {
            tab2x0.setEnabled(false);
            tab2x0.setText(getTexto(objJugada.getTablero(2, 0)));
        }
        if (objJugada.getTablero(2, 1) > 0) {
            tab2x1.setEnabled(false);
            tab2x1.setText(getTexto(objJugada.getTablero(2, 1)));
        }
        if (objJugada.getTablero(2, 2) > 0) {
            tab2x2.setEnabled(false);
            tab2x2.setText(getTexto(objJugada.getTablero(2, 2)));
        }
        
        this.numNodosResultLabel.setText("" + objJuegoTresEnRaya.getNumeroNodosUltima());
        
        if (objJuegoTresEnRaya.estaTerminado()) {
            tab0x0.setEnabled(false);
            tab0x1.setEnabled(false);
            tab0x2.setEnabled(false);
            
            tab1x0.setEnabled(false);
            tab1x1.setEnabled(false);
            tab1x2.setEnabled(false);
            
            tab2x0.setEnabled(false);
            tab2x1.setEnabled(false);
            tab2x2.setEnabled(false);
            
            this.quienGanoResultLabel.setText(getTexto(objJuegoTresEnRaya.getQuienGano()));
        }
    }

    private String getTexto(int jugador) {
        if (jugador == JuegoTresEnRaya.COMPUTADORA)
            return "x";
        else
            return "o";
    }

    public void update(Observable o, Object arg) {
        mostrarTablero();
    }
}
