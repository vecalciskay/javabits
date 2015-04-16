package chat;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class JTalk extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EstadoComunicacion estado;
	private PanelCharla chatIn = null;
	private PanelCharla chatOut = null;
	private JTextField txtHost;
	private JTextField txtPort;
	private JPanel panelesChat;
	private JButton cmdConect;
	private JButton cmdServicio;
	private JButton cmdStop;
	private JPanel pnlUpRight;
	private EsperandoConexion waiting;
	private ServerSocket srvSock1;
	private ServerSocket srvSock2;

	public JTalk() {
		super("Ventana de chat");
		this.addWindowListener(new WindowListener() {
			public void windowIconified(WindowEvent e) {;}
			public void windowDeiconified(WindowEvent e) {;}
			public void windowActivated(WindowEvent e) {;}
			public void windowDeactivated(WindowEvent e) {;}
			public void windowOpened(WindowEvent e) {;}
			public void windowClosing(WindowEvent e) {;}

			public void windowClosed(WindowEvent e) {
				detener();
				dispose();
				setVisible(false);
				System.exit(0);
			}
		});

		// Crear panel con opciones para la conexión
		txtHost = new JTextField(10);
		txtHost.setText("localhost");
		txtPort = new JTextField(4);
		txtPort.setText("9999");
		cmdConect = new JButton("Conectar");
		cmdConect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectarse();
			}
		});
		cmdServicio = new JButton("Esperar conexion");
		cmdServicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				esperar();
			}
		});
		cmdStop = new JButton("Detener todo");
		cmdStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detener();
			}
		});
		cmdStop.setEnabled(false);

		pnlUpRight = new JPanel();
		pnlUpRight.setLayout(new BorderLayout());
		pnlUpRight.add(cmdConect, BorderLayout.NORTH);
		pnlUpRight.add(txtHost, BorderLayout.CENTER);
		pnlUpRight.add(txtPort, BorderLayout.EAST);
		pnlUpRight.add(cmdServicio, BorderLayout.SOUTH);

		JPanel pright = new JPanel();
		pright.setLayout(new BorderLayout());
		// La ventana de estado
		estado = new EstadoComunicacion();
		estado.setEstado(EstadoComunicacion.EN_ESPERA);
		pright.add(estado, BorderLayout.CENTER);
		pright.add(pnlUpRight, BorderLayout.NORTH);
		pright.add(cmdStop, BorderLayout.SOUTH);

		this.getContentPane().add(pright, BorderLayout.EAST);
	}

	protected void detener() {
		if (chatIn != null)
			chatIn.shutdown(true);
		if (chatOut != null)
			chatOut.shutdown(true);
		chatIn = null;
		chatOut = null;
		
		this.getContentPane().remove(panelesChat);

		this.cmdConect.setEnabled(true);
		this.cmdServicio.setEnabled(true);
		this.cmdStop.setEnabled(false);
		
		this.invalidate();
		this.validate();
		this.pack();
	}

	/**
	 * Se acciona cuando se selecciona el botón para crear los sockets de
	 * servidor que servirán para la comunicación. Se necesitan dos.
	 * 
	 */
	protected void esperar() {
		try {
			final int p = Integer.parseInt(txtPort.getText().trim());
			
			System.out.print("conexion en puerto " + p + " y "
					+ (p + 1) + "...");
			System.out.flush();
			
			this.cmdConect.setEnabled(false);
			this.cmdServicio.setEnabled(false);
			waiting = null;
			Runnable worker = new Runnable() {
				public void run() {
					Socket clt1 = null;
					Socket clt2 = null;
						try {
						srvSock1 = new ServerSocket(p);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(JTalk.this,
								"No puede abrir en " + p);
						cancelarEspera();
						return;
					}
					try {
						srvSock2 = new ServerSocket(p + 1);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(JTalk.this,
								"No puede abrir en " + (p + 1));
						cancelarEspera();
						return;
					}
					try {
						clt1 = srvSock1.accept();
						clt2 = srvSock2.accept();
					} catch (IOException e1) { 
						return;}
					try {
						srvSock1.close();
					} catch (IOException e) {
						;
					}
					try {
						srvSock2.close();
					} catch (IOException e) {
						;
					}

					if (clt1 != null && clt2 != null)
						crearPanelesConSockets(clt1, clt2);
					else {
						JOptionPane.showMessageDialog(JTalk.this,
								"Los sockets no se pudieron conectar");
						cancelarEspera();
					}

					if (waiting != null) {
						waiting.dispose();
						waiting.setVisible(false);
					}
				}
			};
			Thread waiter = new Thread(worker);
			waiter.start();
			
			waiting = new EsperandoConexion(this);
			waiting.pack();
			waiting.setVisible(true);
			
			System.out.println("OK");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	protected void conectarse() {
		String h = txtHost.getText().trim();
		int p = Integer.parseInt(txtPort.getText().trim());
		Socket clt1 = null;
		Socket clt2 = null;

		try {
			clt1 = new Socket(h, p);
			clt2 = new Socket(h, p + 1);
			// Tener mucho cuidado de intercambiar prque si no no unciona
			this.crearPanelesConSockets(clt2, clt1);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(this,
			"No existe o no puede resolver ese host");
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
			"No pudo abrir el socket");
			return;
		}
		this.cmdConect.setEnabled(false);
		this.cmdServicio.setEnabled(false);
		this.cmdStop.setEnabled(true);

	}

	/**
	 * Este método nada más crea los paneles necesarios para crear las ventanas
	 * de chat que se adhieren a la aplicación.
	 * 
	 * @param clt1
	 *            Socket para la entrada/salida de datos
	 * @param clt2
	 *            Socket para la entrada/salida de datos
	 */
	private void crearPanelesConSockets(Socket clt1, Socket clt2) {
		// Tener mucho cuidado de intercambiar prque si no no unciona
		try {
			chatIn = new PanelCharla(PanelCharla.TIPO_ENTRADA, clt1, false,
					this.estado);
			chatOut = new PanelCharla(PanelCharla.TIPO_SALIDA, clt2, false,
					this.estado);
			chatIn.setParent(this);
			chatOut.setParent(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panelesChat = new JPanel();
		panelesChat.setLayout(new BorderLayout());
		panelesChat.add(chatIn, BorderLayout.NORTH);
		panelesChat.add(chatOut, BorderLayout.SOUTH);
		this.getContentPane().add(panelesChat, BorderLayout.CENTER);
		this.invalidate();
		this.validate();
		this.cmdConect.setEnabled(false);
		this.cmdServicio.setEnabled(false);
		this.cmdStop.setEnabled(true);
		this.pack();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JTalk obj = new JTalk();

		obj.pack();
		obj.setVisible(true);
	}

	public void cancelarEspera() {
		if (srvSock1 != null)
			try {
				this.srvSock1.close();
			} catch (IOException e) {
				;
			}
		if (srvSock2 != null)
			try {
				this.srvSock2.close();
			} catch (IOException e) {
				;
			}
		this.cmdConect.setEnabled(true);
		this.cmdServicio.setEnabled(true);
		this.cmdStop.setEnabled(false);

		if (waiting != null) {
			waiting.dispose();
			waiting.setVisible(false);
		}
	}

}
