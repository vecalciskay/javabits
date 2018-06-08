package parser.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import parser.obj.ArbolBinario;
import parser.obj.Aritmetico;
import parser.obj.Evaluador;

public class ParserFrame  extends JFrame implements KeyListener {

		private final static Logger logger = LogManager.getRootLogger();
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArbolBinario<Aritmetico> theTree;
		private PanelDibujo thePanel;
		private JTextField expressionBox;
		private JLabel evaluationLabel;

		/**
		 * Create the frame.
		 */
		public ParserFrame() {
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu mnFile = new JMenu("File");
			menuBar.add(mnFile);
			
			JMenuItem mnFileExit = new JMenuItem("Exit");
			mnFileExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mnFileExit_actionPerformed(e);
				}
			});
			mnFile.add(mnFileExit);
			
			theTree = new ArbolBinario<Aritmetico>();
			
			getContentPane().setLayout(new BorderLayout(10, 10));
			
			expressionBox = new JTextField();
			getContentPane().add(expressionBox, BorderLayout.NORTH);
			
			thePanel = new PanelDibujo();
			thePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			JScrollPane scroller = new JScrollPane(thePanel,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroller.setPreferredSize(new Dimension(600,400));
			getContentPane().add(scroller, BorderLayout.CENTER);
			
			evaluationLabel = new JLabel();
			evaluationLabel.setText("R: ");
			evaluationLabel.setForeground(Color.black);
			evaluationLabel.setBackground(Color.white);
			evaluationLabel.setPreferredSize(new Dimension(400,50));
			getContentPane().add(evaluationLabel, BorderLayout.SOUTH);
			
			initBoard();
			
			this.pack();
		}

		protected void mnFileExit_actionPerformed(ActionEvent e) {
			logger.info("Exit from program");
			System.exit(0);
		}

		private void initBoard() {
			expressionBox.addKeyListener(this);
			theTree.addObserver(thePanel);
			thePanel.setTheTree(theTree);
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if (theTree != null) {
				Evaluador obj = new Evaluador(theTree);
				try {
					obj.leerExpresion(expressionBox.getText());
					evaluationLabel.setText("R: " + Double.toString(obj.evaluar()));
				} catch (Exception e) {
					evaluationLabel.setText("Error en la expresión. No olvide los paréntesis del principio. Pruebe con (3+ 5)");
				}
				
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
}
