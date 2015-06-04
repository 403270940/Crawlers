package com.extendbrain.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ProcessImg {

	public static int isWhite(int colorInt , int model){
		Color color =new Color(colorInt);
		Color modelColor = new Color(model);
		int diffRed = Math.abs(color.getRed() - modelColor.getRed());
		int diffGreen = Math.abs(color.getGreen() - modelColor.getGreen());
		int diffBlue = Math.abs(color.getBlue() - modelColor.getBlue());
		if(diffRed + diffGreen + diffBlue > 300)
			return 1;
		return 0;
	}
	
	public static BufferedImage removeBackground(String picFile)throws Exception{
		BufferedImage img = ImageIO.read(new File(picFile));
		int model = img.getRGB(1, 1);
		int width = img.getWidth();
		int height = img.getHeight();
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y ++){
				if(isWhite(img.getRGB(x, y), model) == 1){
					img.setRGB(x, y, Color.WHITE.getRGB());
				}else{
					img.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
				
		return img;
	}
	
	public static void main(String[] args) {
		try {
			BufferedImage img = removeBackground("img.jpg");
			ImageIO.write(img, "jpg", new File("123.jpg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
