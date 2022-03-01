package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;

import java.util.ArrayList;

public class CalculDeplacement {

    protected Game jeu;
    public ArrayList<String> LOGGER = Cockpit.LOGGER;

    public CalculDeplacement(Game jeu) {
        this.jeu = jeu;
    }

    /**
     * Analyse quel déplacement le bateau devra faire pour le tour
     *
     * @param c Checkpoint
     * @return le déplacement que le bateau devra faire pour ce tour
     */
    public Deplacement deplacementPourLeTour(Checkpoint c) {
        Ship s = jeu.getShip();
        Vector v_ship = new Vector(Math.cos(s.getPosition().getOrientation()), Math.sin(s.getPosition().getOrientation()));
        Vector v_check = new Vector(c.getPosition().getX() - s.getPosition().getX(), c.getPosition().getY() - s.getPosition().getY());
        double angle = v_ship.angleBetweenVectors(v_check);
        ArrayList<Deplacement> futur_angle = predictionAngleTourSuivant(v_ship, v_check);
        Deplacement deplacement = new Deplacement(); //vitesse en premier, angle en deuxième
        if (Math.abs(angle) >= Math.PI / 2) {
            // Faire une rotation de PI/2
            deplacement.setVitesse(82.5);
            if (angle < 0) {
                deplacement.setAngle(-Math.PI / 2);
            } else {
                deplacement.setAngle(Math.PI / 2);
            }
            return deplacement;
        } else if (Math.abs(angle) < Math.PI / 2 && Math.abs(angle) > Math.PI / 4) {
            // Faire une rotation de PI/4
            deplacement.setVitesse(41.25);
            if (angle < 0) {
                deplacement.setAngle(-Math.PI / 4);
            } else {
                deplacement.setAngle(Math.PI / 4);
            }
            return deplacement;
        } else if (Math.abs(angle) < Math.PI / 4 && Math.abs(angle) > 0) {
            double vitesse_opti = 0;
            double diffMin = -1;
            for (Deplacement d : futur_angle) {
                double diff = Math.abs(Math.PI / 4 - d.getAngle());
                if (diffMin == -1 || diffMin > diff) {
                    diffMin = diff;
                    vitesse_opti = d.getVitesse();
                }
            }
            deplacement.setVitesse(vitesse_opti);
            deplacement.setAngle(0);
            return deplacement;
            // Regarder toutes les sous-listes de futur_angle, aller tout droit à la vitesse rapprochant au plus de l'angle PI/4
            // à savoir la plus petite différence entre l'angle futur et PI/4
        } else {
            // Avancer tout droit à la vitesse maximale
            deplacement.setVitesse(165);
            deplacement.setAngle(0);
            LOGGER.add(deplacement.toString());
            return deplacement;
        }
    }

    /**
     * Le bateau avance droit, on calcule les différents angles qu'on obtiendra en fonction de la vitesse du bateau qu'on peut appliquer
     * au bateau
     *
     * @param ship       Ship object
     * @param checkpoint Checkpoint
     * @return une liste de liste de double où chaque sous-liste contient une vitesse associée à un angle
     */
    public ArrayList<Deplacement> predictionAngleTourSuivant(Vector ship, Vector checkpoint) {
        int nbr_oars = jeu.getShip().getOars().size();
        ArrayList<Deplacement> prediction = new ArrayList<>();
        double vitesse = 165;
        double angle_init = ship.angleBetweenVectors(checkpoint);
        System.out.println("angle init = " + angle_init);
        double angle_apres_deplacement;
        double positionX_init = ship.getX();
        double positionY_init = ship.getY();
        System.out.println("Boucle:");
        for (int i = 0; i < jeu.getShip().getOars().size() / 2; i++) {
            vitesse = (vitesse * (nbr_oars - 2 * i)) / nbr_oars;
            ship.setX(positionX_init + (vitesse * Math.cos(angle_init))); //On modifie la coordonée en X du bateau en fonction de son orientation
            ship.setY(positionY_init + (vitesse * Math.sin(angle_init))); //On modifie la coordonée en Y du bateau en fonction de son orientation
            angle_apres_deplacement = ship.angleBetweenVectors(checkpoint);
            prediction.add(new Deplacement(vitesse, angle_apres_deplacement));
        }
        return prediction;
    }

    /**
     * @param c Checkpoint object
     * @param s Ship object
     * @return la distance entre un checkpoint et le bateau
     */
    public double distance(Checkpoint c, Ship s) {
        double distance_horizontale = Math.pow((c.getPosition().getX() - s.getPosition().getX()), 2);
        double distance_verticale = Math.pow((c.getPosition().getY() - s.getPosition().getY()), 2);
        return Math.sqrt(distance_horizontale + distance_verticale);
    }
}
