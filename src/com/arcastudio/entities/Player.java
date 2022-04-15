package com.arcastudio.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arcastudio.main.Game;
import com.arcastudio.world.Camera;
import com.arcastudio.world.World;

public class Player extends Entity {
	
	public boolean right, left;
	
	public int dir = 1;
	private double gravity = 1;
	
	public double speed = 2;
	public boolean jump = false;
	public int jumpHeight = 32;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	
	public boolean inFloor = false;
	
	private int frames = 0, maxFrames = 10, index =	0;
	public static String moved = "stop";
	private BufferedImage[] forntPlayerAnimate;

	private BufferedImage frontPlayer;
	
	public static BufferedImage player_front;
	public static BufferedImage player_stop_left;
	public static BufferedImage player_stop_right;
	public static BufferedImage[] player_right;
	public static BufferedImage[] player_left;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
		
		
		player_right = new BufferedImage[2];
		player_right[0] = Game.spriteplayer.getSprite(16, 32, 16, 16);
		player_right[1] = Game.spriteplayer.getSprite(32, 32, 16, 16);
		
		player_left = new BufferedImage[2];
		player_left[0] = Game.spriteplayer.getSprite(16, 16, 16, 16);
		player_left[1] = Game.spriteplayer.getSprite(32, 16, 16, 16);
			
		
		frontPlayer = Game.spriteplayer.getSprite(0, 0, 16, 16);
		player_stop_right = Game.spriteplayer.getSprite(0, 32, 16, 16);
		player_stop_left = Game.spriteplayer.getSprite(0, 16, 16, 16);
	}

	public void tick() {
		moved = "stop";
		
		if(World.isFree((int)x, (int)(y+1)) && isJumping == false) {
			y+=gravity;
		}
					/*PARA UM PERSONAGEM 16X32 
								|
		             			V*/
		
		if(jump) { 
			if(!World.isFree(this.getX(),this.getY()+1)){
				isJumping = true;
		}
			else {
			jump = false;}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-1)) {
				y-=2;	 
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
		
		
		if(right && World.isFree((int)(x+speed), this.getY())) {
			moved = "right";
			x+=speed;
			
		}
		else if(left && World.isFree((int)(x-speed), this.getY())) {
			moved = "left";
			x-=speed;
			
		}
		
		if(moved == "left") {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == player_left.length) {
					index = 0;
				}
			}
		}
		if(moved == "right") {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == player_right.length) {
					index = 0;
				}
			}
		}
		
		//Config Camera
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void render(Graphics g) {
		//Ativar o player no JFrame, flip
		if(moved == "stop") {
			g.drawImage(frontPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "right") {
			g.drawImage(player_right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "stop_right") {
			g.drawImage(player_stop_right, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		else if(moved == "left") {
			g.drawImage(player_left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "stop_left") {
			g.drawImage(player_stop_left, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else {
			g.drawImage(frontPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
