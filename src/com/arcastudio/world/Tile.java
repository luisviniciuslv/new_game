package com.arcastudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arcastudio.main.Game;

public class Tile {
	public static BufferedImage TILE_GRAM = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_EARTH = Game.spritesheet.getSprite(16, 0, 16, 16);
	public static BufferedImage TILE_SKY = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage TILE_BEDROCK = Game.spritesheet.getSprite(16, 16, 16, 16);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
