package com.arcastudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.arcastudio.entities.Enemy;
import com.arcastudio.entities.Entity;
import com.arcastudio.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int Tile_size = 16;

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
					tiles[xx+(yy * WIDTH)] = new SkyTile(xx*16, yy*16, Tile.TILE_SKY);
					if(pixelAtual == 0xFF000000) {
						//grama
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_GRAM);
						
						}else if(pixelAtual == 0xFF404040) {
						//terra
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_EARTH);
						
					} else if(pixelAtual == 0xFF1500FF){
						//céu
						tiles[xx+(yy * WIDTH)] = new SkyTile(xx*16, yy*16, Tile.TILE_SKY);
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
						tiles[xx+(yy * WIDTH)] = new SkyTile(xx*16, yy*16, Tile.TILE_SKY);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / Tile_size;
		int y1 = ynext / Tile_size;
		
		int x2 = (xnext+ Tile_size-1) / Tile_size;
		int y2 = ynext / Tile_size;
		
		int x3 = xnext / Tile_size;
		int y3 = (ynext+Tile_size-1) / Tile_size;
		
		int x4 = (xnext+Tile_size-1) /Tile_size;
		int y4 = (xnext+Tile_size-1) /Tile_size;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof FloorTile)||
				(tiles[x2 + (y2*World.WIDTH)] instanceof FloorTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof FloorTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof FloorTile));
	}
	
	public void render(Graphics g) {
		
		int xstart = Camera.x >> 4;
				int ystart = Camera.y >> 4;
				
				int xfinal = xstart + (Game.WIDTH >> 4);
				int yfinal = ystart + (Game.HEIGHT >> 4);
				
				for(int xx = xstart; xx <= xfinal; xx++) {
					for(int yy = ystart; yy <= yfinal; yy++) {
						
						if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
							continue;
						}
						
						Tile tile = tiles[xx + (yy*WIDTH)];
						tile.render(g);
					}
				}
			}
		}
