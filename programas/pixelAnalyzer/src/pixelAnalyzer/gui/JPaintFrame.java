package pixelAnalyzer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileFilter;

import pixelAnalyzer.ed.ListaDoble;
import pixelAnalyzer.obj.Dibujo;
import pixelAnalyzer.obj.ListaDibujos;
import pixelAnalyzer.obj.tool.FiltroGris;
import pixelAnalyzer.obj.tool.FiltroMediano;
import pixelAnalyzer.obj.tool.Lapiz;
import pixelAnalyzer.obj.tool.Linea;
import pixelAnalyzer.obj.tool.Pintador;

public class JPaintFrame extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListaDibujos dibujos;
	private PanelDibujos objPanel;
	private JFrame theFrame;

	public JPaintFrame() {
		theFrame = this;
		init();
	}

	public void init() {
		dibujos = new ListaDibujos();
		
		dibujos.insertarDibujo(new Dibujo(400, 400));
		
		objPanel = new PanelDibujos(dibujos);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(objPanel, BorderLayout.CENTER);

		buildToolbar();
		buildStatus();
		
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            	mnuArchivo_Salir();
            }
        };
        this.addWindowListener(exitListener);
        

		JMenuBar bar = new JMenuBar();

		JMenu mnuArchivo = new JMenu("Archivo");

		JMenuItem mi = new JMenuItem("Nuevo");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_Nuevo();
			}

		});

		mnuArchivo.add(mi);

		mi = new JMenuItem("Cargar");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_Cargar();
			}

		});

		mnuArchivo.add(mi);
		
		mnuArchivo.addSeparator();
		
		mi = new JMenuItem("Salir");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_Salir();
			}
			
		});
		
		mnuArchivo.add(mi);

		bar.add(mnuArchivo);
		
		
		JMenu mnuFiltro = new JMenu("Filtro");

		mi = new JMenuItem("Mediano");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuFiltro_Mediano();
			}

		});

		mnuFiltro.add(mi);
		
		mi = new JMenuItem("Tonos de Gris");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuFiltro_Gris();
			}

		});

		mnuFiltro.add(mi);
		
		bar.add(mnuFiltro);

		this.setJMenuBar(bar);

		this.pack();
	}

	protected void mnuArchivo_Salir() {
		int confirm = JOptionPane.showOptionDialog(theFrame,
                "Está seguro que desea salir?",
                "Confirmación", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
	}

	protected void mnuFiltro_Mediano() {
		FiltroMediano filtro = new FiltroMediano();
		filtro.clicEn(dibujos.getSeleccionado(), 0, 0);
	}
	
	protected void mnuFiltro_Gris() {
		FiltroGris filtro = new FiltroGris();
		filtro.clicEn(dibujos.getSeleccionado(), 0, 0);
	}

	protected void mnuArchivo_Cargar() {

		JFileChooser inputFile = new JFileChooser();
		inputFile.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
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

		BufferedImage img = null;
		try {
			img = ImageIO.read(inputFile.getSelectedFile());
		} catch (IOException e) {

		}

		if (img.getWidth() != dibujos.getSeleccionado().getAncho()
				|| img.getHeight() != dibujos.getSeleccionado().getAlto()) {
			JOptionPane.showMessageDialog(this,
					"Solamente se aceptan imagenes de " + dibujos.getSeleccionado().getAncho()
							+ "x" + dibujos.getSeleccionado().getAlto());
			return;
		}

		// byte[] pixels = ((DataBufferByte)
		// img.getRaster().getDataBuffer()).getData();

		int width = dibujos.getSeleccionado().getAncho();
		int height = dibujos.getSeleccionado().getAlto();
		int[][] pixeles = dibujos.getSeleccionado().getPixeles();

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				pixeles[row][col] = img.getRGB(row, col);
			}
		}

		dibujos.getSeleccionado().notificar();
	}

	private void buildStatus() {
		StatusPanel theStatus = new StatusPanel();
//		for(Dibujo d : dibujos.getLista())
//			d.addObserver(theStatus);

		this.getContentPane().add(theStatus, BorderLayout.SOUTH);
	}

	private void buildToolbar() {
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new GridLayout(0, 2));

		JButton btn = new JButton("Lapiz");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tool_Lapiz();
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("Línea");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tool_Linea();
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("Pintar");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tool_Pintador();
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("Negro");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contexto_Color(Color.black);
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("Rojo");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contexto_Color(Color.red);
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("Verde");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contexto_Color(Color.green);
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("Blanco");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contexto_Color(Color.white);
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("1");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contexto_Grosor(1);
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("2");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contexto_Grosor(2);
			}

		});
		pnlButtons.add(btn);

		btn = new JButton("4");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contexto_Grosor(4);
			}

		});
		pnlButtons.add(btn);

		this.getContentPane().add(pnlButtons, BorderLayout.EAST);
	}

	protected void tool_Linea() {
		dibujos.getSeleccionado().setHerramientaActual(new Linea());
		dibujos.getSeleccionado().notificar();
	}

	protected void tool_Pintador() {
		dibujos.getSeleccionado().setHerramientaActual(new Pintador());
		dibujos.getSeleccionado().notificar();
	}

	protected void contexto_Grosor(int i) {
		dibujos.getSeleccionado().getContextoActual().setGrosor(i);
		dibujos.getSeleccionado().notificar();
	}

	protected void contexto_Color(Color c) {
		dibujos.getSeleccionado().getContextoActual().setElColor(c);
		dibujos.getSeleccionado().notificar();
	}

	protected void tool_Lapiz() {
		dibujos.getSeleccionado().setHerramientaActual(new Lapiz());
		dibujos.getSeleccionado().notificar();
	}

	protected void mnuArchivo_Nuevo() {
		dibujos.getSeleccionado().reset();
	}
}
