package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.maths.CalculIntersection;
import fr.unice.polytech.si3.qgl.ajil.maths.CalculPoints;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValideCheckpoint {

    private static final List<String> LOGGER = Cockpit.LOGGER;
    private List<Checkpoint> fakeCheckpoint = new ArrayList<>();
    protected final Game jeu;

    public ValideCheckpoint(Game jeu) {
        this.jeu = jeu;
    }

    public List<Checkpoint> getFakeCheckpoint() {
        return fakeCheckpoint;
    }

    public void setFakeCheckpoint(List<Checkpoint> fakeCheckpoint) {
        this.fakeCheckpoint = fakeCheckpoint;
    }

    /**
     * If checkpoints is not empty, return next checkpoint
     *
     * @param checkpoints List of the checkpoints
     * @return the next Checkpoint
     */
    public Checkpoint nextCheckpointTarget(List<Checkpoint> checkpoints) {
        Ship ship = jeu.getShip();
        List<Checkpoint> realOrFalse = realOrFakeCheckpoint(checkpoints);
        if (realOrFalse == null || realOrFalse.isEmpty()) return null;
        Checkpoint checkpointCurrent = realOrFalse.get(0);
        if (fakeCheckpoint!=null && fakeCheckpoint.contains(checkpointCurrent)){
            realOrFalse.remove(checkpointCurrent);
            realOrFalse = realOrFakeCheckpoint(checkpoints);
            if (realOrFalse == null || realOrFalse.isEmpty()) return null;
            checkpointCurrent = realOrFalse.get(0);
        }
        while (isShipInCheckpoint(ship, checkpointCurrent)) {
            realOrFalse.remove(checkpointCurrent);
            realOrFalse = realOrFakeCheckpoint(checkpoints);
            if (realOrFalse == null || realOrFalse.isEmpty()) return null;
            checkpointCurrent = realOrFalse.get(0);
        }
        return checkpointCurrent;
    }

    public void checkRealCheckpoint(List<Checkpoint> checkpoints, Ship ship) {
        if (checkpoints.isEmpty()) return;
        Checkpoint checkpointCurrent = checkpoints.get(0);
        if (checkpointCurrent == null) return;
        while (isShipInCheckpoint(ship, checkpointCurrent)) {
            fakeCheckpoint.clear();
            checkpoints.remove(checkpointCurrent);
            if (checkpoints.isEmpty()) return;
            else checkpointCurrent = checkpoints.get(0);
            if (checkpointCurrent == null) return;
        }
    }

    /**
     * Check if the ship is inside the checkpoint
     *
     * @param ship              ship is needed for hitbox calculation
     * @param checkpointCurrent get the checkpoint
     * @return ship in checkpoint boolean
     */
    private boolean isShipInCheckpoint(Ship ship, Checkpoint checkpointCurrent) {
        List<Point> pointsDuBateau = calculPointShip(ship);
        if (checkpointCurrent.getShape() instanceof Circle) {
            if ((ship.getShape() instanceof Circle)) {
                return checkpointValideShipCircle(ship, checkpointCurrent);
            } else {
                return checkpointValide(pointsDuBateau, checkpointCurrent);
            }
        }
        return false;
    }


    /**
     * if checkpoint is a circle
     *
     * @param ship              Ship needed to fetch shape and position
     * @param checkpointCurrent Checkpoint to test
     * @return is ship in the circle
     */
    public boolean checkpointValideShipCircle(Ship ship, Checkpoint checkpointCurrent) {
        Point pointShip = new Point(ship.getPosition().getX(), ship.getPosition().getY());
        Point pointCheckpoint = new Point(checkpointCurrent.getPosition().getX(), checkpointCurrent.getPosition().getY());
        double rs = ((Circle) ship.getShape()).getRadius();
        double rc = ((Circle) checkpointCurrent.getShape()).getRadius();
        return pointCheckpoint.distance(pointShip) <= (rs + rc);
    }

    /**
     * Calcule les quatres points des quatres coin du bateau.
     *
     * @param ship Our ship
     * @return Points
     */
    public List<Point> calculPointShip(Ship ship) {
        Position position = ship.getPosition();
        Shape shape = ship.getShape();
        return CalculPoints.calculExtremityPoints(shape, position);
    }


    /**
     * Dit si l'un des points du bateau est dans le checkpoint
     *
     * @param pointsDuBateau Points
     * @param checkpoint     Checkpoints
     * @return true si l'un est dedans, false sinon
     */
    public boolean dansLeCercle(List<Point> pointsDuBateau, Checkpoint checkpoint) {
        Point centreCheckpoint = new Point(checkpoint.getPosition().getX(), checkpoint.getPosition().getY());
        for (Point point : pointsDuBateau) {
            if (point.distance(centreCheckpoint) <= ((Circle) (checkpoint.getShape())).getRadius()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dit si le bateau a un point d'intersection
     *
     * @param pointDuBateau ArrayList des points du bateau
     * @param checkpoint    Checkpoint
     * @return If ship intersects
     */
    public boolean intersectionCircleShip(List<Point> pointDuBateau, Checkpoint checkpoint) {
        int size = pointDuBateau.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                //equation cercle (x-checkpoint.x)^2 + (y-checkpoint.y)^2 = R^2
                // équation de la droite du bateau = y = ax+b
                double r = ((Circle) checkpoint.getShape()).getRadius();
                double xc = checkpoint.getPosition().getX();
                double yc = checkpoint.getPosition().getY();
                double xb1 = pointDuBateau.get(i).getX();
                double yb1 = pointDuBateau.get(i).getY();
                double xb2 = pointDuBateau.get(j).getX();
                double yb2 = pointDuBateau.get(j).getY();
                if (xb1 == xb2) {
                    if (intersectionDroiteVerticaleCircle(new Point(xb1, yb1), new Point(xb2, yb2), checkpoint)) {
                        return true;
                    }
                    continue;
                }
                double a = (yb2 - yb1) / (xb2 - xb1);
                double b = (yb1 - a * xb1);
                //Les points pour les résultats
                double x1;
                double y1;
                double x2;
                double y2;

                //Après simplification on obtient une équation du deuxième degré et on obtient donc un delta.
                //Equation : alpha x^2 + beta x + c = 0
                double beta = (-2 * xc) + (2 * a * b) - (2 * a * yc);
                double alpha = (a * a + 1);
                double ceta = ((xc * xc) + ((b - yc) * (b - yc)) - (r * r));
                double delta = beta * beta - (4 * alpha * ceta);
                if (CalculIntersection.calculRoots(xb1, yb1, xb2, yb2, a, b, beta, alpha, delta)) return true;
            }
        }
        return false;
    }

    /**
     * Lors d'une droite verticale, l'équation de droite change
     *
     * @param point1     Point 1
     * @param point2     Point 2
     * @param checkpoint Checkpoint
     * @return true si le côté du bateau coupe le checkpoint
     */
    boolean intersectionDroiteVerticaleCircle(Point point1, Point point2, Checkpoint checkpoint) {
        //Dans ce cas la droite du bateau est de la forme x=a;
        double a = point1.getX();
        double yb1 = point1.getY();
        double yb2 = point2.getY();
        double xc = checkpoint.getPosition().getX();
        double yc = checkpoint.getPosition().getY();
        double r = ((Circle) checkpoint.getShape()).getRadius();
        double b = -2 * yc;
        //On obtient une équation du deuxième degré et on obtient ce delta
        double delta = b * b - 4 * (a * a + xc * xc - 2 * a * xc + yc * yc - r * r);
        double y1;
        double y2;
        if (delta < 0) {
            return false;
        }
        y1 = (-b - Math.sqrt(delta)) / 2;
        y2 = (-b + Math.sqrt(delta)) / 2;
        if ((yb1 <= y1 && y1 <= yb2) || (yb2 <= y1 && y1 <= yb1)) {
            return true;
        }
        return (yb1 <= y2 && y2 <= yb2) || (yb2 <= y2 && y2 <= yb1);
    }

    /**
     * Nous dit si le checkpoint est validé.
     *
     * @param points     Point du bateau
     * @param checkpoint Checkpoint
     * @return true si validé, false sinon
     */
    boolean checkpointValide(List<Point> points, Checkpoint checkpoint) {
        return dansLeCercle(points, checkpoint) || intersectionCircleShip(points, checkpoint);
    }

    public Checkpoint fakeOrRealCheckpoint(List<Checkpoint> real) {
        if (fakeCheckpoint.isEmpty()) {
            if (real.isEmpty()) return null;
            return real.get(0);
        } else {
            return fakeCheckpoint.get(0);
        }
    }

    public Checkpoint nextCheckpointTarget2(List<Checkpoint> checkpoints) {
        Ship ship = jeu.getShip();
        List<Checkpoint> realOrFalse = realOrFakeCheckpoint(checkpoints);
        if (realOrFalse == null || realOrFalse.isEmpty()) return null;
        Checkpoint checkpointCurrent = realOrFalse.get(0);
        while (isShipInCheckpoint(ship, checkpointCurrent)) {
            realOrFalse.remove(checkpointCurrent);
            realOrFalse = realOrFakeCheckpoint(checkpoints);
            if (realOrFalse == null || realOrFalse.isEmpty()) return null;
            checkpointCurrent = realOrFalse.get(0);
        }
        return checkpointCurrent;
    }

    private List<Checkpoint> realOrFakeCheckpoint(List<Checkpoint> checkpoints) {
        if(this.fakeCheckpoint == null && fakeCheckpoint.isEmpty()){
            System.out.println("REAL");
            if (checkpoints.isEmpty()) return Collections.emptyList();
            return checkpoints;
        } else {
            System.out.println("FAKE");
            return fakeCheckpoint;
        }
    }

}
