package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Sailor;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class StratData {

    protected final ArrayList<Action> actions;
    protected Game jeu;
    protected List<Sailor> sailorsManager;
    protected Sailor coxswain;
    protected Sailor vigie;

    public StratData(Game jeu) {
        this.jeu = jeu;
        this.actions = new ArrayList<>();
    }

    public List<Sailor> getSailorsManager() {
        return this.sailorsManager;
    }

    public void setSailorsManager(List<Sailor> sailManager) {
        this.sailorsManager = sailManager;
    }

    public Sailor getCoxswain() {
        return coxswain;
    }

    public void setCoxswain(Sailor coxswain) {
        this.coxswain = coxswain;
    }

    public Sailor getVigie() { return vigie; }

    public void setVigie(Sailor vigie) { this.vigie = vigie; }
}
