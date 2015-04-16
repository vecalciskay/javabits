package ed.hanoi.gui;

import ed.hanoi.objects.Hanoi;

import ed.hanoi.objects.Torre;

import java.awt.Dimension;
import java.awt.Graphics;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class PanelHanoi extends JPanel implements Observer {

    private Hanoi objHanoi;

    public PanelHanoi() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
    }

    public void update(Observable o, Object arg) {
        objHanoi = (Hanoi)o;

        this.repaint();
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);

        if (objHanoi == null) {
            gc.drawString("No hay hanoi", 100, 100);
            return;
        }
        Torre t = objHanoi.getTorre(0);
        DibujoTorre dt = new DibujoTorre(t);
        dt.dibujar(gc, 70, 10);

        t = objHanoi.getTorre(1);
        dt = new DibujoTorre(t);
        dt.dibujar(gc, 190, 10);

        t = objHanoi.getTorre(2);
        dt = new DibujoTorre(t);
        dt.dibujar(gc, 310, 10);
    }
}
