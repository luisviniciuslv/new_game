package com.arcastudio.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.arcastudio.main.Game;
import com.arcastudio.world.World;

public class Enemy extends Entity{
	
	public int sec = 0;
	
	static Random random = new Random();
	
	public boolean jump;
	public int jumpHeight = 20;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	
	
	
	int spd = random.nextInt(3);
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
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
			
			if(this.isCollidingWithPlayer() == false) {
			
			if(x < Game.player.getX()  + 16 && World.isFree((int)(x+spd), y) && !isColliding((int)(x+16), this.getY())) {
				x+=spd;
			}
			else if( x > Game.player.getX() -16 && World.isFree((int)(x-spd), y) && !isColliding((int)(x-16), this.getY())){
				x-=spd;
				if(x == Game.player.getX() - 16) {
					
				}
			}
			}
		}
		}
	
	public boolean isCollidingWithPlayer(){
		
		Rectangle enemyCurrent  = new Rectangle(this.getX(), this.getY(),World.TILE_SIZE,World.TILE_SIZE);	
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);
		
		return enemyCurrent.intersects(player );
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent  = new Rectangle(xnext, ynext,World.TILE_SIZE,World.TILE_SIZE);	
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(),World.TILE_SIZE,World.TILE_SIZE);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		
		}
		
		return false;
	}	
}
