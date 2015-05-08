package bn.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bn.model.Tablero;
import bn.net.ConexionBatallaNaval;

public class VentanaTablero extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getRootLogger();
	private Tablero elTablero;
	private PanelTablero suPanel;
	private PanelTablero miPanel;
	private ConexionBatallaNaval conexion;
	private JLabel labelStatus;
	
	private int port;
	private ServerSocket theServerSocket;
	
	private JMenuItem mnuEsperarConexion;
	private JMenuItem mnuConectar;

	public VentanaTablero() {
		init();
	}
	
	public void init() {
		
		this.getContentPane().setLayout(new BorderLayout());
		
		elTablero = new Tablero();
		elTablero.inicializarMiTablero();
		
		suPanel = new PanelTablero(500,300, false, this);
		suPanel.setElTablero(elTablero);
		elTablero.addObserver(suPanel);
		
		miPanel = new PanelTablero(500,300, true, this);
		miPanel.setElTablero(elTablero);
		elTablero.addObserver(miPanel);
		
		conexion = null;
		
		crearMenus();
		
		labelStatus = new JLabel();
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(suPanel, BorderLayout.CENTER);
		
		topPanel.add(labelStatus, BorderLayout.SOUTH);
		
		this.getContentPane().add(topPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(miPanel, BorderLayout.CENTER);
		
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	public void setStatus(String mensaje) {
		this.labelStatus.setText(mensaje);
	}
	
	/**
	 * Solamente para crear menus.
	 */
	private void crearMenus() {
		JMenuBar bar = new JMenuBar();

		JMenu mnu = new JMenu("Archivo");
		mnuEsperarConexion = new JMenuItem("Esperar conexión");
		mnuEsperarConexion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_EsperarConexion();
			}

		});
		mnu.add(mnuEsperarConexion);

		mnuConectar = new JMenuItem("Conectar");
		mnuConectar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_Conectar();
			}

		});
		mnu.add(mnuConectar);

		mnu.addSeparator();

		JMenuItem mi = new JMenuItem("Salir");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mnuArchivo_Salir();
			}

		});
		mnu.add(mi);

		bar.add(mnu);

		setJMenuBar(bar);
		
		logger.info("Se crearon los menus para la aplicación");
	}

	protected void mnuArchivo_Salir() {
		
		logger.info("Va a salir de la aplicación");
		
		if (conexion != null) {
			conexion.cerrarConexion();
		}
		System.exit(0);
	}

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
		
		labelStatus.setText("Conectándose con " + ipPuerto[0] + " puerto " + ipPuerto[1]);

		try {
			conexion = new ConexionBatallaNaval(ip, leerPuerto);
			elTablero.setConexion(conexion);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"No se pudo conectar al servidor " + ip + ":" + leerPuerto);
			return;
		}
		
		setStatus("Es su turno, use el mouse en el tablero superior");
		
		deshabilitarOpcionesDeMenuArchivo();
		
		elTablero.setTerminado(false);
		elTablero.setMiTurno(true, null);
		
		logger.info("Comienza a ejecutarse el juego.");
	}
	
	private void deshabilitarOpcionesDeMenuArchivo() {
		mnuEsperarConexion.setEnabled(false);
		mnuConectar.setEnabled(false);
		logger.info("Se deshabilitan los menus de esperar conexion y conectar");
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

		port = 0;
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
		
		labelStatus.setText("Esperando conexión en puerto " + port);
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				esperarConexionYJugar();
			} });
		t.start();
	}
	
	public void esperarConexionYJugar() {
		theServerSocket = null;
		try {
			theServerSocket = new ServerSocket(port);
		} catch (IOException e) {
			logger.error("Error al crear la conexión", e);
			JOptionPane.showMessageDialog(this, "No se pudo crear el objeto para escuchar y crear el socket");
			return;
		}
		
		logger.info("Esperando conexion en el puerto " + port);
		
		Socket clt = null;
		try {
			clt = theServerSocket.accept();
		} catch (Exception e) {
			logger.error("Error al esperar la conexión", e);
			JOptionPane.showMessageDialog(this, "Error al esperar que alguien se conecte");
			return;
		} finally {
			try {
				theServerSocket.close();
			} catch (IOException e) {
				;
			}
		}
		
		logger.info("Se acaban de conectar a este servidor");

		try {
			conexion = new ConexionBatallaNaval(clt);
			elTablero.setConexion(conexion);
		} catch (IOException e) {
			logger.error("No pudo crear la conexion con el otro programa", e);
			JOptionPane
					.showMessageDialog(this,
							"Hubo un error al crear el objeto para enviar, trate de nuevo");
			return;
		}
		
		setStatus("Esperando el tiro del oponente");
		
		deshabilitarOpcionesDeMenuArchivo();
		
		elTablero.setTerminado(false);
		
		elTablero.jugarSuTurno();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (elTablero != null) {
			if (elTablero.isMiTurno())
				labelStatus.setText("Clic en el tablero superior para disparar");
			else
				labelStatus.setText("Esperando el disparo");
		}
	}
}
