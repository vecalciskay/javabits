package pixelAnimator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import pixelAnimator.obj.Animator;

public class JAnimatorFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getRootLogger();

	private JAnimatorPanel panel;
	private Animator objAnimator;

	public JAnimatorFrame() {

		init();
	}

	private void init() {

		
		objAnimator = new Animator(null, null);
		panel = new JAnimatorPanel();
		objAnimator.addObserver(panel);
		logger.info("Crea el panel y la relacion con el objeto animator y lo inscribe como observer");
		
		logger.info("Creando el menú");
		
		WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                mnuArchivo_Salir();
            }
        };
        this.addWindowListener(exitListener);

        JMenuBar bar = new JMenuBar();

        JMenu mnuArchivo = new JMenu("Archivo");
		
		JMenuItem mi = new JMenuItem("Cargar");
        mi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                mnuArchivo_Cargar();
            }

        });

        mnuArchivo.add(mi);
        
        JMenu mnuAnimacion = new JMenu("Animacion");
        
        mi = new JMenuItem("Izquierda a Derecha");
        mi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                mnuAnimacion_IzquierdaDerecha();
            }

        });

        mnuAnimacion.add(mi);
        
        bar.add(mnuArchivo);
        bar.add(mnuAnimacion);
        
        this.setJMenuBar(bar);
        
        logger.info("Layout de contenedores y paneles");
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(panel);
        
        this.pack();
	}

	protected void mnuAnimacion_IzquierdaDerecha() {
		logger.info("Clic en la animacion izquierda a derecha");
		
		objAnimator.setAnimador("IZQUIERDADERECHA");
		
		objAnimator.initAnimacion();
	}

	protected void mnuArchivo_Salir() {
		logger.info("Clic en salir, entonces chau");
		System.exit(0);
	}

	protected void mnuArchivo_Cargar() {
		logger.info("Clic en cargar archivo");

		JFileChooser inputFile = new JFileChooser(".");
		inputFile.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String extension = f.getAbsolutePath().substring(
						f.getAbsolutePath().length() - 4);
				extension = extension.toLowerCase();
				return extension.equals(".jpg") || extension.equals(".gif")
						|| extension.equals(".png");
			}
		});
		inputFile.showOpenDialog(this);

		if (inputFile.getSelectedFile() == null) {

			JOptionPane.showMessageDialog(this, "Debe elegir una imagen");
			return;
		}

		try {
			objAnimator.cargarImagen(inputFile.getSelectedFile());
		} catch (Exception q) {
			JOptionPane.showMessageDialog(this,
					"No se pudo leer la imagen, por favor elegir otra");
		}
	}

}
