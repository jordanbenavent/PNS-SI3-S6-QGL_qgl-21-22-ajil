package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Sailor;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class StratData {

    protected Game jeu;
    protected final ArrayList<Action> actions;
    protected List<Sailor> sailorsManager;
    protected Sailor coxswain;

    public StratData(Game jeu) {
        this.jeu = jeu;
        this.actions = new ArrayList<>();
    }

    public List<Sailor> getSailorsManager() {
        return this.sailorsManager;
    }

    public void setSailorsManager(Sailor sailorsManager) {
        this.sailorsManager = new ArrayList<>(sailorsManager);
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
}
