package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;

import java.util.ArrayList;
import java.util.Set;

public class CalculDeplacement {

    protected Game jeu;
    public ArrayList<String> LOGGER = Cockpit.LOGGER;

    public CalculDeplacement(Game jeu) {
        this.jeu = jeu;
    }

    /**
     * Analyse quel déplacement le bateau devra faire pour le tour
     * @param c
     * @return le déplacement que le bateau devra faire pour ce tour
     */
    public Deplacement deplacementPourLeTourRefactor(Checkpoint c){
        Ship s = jeu.getShip();
        int nbr_rames = s.getOars().size();
        Vector v_ship = new Vector(Math.cos(s.getPosition().getOrientation()), Math.sin(s.getPosition().getOrientation()));
        Vector v_check = new Vector(c.getPosition().getX() - s.getPosition().getX(),c.getPosition().getY()-s.getPosition().getY());
        double angle = v_ship.angleBetweenVectors(v_check);
        ArrayList<Deplacement> futur_angle = predictionAngleTourSuivant(v_ship, v_check, c);
        Set<Double> angles_possibles = s.getTurnRange();
        angles_possibles.remove(0.0);
        Double angle_maximum =  quelEstLangleMaximum(angles_possibles);
        Deplacement deplacement = new Deplacement(); //vitesse en premier, angle en deuxième
        deplacement.setAngle(0);

        if(Math.abs(angle) <= Math.PI/4){
            deplacement.setVitesse(165);
            deplacement.setAngle(angle);
            return deplacement;
        }


        if(Math.abs(angle) >= Math.PI/2){
            deplacement.setVitesse(82.5);
            if(angle < 0){
                deplacement.setAngle(-Math.PI/2);
            }
            else{
                deplacement.setAngle(Math.PI/2);
            }
            return deplacement;
        }
        angles_possibles.remove(Math.PI/2);
        angles_possibles.remove(-Math.PI/2);
        // Boucle qui test toutes les valeurs possibles de rotations et renvoie un déplacement si les conditions sont vérifiées
        while(angles_possibles.size() != 0) {
            Double new_angle_maximum = quelEstLangleMaximum(angles_possibles);
            if(Math.abs(angle) < angle_maximum && Math.abs(angle) >= new_angle_maximum){
                // Faire une rotation du nouvel angle maximum
                deplacement.setVitesse(vitesseAdapte(new_angle_maximum, nbr_rames)); // Faire une méthode qui calcule la vitesse minimum pour tourner d'un angle précis avec
                // n'importe quel nombre de rames => on souhaite tourner à une vitesse minimale pour tourner "sec"
                if(angle < 0){
                    deplacement.setAngle(-new_angle_maximum);
                }
                else{
                    deplacement.setAngle(new_angle_maximum);
                }
                return deplacement;
            }
            angles_possibles.remove(new_angle_maximum);
            angles_possibles.remove(-new_angle_maximum);
            angle_maximum = new_angle_maximum;
        }
        // Si on sort de la boucle il nous reste un avant-dernier cas, celui ou l'angle est supérieur à 1° en radian et inférieur au plus petit
        // angle de rotation réalisable par le bateau on fait donc appel à la méthode qui prédit le futur angle et qui choisit la
        // meilleure option de déplacement pour que le bateau puisse avancer.
        if (Math.abs(angle) < angle_maximum && Math.abs(angle) > 0.01745329){
            double vitesse_opti = 0;
            double diffMin = -1;
            for(Deplacement d: futur_angle){
                double diff = Math.abs(angle_maximum - d.getAngle());
                if(diffMin == -1 || diffMin > diff){
                    diffMin = diff;
                    vitesse_opti = d.getVitesse();
                }
            }
            deplacement.setVitesse(vitesse_opti);
            deplacement.setAngle(0);
            return deplacement;
            // Regarder toutes les sous-listes de futur_angle, aller tout droit à la vitesse rapprochant au plus de l'angle PI/4
            // à savoir la plus petite différence entre l'angle futur et PI/4
        }
        // enfin le dernier cas, si on est parfaitement aligné avec le checkpoint
        else {
            // Avancer tout droit à la vitesse maximale
            deplacement.setVitesse(165);
            deplacement.setAngle(0);
            LOGGER.add("Déplacement calculé :" + deplacement.toString());
            return deplacement;
        }
    }

    /**
     * Calcul de la vitesse minimum pour tourner d'un angle précis avec n'importe quel nombre de rames,
     * on souhaite tourner à une vitesse minimale pour tourner "sec"
     * @param angle_rotation
     * @param nbr_rames
     * @return une vitesse minimale
     */
    public Double vitesseAdapte(Double angle_rotation, int nbr_rames){
        Double angle_possible = 0.0;
        int rame_utile = 100;
        for (int i = 0; i <= nbr_rames / 2; i++) {
            angle_possible = Math.PI * i / nbr_rames;
            if (angle_possible.equals(angle_rotation)){
                rame_utile = i;
                break;
            }
        }
        Double vitesse_une_rame = (double) 165/nbr_rames;
        return vitesse_une_rame * rame_utile;
    }

    /**
     * @param angles
     * @return l'angle avec la valeur la plus élevée
     */
    public Double quelEstLangleMaximum(Set<Double> angles){
        double max = -1;
        for(Double a: angles){
            if(Math.abs(a) > max){
                max = a;
            }
        }
        return max;
    }

    /**
     * Le bateau avance droit, on calcule les différents angles qu'on obtiendra en fonction de la vitesse du bateau qu'on peut appliquer
     * au bateau
     * @param v_ship
     * @param v_checkpoint
     * @return une liste de liste de double où chaque sous-liste contient une vitesse associée à un angle
     */
    public ArrayList<Deplacement> predictionAngleTourSuivant(Vector v_ship, Vector v_checkpoint, Checkpoint checkpoint){
        int nbr_oars = jeu.getShip().getOars().size();
        ArrayList<Deplacement> prediction = new ArrayList<>();
        double vitesse = 165;
        double angle_apres_deplacement = 0;
        double positionX_init = jeu.getShip().getPosition().getX();
        double positionY_init = jeu.getShip().getPosition().getY();
        double positionX_apres_deplacement;
        double positionY_apres_deplacement;
        for(int i = 0; i < jeu.getShip().getOars().size()/2; i++){
            vitesse = (vitesse * (nbr_oars-2*i))/nbr_oars;
            positionX_apres_deplacement = positionX_init + (vitesse * Math.cos(jeu.getShip().getPosition().getOrientation()));
            positionY_apres_deplacement = positionY_init + (vitesse * Math.sin(jeu.getShip().getPosition().getOrientation()));
            Vector new_v_check = new Vector(checkpoint.getPosition().getX() - positionX_apres_deplacement,checkpoint.getPosition().getY() - positionY_apres_deplacement);
            angle_apres_deplacement = v_ship.angleBetweenVectors(new_v_check);
            prediction.add(new Deplacement(vitesse, angle_apres_deplacement));
        }
        LOGGER.add("Prédiction :" + prediction);
        return prediction;
    }

    /**
     * @param c
     * @param s
     * @return la distance entre un checkpoint et le bateau
     */
    public double distance(Checkpoint c, Ship s){
        double distance_horizontale = Math.pow((c.getPosition().getX() - s.getPosition().getX()), 2);
        double distance_verticale = Math.pow((c.getPosition().getY() - s.getPosition().getY()), 2);
        return Math.sqrt(distance_horizontale + distance_verticale);
    }
}
