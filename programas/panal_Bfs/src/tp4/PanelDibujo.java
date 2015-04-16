package tp4;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PanelDibujo extends JPanel {

    private Dibujo interfase;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PanelDibujo() {
        interfase = null;
        setPreferredSize(new Dimension(200, 200));
    }

    public Dibujo getInterfase() {
        return this.interfase;
    }

    /**
     * @param interfase The interfase to set.
     */
    public void setInterfase(Dibujo interfase) {
        this.interfase = interfase;
        setPreferredSize(new Dimension(interfase.getWidth(), 
                                       interfase.getHeight()));
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);

        if (interfase != null) {
            interfase.dibujar(gc);
        }
    }
}
