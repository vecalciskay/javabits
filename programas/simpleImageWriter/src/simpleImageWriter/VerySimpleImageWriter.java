package simpleImageWriter;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VerySimpleImageWriter {

	private int[] puntos;
	private int ancho;
	private int alto;

	public VerySimpleImageWriter(int i, int j) {
		ancho = i;
		alto = j;
		puntos = new int[ancho * alto];
	}

	public VerySimpleImageWriter(BufferedImage bi) {
		ancho = bi.getWidth();
		alto = bi.getHeight();
		
		puntos = new int[ancho * alto];
		for (int j = 0; j < alto; j++) {
			for (int i = 0; i < ancho; i++) {
				int bgr = bi.getRGB(i, j);
				int red = (bgr >> 16) & 0x000000ff;
				int green = (bgr & 0x0000ff00);
				int blue = (bgr & 0x000000ff) << 16; 
				puntos[j * ancho + i] = blue | green | red;
			}			
		}
	}

	public int[] getPuntos() {
		return puntos;
	}

	public void setPuntos(int[] puntos) {
		this.puntos = puntos;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public void escribirArchivo(String archivo) {
		BufferedImage bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = (WritableRaster)bi.getRaster();
		
		int[] rasterPixels = transformarPuntos();
		raster.setPixels(0, 0, ancho, alto, rasterPixels);
		
		try {
			File f = new File(archivo);
			ImageIO.write(bi, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void crearPuntos() {
		
		for (int j = 0; j < 100; j++) {
			for (int i = 0; i < ancho; i++) {
				puntos[j * ancho + i] = i;
			}
		}
		
		for (int j = 100; j < 200; j++) {
			for (int i = 0; i < ancho; i++) {
				puntos[j * ancho + i] = i + i*256;
			}
		}
		
		for (int j = 200; j < 300; j++) {
			for (int i = 0; i < ancho; i++) {
				puntos[j * ancho + i] = i*256;
			}
		}
	}

	private int[] transformarPuntos() {
		int[] r = new int[3*ancho*alto];
		
		for (int i = 0; i < puntos.length; i++) {
			int red = puntos[i] & 0x000000ff;
			int green = (puntos[i] >> 8) & 0x000000ff;
			int blue = (puntos[i] >> 16) & 0x000000ff;
			
			r[3*i] = red;
			r[3*i+1] = green;
			r[3*i+2] = blue;
		}
		
		return r;
	}

	public static void main(String[] args) {
		
		VerySimpleImageWriter obj = new VerySimpleImageWriter(256,300);
		
		obj.crearPuntos();
		
		obj.escribirArchivo("D:\\temp\\aaa.png");
	}
	
}
