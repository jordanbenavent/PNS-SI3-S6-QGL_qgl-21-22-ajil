package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import java.util.ArrayList;

public class StratData {

    protected Game jeu;
    protected final ArrayList<Action> actions;

    public StratData(Game jeu){
        this.jeu=jeu;
        this.actions= new ArrayList<>();
    }

}
