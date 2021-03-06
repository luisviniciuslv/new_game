package com.arcastudio.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arcastudio.main.Game;
import com.arcastudio.world.Camera;
import com.arcastudio.world.World;

public class Player extends Entity {
	
	public int curAnimation = 0;
	public int curFrames = 0, targetFrames = 15;
	
	public boolean right, up, left;
	public static boolean dodge;
	
	
	public static boolean atack;
	public static boolean PlayercanAtack;
	
	
	public static boolean canAtack;
	public static String position = "stop";
	public int speed = 1;
	public int timing;
	
	
	public boolean jump = false;
	public int jumpHeight = 32;
	public int jumpFrames = 0;
	public boolean isJumping = false;
	
	private int frames = 0, maxFrames = 15, index =	0, maxIndex = 3;
	private boolean moved = false;
	
	private BufferedImage frontPlayer;
	private BufferedImage downRightPlayer;
	private BufferedImage downLeftPlayer;
	private BufferedImage jumpRightPlayer;
	private BufferedImage jumpLeftPlayer;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] rightPlayerAtack;
	private BufferedImage[] leftPlayerAtack;
	
	
	public static double life = 100, maxLife = 100;
	
	
	
	// ITENS
	public static int LIFEPACKS = 0;
	public boolean useLifePACKS = false;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		//Anima��es quantidade
		frontPlayer = Game.spriteplayer.getSprite(0,0,16,16);
		downRightPlayer = Game.spriteplayer.getSprite(112,0,16,16);
		downLeftPlayer = Game.spriteplayer.getSprite(112,16,16,16);
		jumpRightPlayer = Game.spriteplayer.getSprite(16,32,16,16);
		jumpLeftPlayer = Game.spriteplayer.getSprite(0,32,16,16);
		
		rightPlayer = new BufferedImage[6];	
		rightPlayer[0] = Game.spriteplayer.getSprite(16,0,16,16);
		rightPlayer[1] = Game.spriteplayer.getSprite(32,0,16,16);
		rightPlayer[2] = Game.spriteplayer.getSprite(48,0,16,16);
		rightPlayer[3] = Game.spriteplayer.getSprite(64,0,16,16);
		rightPlayer[4] = Game.spriteplayer.getSprite(80,0,16,16);
		rightPlayer[5] = Game.spriteplayer.getSprite(96,0,16,16);
		
		leftPlayer = new BufferedImage[6];
		leftPlayer[0] = Game.spriteplayer.getSprite(96, 16, 16, 16);
		leftPlayer[1] = Game.spriteplayer.getSprite(80, 16, 16, 16);
		leftPlayer[2] = Game.spriteplayer.getSprite(64, 16, 16, 16);
		leftPlayer[3] = Game.spriteplayer.getSprite(48, 16, 16, 16);
		leftPlayer[4] = Game.spriteplayer.getSprite(32, 16, 16, 16);
		leftPlayer[5] = Game.spriteplayer.getSprite(16, 16, 16, 16);

		rightPlayerAtack = new BufferedImage[3];
		rightPlayerAtack[0] = Game.spriteplayer.getSprite(0, 64, 16, 16);
		rightPlayerAtack[1] = Game.spriteplayer.getSprite(16, 64, 22, 16);
		rightPlayerAtack[2] = Game.spriteplayer.getSprite(0, 64, 16, 16);
		
		leftPlayerAtack = new BufferedImage[3];
		leftPlayerAtack[0] = Game.spriteplayer.getSprite(80, 64, 16, 16);
		leftPlayerAtack[1] = Game.spriteplayer.getSprite(58, 64, 22, 16);
		leftPlayerAtack[2] = Game.spriteplayer.getSprite(80, 64, 16, 16);

	}

	public void tick() {
		moved = false;		
		if(atack && position == "left" || position == "leftAtack") {
			curFrames++;
			position = "leftAtack";
			if(curFrames == 10) {
				curFrames = 0;
				curAnimation++;
				if(curAnimation == leftPlayerAtack.length) {
					curAnimation = 0;
					position = "left";
					atack = false;
				}
			}}
		if(atack && position == "right" || position == "rightAtack") {
			curFrames++;
			position = "rightAtack";
			if(curFrames == 10) {
				curFrames = 0;
				curAnimation++;
				if(curAnimation == leftPlayerAtack.length) {
					curAnimation = 0;
					position = "right";
					atack = false;
				}
			}}
	

		if(useLifePACKS) {
			if(life < maxLife) {
				if(LIFEPACKS > 0) {
					LIFEPACKS--;	
					if(LIFEPACKS < 0) {
						LIFEPACKS = 0;
					}
					life +=10;
					if(life > maxLife) {
						life = maxLife;
					}
				}
			}
			useLifePACKS = false;
		}
		if(right && World.isFree((int)(x+speed), this.getY())) {
			moved = true;
			position = "right";
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed), this.getY())) {
			moved = true;
			position = "left";
			x-=speed;
		}
		if(dodge) {
			timing++;
			if(position == "left") {
				position = "downleft";
			}
			else if(position == "right") {
				position = "downright";
			}
		}
			if(timing == 30) {
				timing = 0;
				dodge = false;
				if(position == "downleft") {
					position = "left";
				}
				else if(position == "downright") {
					position = "right";
				}
		}
		
		if(jump) { 
			if(!World.isFree(this.getX(),this.getY()+1)){
				isJumping = true;
		}
			else {
			jump = false;
			}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-1)) {
				if(position == "left") {
					position = "jumpLeft";
				}
				if(position == "right") {
					position = "jumpRight";
				}
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
	
		else if(World.isFree(this.getX(), (int)(y+1))) {
			moved = true;
			y+=2;
		}else {
			if(position == "jumpLeft") {
				position = "left";
			}
			else if(position == "jumpRight"){
				position = "right";
			}
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
		
		checkCollisionLifePack();
		
		//Config Camera
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	//Coletar vida
	public void checkCollisionLifePack() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Lifepack) {
				if(Entity.isCollidding(this, e)) {
					LIFEPACKS+=1;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		//Ativar o player no JFrame, flip
		if(position == "rightAtack") {
			g.drawImage(rightPlayerAtack[curAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if(position == "leftAtack") {
			g.drawImage(leftPlayerAtack[curAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if(position == "jumpRight") {
			g.drawImage(jumpRightPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if(position == "jumpLeft") {
			g.drawImage(jumpLeftPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if(position == "downright") {
			g.drawImage(downRightPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if(position == "downleft") {
			g.drawImage(downLeftPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if(position == "stop") {
			g.drawImage(frontPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if(position == "right") {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if(position == "left") {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
