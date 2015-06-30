package com.extendbrain.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import sun.awt.image.PixelConverter.Rgba;


public class BMConvertToIMG {
	
	public static void test(File file){
		File outFile = new File("d:\\image\\"+file.getName()+".jpg");
		int width = 96;
		int height = 96;

		BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		try {
			InputStream is = new FileInputStream(file);
			for(int x = 0; x < width; x ++){
				for(int y = 0; y < width; y ++){
					byte[] rgba = new byte[4];
					int red=0;
					int green = 0;
					int blue = 0;
					int alpha = 0;
					is.read(rgba);
					red = rgba[0] & 0xFF;
					green = rgba[1]& 0xFF;
					blue = rgba[2]& 0xFF;
					alpha = rgba[3]& 0xFF;
					int rgb = red;
					rgb = (rgb<<8)+green;
					rgb = (rgb<<8)+blue;
					rgb = (rgb<<8)+alpha;
					bf.setRGB(y, x, rgb);
//					System.out.println(red +":" + green + ":" + blue);
				}
			}
			
			ImageIO.write(bf, "jpg", outFile);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void start(String imgFolder){
		File folder = new File(imgFolder);
		File[] files = folder.listFiles();
		for(File file : files ){
			System.out.println(file.getName());
			test(file);
		}
	}
	
	public static void main(String[] args) {
//		String fileName = args[0];
//		System.out.println(fileName);
//		test(fileName);
		start("d:\\srcimg");
	}
}
