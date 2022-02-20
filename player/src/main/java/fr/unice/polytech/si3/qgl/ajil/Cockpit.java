package fr.unice.polytech.si3.qgl.ajil;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

/**
 * Classe Cockpit
 * C'est notre interface pour communiquer avec le referee grâce aux méthodes initGame et nextRound
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Cockpit implements ICockpit {

	private Game jeu;
	private Strategie strategie;
	private NextRound nextRound;
	ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	/**
	 * @return la partie en cours
	 */
	public Game getJeu() {
		return jeu;
	}

	/**
	 * Initialise la partie
	 * @param game
	 */
	public void initGame(String game) {
		try {
			this.jeu = objectMapper.readValue(game, Game.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		strategie = new Strategie(jeu);
	}

	/**
	 * Après application de la stratégie de jeu une liste d'actions est crée, on renvoie un JSON qui est cette liste d'actions
	 * @param round
	 * @return un string composé de la liste des actions
	 */
	public String nextRound(String round) {
		try {
			this.nextRound = objectMapper.readValue(round, NextRound.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		nextRound.updateGame(jeu);
		strategie.setJeu(jeu);
		//System.out.println(nextRound.getShip().getEntities());
		//System.out.println(jeu.getSailors());

		return strategie.getActions();
	}

	/**
	 * @return un logger
	 */
	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}

	/**
	 * @return les informations du tour en cours
	 */
	public NextRound getNextRound() {
		return nextRound;
	}

	/**
	 * Modifie les informations du tour en cours
	 * @param nextRound
	 */
	public void setNextRound(NextRound nextRound) {
		this.nextRound = nextRound;
	}

	public static void main(String[] args) {
		Cockpit cockpit = new Cockpit();
		cockpit.initGame("{\n  \"goal\": {\n    \"mode\": \"REGATTA\",\n    \"checkpoints\": [\n      {\n        \"position\": {\n          \"x\": \"1000\",\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      },\n      {\n        \"position\": {\n          \"x\": 0,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      }\n    ]\n  },\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"sailors\": [\n    {\n      \"x\": 0,\n      \"y\": 0,\n      \"id\": 0,\n      \"name\": \"Edward Teach\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 1,\n      \"id\": 1,\n      \"name\": \"Edward Pouce\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 2,\n      \"id\": 2,\n      \"name\": \"Tom Pouce\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 0,\n      \"id\": 3,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 1,\n      \"id\": 4,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 2,\n      \"id\": 5,\n      \"name\": \"Tom Pouce\"\n    }\n  ],\n  \"shipCount\": 1\n}");
		cockpit.nextRound("{\n" +
				"  \"ship\": {\n" +
				"    \"type\": \"ship\",\n" +
				"    \"life\": 100,\n" +
				"    \"position\": {\n" +
				"      \"x\": 0,\n" +
				"      \"y\": 0,\n" +
				"      \"orientation\": 0\n" +
				"    },\n" +
				"    \"name\": \"Les copaings d'abord!\",\n" +
				"    \"deck\": {\n" +
				"      \"width\": 3,\n" +
				"      \"length\": 6\n" +
				"    },\n" +
				"    \"entities\": [\n" +
				"      {\n" +
				"        \"x\": 1,\n" +
				"        \"y\": 0,\n" +
				"        \"type\": \"oar\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"x\": 1,\n" +
				"        \"y\": 2,\n" +
				"        \"type\": \"oar\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"x\": 3,\n" +
				"        \"y\": 0,\n" +
				"        \"type\": \"oar\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"x\": 3,\n" +
				"        \"y\": 2,\n" +
				"        \"type\": \"oar\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"x\": 4,\n" +
				"        \"y\": 0,\n" +
				"        \"type\": \"oar\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"x\": 4,\n" +
				"        \"y\": 2,\n" +
				"        \"type\": \"oar\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"x\": 2,\n" +
				"        \"y\": 1,\n" +
				"        \"type\": \"sail\",\n" +
				"        \"openned\": false\n" +
				"      },\n" +
				"      {\n" +
				"        \"x\": 5,\n" +
				"        \"y\": 0,\n" +
				"        \"type\": \"rudder\"\n" +
				"      }\n" +
				"    ],\n" +
				"    \"shape\": {\n" +
				"      \"type\": \"rectangle\",\n" +
				"      \"width\": 3,\n" +
				"      \"height\": 6,\n" +
				"      \"orientation\": 0\n" +
				"    }\n" +
				"  },\n" +
				"  \"visibleEntities\": [\n" +
				"    {\n" +
				"      \"type\": \"stream\",\n" +
				"      \"position\": {\n" +
				"        \"x\": 500,\n" +
				"        \"y\": 0,\n" +
				"        \"orientation\": 0\n" +
				"      },\n" +
				"      \"shape\": {\n" +
				"        \"type\": \"rectangle\",\n" +
				"        \"width\": 50,\n" +
				"        \"height\": 500,\n" +
				"        \"orientation\": 0\n" +
				"      },\n" +
				"      \"strength\": 40\n" +
				"    }\n" +
				"  ],\n" +
				"  \"wind\": {\n" +
				"    \"orientation\": 0,\n" +
				"    \"strength\": 110\n" +
				"  }\n" +
				"}");
	}
}
