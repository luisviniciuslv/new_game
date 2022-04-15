package com.arcastudio.graficos;

import java.awt.Color;
import java.awt.Graphics;

import com.arcastudio.entities.Player;

public class UI {
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8, 4, 50,8);
		g.setColor(Color.GREEN);
		g.fillRect(8, 4, (int)((Player.life/Player.maxlife)*50), 8);
	}
}
