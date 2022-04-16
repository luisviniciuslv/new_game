package com.arcastudio.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.arcastudio.main.Game;

public class Lifepack extends Entity{

	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	
	public Lifepack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		if(checkCollisionLifePack()) {		
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Lifepack) {					
					if(Player.life < Player.maxlife) {
						if(Player.life >= Player.maxlife) {
							Player.life = Player.maxlife;	
						}
						Player.life += 10;
						Game.entities.remove(atual);
					
				}
					System.out.println(i);
				}
		
			
			}
		}		
	}
	public boolean checkCollisionLifePack() {
		Rectangle LifepackCurrent  = new Rectangle(this.getX() + maskx, this.getY() + masky ,maskw,maskh);
		
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);
		
		return LifepackCurrent.intersects(player);
		
	}

}
