package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CalculDeplacement {

    protected Game jeu;
    protected StratData stratData;
    public List<String> LOGGER = Cockpit.LOGGER;

    public CalculDeplacement(StratData stratData) {
        this.jeu = stratData.jeu;
        this.stratData = stratData;
    }

    /**
     * Actionne la voile
     */

    void putSail() {
        /*
        Vitesse du vent:
        Direction: direction du bateau
        Valeur: (nombre de voile ouverte / nombre de voile) x force du vent x cosinus(angle entre la direction du vent et la direction du bateau)
         */
        Ship s = stratData.jeu.getShip();
        Wind wind = stratData.jeu.getWind();
        double shipOrientation = s.getPosition().getOrientation()  % (2 * Math.PI);
        double windOrientation = wind.getOrientation();
        if (stratData.getSailorsManager() != null) {
            int barreur = stratData.getSailorsManager().getId();
            LiftSail lift = new LiftSail(barreur);
            LowerSail lower = new LowerSail(barreur);

            final double RANGE = Math.PI / 2;
            stratData.actions.remove(lift);
            stratData.actions.remove(lower);
            if ((windOrientation + RANGE > shipOrientation) && (windOrientation - RANGE < shipOrientation)) {
                stratData.actions.add(lift);
                LOGGER.add("Voile UP");
            } else {
                stratData.actions.add(lower);
                LOGGER.add("DOWN");

            }
        } else {
            LOGGER.add("Pas de Sailer Manager");
        }
    }

    /**
     * Analyse quel déplacement le bateau devra faire pour le tour
     *
     * @param checkpoint Checkpoint needed to fetch position
     * @return le déplacement que le bateau devra faire pour ce tour
     */
    public Deplacement deplacementPourLeTourRefactor(Checkpoint checkpoint) {
        Ship ship = jeu.getShip();
        int nbr_rames = ship.getOars().size();
        Vector v_ship = calculVecteurBateau(ship);
        Vector v_check = calculVecteurCheckpoint(checkpoint, ship);
        double angle = v_ship.angleBetweenVectors(v_check);
        ArrayList<Deplacement> futur_angle = predictionAngleTourSuivant(v_ship, v_check, checkpoint);
        Set<Double> angles_possibles = ship.getTurnRange();
        angles_possibles.remove(0.0);
        Double angle_maximum =  quelEstLangleMaximum(angles_possibles);
        Deplacement deplacement = new Deplacement(); //vitesse en premier, angle en deuxième
        Sailor barreur = stratData.getBarreur();
        // Stratégie de déplacement commune aux deux stratégies (avec et sans barreur)
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
        if(barreur != null){
            // Dans le cas ou l'angle est inférieur ou égale à la valeur absolue de PI/4 on renvoie l'angle précis car c'est le gouvernail qui se chargera de tourner
            if(Math.abs(angle) <= Math.PI/4) {
                deplacement = deplacementSiGouvernail(angle, deplacement);
                return deplacement;
            }
            angles_possibles.removeIf(a -> Math.abs(a) < Math.PI/4);
            if(angles_possibles.size() == 0){
                return deplacement;
            }
        }
        while(angles_possibles.size() != 0) {
            Double new_angle_maximum = quelEstLangleMaximum(angles_possibles);
            if(Math.abs(angle) < angle_maximum && Math.abs(angle) >= new_angle_maximum){
                // Faire une rotation du nouvel angle maximum
                deplacement.setVitesse(vitesseAdapte(new_angle_maximum, nbr_rames)); // Faire une méthode qui calcule la vitesse minimum pour tourner d'un angle précis avec
                // n'importe quel nombre de rames => on s
                // ouhaite tourner à une vitesse minimale pour tourner "sec"
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
        //deplacement = deplacementParmiAnglesPossibles(angle, angles_possibles, angle_maximum, deplacement, nbr_rames);
        // Si on sort de la boucle il nous reste un avant-dernier cas, celui ou l'angle est supérieur à 1° en radian et inférieur au plus petit
        // angle de rotation réalisable par le bateau on fait donc appel à la méthode qui prédit le futur angle et qui choisit la
        // meilleure option de déplacement pour que le bateau puisse avancer.
        if(Math.abs(angle) < angle_maximum && Math.abs(angle) > 0.01745329){
            deplacement = deplacementSelonPrediction(angle_maximum, futur_angle, deplacement);
        }
        return deplacement;
    }

    /**
     * Méthode de calcul du vecteur bateau permettant une meilleure compréhension de la méthode
     * déplacementPourLeTourRefactor
     * @param ship
     * @return
     */
    public Vector calculVecteurBateau(Ship ship){
        Vector v_ship =  new Vector(Math.cos(ship.getPosition().getOrientation()), Math.sin(ship.getPosition().getOrientation()));
        return v_ship;
    }

    /**
     * Méthode de calcul du vecteur checkpoint permettant une meilleure compréhension de la méthode
     * déplacementPourLeTourRefactor
     * @param checkpoint
     * @param ship
     * @return
     */
    public Vector calculVecteurCheckpoint(Checkpoint checkpoint, Ship ship){
        Vector v_check = new Vector(checkpoint.getPosition().getX() - ship.getPosition().getX(), checkpoint.getPosition().getY()-ship.getPosition().getY());
        return v_check;
    }

    /**
     * Stratégie de déplacement si on a un barreur sur le bateau
     * @param angle
     * @return le déplacement à effectuer
     */
    public Deplacement deplacementSiGouvernail(Double angle, Deplacement deplacement){
        deplacement.setAngle(angle);
        deplacement.setVitesse(165.0);
        return deplacement;
    }

    /**
     * Stratégie de déplacement selon la prédiction du futur angle que le bateau aura avec le checkpoint après son déplacement
     * @param angle_maximum
     * @param futur_angle
     * @param deplacement
     * @return le déplacement à effectuer
     */
    public Deplacement deplacementSelonPrediction(Double angle_maximum, ArrayList<Deplacement> futur_angle, Deplacement deplacement){
        double vitesse_opti = 0;
        double diffMin = -1;
        for(Deplacement d: futur_angle){
            double diff;
            if (d.getAngle() < 0){
                diff = Math.abs(angle_maximum + d.getAngle());
            }
            else{
                diff = Math.abs(angle_maximum - d.getAngle());
            }
            if(diffMin == -1 || diffMin > diff){
                diffMin = diff;
                vitesse_opti = d.getVitesse();
            }
        }
        deplacement.setVitesse(vitesse_opti);
        deplacement.setAngle(0);
        return deplacement;
    }

    /**
     * Calcul de la vitesse minimum pour tourner d'un angle précis avec n'importe quel nombre de rames,
     * on souhaite tourner à une vitesse minimale pour tourner "sec"
     *
     * @param angle_rotation Angle
     * @param nbr_rames nombre de Oars
     * @return une vitesse minimale
     */
    public Double vitesseAdapte(Double angle_rotation, int nbr_rames) {
        Double angle_possible;
        int rame_utile = 100;
        for (int i = 0; i <= nbr_rames / 2; i++) {
            angle_possible = Math.PI * i / nbr_rames;
            if (angle_possible.equals(angle_rotation)) {
                rame_utile = i;
                break;
            }
        }
        double vitesse_une_rame = (double) 165 / nbr_rames;
        return vitesse_une_rame * rame_utile;
    }

    /**
     * @param angles Une liste comportant des angles sous forme décimal / radian
     * @return l'angle avec la valeur la plus élevée
     */
    public Double quelEstLangleMaximum(Set<Double> angles) {
        double max = -1;
        for (Double a : angles) {
            if (Math.abs(a) > max) {
                max = a;
            }
        }
        return max;
    }

    /**
     * Le bateau avance droit, on calcule les différents angles qu'on obtiendra en fonction de la vitesse du bateau qu'on peut appliquer
     * au bateau
     *
     *
     * @param v_ship Vecteur bateau
     * @param v_checkpoint vecteur checkpoint
     * @param checkpoint checkpoint
     * @return une liste de liste de double où chaque sous-liste contient une vitesse associée à un angle
     */
    public ArrayList<Deplacement> predictionAngleTourSuivant(Vector v_ship, Vector v_checkpoint, Checkpoint checkpoint){
        int nbr_oars = jeu.getShip().getOars().size();
        ArrayList<Deplacement> prediction = new ArrayList<>();
        double vitesse_init = 165;
        double vitesse;
        double angle_apres_deplacement;
        double positionX_init = jeu.getShip().getPosition().getX();
        double positionY_init = jeu.getShip().getPosition().getY();
        double positionX_apres_deplacement;
        double positionY_apres_deplacement;
        for(int i = 0; i < jeu.getShip().getOars().size()/2; i++){
            vitesse = (vitesse_init * (nbr_oars-2*i))/nbr_oars;
            positionX_apres_deplacement = positionX_init + (vitesse * Math.cos(jeu.getShip().getPosition().getOrientation()));
            positionY_apres_deplacement = positionY_init + (vitesse * Math.sin(jeu.getShip().getPosition().getOrientation()));
            Vector new_v_check = new Vector(checkpoint.getPosition().getX() - positionX_apres_deplacement,checkpoint.getPosition().getY() - positionY_apres_deplacement);
            angle_apres_deplacement = v_ship.angleBetweenVectors(new_v_check);
            prediction.add(new Deplacement(vitesse, angle_apres_deplacement));
        }
        LOGGER.add("Prédiction :" + prediction);
        return prediction;
    }
}
