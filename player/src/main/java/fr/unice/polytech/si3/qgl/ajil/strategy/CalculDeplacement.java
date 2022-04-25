package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Classe regroupant tous les calculs de déplacement en fonction du checkpoint que l'on vise, de si on a un gouvernail ou non
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class CalculDeplacement {

    private static final List<String> LOGGER = Cockpit.LOGGER;
    protected final Game jeu;
    protected final StratData stratData;

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
        final Ship ship = jeu.getShip();
        final int nbr_rames = ship.getOars().size();

        double distance = getDistance(ship, checkpoint);
        if (stratData.jeu.getGoal().getCheckpoints().size() > 1) {
            checkpoint = viseExtremiteCheckpoint(checkpoint);
        }

        final Vector shipVector = calculVecteurBateau(ship);
        final Vector checkVector = calculVecteurCheckpoint(checkpoint, ship);
        final double angle = shipVector.angleBetweenVectors(checkVector);
        System.out.println(checkpoint);
        // Si le bateau est aligné avec le checkpoint d'un angle inférieur à 1° sinon on aurait pas de points d'intersection
        if (Math.abs(angle) < 0.01745329) {
            List<Point> intersectionPoints = intersection(ship, shipVector, checkpoint);
            System.out.println(intersectionPoints);
            distance = getDistancePointIntersection(intersectionPoints, ship);
            System.out.println(distance);
        }

        final List<Deplacement> nextAngle = predictionAngleTourSuivant(shipVector, checkpoint);
        Set<Double> anglesAvailable = ship.getTurnRange();
        anglesAvailable.remove(0.0);
        final double angle_maximum = quelEstLangleMaximum(anglesAvailable);
        return getMove(nbr_rames, distance, angle, nextAngle, anglesAvailable, angle_maximum);
    }

    /**
     * @param ship
     * @param checkpoint
     * @return la distance entre le bateau et un checkpoint
     */
    private double getDistance(Ship ship, Checkpoint checkpoint) {
        return Math.sqrt(Math.pow((checkpoint.getPosition().getX() - ship.getPosition().getX()), 2) + Math.pow((checkpoint.getPosition().getY() - ship.getPosition().getY()), 2));
    }

    /**
     * Méthode calculant la distance la plus petite entre le bateau et le point d'intersection avec le checkpoint visé
     *
     * @param points
     * @param ship
     * @return la distance la plus petite entre le bateau et les points de la liste de points d'intersection
     */
    double getDistancePointIntersection(List<Point> points, Ship ship) {
        if (points.isEmpty()) {
            return 0; // Code temporaire, une erreur à gérer
        }
        System.out.println("Ship: " + ship.getPosition());
        double distmin = Math.sqrt(Math.pow((points.get(0).getX() - ship.getPosition().getX()), 2) + Math.pow((points.get(0).getY() - ship.getPosition().getY()), 2));
        System.out.println("Point 1: " + points.get(0));
        points.remove(0);
        for (Point point : points) {
            System.out.println("Point 2: " + point);
            double distance = Math.sqrt(Math.pow((point.getX() - ship.getPosition().getX()), 2) + Math.pow((point.getY() - ship.getPosition().getY()), 2));
            if (distmin > distance) {
                distmin = distance;
            }
        }
        return distmin;
    }

    private Deplacement getMove(int oarsNb, double distance, double angle, List<Deplacement> nextAngle, Set<Double> availableAngles, double maxAngle) {
    /**
     * Méthode calculant le déplacement à effectuer pour le tour en cours
     *
     * @param nbr_rames
     * @param distance
     * @param angle
     * @param futur_angle
     * @param angles_possibles
     * @param angle_maximum
     * @return le déplacement (composé d'une vitesse et d'un angle) à effectuer
     */
    private Deplacement getDeplacement(int nbr_rames, double distance, double angle, ArrayList<Deplacement> futur_angle, Set<Double> angles_possibles, double angle_maximum) {
        Deplacement deplacement = new Deplacement(); //vitesse en premier, angle en deuxième
        final Sailor coxswain = stratData.getCoxswain();
        // Stratégie de déplacement commune aux deux stratégies (avec et sans coxswain)
        if (Math.abs(angle) >= Math.PI / 2) {
            deplacement.setVitesse(82.5);
            if (angle < 0) deplacement.setAngle(-Math.PI / 2);
            else deplacement.setAngle(Math.PI / 2);
            return deplacement;
        }
        availableAngles.remove(Math.PI / 2);
        availableAngles.remove(-Math.PI / 2);
        if (barreur != null) {
            // Dans le cas ou l'angle est inférieur ou égale à la valeur absolue de PI/4 on renvoie l'angle précis car c'est le gouvernail qui se chargera de tourner
            if (Math.abs(angle) <= Math.PI / 4) {
                deplacement = deplacementSiGouvernail(angle, deplacement);
                if (distance < 165.0) deplacement.setVitesse(vitesseSelonDistance(distance, oarsNb));
                return deplacement;
            }
            availableAngles.removeIf(a -> Math.abs(a) < Math.PI / 4);
            if (availableAngles.size() == 0) return deplacement;
        }
        while (angles_possibles.size() != 0) {
            Double new_angle_maximum = getMaxAngle(angles_possibles);
            if (Math.abs(angle) < angle_maximum && Math.abs(angle) >= new_angle_maximum) {
        while (!availableAngles.isEmpty()) {
            Double newMaximumAngle = quelEstLangleMaximum(availableAngles);
            if (Math.abs(angle) < maxAngle && Math.abs(angle) >= newMaximumAngle) {
                // Faire une rotation du nouvel angle maximum
                deplacement.setVitesse(vitesseAdapte(newMaximumAngle, oarsNb)); // Faire une méthode qui calcule la vitesse minimum pour tourner d'un angle précis avec
                // n'importe quel nombre de rames => on souhaite tourner à une vitesse minimale pour tourner "sec"
                if (angle < 0) deplacement.setAngle(-newMaximumAngle);
                else deplacement.setAngle(newMaximumAngle);
                return deplacement;
            }
            availableAngles.remove(newMaximumAngle);
            availableAngles.remove(-newMaximumAngle);
            maxAngle = newMaximumAngle;
        }
        // Si on sort de la boucle il nous reste un avant-dernier cas, celui ou l'angle est supérieur à 1° en radian et inférieur au plus petit
        // angle de rotation réalisable par le bateau on fait donc appel à la méthode qui prédit le futur angle et qui choisit la
        // meilleure option de déplacement pour que le bateau puisse avancer.
        if (Math.abs(angle) < maxAngle && Math.abs(angle) > 0.01745329) {
            deplacement = deplacementSelonPrediction(maxAngle, nextAngle, deplacement);
        }
        // Cas où lorsqu'on avance tout droit et qu'on est donc proche du checkpoint => on ralentit
        if (distance < 165.0) deplacement.setVitesse(vitesseSelonDistance(distance, oarsNb));
        return deplacement;
    }

    /**
     * Méthode calculant les points d'intersection entre la droite de la trajectoire du bateau et le checkpoint (un cercle)
     *
     * @param ship
     * @param checkpoint
     * @return les points d'intersection
     */
    public List<Point> intersection(Ship ship, Vector v_ship, Checkpoint checkpoint) {
        // (1) équation cercle: (x-checkpoint.x)^2 + (y-checkpoint.y)^2 = R^2
        double r = ((Circle) checkpoint.getShape()).getRadius();
        double xc = checkpoint.getPosition().getX();
        double yc = checkpoint.getPosition().getY();

        // (2) équation de la droite du bateau: y = ax+b
        // Etape 1: On prend d'abord deux points (à partir du vecteur bateau) pour pouvoir calculer la droite
        double x1 = ship.getPosition().getX();
        double y1 = ship.getPosition().getY();
        double x2 = ship.getPosition().getX() + v_ship.getX();
        double y2 = ship.getPosition().getY() + v_ship.getY();
        if (Math.abs(x2 - x1) < 0.0001) {
            return intersectionDroiteVerticaleCircle(ship, checkpoint);
        }

        // Etape 2: on calcule la pente si x1 != x2
        double a = (y2 - y1) / (x2 - x1);
        // Etape 3: On remplace dans l'équation a par la pente et x et y par un point pour trouver b
        double b = y1 - a * x1;

        // Maintenant on remplace (2) dans (1)
        // (x − xc)² + (a*x + b −yc)² = R²
        // x² − 2*x*xc + xc² + a²*x² + b² + yc² + 2(a*x*b − a*x*yc − b*yc) − R² = 0
        // x²(1 + a²) + x(−2*xc + 2*a*b − 2*a*yc) + (xc² + yc² + b²− 2*b*yc − R²) = 0
        // On a donc du second degré de la forme ax² + bx + c = 0 avec:
        ArrayList<Point> points_intersection = new ArrayList<>();
        double A = 1 + a * a;
        double B = 2 * (-xc + a * b - a * yc);
        double C = xc * xc + yc * yc + b * b - 2 * b * yc - r * r;
        double delta = B * B - 4 * A * C;

        if (delta > 0) {
            double x = (-B - Math.sqrt(delta)) / (2 * A);
            double y = a * x + b;
            points_intersection.add(new Point(x, y));

            x = (-B + Math.sqrt(delta)) / (2 * A);
            y = a * x + b;
            points_intersection.add(new Point(x, y));
        } else if (delta == 0) {
            double x = -B / (2 * A);
            double y = a * x + b;
            points_intersection.add(new Point(x, y));
        }

        return points_intersection;
    }

    /**
     * Lors d'une droite verticale, l'équation de droite change
     *
     * @param ship
     * @param checkpoint
     * @return true si le côté du bateau coupe le checkpoint
     */
    public List<Point> intersectionDroiteVerticaleCircle(Ship ship, Checkpoint checkpoint) {
        //Dans ce cas la droite du bateau est de la forme x = a;
        double a = ship.getPosition().getX();
        double xc = checkpoint.getPosition().getX();
        double yc = checkpoint.getPosition().getY();
        double r = ((Circle) checkpoint.getShape()).getRadius();

        // (a − xc)² + (y − yc)² = R²
        // a² − 2*a*xc + xc² + y² -2*y*yc + yc² − R² = 0
        // y² + y(−2*yc) + (a² - 2*a*xc + xc² + yc² − R²) = 0
        // On a donc du second degré de la forme ax² + bx + c = 0 avec:
        ArrayList<Point> points_intersection = new ArrayList<>();
        double A = 1;
        double B = -2 * yc;
        double C = a * a - 2 * a * xc + xc * xc + yc * yc - r * r;
        double delta = B * B - 4 * A * C;

        if (delta > 0) {
            double x = a;
            double y = (-B - Math.sqrt(delta)) / (2 * A);
            points_intersection.add(new Point(x, y));

            x = a;
            y = (-B + Math.sqrt(delta)) / (2 * A);
            points_intersection.add(new Point(x, y));
        } else if (delta == 0) {
            double y = -B / (2 * A);
            points_intersection.add(new Point(a, y));
        }
        return points_intersection;
    }

    /**
     * Méthode retournant un nouveau checkpoint, on basera le calcul du déplacement en fonction
     * de ce checkpoint. Le but étant de viser l'extrémité du checkpoint pour gagner du temps
     *
     * @param checkpoint
     * @return le nouveau checkpoint se basant sur l'extrémité de celui passé en paramètre
     */
    public Checkpoint viseExtremiteCheckpoint(Checkpoint checkpoint) {
        Checkpoint checkpoint_suivant = stratData.jeu.getGoal().getCheckpoints().get(1);
        Vector v_check = new Vector(1.0, 0.0); //vecteur unitaire du checkpoint suivant l'axe des abscisses
        Vector v_suivant = new Vector(checkpoint_suivant.getPosition().getX() - checkpoint.getPosition().getX(), checkpoint_suivant.getPosition().getY() - checkpoint.getPosition().getY());
        double angle = v_check.angleBetweenVectors(v_suivant);
        Checkpoint new_checkpoint = new Checkpoint();
        Shape shape_checkpoint = checkpoint.getShape();
        if (shape_checkpoint.getType().equals("circle")) {
            Circle shape = (Circle) shape_checkpoint;
            double radius = shape.getRadius();
            // On multiplie par 0.75 pour ne pas être totalement à l'extrémité du checkpoint pour éviter de le rater
            double new_x = checkpoint.getPosition().getX() + (radius * 0.9 * Math.cos(angle));
            double new_y = checkpoint.getPosition().getY() + (radius * 0.9 * Math.sin(angle));
            new_checkpoint.setPosition(new Position(new_x, new_y, 0.0));
            new_checkpoint.setShape(shape);
        }
        return new_checkpoint;
    }

    /**
     * Méthode de calcul du vecteur bateau permettant une meilleure compréhension de la méthode
     * déplacementPourLeTourRefactor
     *
     * @param ship
     * @return
     */
    public Vector calculVecteurBateau(Ship ship) {
        return new Vector(Math.cos(ship.getPosition().getOrientation()), Math.sin(ship.getPosition().getOrientation()));
    }

    /**
     * Méthode de calcul du vecteur checkpoint permettant une meilleure compréhension de la méthode
     * déplacementPourLeTourRefactor
     *
     * @param checkpoint
     * @param ship
     * @return
     */
    public Vector calculVecteurCheckpoint(Checkpoint checkpoint, Ship ship) {
        return new Vector(checkpoint.getPosition().getX() - ship.getPosition().getX(), checkpoint.getPosition().getY() - ship.getPosition().getY());
    }

    /**
     * Stratégie de déplacement si on a un coxswain sur le bateau
     *
     * @param angle
     * @return le déplacement à effectuer
     */
    public Deplacement deplacementSiGouvernail(Double angle, Deplacement deplacement) {
        deplacement.setAngle(angle);
        deplacement.setVitesse(165.0);
        return deplacement;
    }

    /**
     * Stratégie de déplacement selon la prédiction du futur angle que le bateau aura avec le checkpoint après son déplacement
     *
     * @param maxAngle
     * @param nextAngle
     * @param move
     * @return le déplacement à effectuer
     */
    public Deplacement deplacementSelonPrediction(Double maxAngle, List<Deplacement> nextAngle, Deplacement move) {
        double bestSpeed = 0;
        double diffMin = -1;
        for (Deplacement d : nextAngle) {
            double diff;
            if (d.getAngle() < 0) {
                diff = Math.abs(maxAngle + d.getAngle());
            } else {
                diff = Math.abs(maxAngle - d.getAngle());
            }
            if (diffMin == -1 || diffMin > diff) {
                diffMin = diff;
                bestSpeed = d.getVitesse();
            }
        }
        move.setVitesse(bestSpeed);
        move.setAngle(0);
        return move;
    }

    /**
     * Calcul de la vitesse minimum pour tourner d'un angle précis avec n'importe quel nombre de rames,
     * on souhaite tourner à une vitesse minimale pour tourner "sec"
     *
     * @param angleRotation Angle
     * @param oars          nombre de Oars
     * @return une vitesse minimale
     */
    public Double vitesseAdapte(Double angleRotation, int oars) {
        Double angle_possible;
        int usefulOars = 100;
        for (int i = 0; i <= oars / 2; i++) {
            angle_possible = Math.PI * i / oars;
            if (angle_possible.equals(angleRotation)) {
                usefulOars = i;
                break;
            }
        }
        double speedPerOar = (double) 165 / oars;
        return speedPerOar * usefulOars;
    }

    /**
     * Calcule la vitesse max que l'on peut faire en allant tout droit lorsqu'on se rapproche du checkpoint
     *
     * @param distance
     * @param oars
     * @return la vitesse du bateau adapté
     */
    public Double vitesseSelonDistance(Double distance, int oars) {
        double speedPerOar = (double) 165 / oars;
        double maxSpeed = 165.0;
        while (maxSpeed > 0) {
            if (distance >= maxSpeed) {
                break;
            }
            maxSpeed -= 2 * speedPerOar;
        }
        // Si on obtient une vitesse valant 0 on avance le moins vite possible pour pouvoir faire une rotation
        // par la suite sans trop s'éloigner
        if (maxSpeed <= 0) {
            maxSpeed = 2 * speedPerOar;
        }
        return maxSpeed;
    }

    /**
     * @param angles Une liste comportant des angles sous forme décimal / radian
     * @return l'angle avec la valeur la plus élevée
     */
    public Double getMaxAngle(Set<Double> angles) {
        return angles.stream().mapToDouble(Double::doubleValue).max().isPresent() ? angles.stream().mapToDouble(Double::doubleValue).max().getAsDouble() : 0.0;
    }

    /**
     * Le bateau avance droit, on calcule les différents angles qu'on obtiendra en fonction de la vitesse du bateau qu'on peut appliquer
     * au bateau
     *
     * @param v_ship     Vecteur bateau
     * @param checkpoint checkpoint
     * @return une liste de liste de double où chaque sous-liste contient une vitesse associée à un angle
     */
    public List<Deplacement> predictionAngleTourSuivant(Vector v_ship, Checkpoint checkpoint) {
        int oars = jeu.getShip().getOars().size();
        ArrayList<Deplacement> prediction = new ArrayList<>();
        final double initialSpeed = 165;
        double vitesse;
        double angle_apres_deplacement;
        double positionX_init = jeu.getShip().getPosition().getX();
        double positionY_init = jeu.getShip().getPosition().getY();
        double positionX_apres_deplacement;
        double positionY_apres_deplacement;
        for (int i = 0; i < jeu.getShip().getOars().size() / 2; i++) {
            vitesse = (initialSpeed * (oars - 2 * i)) / oars;
            positionX_apres_deplacement = positionX_init + (vitesse * Math.cos(jeu.getShip().getPosition().getOrientation()));
            positionY_apres_deplacement = positionY_init + (vitesse * Math.sin(jeu.getShip().getPosition().getOrientation()));
            Vector new_v_check = new Vector(checkpoint.getPosition().getX() - positionX_apres_deplacement, checkpoint.getPosition().getY() - positionY_apres_deplacement);
            angle_apres_deplacement = v_ship.angleBetweenVectors(new_v_check);
            prediction.add(new Deplacement(vitesse, angle_apres_deplacement));
        }
        LOGGER.add("Prédiction :" + prediction);
        return prediction;
    }
}