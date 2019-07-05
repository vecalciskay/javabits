package dijkstra;

import java.awt.EventQueue;

import dijkstra.gui.DijkstraFrame;


public class Dijkstra {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DijkstraFrame frame = new DijkstraFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
