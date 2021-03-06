package com.arcastudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.arcastudio.entities.*;
import com.arcastudio.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

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
					tiles[xx+(yy * WIDTH)] = new NoCollision(xx*16, yy*16, Tile.TILE_SKY);
					
					if(pixelAtual == 0xFF1500FF) {
						//Sky
						tiles[xx+(yy * WIDTH)] = new NoCollision(xx*16, yy*16, Tile.TILE_SKY);
						
					}else if(pixelAtual == 0xFF000000) {
						//GRAM
						tiles[xx+(yy * WIDTH)] = new Collision(xx*16, yy*16, Tile.TILE_BEDROCK);}
					else if(pixelAtual == 0xFF4CFF00) {
						//GRAM
						tiles[xx+(yy * WIDTH)] = new Collision(xx*16, yy*16, Tile.TILE_GRAM);
						
					} else if(pixelAtual == 0xFF7C423F) {
						//GRAM
						tiles[xx+(yy * WIDTH)] = new Collision(xx*16, yy*16, Tile.TILE_EARTH);
						
						
					} else if(pixelAtual == 0xFFFFFFFF) {
						//Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}
					
					//Objs Game
					else if(pixelAtual == 0xFFFF0000){
						//Enemy
						Enemy enemyObj = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(enemyObj);
						Game.enemies.add(enemyObj);
						
						
					} else if(pixelAtual == 0xFFFF6A00){
						//Weapon
						Game.entities.add(new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN));
					} else if(pixelAtual == 0xFFFF7F7F){
						//Life Pack
						Lifepack lifePack = new Lifepack(xx*16, yy*16, 16, 16, Entity.LIFEPACK_EN);
						//lifePack.setMask(8, 8, 8, 8);
						Game.entities.add(lifePack);
						
					} else if(pixelAtual == 0xFFF9FF56){
						//Bullet
						Game.entities.add(new Bullet(xx*16, yy*16, 16, 16, Entity.BULLET_EN));
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof Collision || 
				tiles[x2 + (y2*World.WIDTH)] instanceof Collision || 
				tiles[x3 + (y3*World.WIDTH)] instanceof Collision || 
				tiles[x4 + (y4*World.WIDTH)] instanceof Collision);
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
