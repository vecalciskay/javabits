package simpleImageReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import simpleImageWriter.VerySimpleImageWriter;

public class VerySimpleImageReader {

	public static void main(String[] args) {

		BufferedImage bi = null;
		try {
			File f = new File("D:\\temp\\aaa.png");
			bi = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		VerySimpleImageWriter vsiw = new VerySimpleImageWriter(bi);
		
		vsiw.escribirArchivo("D:\\temp\\ccc.png");
	}

}
