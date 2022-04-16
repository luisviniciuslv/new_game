package com.arcastudio.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arcastudio.main.Game;
import com.arcastudio.world.Camera;
import com.arcastudio.world.World;

public class Player extends Entity {
	
	public boolean right, left;
	
	public int dir = 1;
	private double gravity = 4;
	
	public double speed = 3;
	public boolean jump = false;
	public int jumpHeight = 32;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	public static boolean dodge = false;
	
	public boolean inFloor = false;
	
	private int frames = 0, maxFrames = 10, index =	0;
	
	public static String moved = "stop";
	
	private BufferedImage frontPlayer;
	
	
	public static BufferedImage player_front;
	public static BufferedImage player_stop_left;
	public static BufferedImage player_stop_right;
	public static BufferedImage[] player_right;
	public static BufferedImage[] player_left;
	public static BufferedImage player_dodge ;
	
	public static double life = 100, maxlife = 100;
	
	
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
		
		
		player_right = new BufferedImage[6];
		player_right[0] = Game.spriteplayer.getSprite(2, 0, 11, 16);
		player_right[1] = Game.spriteplayer.getSprite(19, 0, 10, 16);
		player_right[2] = Game.spriteplayer.getSprite(34, 0, 10, 16);
		player_right[3] = Game.spriteplayer.getSprite(51, 0, 10, 16);
		player_right[4] = Game.spriteplayer.getSprite(67, 0, 10, 16);
		player_right[5] = Game.spriteplayer.getSprite(83, 0, 10, 16);
		
		player_left = new BufferedImage[6];
		player_left[0] = Game.spriteplayer.getSprite(3, 16, 11, 16);
		player_left[1] = Game.spriteplayer.getSprite(20, 16, 10, 16);
		player_left[2] = Game.spriteplayer.getSprite(36, 16, 10, 16);
		player_left[3] = Game.spriteplayer.getSprite(52, 16, 10, 16);
		player_left[4] = Game.spriteplayer.getSprite(68, 16, 10, 16);
		player_left[5] = Game.spriteplayer.getSprite(85, 16, 10, 16);
		
		player_stop_right = Game.spriteplayer.getSprite(2, 0, 11, 16);
		player_stop_left = Game.spriteplayer.getSprite(3, 16, 11, 16);
		player_dodge = Game.spriteplayer.getSprite(96, 0, 16, 16);
	}

	public void tick() {
		
		if(life <= 0) {
			Game.player.setX(World.xSpawn);
			Game.player.setY(World.ySpawn);
			life = maxlife;
		}
		
		if(dodge) {
			moved = "dodge";
		}
		
		if(World.isFree((int)x, (int)(y+1)) && isJumping == false) {
			y+=gravity;
		}
		
		
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
		
		checkCollisionLifePack();
		
		//Config Camera
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkCollisionLifePack() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Lifepack) {
				if(Entity.isCollidding(this, e)) {
					life+=8;
					if(life >= 100) {
						life = 100;
					}
					Game.entities.remove(i);
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		//Ativar o player no JFrame, flip
		if(moved == "stop") {
			g.drawImage(player_stop_right, this.getX() - Camera.x, this.getY() - Camera.y, null);
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
		else if(moved == "dodge") {
			g.drawImage(player_dodge, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else {
			g.drawImage(frontPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
