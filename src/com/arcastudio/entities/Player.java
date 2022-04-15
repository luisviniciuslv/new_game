package com.arcastudio.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arcastudio.main.Game;
import com.arcastudio.world.Camera;
import com.arcastudio.world.World;

public class Player extends Entity {
	
	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double speed = 1;
	
	public boolean jump;
	public int jumpHeight = 20;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	
	private int frames = 0, maxFrames = 10, index =	0;
	public static String moved = "stop";
	private BufferedImage[] rightPlayer;
	private BufferedImage rightStopPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage leftStopPlayer;
	private BufferedImage frontPlayer;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
			rightPlayer = new BufferedImage[2];
			leftPlayer = new BufferedImage[2];
		
			rightPlayer[0] = Game.spritesheet.getSprite(65,36, 12, 28);
			rightPlayer[1] = Game.spritesheet.getSprite(82,36, 12, 28);
			rightStopPlayer = Game.spritesheet.getSprite(50,36, 12, 28);
			
			leftPlayer[0] = Game.spritesheet.getSprite(3, 36, 12, 28);
			leftPlayer[1] = Game.spritesheet.getSprite(3, 68, 12, 28);
			rightStopPlayer = Game.spritesheet.getSprite(19,36, 12, 28);
			
			frontPlayer = Game.spritesheet.getSprite(34, 36, 12, 28);
		
	}

	public void tick() {
		moved = "stop";
		
		
		if(World.isFree(this.getX(), (int)(y+15)) || isJumping) {
			y+=4;
		}
					/*PARA UM PERSONAGEM 16X32 */
								/*|*/
		if(jump) {              /*V*/
			if(!World.isFree(x, y+15)) {
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
				if(index == leftPlayer.length) {
					index = 0;
				}
			}
		}
		if(moved == "right") {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == rightPlayer.length) {
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
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "stop_right") {
			g.drawImage(rightStopPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		else if(moved == "left") {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else if(moved == "stop_left") {
			g.drawImage(leftStopPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else {
			g.drawImage(frontPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
