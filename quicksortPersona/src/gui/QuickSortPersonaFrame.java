package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.swing.JSeparator;

import objects.ListaPersonas;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuickSortPersonaFrame extends JFrame {

	private final static Logger logger = LogManager.getRootLogger();
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private ListaPersonas theList;
	private QuickSortPersonaPanel thePanel;

	/**
	 * Create the frame.
	 */
	public QuickSortPersonaFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mnFileStart = new JMenuItem("Start");
		mnFileStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mnFileStart_actionPerformed(arg0);
			}
		});
		mnFileStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		mnFile.add(mnFileStart);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mnFileExit = new JMenuItem("Exit");
		mnFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnFileExit_actionPerformed(e);
			}
		});
		mnFile.add(mnFileExit);

		thePanel = new QuickSortPersonaPanel();
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
		theList = new ListaPersonas();
		theList.addObserver(thePanel);
	}

	protected void mnFileStart_actionPerformed(ActionEvent arg0) {

		Runnable theWorker = new Runnable() {
			public void run() {
				theList.reset();
				theList.ordenar();
			}
		};

		Thread t = new Thread(theWorker);
		t.start();
	}

}
