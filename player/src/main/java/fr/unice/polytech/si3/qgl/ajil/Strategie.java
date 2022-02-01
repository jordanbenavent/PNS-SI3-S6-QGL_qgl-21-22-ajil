package fr.unice.polytech.si3.qgl.ajil;

public class Strategie {
    private Game jeu;

    public Strategie(Game jeu) {
        this.jeu = jeu;
    }

    public Game getJeu() {
        return jeu;
    }

    public void setJeu(Game jeu) {
        this.jeu = jeu;
    }
}
