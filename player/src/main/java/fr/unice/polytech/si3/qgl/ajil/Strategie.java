package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.actions.Oar;

import java.util.ArrayList;

public class Strategie {
    private Game jeu;
    private ArrayList<Action> actions;
    private ObjectMapper objectMapper;
    private boolean placementInit = false; // Placement des marins sur les rames au debut de partie

    public Strategie(Game jeu) {
        this.jeu = jeu;
        actions = new ArrayList<Action>();
        objectMapper = new ObjectMapper();
    }

    public Game getJeu() {
        return jeu;
    }

    public void setJeu(Game jeu) {
        this.jeu = jeu;
    }

    public String getActions(){
        actions.clear();
        effectuerActions();
        try {
            return objectMapper.writeValueAsString(actions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void effectuerActions() {
        if (!placementInit){
            placerSurRames();
        }
    }

    // Placement initial
    private void placerSurRames() {
        ArrayList<Entity> oars = jeu.getShip().getOars();
        ArrayList<Sailor> sailors = jeu.getSailors();
        int distMin;
        for (Sailor s : sailors){
            distMin = 6;
            int indexMin = 0;
            for (int i =0 ; i< oars.size(); i++){
                int dist = oars.get(i).getDist(s);
                if (dist < distMin){
                    distMin = dist;
                    indexMin = i;
                }
            }
            int movX = oars.get(indexMin).getX() - s.getX();
            int movY = oars.get(indexMin).getY() - s.getY();
            oars.remove(indexMin);
            actions.add(new Moving(s.getId(), movX, movY));
        }
        this.placementInit = true;
    }

    void avancer(){
        actions.add(new Oar(0));
        actions.add(new Oar(1));
    }
}
