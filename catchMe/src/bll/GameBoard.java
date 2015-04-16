package bll;

import java.awt.Color;
import java.util.Iterator;
import java.util.Observable;

import org.apache.log4j.Logger;

public class GameBoard extends Observable {
	
	private static final Logger logger = Logger.getRootLogger();

	private int width;
	private int height;
	private Cadena<Figure> figures;	
	private boolean pleaseStop = false;
	private int initialNumberOfFigures;
	
	public GameBoard(int w, int h) {
		width = w;
		height = h;
		figures = new Cadena<Figure>();
		initialNumberOfFigures = 0;
	}
	
	public void initBoard(int nbFigures) {
		logger.info("Creating the board with " + nbFigures + " figures");
		
		initialNumberOfFigures = nbFigures;
		
		for(int i = 0; i<nbFigures; i++) {
			Figure f = null;
			int posX = (int)(Math.random() * (double)(width - Figure.BASIC_SIZE * 2));
			int posY = (int)(Math.random() * (double)(height - Figure.BASIC_SIZE * 2));
			if (i % 2 == 0)
				f = new Circle(posX, posY, Color.red);
			else
				f = new Square(posX, posY, Color.red);
			
			try {
				figures.insert(f);
			} catch (Exception e) {
				logger.error("No puede ser que f sea nulo");
			}
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void move() {
		Iterator<Figure> iter = figures.iterator();
		while(iter.hasNext()) {
			Figure f = iter.next();
			f.moveAlongDirection(this);
		}
		this.setChanged();
		this.notifyObservers();
	}
	
	public void startAnimation() {
		Thread worker = new Thread(new Runnable() {
	
			@Override
			public void run() {
				animate();
			}
		});
		
		worker.start();
	}
	
	public void animate() {
		while(!pleaseStop) {
			move();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				;
			}
		}
	}

	public Figure getFigureTouched(int x, int y) {
		Iterator<Figure> iter = figures.iterator();
		while(iter.hasNext()) {
			Figure f = iter.next();
			if (f.isInside(x, y)) {
				logger.info("Hizo clic en la figura e posicion " + x + "x" + y);
				return f;
			}
		}

		return null;
	}
	
	public int getScore() {
		return this.initialNumberOfFigures - this.figures.size();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Cadena<Figure> getFigures() {
		return figures;
	}

	public void setFigures(Cadena<Figure> figures) {
		this.figures = figures;
	}

	public boolean isPleaseStop() {
		return pleaseStop;
	}

	public void setPleaseStop(boolean pleaseStop) {
		this.pleaseStop = pleaseStop;
	}

	public int getInitialNumberOfFigures() {
		return initialNumberOfFigures;
	}
}
