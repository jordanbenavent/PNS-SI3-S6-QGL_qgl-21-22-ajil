package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.*;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

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
     * Analyse quel déplacement le bateau devra faire pour le tour
     *
     * @param checkpoint Checkpoint needed to fetch position
     * @return le déplacement que le bateau devra faire pour ce tour
     */
    public Deplacement deplacementPourLeTourRefactor(Checkpoint checkpoint) {
        Ship ship = jeu.getShip();
        int nbr_rames = ship.getOars().size();
        if(stratData.jeu.getGoal().getCheckpoints().size() > 1){
            checkpoint = viseExtremiteCheckpoint(checkpoint);
        }
        Vector v_ship = calculVecteurBateau(ship);
        Vector v_check = calculVecteurCheckpoint(checkpoint, ship);
        double angle = v_ship.angleBetweenVectors(v_check);
        double distance = Math.sqrt(Math.pow((checkpoint.getPosition().getX() - ship.getPosition().getX()), 2) + Math.pow((checkpoint.getPosition().getY() - ship.getPosition().getY()), 2));
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
                if(distance < 165.0){
                    deplacement.setVitesse(vitesseSelonDistance(distance, nbr_rames));
                }
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
        // Cas où lorsqu'on avance tout droit et qu'on est donc proche du checkpoint => on ralentit
        if(distance < 165.0){
            deplacement.setVitesse(vitesseSelonDistance(distance, nbr_rames));
        }
        return deplacement;
    }

    /**
     * Méthode retournant un nouveau checkpoint, on basera le calcul du déplacement en fonction
     * de ce checkpoint. Le but étant de viser l'extrémité du checkpoint pour gagner du temps
     * @param checkpoint
     * @return le nouveau checkpoint se basant sur l'extrémité de celui passé en paramètre
     */
    public Checkpoint viseExtremiteCheckpoint(Checkpoint checkpoint){
        Checkpoint checkpoint_suivant = stratData.jeu.getGoal().getCheckpoints().get(1);
        Vector v_check = new Vector(1.0, 0.0); //vecteur unitaire du checkpoint suivant l'axe des abscisses
        Vector v_suivant = new Vector(checkpoint_suivant.getPosition().getX() - checkpoint.getPosition().getX(), checkpoint_suivant.getPosition().getY() - checkpoint.getPosition().getY());
        double angle = v_check.angleBetweenVectors(v_suivant);
        Checkpoint new_checkpoint = new Checkpoint();
        Shape shape_checkpoint = checkpoint.getShape();
        if(shape_checkpoint.getType().equals("circle")){
            Circle shape = (Circle) shape_checkpoint;
            double radius = shape.getRadius();
            // On multiplie par 0.99 pour ne pas être totalement à l'extrémité du checkpoint pour éviter de le rater
            double new_x = checkpoint.getPosition().getX() + (radius * Math.cos(angle));
            double new_y = checkpoint.getPosition().getY() + (radius * Math.sin(angle));
            new_checkpoint.setPosition(new Position(new_x, new_y, 0.0));
            new_checkpoint.setShape(shape);
        }
        /*
        else if(shape_checkpoint.getType().equals("rectangle")){
            Rectangle shape = (Rectangle) shape_checkpoint;
            new_checkpoint.setPosition();
            new_checkpoint.setShape(shape);
        }
        else{
            Polygone shape = (Polygone) shape_checkpoint;
            new_checkpoint.setPosition();
            new_checkpoint.setShape(shape);
        }

         */
        return new_checkpoint;
    }

    /**
     * Permet de savoir si un checkpoint est à gauche ou à droite d'un autre checkpoint grâce à leur angle par rapport au bateau
     * @param angle
     * @param angle_suivant
     * @return true si le checkpoint suivant est à gauche, false sinon
     */
    public boolean estAGauche(double angle, double angle_suivant){
        if(angle_suivant > angle){
            return true;
        }
        return false;
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
     * Calcule la vitesse max que l'on peut faire en allant tout droit lorsqu'on se rapproche du checkpoint
     * @param distance
     * @param nbr_rames
     * @return la vitesse du bateau adapté
     */
    public Double vitesseSelonDistance(Double distance, int nbr_rames){
        double vitesse_une_rame = (double) 165 / nbr_rames;
        double vitesse_max = 165.0;
        while(vitesse_max > 0){
            if(distance >= vitesse_max){
                break;
            }
            vitesse_max -= 2 * vitesse_une_rame;
        }
        // Si on obtient une vitesse valant 0 on avance le moins vite possible pour pouvoir faire une rotation
        // par la suite sans trop s'éloigner
        if(vitesse_max <= 0){
            vitesse_max = 2* vitesse_une_rame;
        }
        return vitesse_max;
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
