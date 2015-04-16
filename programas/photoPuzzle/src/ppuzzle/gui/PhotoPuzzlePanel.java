package ppuzzle.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ppuzzle.obj.PicsMatrix;

public class PhotoPuzzlePanel extends JPanel implements Observer, MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger logger = LogManager.getRootLogger();
	
	private final static int BASEX = 20;
	private final static int BASEY = 20;
	
	private PicsMatrix theMatrix;


	public PhotoPuzzlePanel() {
		theMatrix = null;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof PicsMatrix)
			theMatrix = (PicsMatrix)arg0;
		
		this.repaint();
	}

	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		
		if (theMatrix == null) {
			logger.warn("No pics to draw");
			return;
		}
		
		theMatrix.draw(gc, BASEX, BASEY);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(550,550);
	}
	
	public PicsMatrix getTheMatrix() {
		return theMatrix;
	}
	public void setTheMatrix(PicsMatrix theMatrix) {
		this.theMatrix = theMatrix;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		int[] selectedIndexes = null;
		
		try {
			selectedIndexes = theMatrix.getIndexOfPicInPositionXY(BASEX, BASEY, x, y);
			logger.info("Will move pic in position: " + selectedIndexes[0] + "x"+ selectedIndexes[1]);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Something very wrong when getting the selected pic");
		}
		if (selectedIndexes != null) {
			theMatrix.setSelected(selectedIndexes[0], selectedIndexes[1]);
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		int[] selectedIndexes = null;
		
		try {
			selectedIndexes = theMatrix.getIndexOfPicInPositionXY(BASEX, BASEY, x, y); 
			logger.info("Will switch pic with the one in position " + selectedIndexes[0] + "x"+ selectedIndexes[1]);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Something very wrong when getting the selected pic");
		}
		if (selectedIndexes != null) {
			theMatrix.interChangeSelectedWith(selectedIndexes[0], selectedIndexes[1]);
		}
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		theMatrix.movingSelectedTo(arg0.getX(), arg0.getY());
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

}
