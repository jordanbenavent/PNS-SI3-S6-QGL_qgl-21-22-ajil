package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Checkpoint;
import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;

import java.util.ArrayList;

public class ValideCheckpoint  {

    protected Game jeu;
    public ValideCheckpoint(Game jeu) {
        this.jeu=jeu;
    }


    /**
     * Retourne le checkpoint à viser
     * @param checkpoints
     * @return
     */
    public Checkpoint checkpointTarget(ArrayList<Checkpoint> checkpoints) {
        boolean estDedans = false;
        if(checkpoints.isEmpty()) {
            return null;
        }
        Checkpoint checkpointCurrent = checkpoints.get(0);
        Ship ship = jeu.getShip();
        ArrayList<Point> pointsDuBateau = calculPointShip(ship);
        if(checkpointCurrent.getShape() instanceof Circle) {
            estDedans = checkpointValide(pointsDuBateau, checkpointCurrent);
            if(estDedans){
                checkpoints.remove(checkpointCurrent);
                if(checkpoints.isEmpty()) {
                    checkpointCurrent = null;
                } else {
                    checkpointCurrent = checkpoints.get(0);
                }
            }
        }
        return checkpointCurrent;
    }



    public ArrayList<Point> calculPointShip(Ship ship){
        Point centre = new Point(ship.getPosition().getX(), ship.getPosition().getY());
        double largeur = ship.getDeck().getWidth();
        double longueur = ship.getDeck().getLength();
        double sinus = Math.sin(ship.getPosition().getOrientation());
        double cosinus = Math.cos(ship.getPosition().getOrientation());
        ArrayList<Point> pointShip = new ArrayList<>();

        //Matrice changement de référentiel
        ArrayList<ArrayList<Double>> matrice = new ArrayList<>();
        ArrayList<Double> firstColumn = new ArrayList<>();
        firstColumn.add(Math.cos(ship.getPosition().getOrientation()));
        firstColumn.add(Math.sin(ship.getPosition().getOrientation()));
        ArrayList<Double> secondColumn = new ArrayList<>();
        secondColumn.add(-1 * Math.sin(ship.getPosition().getOrientation()));
        secondColumn.add(Math.cos(ship.getPosition().getOrientation()));
        matrice.add(firstColumn);
        matrice.add(secondColumn);

        pointShip.add( new Point(largeur/2*cosinus+longueur/2*sinus, -largeur/2*sinus+longueur/2*cosinus).addPoint(centre));
        pointShip.add( new Point(-largeur/2*cosinus+longueur/2*sinus, largeur/2*sinus+longueur/2*cosinus).addPoint(centre));
        pointShip.add( new Point(largeur/2*cosinus-longueur/2*sinus, -largeur/2*sinus-longueur/2*cosinus).addPoint(centre));
        pointShip.add( new Point(-largeur/2*cosinus-longueur/2*sinus, largeur/2*sinus-longueur/2*cosinus).addPoint(centre));
        return pointShip;
    }

    /**
     * Dit si l'un des points du bateau est dans le checkpoint
     * @param pointsDuBateau
     * @param checkpoint
     * @return true si l'un est dedans, false sinon
     */
    public boolean dansLeCercle(ArrayList<Point> pointsDuBateau, Checkpoint checkpoint){
        Point centreCheckpoint = new Point(checkpoint.getPosition().getX(), checkpoint.getPosition().getY());
        for(Point point : pointsDuBateau){
            if(point.distance(centreCheckpoint) <= ((Circle)(checkpoint.getShape())).getRadius()){
                return true;
            }
        }
        return false;
    }

    /**
     * Dit si le bateau a un point d'intection
     * @param pointDuBateau
     * @param checkpoint
     * @return
     */
    public boolean intersectionCircleShip( ArrayList<Point> pointDuBateau, Checkpoint checkpoint){
        for(int i=0; i<pointDuBateau.size()-1; i++ ){
            for(int j=i+1; j< pointDuBateau.size(); j++){
                //equation cercle (x-checkpoint.x)^2 + (y-checkpoint.y)^2 = R^2
                // équation de la droite du bateau = y = ax+b
                double r = ((Circle) checkpoint.getShape()).getRadius();
                double xc = checkpoint.getPosition().getX();
                double yc = checkpoint.getPosition().getY();
                double xb1 = pointDuBateau.get(i).getX();
                double yb1 = pointDuBateau.get(i).getY();
                double xb2 = pointDuBateau.get(j).getX();
                double yb2 = pointDuBateau.get(j).getY();
                if(xb1==xb2) {
                    if(intersectionDroiteVerticaleCircle(new Point(xb1, yb1), new Point(xb2, yb2), checkpoint)){
                        return true;
                    }
                    continue;
                }
                double a = (yb2 - yb1)/(xb2-xb1);
                double b = (yb1 - a*xb1);
                //Les points pour les résultats
                double x1;
                double y1;
                double x2;
                double y2;

                //Après simplification on obtient une équation du deuxième degré et on obtient donc un delta.
                //Equation : alpha x^2 + beta x + c = 0
                double beta = -2 * xc - 2 * a * b + 2 * a * yc;
                double alpha = (a*a +1);
                double delta = beta * beta - 4*alpha *(xc*xc + (b-yc)*(b-yc) -r*r);
                if(delta<0){
                    continue;
                } else {
                    x1 = (-beta - Math.sqrt(delta)) / (2*alpha);
                    y1 = a*x1 + b;
                    x2 = (-beta + Math.sqrt(delta)) / (2*alpha);
                    y2 = a*x2+b;
                    // x1 appartient à [xb1 ; xb2] ou [xb2 ; xb1] et y1 appartient à [yb1 ; yb2] ou [yb2 ; yb1]
                    if( ((xb1<=x1 && x1<=xb2) || (xb1>=x1 && x1>=xb2)) && ((yb1<=y1 && y1<=yb2) || (yb1>=y1 && y1>=yb2))){
                        return true;
                    }
                    // x2 appartient à [xb1 ; xb2] ou [xb2 ; xb1] et y2 appartient à [yb1 ; yb2] ou [yb2 ; yb1]
                    if(((xb1<=x2 && x2<=xb2) || (xb1>=x2 && x2>=xb2)) && ((yb1<=y2 && y2<=yb2) || (yb1>=y2 && y2>=yb2)) ){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Lors d'une droite verticale, l'équation de droite change
     * @param point1
     * @param point2
     * @param checkpoint
     * @return true si le côté du bateau coupe le checkpoint
     */
    boolean intersectionDroiteVerticaleCircle(Point point1, Point point2, Checkpoint checkpoint){
        //Dans ce cas la droite du bateau est de la forme x=a;
        double a = point1.getX();
        double xb1 = point1.getX();
        double yb1 = point1.getY();
        double xb2 = point2.getX();
        double yb2 = point2.getY();
        double xc = checkpoint.getPosition().getX();
        double yc = checkpoint.getPosition().getY();
        double r = ((Circle) checkpoint.getShape()).getRadius();
        double b = -2*yc;
        //On obtient une équation du deuxième degré et on obtient ce delta
        double delta = b*b - 4*(a*a + xc*xc -2*a*xc + yc*yc -r*r);
        double y1;
        double y2;
        if( delta < 0){
            return false;
        }
        y1 = (-b - Math.sqrt(delta)) / 2;
        y2 = (-b + Math.sqrt(delta)) / 2;
        if( (yb1<=y1 && y1<=yb2) || (yb2<=y1 && y1<=yb1)){
            return true;
        }
        if( (yb1<=y2 && y2<=yb2) || (yb2<=y2 && y2<=yb1)){
            return true;
        }
        return false;
    }

    /**
     * Nous dit si le checkpoint est validé.
     * @param pointsDuBateau
     * @param checkpoint
     * @return true si validé, false sinon
     */
    boolean checkpointValide(ArrayList<Point> pointsDuBateau, Checkpoint checkpoint){
        return dansLeCercle(pointsDuBateau, checkpoint) || intersectionCircleShip(pointsDuBateau, checkpoint);
    }
}
