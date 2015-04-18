package q8.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import q8.obj.ChessBoard;

public class BoardPanel extends JPanel implements Observer {

	private final static Logger logger = LogManager.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChessBoard theBoard;
	/**
	 * Create the panel.
	 */
	public BoardPanel() {
		theBoard = null;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof ChessBoard)
			theBoard = (ChessBoard)arg0;
		
		this.repaint();
	}

	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		
		if (theBoard == null) {
			logger.warn("No chessboard to draw");
			return;
		}
		
		theBoard.draw(gc);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(500,500);
	}
}
