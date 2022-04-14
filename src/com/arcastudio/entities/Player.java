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
	public int speed = 2;
	
	public boolean jump;
	public int jumpHeight = 20;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	
	private int frames = 0, maxFrames = 5, index =	0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		//Anima��es quantidade
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for(int i = 0; i< 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 +(i*16), 0, 16, 16);
		}
		
		for(int i = 0; i< 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 +(i*16), 16, 16, 16);
		}
		
	}

	public void tick() {
		moved = false;
		
		if(World.isFree(this.getX(), (int)(y+2)) || isJumping) {
			y+=4;
		}
		
		
		if(jump) {
			if(!World.isFree(x, y+1)) {
				isJumping = true;
		}else {
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
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		
		if(up && World.isFree(this.getX(), (int)(y-speed))) {
			moved = true;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(), (int)(y+speed))) {
			moved = true;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
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
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
	}

}
