package com.arcastudio.world;

public class Camera {
	
	public static int x = 0;
	public static int y = 0;
	
	public static int clamp(int Atual, int Min, int Max) {
		if(Atual < Min) {
			Atual = Min;
		}
		
		if(Atual > Max) {
			Atual = Max;
		}
		
		System.out.println(x);
		return Atual;
	}
	
}
