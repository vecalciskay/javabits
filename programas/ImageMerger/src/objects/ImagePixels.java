package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.ImageUtils;

public class ImagePixels {

	private static final Logger theLog = LogManager.getRootLogger();
	private String name;
	private int[][] pixels;
	private int width;
	private int height;

	private PropertyChangeSupport observed; 
	
	public ImagePixels(int w, int h) {
		name = "noname";
		this.width = w;
		this.height = h;
		
		pixels = new int[w][h];
		observed = new PropertyChangeSupport(this);
	}
	
	public void addObserver(PropertyChangeListener panel) {
		observed.addPropertyChangeListener(panel);
	}

	public void setPixel(int c, int x, int y) {
		pixels[x][y] = c;
	}
	
	public int getPixel(int x, int y) {
		return pixels[x][y];
	}

	public void draw(Graphics g) {
		BufferedImage rsm = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rsm.createGraphics();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				g2d.setColor(new Color(pixels[i][j]));
				g2d.drawLine(i, j, i, j);
			}
		}
		
		g.drawImage(rsm, 0, 0, null);
		theLog.debug("Just drew image " + toString());
	}

	public void changeOk() {
		observed.firePropertyChange("Image", 1, 2);
	}

	public void setImage(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		
		BufferedImage workingImg = img;
		
		if (width != w || height != h) {
			try {
				workingImg = ImageUtils.resizeImage(img, width, height);
			}catch(Exception e) {
				workingImg = img;
			}
		}
		pixels = new int[width][height];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i][j] = workingImg.getRGB(i, j);
			}
		}
		
		changeOk();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name + " (" + width + "x" + height + ")";
	}
}
