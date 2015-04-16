package tp4;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import util.Logger;


public class WinPanal extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JLabel lblRespuesta;

    private PanelDibujo panel;

    /**
     * @param args
     */
    public static void main(String[] args) {
        WinPanal win = new WinPanal();
        win.setVisible(true);
    }

    public WinPanal() {
        super("Grafos y Panal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.init();
        pack();
    }

    private void init() {
        //		 Crear barra menu
        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);
        // Crear menu 
        JMenu m1 = new JMenu("Grafo");
        mb.add(m1);
        JMenuItem mi = new JMenuItem("Nuevo");
        mi.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cmd_nuevo();
                    }
                });
        m1.add(mi);

        mi = new JMenuItem("Preguntar");
        mi.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cmd_preguntar();
                    }
                });
        m1.add(mi);

        m1.addSeparator();

        mi = new JMenuItem("Salir");
        mi.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cmd_salir();
                    }
                });
        m1.add(mi);

        panel = new PanelDibujo();
        this.getContentPane().add(panel, BorderLayout.CENTER);

        this.lblRespuesta = new JLabel("Aquí la respuesta");
        this.getContentPane().add(this.lblRespuesta, BorderLayout.SOUTH);
    }

    protected void cmd_preguntar() {
        String deStr = JOptionPane.showInputDialog(this, "De:");
        int de = Integer.parseInt(deStr);
        String aStr = JOptionPane.showInputDialog(this, "A:");
        int a = Integer.parseInt(aStr);

        int respuesta = panel.getInterfase().caminoMasCorto(de, a);
        panel.repaint();

        this.lblRespuesta.setText(de + " - " + a + " = " + respuesta);
        this.pack();
    }

    protected void cmd_nuevo() {
        String cuantos = 
            JOptionPane.showInputDialog(this, "Numero de celdas?");
        int nb = Integer.parseInt(cuantos);

        Grafo panal = new Grafo(nb, 600, 600, 55);
        panel.setInterfase(panal);
        this.pack();
        Logger.log("Panal creado", Logger.NORMAL);
    }

    protected void cmd_salir() {
        dispose();
        System.exit(0);
    }
}
