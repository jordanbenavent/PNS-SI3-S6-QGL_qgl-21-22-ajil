package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.actions.Oar;

import java.util.ArrayList;

public class Strategie {
    private Game jeu;
    private ArrayList<Action> actions = new ArrayList<Action>();
    private ObjectMapper objectMapper = new ObjectMapper();

    public Strategie(Game jeu) {
        this.jeu = jeu;
    }

    public Game getJeu() {
        return jeu;
    }

    public void setJeu(Game jeu) {
        this.jeu = jeu;
    }

    public String getActions(){
        avancer();
        try {
            return objectMapper.writeValueAsString(actions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
    void avancer(){
        actions.add(new Oar(0));
        actions.add(new Oar(1));
    }
}
