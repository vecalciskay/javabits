package chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EsperandoConexion extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTalk parent;

	public EsperandoConexion(JTalk p) {
		parent = p;
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		JProgressBar pb = new JProgressBar();
		pb.setIndeterminate(true);
		pb.setString("Esperando...");
		pb.setStringPainted(true);
		this.getContentPane().add(pb,BorderLayout.CENTER);
		
		JButton cmdCancel = new JButton("Cancelar");
		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		this.getContentPane().add(cmdCancel, BorderLayout.SOUTH);
	}
	
	protected void cancelar() {
		parent.cancelarEspera();
		this.dispose();
		this.setVisible(false);
	}
}
