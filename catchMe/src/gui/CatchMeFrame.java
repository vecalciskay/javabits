package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import bll.GameBoard;

public class CatchMeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getRootLogger();

	private GamePanel contentPane;
	private StatusPanel statusPane;
	private JMenuBar myMenuBar;
	private JMenu menuFile;
	private JMenuItem menuFile_New;
	private JMenuItem menuFile_Exit;
	private JMenuItem menuFile_Start;

	private GameBoard theGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		String resource = "/auditoria.properties";
		URL configFileResource;
		configFileResource = CatchMeFrame.class.getResource(resource);
		PropertyConfigurator.configure(configFileResource);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CatchMeFrame frame = new CatchMeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CatchMeFrame() {

		initComponents();
	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		this.getContentPane().setLayout(new BorderLayout());
		contentPane = new GamePanel();
		this.getContentPane().add(contentPane, BorderLayout.CENTER);
		statusPane = new StatusPanel();
		this.getContentPane().add(statusPane, BorderLayout.EAST);

		logger.info("Added Game and status panels to frame");

		myMenuBar = new JMenuBar();

		menuFile = new JMenu("Juego");

		menuFile_New = new JMenuItem("Nuevo");
		menuFile_New.setMnemonic(KeyEvent.VK_N);
		menuFile_New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));
		menuFile_New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFile_New_Clicked(e);
			}
		});
		menuFile.add(menuFile_New);

		menuFile_Start = new JMenuItem("Comenzar");
		menuFile_Start.setMnemonic(KeyEvent.VK_C);
		menuFile_Start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.ALT_MASK));
		menuFile_Start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				menuFile_Start_Clicked(e);
			}

		});
		menuFile.add(menuFile_Start);

		menuFile.addSeparator();

		menuFile_Exit = new JMenuItem("Quit");
		menuFile_Exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				menuFile_Exit_Clicked(e);
			}

		});
		menuFile.add(menuFile_Exit);

		myMenuBar.add(menuFile);

		this.setJMenuBar(myMenuBar);

		this.pack();
	}

	protected void menuFile_Exit_Clicked(ActionEvent e) {
		logger.info("Clicked on the exit option, exiting now...");

		this.dispose();
		System.exit(0);
	}

	protected void menuFile_Start_Clicked(ActionEvent e) {

		this.contentPane.addMouseListener(contentPane);

		if (theGame != null) {
			theGame.startAnimation();
			this.statusPane.startTimer();
		}
	}

	protected void menuFile_New_Clicked(ActionEvent e) {

		// Cleanup previous
		if (theGame != null) {
			theGame.deleteObservers();
			theGame.setPleaseStop(true);
			theGame = null;

			statusPane.stopTimer();
		}

		theGame = new GameBoard(GamePanel.WIDTH, GamePanel.HEIGHT);
		String nroFiguras = JOptionPane
				.showInputDialog("Cuantas figuras desea eliminar?");
		int nb = -1;
		try {
			nb = Integer.parseInt(nroFiguras);
			if (nb <= 0 || nb > 30)
				throw new Exception("Solo positivos por favor y menores de 30");
		} catch (NumberFormatException e1) {
			logger.error("Solamente numeros enteros positivos aqui");
			JOptionPane.showMessageDialog(this, "Solamente se aceptan números");
		} catch (Exception e1) {
			logger.error("Se leyo un numero, pero no es positivo o es muy grande");
			JOptionPane.showMessageDialog(this,
					"El número debe ser positivo menor de 30");
		}

		if (nb == -1)
			return;

		theGame.addObserver(contentPane);
		theGame.addObserver(statusPane);

		// input is safe now
		theGame.initBoard(nb);
	}

}
