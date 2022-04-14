package com.arcastudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.arcastudio.entities.Enemy;
import com.arcastudio.entities.Entity;
import com.arcastudio.main.Game;

public class World {
	
	private Tile[] tiles;
	public static int WIDTH, HEIGHT;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			//Calcular os pixels do mapa
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx<map.getWidth(); xx++) {
				for(int yy = 0; yy<map.getHeight(); yy++) {
					
					int pixelAtual = pixels[xx +(yy * map.getWidth())];
					tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_SKY);
					if(pixelAtual == 0xFF000000) {
						//grama
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_GRAM);
						
						}else if(pixelAtual == 0xFF404040) {
						//terra
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_EARTH);
						
					} else if(pixelAtual == 0xFF1500FF){
						//céu
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_SKY);
					}else if(pixelAtual == 0xFFFFFFFF) {
						
						
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						
					}else if(pixelAtual == 0xFFFF0000) {
						
						//enemy
						Game.entities.add(new Enemy(xx*16,yy*16,16,16, Entity.ENEMY_EN));
					
					}else if(pixelAtual == 0xFFFF703D) {
						//gun
					}else if(pixelAtual == 0xFFFFD800) {
						//bullet
					}else if(pixelAtual == 0xFF00FF08) {
						//lifepack
					}
					else {
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_SKY);
					}
				}
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x>>4;
		int ystart = Camera.y>>4;
		
		int xfinal = xstart + (Game.WIDTH>>4);
		int yfinal = ystart + (Game.HEIGHT>>4);
		
		for(int xx = xstart; xx<= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy>= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
