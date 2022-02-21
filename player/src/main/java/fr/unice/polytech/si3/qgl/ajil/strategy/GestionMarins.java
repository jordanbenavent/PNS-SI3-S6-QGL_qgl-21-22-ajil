package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Sailor;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.actions.Oar;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;

import java.util.ArrayList;

public class GestionMarins  {

    private boolean placementInit = false;
    protected StratData stratData;
    public GestionMarins(StratData stratData) {
        this.stratData = stratData;
    }

    // marins
    private final ArrayList<Sailor> leftSailors = new ArrayList<>();
    private final ArrayList<Sailor> rightSailors = new ArrayList<>();


    /**
     * @return la liste des marins à gauche du bateau
     */
    public ArrayList<Sailor> getLeftSailors() {
        return leftSailors;
    }

    /**
     * @return la liste des marins à droite du bateau
     */
    public ArrayList<Sailor> getRightSailors() {
        return rightSailors;
    }

    public boolean isPlacementInit() {
        return placementInit;
    }



    /**
     * Ajoute les marins dans la liste de marins à gauche ou à droite du bateau en fonction de leur position sur ce dernier
     */
    public void whereAreSailors() {
        ArrayList<Sailor> sailors = stratData.jeu.getSailors();
        for (Sailor sailor : sailors){
            if (sailor.getY() < (stratData.jeu.getShip().getDeck().getWidth()/2)) {
                leftSailors.add(sailor);
            } else {
                rightSailors.add(sailor);
            }
        }
    }


    /**
     * Ajoute à la liste d'actions une ou plusieurs ramer en fonction de la vitesse et de l'angle voulu.
     * @param deplacement
     */
    public void ramer(Deplacement deplacement) {
        if (deplacement.getAngle() < 0) {
            if (deplacement.getAngle() == -Math.PI / 2) {
                for (Sailor sailor : leftSailors) {
                    stratData.actions.add(new Oar(sailor.getId()));
                }

            }
            if (deplacement.getAngle() == -Math.PI / 4) {
                for (int i = 0; i < leftSailors.size() / 2; i++) {
                    stratData.actions.add(new Oar(leftSailors.get(i).getId()));
                }
            }
        }
        if (deplacement.getAngle() > 0) {
            if (deplacement.getAngle() == Math.PI / 2) {
                for (Sailor sailor : rightSailors) {
                    stratData.actions.add(new Oar(sailor.getId()));
                }
            }
            if (deplacement.getAngle() == Math.PI / 4) {
                for (int i = 0; i < rightSailors.size() / 2; i++) {
                    stratData.actions.add(new Oar(rightSailors.get(i).getId()));
                }
            }
        }
        if (deplacement.getVitesse() == 165) {
            for (Sailor sailor : stratData.jeu.getSailors()) {
                stratData.actions.add(new Oar(sailor.getId()));

            }
        } else {
            for (int i = 0; i < rightSailors.size() / 2; i++) {
                stratData.actions.add(new Oar(rightSailors.get(i).getId()));
            }
            for (int i = 0; i < leftSailors.size() / 2; i++) {
                stratData.actions.add(new Oar(leftSailors.get(i).getId()));
            }
        }
    }



    /**
     * Ajoute à la liste d'actions les déplacement que doivent effectuer les marins pour se placer sur les rames
     */
    public void placerSurRames() {
        ArrayList<Entity> oars = stratData.jeu.getShip().getOars();
        ArrayList<Sailor> sailors = stratData.jeu.getSailors();
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
            stratData.actions.add(new Moving(s.getId(), movX, movY));
        }
        this.placementInit = true;
    }




}
