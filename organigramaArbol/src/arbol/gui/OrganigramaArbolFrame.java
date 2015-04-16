package arbol.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import arbol.obj.Arbol;
import arbol.obj.Persona;

public class OrganigramaArbolFrame extends JFrame {

	private final static Logger logger = LogManager.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arbol<Persona> elArbol;
	private ArbolPanel thePanel;

	/**
	 * Create the frame.
	 */
	public OrganigramaArbolFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Archivo");
		menuBar.add(mnFile);

		JMenuItem mnFileStart = new JMenuItem("Nuevo Nodo");
		mnFileStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mnuFile_AddNodo_actionPerformed(arg0);
			}
		});
		mnFileStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		mnFile.add(mnFileStart);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mnFileExit = new JMenuItem("Salir");
		mnFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnFileExit_actionPerformed(e);
			}
		});
		mnFile.add(mnFileExit);
		
		thePanel = new ArbolPanel(this);
		thePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JScrollPane scroller = new JScrollPane(thePanel);
		scroller.setSize(new Dimension(600,400));
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(scroller, BorderLayout.CENTER);

		initBoard();
	}

	public void mnuFile_AddNodo_actionPerformed(ActionEvent arg0) {
		JTextField fieldNombre = new JTextField(5);
		JComboBox<String> fieldCargo = new JComboBox<String>(new CargosComboBoxModel());

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());
		JPanel myPanelTop = new JPanel();
		myPanelTop.setLayout(new BorderLayout());
		myPanel.add(myPanelTop, BorderLayout.NORTH);
		
		myPanelTop.add(new JLabel("Nombre:"), BorderLayout.NORTH);
		myPanelTop.add(fieldNombre, BorderLayout.CENTER);
		myPanelTop.add(Box.createVerticalStrut(15), BorderLayout.SOUTH); // a spacer
		myPanel.add(new JLabel("Cargo:"), BorderLayout.CENTER);
		myPanel.add(fieldCargo, BorderLayout.SOUTH);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Datos de la persona", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {

			String nombre = fieldNombre.getText();
			int cargo = fieldCargo.getSelectedIndex();
			Persona objNuevo = new Persona(nombre, cargo);
			
			try {
				if (elArbol.getSeleccionadoNodo() == null)
					elArbol.insertar(objNuevo, null);
				else {
					String idPadre = elArbol.getSeleccionadoId();
					elArbol.insertar(objNuevo, idPadre);
				}
			} catch (Exception e) {
				logger.error("No pudo insertar el nodo", e);
				JOptionPane.showMessageDialog(this, "El nodo no pudo ser insertado, coloque todos los datos");
			}
		}
	}

	protected void mnFileExit_actionPerformed(ActionEvent e) {
		logger.info("Exit from program");
		System.exit(0);
	}

	private void initBoard() {
		elArbol = new Arbol<Persona>();
		elArbol.addObserver(thePanel);
		
//		try {
//			Persona p1 = new Persona("Hugo", 2);
//			Persona p2 = new Persona("Paco", 4);
//			Persona p3 = new Persona("Luis", 3);
//			elArbol.insertar(p1, null);
//			elArbol.insertar(p2, p1.getId());
//			elArbol.insertar(p3, p1.getId());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
