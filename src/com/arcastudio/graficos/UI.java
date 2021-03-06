package com.arcastudio.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.arcastudio.entities.Entity;
import com.arcastudio.entities.Player;

public class UI {
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(5, 8, 50,8);
		g.setColor(Color.green);
		g.fillRect(5, 8,(int)((Player.life/Player.maxLife)*50),8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString((int)Player.life+"/"+(int)Player.maxLife, 16, 15);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString("Q", 5, 143);
		
		g.drawImage(Entity.LIFEPACK_EN, 0, 140, null);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString((int)(Player.LIFEPACKS) + "", 15, 155);
		
	
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString("E", 36, 143);
		
		g.drawImage(Entity.FIRE_EN, 30, 140, null);
		g.setColor(Color.white);

		
		
	}
}
