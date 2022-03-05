package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CalculDeplacement {

    protected Game jeu;
    public List<String> LOGGER = Cockpit.LOGGER;

    public CalculDeplacement(Game jeu) {
        this.jeu = jeu;
    }

    /**
     * Analyse quel déplacement le bateau devra faire pour le tour
     *
     * @param c Checkpoint needed to fetch position
     * @return le déplacement que le bateau devra faire pour ce tour
     */
    public Deplacement deplacementPourLeTourRefactor(Checkpoint c) {
        final Ship s = jeu.getShip();
        final int nbr_oars = s.getOars().size();

        final Position shipPosition = s.getPosition();
        final Position checkpointPosition = c.getPosition();

        final Vector v_ship = new Vector(Math.cos(shipPosition.getOrientation()), Math.sin(shipPosition.getOrientation()));
        final Vector v_check = new Vector(checkpointPosition.getX() - shipPosition.getX(), checkpointPosition.getY() - shipPosition.getY());

        final double angle = v_ship.angleBetweenVectors(v_check);
        ArrayList<Deplacement> futur_angle = predictionAngleTourSuivant(shipPosition, checkpointPosition, v_ship, nbr_oars);

        return adjustShipVelocity(angle, futur_angle, s.getTurnRange(), s.getOars().size());
    }

    private Deplacement adjustShipVelocity(double angle, ArrayList<Deplacement> futur_angle, Set<Double> angles_possibles, int nbr_rames) {
        Deplacement newMove = new Deplacement();
        if (Math.abs(angle) >= Math.PI / 2) {
            newMove.setVitesse(82.5);
            if (angle < 0) newMove.setAngle(-Math.PI / 2);
            else newMove.setAngle(Math.PI / 2);
            return newMove;
        }

        // Boucle qui test toutes les valeurs possibles de rotations et renvoie un déplacement si les conditions sont vérifiées
        Double angle_maximum = quelEstLangleMaximum(angles_possibles);
        while (angles_possibles.size() != 0) {
            Double new_angle_maximum = quelEstLangleMaximum(angles_possibles);
            if (Math.abs(angle) < angle_maximum && Math.abs(angle) >= new_angle_maximum) {
                // Faire une rotation du nouvel angle maximum
                newMove.setVitesse(vitesseAdapte(new_angle_maximum, nbr_rames)); // Faire une méthode qui calcule la vitesse minimum pour tourner d'un angle précis avec
                // n'importe quel nombre de rames => on souhaite tourner à une vitesse minimale pour tourner "sec"
                if (angle < 0) newMove.setAngle(-new_angle_maximum);
                else newMove.setAngle(new_angle_maximum);
                return newMove;
            }
            angles_possibles.remove(new_angle_maximum);
            angles_possibles.remove(-new_angle_maximum);
            angle_maximum = new_angle_maximum;
        }
        // Si on sort de la boucle il nous reste un avant-dernier cas, celui ou l'angle est supérieur à 1° en radian et inférieur au plus petit
        // angle de rotation réalisable par le bateau on fait donc appel à la méthode qui prédit le futur angle et qui choisit la
        // meilleure option de déplacement pour que le bateau puisse avancer.
        if (Math.abs(angle) < angle_maximum && Math.abs(angle) > 0.01745329) {
            double diffMin = -1;
            for (Deplacement d : futur_angle) {
                double diff = Math.abs(angle_maximum - d.getAngle());
                if (diffMin == -1 || diffMin > diff) {
                    diffMin = diff;
                    newMove.setVitesse(d.getVitesse());
                }
            }
            // Regarder toutes les sous-listes de futur_angle, aller tout droit à la vitesse rapprochant au plus de l'angle PI/4
            // à savoir la plus petite différence entre l'angle futur et PI/4
        }  // enfin le dernier cas, si on est parfaitement aligné sur le checkpoint

        LOGGER.add("Déplacement calculé :" + newMove);
        return newMove;
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
     * @param shipPosition Emplacement du bateau
     * @param checkpointPosition Emplacement du checkpoint
     * @param v_ship Vecteur bateau
     * @param nbr_oars Nombre de rames
     * @return une liste de liste de double où chaque sous-liste contient une vitesse associée à un angle
     */
    public ArrayList<Deplacement> predictionAngleTourSuivant(Position shipPosition, Position checkpointPosition, Vector v_ship, int nbr_oars) {
        final ArrayList<Deplacement> prediction = new ArrayList<>();
        double vitesse = 165;
        double positionX_apres_deplacement;
        double positionY_apres_deplacement;
        for (int i = 0; i < nbr_oars / 2; i++) {
            vitesse = (vitesse * (nbr_oars - 2 * i)) / nbr_oars;
            positionX_apres_deplacement = shipPosition.getX() + (vitesse * Math.cos(shipPosition.getOrientation()));
            positionY_apres_deplacement = shipPosition.getY() + (vitesse * Math.sin(shipPosition.getOrientation()));
            Vector new_v_check = new Vector(checkpointPosition.getX() - positionX_apres_deplacement, checkpointPosition.getY() - positionY_apres_deplacement);
            prediction.add(new Deplacement(vitesse, v_ship.angleBetweenVectors(new_v_check)));
        }
        LOGGER.add("Prédiction :" + prediction);
        return prediction;
    }
}
