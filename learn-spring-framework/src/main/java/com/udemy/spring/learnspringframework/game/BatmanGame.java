package com.udemy.spring.learnspringframework.game;

import org.springframework.stereotype.Component;

@Component
public class BatmanGame implements GamingConsole {

	public void up() {
		System.out.println("Batman up");
	}
	
	public void down() {
		System.out.println("Batman down");
	}
	
	public void left() {
		System.out.println("Batman left");
	}
	
	public void right() {
		System.out.println("Batman right");
	}
}
