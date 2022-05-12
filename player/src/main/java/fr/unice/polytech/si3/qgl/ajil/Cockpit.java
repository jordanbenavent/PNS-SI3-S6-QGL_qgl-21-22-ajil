package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.strategy.Strategy;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Cockpit
 * C'est notre interface pour communiquer avec le referee grâce aux méthodes initGame et nextRound
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Cockpit implements ICockpit {

    public static final List<String> LOGGER = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private Game jeu;
    private Strategy strategy;
    private NextRound nextRound;

    public static void main(String[] args) {

    }

    /**
     * @return la partie en cours
     */
    public Game getJeu() {
        return jeu;
    }

    /**
     * Initialise la partie
     *
     * @param game Json string
     */
    public void initGame(String game) {
        LOGGER.add("Début de la partie");
        System.out.println("Début de la partie");
        try {
            this.jeu = objectMapper.readValue(game, Game.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        strategy = new Strategy(jeu);
        LOGGER.add("INIT GAME");
    }

    /**
     * Après application de la stratégie de jeu une liste d'actions est crée, on renvoie un JSON qui est cette liste d'actions
     *
     * @param round Json next round
     * @return un string composé de la liste des actions
     */
    public String nextRound(String round) {
        try {
            this.nextRound = objectMapper.readValue(round, NextRound.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        nextRound.updateGame(jeu);
        strategy.setJeu(jeu);

        return strategy.getActions();
    }

    /**
     * @return un logger
     */
    @Override
    public List<String> getLogs() {
        return LOGGER;
    }

    /**
     * @return les informations du tour en cours
     */
    public NextRound getNextRound() {
        return nextRound;
    }

    /**
     * Modifie les informations du tour en cours
     *
     * @param nextRound JSON
     */
    public void setNextRound(NextRound nextRound) {
        this.nextRound = nextRound;
    }
}
