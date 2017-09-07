package server2017tpImagen;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ImagenRecursiva {

	private static final Logger logger = LogManager.getRootLogger();
	
	// #9AB248, #9AC2FF, #E1FF80, #CC6052, #B25C51
	public static final int[] COLORES = { 4764314, 16761498, 8454113, 5398732, 5332146 }; 
	
	private int profundidad;
	
	private int tamano;
	
	private int[] pixeles;
	
	public static ImagenRecursiva crearImagenRecursiva(String nombre) throws Exception {
		String regex = "/imagen_tp2_([0-9]+)x([0-9])\\.png";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(nombre);
		
		if (!m.matches()) {
			throw new Exception("No se puede formar creador de imagen con texto " + nombre);
		}
		
		try {
			int t = Integer.parseInt(m.group(1));
			int prof = Integer.parseInt(m.group(2));
			
			return new ImagenRecursiva(prof, t);
		} catch(Exception q) {
			throw q;
		}
	}
	
	public ImagenRecursiva(int prof, int tam) {
		pixeles = new int[tam * tam];
		tamano = tam;
		profundidad = prof;
		logger.info("Creado el objeto para crear la imagen " + tam + "x" + tam);
	}
	
	public void hacerImagen(int x1, int y1, int x2, int y2, boolean derecha, int n) {
		
		logger.info("Pinta el rectangulo de (" + x1 + "," + y1 + ") - (" + x2 + "," + y2 + ") con n = " + n);
		
		int color = COLORES[n - 1];
		
		int i = 0;
		int j = 0;
		try {
			for (j = y1; j < y2; j++) {
				for (i = x1; i < x2; i++) {
			
					pixeles[j * tamano + i] = color;
				}
			}
		} catch(Exception e) {
			logger.error("No pudo asignar el color a la posición " + i + "x" + j, e);
		}
		
		if (n == 1) {
			logger.info("Termina la recurrencia ya que n es 1");
			return;
		}
		
		int mitad = (x2 - x1) / 2;
		int nx1 = x1;
		int nx2 = x2;
		int ny1 = y1;
		int ny2 = y2;
		
		if (derecha) {
			nx1 = x1 + mitad;
			ny1 = y1 + mitad;
		} else {
			nx2 = x2 - mitad;
			ny2 = y2 - mitad;
		}
		
		hacerImagen(nx1, ny1, nx2, ny2, !derecha, n - 1);
	}
	
	public String grabarImagen(String dir) {
		
		hacerImagen(0, 0, tamano, tamano, true, profundidad);		
		String base = "imagen_tp2_" + tamano + "x" + profundidad + ".png";		
		String ruta = dir + "/" + base;
		
		File f = new File(ruta);
		
		if (f.exists()) {
			logger.info("Borramos el archivo que ya existía");
			f.delete();
		}
		
		try {
		    // retrieve image
		    /**BufferedImage bi = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_RGB);
            WritableRaster raster = (WritableRaster) bi.getData();
            int[] pixelesRaster = getPixelesRaster();
            raster.setPixels(0, 0, tamano, tamano, pixelesRaster);
		    File outputfile = new File(ruta);
		    ImageIO.write(bi, "png", outputfile);
		    */
			final int height = tamano;
			final int width = tamano;
			final BufferedImage image =
			    new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int y = 0; y < height; ++y) {
			    for (int x = 0; x < width; ++x) {
			        image.setRGB(x, y, pixeles[y * width + x]);
			    }
			}
			File outputfile = new File(ruta);
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
		    logger.error("No se pudo grabar el archivo, dara error el llamado del navegador", e);
		}
		
		return base;
	}

	private int[] getPixelesRaster() {
		int[] result = new int[tamano * tamano * 3];
		
		for (int i = 0; i < tamano*tamano; i++) {
			result[i*3] = 255;
			result[i*3+1] = 0 ;
			result[i*3+2] = 0 ;
		}
		
		return result;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	
}
