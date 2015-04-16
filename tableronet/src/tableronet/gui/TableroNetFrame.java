package tableronet.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tableronet.modelo.Tablero;
import tableronet.net.ObserverEnviar;
import tableronet.net.ThreadRecibir;

/**
 * Esta clase es la interface gráfica principal del programa. Tiene todos los
 * menús necesarios para que se lo pueda manejar.
 * 
 * @author Vladimir
 *
 */
public class TableroNetFrame extends JFrame {

	private static final Logger logger = LogManager.getRootLogger();

	private Tablero elTablero;
	private PanelTablero elPanel;
	private ObserverEnviar conexion;

	public TableroNetFrame() {
		init();
	}

	/**
	 * Aquí se forma el menú, las opciones del menu, se enlazan los observers
	 * del PanelTablero y se anaden los componentes al contentpane
	 */
	private void init() {
		elTablero = new Tablero();
		elPanel = new PanelTablero(elTablero);

		elTablero.addObserver(elPanel);
		logger.info("El panel es observadr de Tablero");

		JMenuBar bar = new JMenuBar();

		JMenu mnu = new JMenu("Archivo");
		JMenuItem mi = new JMenuItem("Esperar conexión");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_EsperarConexion();
			}

		});
		mnu.add(mi);

		mi = new JMenuItem("Conectar");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_Conectar();
			}

		});
		mnu.add(mi);

		mnu.addSeparator();

		mi = new JMenuItem("Salir");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_Salir();
			}

		});
		mnu.add(mi);

		bar.add(mnu);

		setJMenuBar(bar);

		// Ahora paneles y demas
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(elPanel, BorderLayout.CENTER);
		
		this.pack();
	}

	/**
	 * Este metodo solamente nos saca de la aplicación
	 */
	protected void mnuArchivo_Salir() {
		if (conexion != null) {
			conexion.cerrarConexion();
		}
		System.exit(0);
	}

	/**
	 * Este método supone que hay un porgrama que ya está escuchando para que
	 * nosotros nos conectems. El algoritmo es el siguiente: obtener IP:Puerto,
	 * luego conectarse y crear los objetos de tipo ObserverEnviar y
	 * ThreadRecibir a partir de lo que se obtenga. Si no se obtiene nada
	 * indicar el error.
	 */
	protected void mnuArchivo_Conectar() {
		logger.info("Pide ip:puerto para conectarse");

		String ipPuertoInput = JOptionPane
				.showInputDialog("Coloque el ip:puerto en el formato indicado");

		String[] ipPuerto = ipPuertoInput.split(":");
		if (ipPuerto.length != 2) {
			JOptionPane.showMessageDialog(this,
					"Debe colocar ip y puerto separado por :");
			return;
		}

		int leerPuerto = leerPuerto(ipPuerto[1]);
		if (leerPuerto == 0) {
			JOptionPane.showMessageDialog(this,
					"El puerto debe ser un entero mas de 1024");
			return;
		}

		String ip = leerIP(ipPuerto[0]);
		if (ip.equals("ERROR")) {
			JOptionPane.showMessageDialog(this,
					"La ip deben ser 4 numeros enteros menores a 255");
			return;
		}

		try {
			conexion = new ObserverEnviar(ip, leerPuerto);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"No se pudo conectar al servidor " + ip + ":" + leerPuerto);
			return;
		}

		ThreadRecibir objRecibir = null;

		try {
			objRecibir = new ThreadRecibir(conexion.getSck(), elTablero);
		} catch (IOException e) {
			logger.error("No pudo crear la conexion con el otro programa", e);
			JOptionPane
					.showMessageDialog(this,
							"Hubo un error al crear el objeto para recibir, trate de nuevo");
			return;
		}
		
		Thread recibir = new Thread(objRecibir);
		recibir.start();
		
		initPanel();
	}

	private String leerIP(String supuestoIP) {
		String[] numeros = supuestoIP.split("\\.");
		StringBuilder respuesta = new StringBuilder();
		
		if (numeros.length != 4)
			return "ERROR";
		
		for(String unNumero : numeros) {
			int n = -1;
			try {
				n = Integer.parseInt(unNumero);
				
				if (n < 0 || n > 255)
					return "ERROR";
			} catch (Exception e) {
				return "ERROR";
			}
			
			respuesta.append("." + n);
		}
		return respuesta.substring(1).toString();
	}

	private int leerPuerto(String puerto) {
		int n = -1;
		try {
			n = Integer.parseInt(puerto);
			
			if (n < 1024 || n > 65535)
				return 0;
		} catch (Exception e) {
			return 0;
		}
		return n;
	}

	protected void mnuArchivo_EsperarConexion() {
		logger.info("Pide puerto para esperar la conexión");

		String puertoString = JOptionPane
				.showInputDialog("Colocar en qué puerto escuchará por favor:");

		int port = 0;
		try {
			port = Integer.parseInt(puertoString);
			if (port <= 1024 || port > 65000) {
				throw new Exception("Debe colocar un entero");
			}
		} catch (Exception e) {
			logger.error("Slamente numeros en el nro de puerto: "
					+ puertoString, e);
			JOptionPane
					.showMessageDialog(
							this,
							"Debe colocar un número entero positivo mayor a 1024, intente de nuevo por favor");
			return;
		}

		ThreadRecibir objRecibir = null;

		try {
			objRecibir = new ThreadRecibir(port, elTablero);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(this,
							"Hubo un error al crear/esperar la conexión, trate con otro puerto");
			return;
		}

		try {
			conexion = new ObserverEnviar(objRecibir.getClt());
		} catch (IOException e) {
			logger.error("No pudo crear la conexion con el otro programa", e);
			JOptionPane
					.showMessageDialog(this,
							"Hubo un error al crear el objeto para enviar, trate de nuevo");
			return;
		}

		Thread recibir = new Thread(objRecibir);
		recibir.start();
		
		initPanel();
	}

	/**
	 * LE coloca el listener al panel
	 */
	private void initPanel() {		
		elTablero.addObserver(conexion);
	}

}
