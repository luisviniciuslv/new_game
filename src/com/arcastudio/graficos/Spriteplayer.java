package com.arcastudio.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spriteplayer {
private BufferedImage spriteplayer;
	
	public Spriteplayer(String path) {
		try {
			spriteplayer = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spriteplayer.getSubimage(x, y, width, height);
	}
}
