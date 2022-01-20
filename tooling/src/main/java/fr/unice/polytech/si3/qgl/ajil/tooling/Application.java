package fr.unice.polytech.si3.qgl.ajil.tooling;

import fr.unice.polytech.si3.qgl.ajil.Cockpit;

public class Application {
	
	public static void main(String [] args) {
		Cockpit cockpit = new Cockpit();
		System.out.println("An instance of my team player: " + cockpit);
		System.out.println("When called, it returns some JSON: " + cockpit.nextRound(""));
	}
}
