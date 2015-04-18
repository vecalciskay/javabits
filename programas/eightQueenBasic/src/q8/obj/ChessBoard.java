/**
 * 
 */
package q8.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import q8.gui.Drawable;

/**
 * Esta clase representa un tablero de ajedrez que tiene la mtriz de enteros que 
 * indica donde estan las reinas y tambien avisa a todos cuando se colocan y sacan reinas
 * @author vladimir
 *
 */
public class ChessBoard extends Observable implements Drawable {

	private final static Logger logger = LogManager.getRootLogger();
	
	private int[][] grid;
	 
	public ChessBoard(int size) {
		logger.info("Ha creado un objeto chessbard de " + size);
		grid = new int[size][size];
	}

	public void putQueen(int i, int j) {
		grid[i][j] = 1;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void removeQueen(int i, int j) {
		grid[i][j] = 0;
		this.setChanged();
		this.notifyObservers();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			logger.warn("Program threw an exception when thread waiting in sleep");
			e.printStackTrace();
		}
	}

	public boolean queensInSameRowCheck(int row, int col) {
		for (int i = 0; i < col; i++) {
			if (grid[row][i] == 1)
				return true;
		}
		return false;
	}

	public boolean queensInDiagonalsOnLeft(int row, int col) {
		// Left top diagonal
		for (int i = row, j = col; i>=0 && j>=0; i--,j--) {
			if (grid[i][j] == 1)
				return true;
		}
		
		// Left bottom diagonal
		for (int i = row, j= col; i<grid.length && j>=0; i++, j--) {
			if (grid[i][j] == 1)
				return true;
		}
		
		return false;
	}

	public boolean boardFinalCheck() {
		
		for (int col = 0; col < grid.length; col++) {
			for (int row = 0; row < grid.length; row++) {
				if(grid[row][col] == 1) {
					if (!queenInRightPlace(row, col))
						return false;
				}
			}
		}
		return true;
	}

	private boolean queenInRightPlace(int row, int col) {
		for (int i = 0; i < grid.length; i++) {
			if (grid[row][i] == 1 && i != col)
				return false;
		}
		
		for (int i = 0; i < grid.length; i++) {
			if (grid[i][col] == 1 && i != row)
				return false;
		}
		
		for(int i = row, j=col; i>=0 && j>=0; i--,j--) {
			if (grid[i][j] == 1 && i!=row && j!=col)
				return false;
		}

		for (int i = row, j = col; i<grid.length && j<grid.length ; i++,j++) {
			if (grid[i][j] == 1 && i!=row && j!=col)
				return false;
		}
		
		for(int i = row, j=col; i<grid.length && j>=0; i++,j--) {
			if (grid[i][j] == 1 && i!=row && j!=col)
				return false;
		}
		
		for(int i = row, j=col; i>=0 && j<grid.length; i--,j++) {
			if (grid[i][j] == 1 && i!=row && j!=col)
				return false;
		}
		
		return true;
	}
	
	public boolean placeQueenInBoard(int col) {
		if (col >= grid.length)
			return true;
		
		for (int i = 0; i < grid.length; i++) {
			if(possibleValidPosition(i, col)) {
				this.putQueen(i, col);
				if (placeQueenInBoard(col+1))
					return true;
				this.removeQueen(i, col);
			}
		}
		
		return false;
	}

	private boolean possibleValidPosition(int row, int col) {
		
		if (this.queensInSameRowCheck(row, col) ||
			this.queensInDiagonalsOnLeft(row, col)) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				result.append(grid[i][j] + " ");
			}
			result.append("\n");
		}
		
		return result.toString();
	}
	
	public void draw(Graphics gc) {
		
		int startX = 50;
		int startY = 50;
		
		int w = 50;
		int h = 50;
		int x = 0;
		int y = startY;
		
		for (int i = 0; i < grid.length; i++) {
			x = startX;
			for (int j = 0; j < grid.length; j++) {
				if ((i+j)%2 == 0)
					gc.setColor(Color.white);
				else
					gc.setColor(Color.lightGray);
				
				gc.fillRect(x, y, w, h);
				gc.setColor(Color.gray);
				gc.drawRect(x, y, w, h);
		
				if (grid[i][j] == 1) {
					logger.debug("Drawing queen in " + i + "x" + j);
					
					drawQueen(gc, x, y);
				}
				x += w;
			}
			y += h;
		}
	}

	/**
	 * ______________
	 * |            |
	 * | \   /\   / |   |\        /|      /\
	 * |  \      /  | = | \   +  / | +   /  \
	 * |   \____/   |   |P1\    /P2|    /_P3_\
	 * --------------
	 * 
	 * @param gc
	 * @param x
	 * @param y
	 */
	private void drawQueen(Graphics gc, int x, int y) {
		
		int[] p1x = new int[3];
		p1x[0] = x + 10;
		p1x[1] = x + 15;
		p1x[2] = x + 35;
		int[] p1y = new int[3];
		p1y[0] = y + 10;
		p1y[1] = y + 40;
		p1y[2] = y + 40;
		
		gc.setColor(Color.black);
		gc.fillPolygon(p1x, p1y, 3);
		
		int[] p2x = new int[3];
		p2x[0] = x + 40;
		p2x[1] = x + 35;
		p2x[2] = x + 15;
		int[] p2y = new int[3];
		p2y[0] = y + 10;
		p2y[1] = y + 40;
		p2y[2] = y + 40;
		
		gc.setColor(Color.black);
		gc.fillPolygon(p2x, p2y, 3);
		
		int[] p3x = new int[3];
		p3x[0] = x + 15;
		p3x[1] = x + 25;
		p3x[2] = x + 35;
		int[] p3y = new int[3];
		p3y[0] = y + 40;
		p3y[1] = y + 10;
		p3y[2] = y + 40;
		
		gc.setColor(Color.black);
		gc.fillPolygon(p3x, p3y, 3);
	}
	
	public void emptyBoard() {
		if (grid == null) {
			logger.error("Grid was null");
			return;
		}
		int size = grid.length;
		grid = new int[size][size];
	}
}
