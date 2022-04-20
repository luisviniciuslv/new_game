package com.arcastudio.entities;

import java.awt.Color;
import java.awt.Font;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.arcastudio.main.Game;
import com.arcastudio.world.Camera;
import com.arcastudio.world.World;

public class Enemy extends Entity {

	public int tick = 0;
	
	public static boolean isLeft;
	public static boolean isRight;
	public static boolean canAtack;
	
	private int speed = 1;
	public static double life = 100, maxLife = 100;
	public int timing = 0;
	public static Player player;
	public boolean jump = false;
	public int jumpHeight = 32;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	public static String  position = "right";
	
	
	private int maskX = 8, maskY = 8, maskW = 10, maskH = 10;
	private int frames = 0, maxFrames = 15, index =	0, maxIndex = 1;
	private BufferedImage[] left;
	private BufferedImage[] right;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		//Frames de Anima��o
		left = new BufferedImage[4];
		left[0] = Game.spriteslime.getSprite(48, 0, 16, 16);
		left[1] = Game.spriteslime.getSprite(32, 0, 16, 16);
		left[2] = Game.spriteslime.getSprite(16, 0, 16, 16);
		left[3] = Game.spriteslime.getSprite(0, 0, 16, 16);
		
		right = new BufferedImage[4];
		right[0] = Game.spriteslime.getSprite(64, 0, 16, 16);
		right[1] = Game.spriteslime.getSprite(80, 0, 16, 16);
		right[2] = Game.spriteslime.getSprite(96, 0, 16, 16);
		right[3] = Game.spriteslime.getSprite(112, 0, 16, 16);

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
			if (x < Game.player.getX() && World.isFree(x + speed, this.getY())
					&& !isCollidding(x + speed, this.getY())) {
				canAtack = false;
				isLeft = true;
				isRight = false;
				x += speed;
				position = "right";
			} else if (x > Game.player.getX() && World.isFree(x - speed, this.getY())
					&& !isCollidding(x - speed, this.getY())) {
				canAtack = false;
				isLeft = false;
				isRight = true;
				x -= speed;
				position = "left";
			}


		} else {
			//Estamos colidindo
			canAtack = true;

			if(canAtack && Player.atack && Player.position == "rightAtack" && isRight) {
				tick++;
				if(tick == 1) {
					life-=10;
					
					return;	
				}
				if(tick == 30) {
					tick = 0;
				}
			}
			if(canAtack && Player.atack && Player.position == "leftAtack" && isLeft) {
				tick++;
				if(tick == 1) {
				life-=10;
				
				return;
				}
				if(tick == 30) {
					tick = 0;
				}
			}
			
			/*
			 * |
			 * | A CADA 1 SEGUNDO O SLIME DA DANO NO PERSONAGEM
			 * |
			 * V  
			 */
			timing++;
			if(timing == 60) {
				if(Player.dodge) {
					timing = 0;
					return;
				}
				Player.life-=10;
				timing = 0;
			}}}}
		
			

		//Animation

	
	public boolean isColliddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX(), this.getY(), 16, 16);
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
		g.setColor(Color.red);
		g.fillRect(this.getX() - Camera.x - 1, this.getY() - 5 - Camera.y, 20,3);
		g.setColor(Color.green);
		g.fillRect(this.getX() - Camera.x - 1, this.getY() - 5 - Camera.y,(int)((life/maxLife)*20),3);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString((int)life+"/"+(int)maxLife, this.getX() - Camera.x, this.getY() - 5 - Camera.y-5);
		if(position == "left") {
			g.drawImage(left[index], this.getX()- Camera.x, this.getY()- Camera.y, null);			
		}
		if(position == "right") {
			g.drawImage(right[index], this.getX()- Camera.x, this.getY()- Camera.y, null);			
		}
	}
}
