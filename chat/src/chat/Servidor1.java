package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.Date;

public class Servidor1 {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			ServerSocket srv = new ServerSocket(8888);
			Socket s = srv.accept();
			
			PrintStream out = new PrintStream(s.getOutputStream());
			BufferedReader in = new BufferedReader(
					new InputStreamReader(s.getInputStream()));
			
			String mensaje = "" + new Date();
			out.println(mensaje);
			
			String cmd = "";
			while(!cmd.equals("fin")) {
				cmd = in.readLine();
				System.out.println("<---[CLT] " + cmd);
			}
			
			s.close();
			srv.close();
		} catch (Exception e) {;}
	}

}
