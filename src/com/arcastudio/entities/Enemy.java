package com.arcastudio.entities;

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
				
			if(World.isFree(this.getX(), (int)(y+1))) {
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
			
			
			
			
			if(x < Game.player.getX() && World.isFree((int)(x+spd), y)) {
				System.out.println(x);
				x+=spd;
			}
			else if( x > Game.player.getX()&& World.isFree((int)(x-spd), y)){
				x-=spd;
				System.out.println(x);
			}
			 
			}
			 
	}
}
