package com.gcstudios.world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth()*map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels,0,map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					if(pixels[xx + (yy*map.getWidth())] == 0xFF4cFF00) {
						System.out.println("adicionar chão");
					}
				}
			}
			for(int i = 0; i < pixels.length; i++) {
				if(pixels[i] == 0xFFFF0000) {
					System.out.println("estou no vermelho");
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
