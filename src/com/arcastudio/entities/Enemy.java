package com.arcastudio.entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.arcastudio.main.Game;
import com.arcastudio.world.Camera;
import com.arcastudio.world.World;

public class Enemy extends Entity {

	private int speed = 1;
	
	public int timing = 0;
	public static Player player;
	public boolean jump = false;
	public int jumpHeight = 32;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	
	
	private int maskX = 8, maskY = 8, maskW = 10, maskH = 10;
	private int frames = 0, maxFrames = 5, index =	0, maxIndex = 1;
	private BufferedImage[] sprites;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		//Frames de Anima��o
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(112+16, 16, 16, 16);
	}

	public void tick() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		if(x - Game.player.getX()  < 100) {
			
			if(World.isFree(this.getX(),(int)(y+1))) {
				y+=1;
			}
			if(!World.isFree(x+1,y) || !World.isFree(x-1,y)){
				jump = true;
				if(jump) { 
					if(!World.isFree(x, y+1)) {
						isJumping = true;
				}
					else {
					jump = false;}
				}
				
				if(isJumping) {
					if(World.isFree(x, y-1)) {
						y-=8;
						jumpFrames+=2;
						if(jumpFrames == jumpHeight) {
							isJumping = false;
							jump = false;
							jumpFrames = 0;}
						}
					
					else {
						isJumping = false;
						jump = false;
						jumpFrames = 0;
					}
				}
				
			}
		if (this.isColliddingWithPlayer() == false) {
			Player.canAtack = false;
			if (x < Game.player.getX() && World.isFree(x + speed, this.getY())
					&& !isCollidding(x + speed, this.getY())) {
				x += speed;
			} else if (x > Game.player.getX() && World.isFree(x - speed, this.getY())
					&& !isCollidding(x - speed, this.getY())) {
				x -= speed;
			}

			if (y < Game.player.getY() && World.isFree(this.getX(), y + speed)
					&& !isCollidding(this.getX(), y + speed)) {
				y += speed;
			} else if (y > Game.player.getY() && World.isFree(this.getX(), y - speed)
					&& !isCollidding(this.getX(), y - speed)) {
				y -= speed;
			}
		} else {
			//Estamos colidindo
			Player.canAtack = true;
			timing++;
			if(timing == 10) {
				if(Player.dodge) {
					timing = 0;
					return;
				}
				Player.life-=10;
				timing = 0;
			}}}}
		
			

		//Animation

	
	public boolean isColliddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskX, this.getY() + maskY, maskW, maskH);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isCollidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskX, ynext + maskY, maskW, maskH); //Classe que cria retangulos fict�cios para testar colis�es.
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskX, e.getY() + maskY, maskW, maskH);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX()- Camera.x, this.getY()- Camera.y, null);
	}
}
