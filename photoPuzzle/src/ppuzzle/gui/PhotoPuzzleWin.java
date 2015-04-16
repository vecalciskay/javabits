package ppuzzle.gui;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ppuzzle.obj.PicsMatrix;

public class PhotoPuzzleWin extends JFrame {

	private final static Logger logger = LogManager.getRootLogger();
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private PicsMatrix theMatrix;
	private PhotoPuzzlePanel thePanel;

	/**
	 * Create the frame.
	 */
	public PhotoPuzzleWin() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mnFileLoad = new JMenuItem("Load File");
		mnFileLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mnFileLoad_actionPerformed(arg0);
			}
		});
		mnFileLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				InputEvent.CTRL_MASK));
		mnFile.add(mnFileLoad);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mnFileExit = new JMenuItem("Exit");
		mnFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnFileExit_actionPerformed(e);
			}
		});
		mnFile.add(mnFileExit);

		thePanel = new PhotoPuzzlePanel();
		thePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(thePanel);
		thePanel.setLayout(new BorderLayout(0, 0));

		initBoard();
	}

	protected void mnFileExit_actionPerformed(ActionEvent e) {
		logger.info("Exit from program");
		System.exit(0);
	}

	private void initBoard() {
		theMatrix = new PicsMatrix();
		theMatrix.addObserver(thePanel);
		thePanel.setTheMatrix(theMatrix);
	}

	protected void mnFileLoad_actionPerformed(ActionEvent arg0) {
		JFileChooser inputFileChooser = new JFileChooser();

		int returnFile = inputFileChooser.showOpenDialog(this);
		
		if (returnFile == JFileChooser.APPROVE_OPTION) {
			
			try {
				theMatrix.readFile(inputFileChooser.getSelectedFile());
			} catch(Exception e) {
				logger.error("Error reading the file in UI", e);
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
			
			theMatrix.shuffle();
			thePanel.addMouseListener(thePanel);
			thePanel.addMouseMotionListener(thePanel);
		}
	}

}
