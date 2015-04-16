package exampledao.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Rectangle;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class InstallBDFrame extends JFrame {
    private JTextArea helpTextArea = new JTextArea();
    private BorderLayout borderLayout1 = new BorderLayout();

    public InstallBDFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setSize( new Dimension(400, 300) );
        this.setTitle( "Instalar BD" );
        this.getContentPane().add(helpTextArea, BorderLayout.CENTER);
        
        InstallBDFrame.class.getResource("openfile.gif");
    }
}
