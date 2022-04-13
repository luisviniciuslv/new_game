package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {
	private Tile[] tiles;
	public static int  WIDTH, HEIGHT;
	
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth()*map.getHeight()];
			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
				
			tiles = new Tile[map.getWidth()*map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels,0,map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					if(pixelAtual == 0xFF4cFF00) {
						System.out.println("adicionar chão");
					}
					else if(pixelAtual == 0xFF7F6A00) {
						System.out.println("adicionar terra");

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
	public void render(Graphics g) {
			for(int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					Tile tile = tiles[xx + (yy*WIDTH)];
					tile.render(g);
				}
			}
	}
}
