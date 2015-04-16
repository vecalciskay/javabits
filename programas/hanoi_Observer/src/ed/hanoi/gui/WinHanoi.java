package ed.hanoi.gui;

import ed.hanoi.objects.Hanoi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class WinHanoi extends JFrame {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenuItem menuFileNuevo = new JMenuItem();
    private JMenuItem menuFileHacer = new JMenuItem();

    private PanelHanoi pnlHanoi;
    private Hanoi objHanoi;
    
    private static Logger logger =
            Logger.getLogger(WinHanoi.class);

    public static void main(String[] args) {
        String resource =
                "/auditoria.properties";
        URL configFileResource =
                WinHanoi.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);
    
        WinHanoi w = new WinHanoi();

        w.setVisible(true);
        w.pack();
    }

    public WinHanoi() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(new BorderLayout());

        this.setTitle("Hanoi");
        menuFile.setText("File");

        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        fileExit_ActionPerformed(ae);
                    }
                });

        menuFileNuevo.setText("Nuevo");
        menuFileNuevo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        fileNuevo_ActionPerformed(ae);
                    }
                });
        menuFile.add(menuFileNuevo);

        menuFile.add(menuFileHacer);
        menuFile.add(menuFileExit);
        menuFileHacer.setText("Hacer");
        menuFileHacer.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        fileHacer_ActionPerformed(ae);
                    }
                });

        menuBar.add(menuFile);

        pnlHanoi = new PanelHanoi();
        this.getContentPane().add(pnlHanoi, BorderLayout.CENTER);
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        this.setVisible(false);
        System.exit(0);
    }

    void fileNuevo_ActionPerformed(ActionEvent e) {
        String nb = JOptionPane.showInputDialog("Ingrese el número de discos");        
        int nDiscos = Integer.parseInt(nb);
        
        logger.debug("Hace nuevo hanoi con " + nDiscos + " discos");
        objHanoi = new Hanoi(nDiscos);
        
        logger.debug("Le añade el observadore panelHanoi: " + pnlHanoi.toString());
        objHanoi.addObserver(pnlHanoi);
        
        logger.debug("lo marca como iniciado para que sea pintado");
        objHanoi.iniciado();
    }

    void fileHacer_ActionPerformed(ActionEvent e) {
        logger.debug("Comienza el thread con " + objHanoi.toString());
        Runnable worker = new Runnable() {
                public void run() {
                    objHanoi.hacer();
                }
            };
        Thread t = new Thread(worker);
        t.start();
    }
}
