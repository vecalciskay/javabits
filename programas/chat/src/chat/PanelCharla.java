package chat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

import guilib.*;

public class PanelCharla extends JPanel implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Este es el tipo para definir cuando una consola recibe datos. Si necesita
	 * un thread aparte para estar verificando si no llegó nada por el socket.
	 */
	public static final int TIPO_ENTRADA = 1;
	/**
	 * Este es el tipo para definir que la consola va a enviar datos. Este tipo
	 * de consolas no necesita un thread ya que es el usuario que determina cuando
	 * se envía algo.
	 */
	public static final int TIPO_SALIDA = 2;
	
	private ConsolePanel consola;
	private Socket com;
	private int tipo;
	private JTextField input;
	private Thread proceso = null;
	private PrintStream out;
	private BufferedReader in;
	private boolean please_stop;
	private boolean testgui;
	private EstadoComunicacion estado;
	private JTalk parent;
	
	/**
	 * Un panel para crear una ventana de chat, se le puede decir que sea de entrada
	 * o de salida. Se tiene que colocar obligatoriamente el tipo de ventan que es.
	 * @param t
	 * @param s
	 * @throws IOException
	 */
	public PanelCharla(int type, Socket s, boolean testONo, EstadoComunicacion est) 
		throws IOException {
		input = null;
		consola = new ConsolePanel();
		consola.setPreferredSize(new Dimension(300,1500));
		JScrollPane jspConsola = new JScrollPane(consola);
		jspConsola.setPreferredSize(new Dimension(300,200));
//		JPanel p1 = new JPanel();
//		p1.add(jspConsola);
		com = s;
		tipo = type;
		testgui = testONo;
		please_stop = false;
		estado = est;
		parent = null;
		
		if (com == null && !testgui) {
			throw new IOException("APPERROR: Socket can not be null");
		}
		if (!testgui) {
		out = new PrintStream(com.getOutputStream());
		in = new BufferedReader(new InputStreamReader(com.getInputStream()));
		}
		
		this.setLayout(new BorderLayout());
		this.add(jspConsola, BorderLayout.CENTER);
		
		// Esto colocara el textfield y el boton en caso de que sea de entrada
		if (tipo == PanelCharla.TIPO_SALIDA) {
			input = new JTextField(30);
			JButton cmdInput = new JButton("Enviar");
			cmdInput.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enviarTexto();
				}
			});
			JPanel psouth = new JPanel();
			psouth.add(input, BorderLayout.CENTER);
			psouth.add(cmdInput, BorderLayout.WEST);
			this.add(psouth, BorderLayout.SOUTH);
		}
		else {
			if (!testgui) {
				proceso  = new Thread(this);
				proceso.start();
			}
		}
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(JTalk parent) {
		this.parent = parent;
	}



	/**
	 * Metodo para enviar un texto que aparezca en el componente input de tipo
	 * JTextField. Esto nada mas envia el string por el socket y refleja esto
	 * en la ventana de la consola.
	 *
	 */
	protected void enviarTexto() {
		String aEnviar = this.input.getText().trim();
		if (aEnviar.equals("")) return;
		
		if (!testgui)
			out.println(aEnviar);
		this.consola.putln("<-- " + aEnviar);
		this.input.setText("");
		if (estado !=  null)
		this.estado.setEstado(EstadoComunicacion.FLECHA_ABAJO);
	}
	
	/**
	 * Este metodo detiene todo. Cierra el socket también.
	 * La forma de detener el thread es haciendo llegar al método run
	 * al final de su ejecución mediante una bandera please_stop.
	 *
	 */
	public void shutdown(boolean fromParent) {
		if (this.tipo == PanelCharla.TIPO_ENTRADA) {
			please_stop = true;
			if (parent != null && !fromParent)
				parent.detener();
		}
		try {
			com.close();
		} catch (IOException e) {;}
	}

	/**
	 * Tratará de leer indefinidamente una linea del socket para poder
	 * desplegarla en la consola.
	 */
	public void run() {
		while(!please_stop) {
			try {
				String linea = in.readLine();
				if (linea == null) throw new IOException("Leyo NULO");
				this.consola.putln("--> " + linea);
				if (estado !=  null)
				this.estado.setEstado(EstadoComunicacion.FLECHA_ARRIBA);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent, "ERROR EN LECTURA");
				please_stop = true;
			}
		}
		this.shutdown(false);
	}
	
	public static void main(String[] args) {
		try {
			
			ServerSocket srv = new ServerSocket(9999);
			System.out.print("Esperando conexion...");
			System.out.flush();
			Socket s = srv.accept();
			System.out.println("OK");
			
			JFrame win = new JFrame("test de chat");
			win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			PanelCharla obj = new PanelCharla(PanelCharla.TIPO_ENTRADA, s, false, null);
			win.getContentPane().add(obj);
			
			win.pack();
			win.setVisible(true);
			
			//srv.close();
		} catch (Exception e) {;}
		
	}
}
