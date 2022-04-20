package com.arcastudio.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteEnemySlime {
private BufferedImage SpriteEnemySlime;
	
	public SpriteEnemySlime(String path) {
		try {
			SpriteEnemySlime = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return SpriteEnemySlime.getSubimage(x, y, width, height);
	}
}
