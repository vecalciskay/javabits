package ppuzzle.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;

import javax.imageio.ImageIO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PicsMatrix extends Observable {

	private final static Logger logger = LogManager.getRootLogger();
	
	private BufferedImage[][] pics;
	private boolean imageInPics;
	private int selectedI;
	private int selectedJ;
	private int movingSelectedX;
	private int movingSelectedY;
	private int relativeToMovingX;
	private int relativeToMovingY;
	
	public static final int NB_WIDTH = 4;
	
	public PicsMatrix() {
		pics = new BufferedImage[NB_WIDTH][NB_WIDTH];
		imageInPics = false;
		selectedI = -1;
		selectedJ = -1;
		movingSelectedX = 0;
		movingSelectedY = 0;
	}
	
	public void readFile(File theFile) throws Exception {
		
		if (theFile == null)
			throw new Exception("File cannot be null");
		
		if (! theFile.canRead())
			throw new Exception("File cannot be read");
		
		BufferedImage allImage = null;
		
		try {
			allImage = ImageIO.read(theFile);
		} catch(Exception q) {
			logger.error("Error reading the file ", q);
			throw new Exception("Error reading the file");
		}
		
		int h = allImage.getHeight();
		int w = allImage.getWidth();
		
		if (w != 512 || h != 512) {
			throw new Exception ("Please use a 512x512 image");
		}
		
		int sizePics = 512 / NB_WIDTH; 
		
		for (int i = 0; i < NB_WIDTH; i++) {
			for (int j = 0; j < NB_WIDTH; j++) {
				logger.debug("Reading image coordinates: " + (i*sizePics) + "x" + (j*sizePics));
				this.pics[i][j] = 
						allImage.getSubimage(i * sizePics, j * sizePics, sizePics, sizePics);
			}
		}
		
		imageInPics = true;
		
		logger.info("Read image from file " + theFile.getName());
	}
	
	public void interChangeSelectedWith(int i, int j) throws IndexOutOfBoundsException {
		
		if (i < 0 || j < 0 || i > NB_WIDTH || j > NB_WIDTH)
			throw new IndexOutOfBoundsException("At lest one of the arguments is wrong (i,j): " + 
					i + "x" + j);
		
		if (selectedI < 0 || selectedJ < 0) {
			logger.warn("Nothing to interchange with");
			return;
		}
		
		BufferedImage aux = pics[selectedI][selectedJ];
		pics[selectedI][selectedJ] = pics[i][j];
		pics[i][j] = aux;
		
		selectedI = -1;
		selectedJ = -1;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void shuffle() {
		if (!imageInPics) {
			return;
		}
		for (int i = 0; i < NB_WIDTH/2; i++) {
			for (int j = 0; j < NB_WIDTH; j++) {
				int randI = (int)(Math.random() * NB_WIDTH);
				int randJ = (int)(Math.random() * NB_WIDTH);
				
				BufferedImage img = pics[i][j];
				pics[i][j] = pics[randI][randJ];
				pics[randI][randJ] = img;
			}
		}
	}
	
	public int[] getIndexOfPicInPositionXY(int baseX, int baseY, int x, int y) throws Exception {
		if (baseX < 0 || baseY < 0 || x < 0 || y < 0)
			throw new Exception("ALl arguments must be positive integers");
		
		int[] result = new int[2];
		
		if (x < baseX)
			return null;
		if (y < baseY)
			return null;
		
		int sizePic = 512 / NB_WIDTH;
		
		result[0] = (x - baseX) / sizePic;
		result[1] = (y - baseY) / sizePic;
		
		if (result[0] >= NB_WIDTH || result[1] >= NB_WIDTH) {
			return null;
		}
		
		logger.info("The position " + x + "x" + y + " resulted in the pic " + result[0] + "x" + result[1]);
		
		this.setRelativeToMoving(x - (baseX + result[0] * sizePic), y - (baseY + result[1] * sizePic));
		
		return result;
	}
	
	public void draw(Graphics g, int baseX, int baseY) {
		
		if (!imageInPics) {
			logger.info("Not drawing anything");
			return;
		}
		
		logger.debug("Drawing the pics");
		
		int sizePic = 512 / NB_WIDTH;
		
		for (int i = 0; i < NB_WIDTH; i++) {
			for (int j = 0; j < NB_WIDTH; j++) {
				if (i == selectedI && j == selectedJ)
					continue;
				g.drawImage(pics[i][j], baseX + i * sizePic, baseY + j * sizePic, null);
			}
		}
		
		if (selectedI >= 0 && selectedJ >= 0) {
			g.drawImage(pics[selectedI][selectedJ], 
					movingSelectedX - relativeToMovingX, 
					movingSelectedY - relativeToMovingY, null);
		}
	}
	
	public void movingSelectedTo(int x, int y) {
		movingSelectedX = x;
		movingSelectedY = y;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setSelected(int i, int j) {
		selectedI = i;
		selectedJ = j;
	}
	
	private void setRelativeToMoving(int x, int y) {
		relativeToMovingX = x;
		relativeToMovingY = y;
	}
}
