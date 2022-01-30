package fr.unice.polytech.si3.qgl.ajil;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

public class Cockpit implements ICockpit {
	private Game jeu;

	public Game getJeu() {
		return jeu;
	}

	public void initGame(String game) {
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			this.jeu = objectMapper.readValue(game, Game.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Init game input: " + game);
	}

	public String nextRound(String round) {
		System.out.println("Next round input: " + round);
		return "[]";
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}
