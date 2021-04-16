package objects;

public class ImageMergerModel {
	
	public static final int DEFAULT_NUMBER_DIVISIONS = 5;
	public static final int MIN_NUMBER_DIVISIONS = 3;
	public static final int MAX_NUMBER_DIVISIONS = 50;

	private ImagePixels image1;	
	private ImagePixels image2;
	private ImagePixels result;
	
	private int width;
	private int height;
	
	private int numberDivisions;
	private double percentagePerDivision;
	
	public ImageMergerModel(int w, int h) {
		this(DEFAULT_NUMBER_DIVISIONS, w, h);
	}
	
	public ImageMergerModel(int n, int w, int h) {
		if (n < MIN_NUMBER_DIVISIONS)
			numberDivisions = MIN_NUMBER_DIVISIONS;
		else 
			numberDivisions = n;
		
		this.width = w;
		this.height = h;
		
		calculatePercentPerDivision();
		
		image1 = new ImagePixels(w, h);
		image2 = new ImagePixels(w, h);
		result = new ImagePixels(w, h);
	}
	
	private void calculatePercentPerDivision() {
		percentagePerDivision = 100.0 / (double)(numberDivisions - 1);
	}

	public void calculateResult() {
		double currentPercentageImage1 = 100.0;
		int currentDivision = 0;
		int firstX = 0;
		int lastX = 0;
		
		while(currentDivision < numberDivisions) {
			lastX = (int)((double)((currentDivision + 1) * width) / (double)numberDivisions);
			if ((currentDivision + 1) == numberDivisions ) {
				lastX = width;
				currentPercentageImage1 = 0;
			}
			for (int px = firstX; px < lastX; px++) {
				for (int py = 0; py < height; py++) {
					ImageColor c1  = new ImageColor(image1.getPixel(px, py));
					c1.percentage(currentPercentageImage1);
					ImageColor c2  = new ImageColor(image2.getPixel(px, py));
					c2.percentage(100.0 - currentPercentageImage1);
					
					ImageColor r = c1.add(c2);
					result.setPixel(r.getIntColor(), px, py);
				}
			}
			
			firstX = lastX;
			currentDivision++;
			currentPercentageImage1 -= percentagePerDivision;
		}
		
		result.changeOk();
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

	public int getNumberDivisions() {
		return numberDivisions;
	}

	public void setNumberDivisions(int numberDivisions) {
		this.numberDivisions = numberDivisions;
		calculatePercentPerDivision();
	}

	public ImagePixels getImage1() {
		return image1;
	}

	public ImagePixels getImage2() {
		return image2;
	}

	public ImagePixels getResult() {
		return result;
	}

	public double getPercentagePerDivision() {
		return percentagePerDivision;
	}
	
	@Override
	public String toString() {
		return numberDivisions + " division (" + width + "x" + height + ")";
	}
}
