package com.arcastudio.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.arcastudio.main.Game;
import com.arcastudio.world.Camera;
import com.arcastudio.world.World;

public class Enemy extends Entity{
	
	public int sec = 0;
	
	static Random random = new Random();
	
	public boolean jump;
	public int jumpHeight = 20;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	
	public int timeing = 0;
	
	private int frames = 0, maxFrames = 10, index =	0;
	public static String moved = "stop";
	
	
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	
	int spd = random.nextInt(3);
	
	private BufferedImage[] leftSlime;
	private BufferedImage[] rightSlime;
	private BufferedImage[] rightAtackSlime;
	private BufferedImage[] leftAtackSlime;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		rightSlime = new BufferedImage[4];
		rightSlime[0]= Game.spriteEnemy.getSprite(112, 0, 16, 16);
		rightSlime[1]= Game.spriteEnemy.getSprite(96, 0, 16, 16);
		rightSlime[2]= Game.spriteEnemy.getSprite(80, 0, 16, 16);
		rightSlime[3]= Game.spriteEnemy.getSprite(64, 0, 16, 16);
									/*
									  |
									  | 	esse numero não pode ser ímpar
									  |
									  |
									  |
									  |
									  V
									 */
		leftSlime = new BufferedImage[4];
		leftSlime[0]= Game.spriteEnemy.getSprite(48, 0, 16, 16);
		leftSlime[1]= Game.spriteEnemy.getSprite(32, 0, 16, 16);
		leftSlime[2]= Game.spriteEnemy.getSprite(16, 0, 16, 16);
		leftSlime[3]= Game.spriteEnemy.getSprite(0, 0, 16, 16);
		
		leftAtackSlime = new BufferedImage[4];
		leftAtackSlime[0]= Game.spriteEnemy.getSprite(0, 16, 16, 16);
		leftAtackSlime[1]= Game.spriteEnemy.getSprite(16, 16, 16, 16);
		leftAtackSlime[2]= Game.spriteEnemy.getSprite(32, 16, 16, 16);
		leftAtackSlime[3]= Game.spriteEnemy.getSprite(48, 0, 16, 16);

		rightAtackSlime = new BufferedImage[4];
		rightAtackSlime[0]= Game.spriteEnemy.getSprite(112, 16, 16, 16);
		rightAtackSlime[1]= Game.spriteEnemy.getSprite(96, 16, 16, 16);
		rightAtackSlime[2]= Game.spriteEnemy.getSprite(112, 16, 16, 16);
		rightAtackSlime[3]= Game.spriteEnemy.getSprite(64, 0, 16, 16);
		
	}
	public void tick() {
		
		sec++;
		if(sec == 60) {
			spd = random.nextInt(3);
			if(spd == 0) {
				spd++;
			}
			sec = 0;
				}
		/* SISTEMA PARA QUANDO CHEGAR PERTO DE INIMIGOS ELES ATACAREM
		 							|
		 							|
		  							| 						
		  							V	                                */
		
		if(x - Game.player.getX()  < 100) {
				
			if(World.isFree(this.getX(),(int)(y+1))) {
				y+=1;
			}
		
		if(moved == "right") {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == rightSlime.length) {
					index = 0;
				}
			}
		}
		if(moved == "left") {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == leftSlime.length) {
					index = 0;
				}
			}
		}
		if(moved == "rightAtack") {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == rightAtackSlime.length) {
					index = 0;
					moved = "right";
				}
			}
		}
		if(moved == "leftAtack") {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == leftAtackSlime.length) {
					index = 0;
					moved = "left";
				}
			}
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
			
			if(isCollidingWithPlayer() == false) {
			if(x < Game.player.getX() && World.isFree((int)(x+spd), y) && !isColliding((int)(x+spd), this.getY())) {
				x+=spd;
				moved = "right";
			}
			if( x > Game.player.getX() && World.isFree((int)(x-spd), y) && !isColliding((int)(x-spd), this.getY())){
				x-=spd;
				moved = "left";
			}
			}}
		//COLIDINDO
		if(isCollidingWithPlayer()) {
			//A CADA 100 MS ELE DA DANO
				timeing++; 
				
				if(timeing == 100) {
					if(moved == "right") {
						moved = "rightAtack";
					}if(moved == "left") {
						moved = "leftAtack";
					}
					
					Player.life = Player.life - 30;
					System.out.println("life: " + Game.player.life);	
					timeing = 0;
					}
				}
								
				
				}
		
	
	public boolean isCollidingWithPlayer(){
		
		Rectangle enemyCurrent  = new Rectangle(this.getX() + maskx, this.getY() + masky ,maskw,maskh);
		
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);
		 
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		
		Rectangle enemyCurrent  = new Rectangle(xnext, ynext ,maskw,maskh);	
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		
		}
		return false;

	}
	public void render(Graphics g) {
		if(moved == "stop") {
		g.drawImage(leftSlime[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "right") {
			g.drawImage(rightSlime[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "left") {
			g.drawImage(leftSlime[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "rightAtack") {
			g.drawImage(rightAtackSlime[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "leftAtack") {
			g.drawImage(leftAtackSlime[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

	}
}
