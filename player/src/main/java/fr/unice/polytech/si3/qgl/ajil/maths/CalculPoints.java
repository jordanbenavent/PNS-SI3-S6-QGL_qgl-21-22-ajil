package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.*;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;

public class CalculPoints {

    public static ArrayList<Point> calculExtremityPoints(Shape shape, Position position) {
        if (shape instanceof Polygone) {
            return calculPointPolygon(shape, position);
        }
        double largeur = 0;
        double longueur = 0;
        if (shape instanceof Rectangle) {
            largeur = ((Rectangle) shape).getWidth();
            longueur = ((Rectangle) shape).getHeight();
        }
        double angle = CalculPoints.calculAngleTotal(shape, position);
        return calculPointGeneric(angle, position, largeur / 2, longueur / 2);
    }

    private static double calculAngleTotal(Shape shape, Position pos) {
        if (shape instanceof Circle) {
            return pos.getOrientation();
        }
        if (shape instanceof Rectangle) {
            return pos.getOrientation() + shape.getOrientation();
        }
        if (shape instanceof Polygone) {
            return pos.getOrientation() + shape.getOrientation();
        }
        return 0;
    }

    private static ArrayList<Point> calculPointPolygon(Shape shape, Position pos) {
        ArrayList<Point> points = new ArrayList<>();
        Point[] pointPolygone = ((Polygone) shape).getVertices();
        double angleRotation = -calculAngleTotal(shape, pos);
        double cosAngle = Math.cos(angleRotation);
        double sinAngle = Math.sin(angleRotation);
        for (Point point : pointPolygone) {
            double xPremierProj = point.getX() * cosAngle + point.getY() * sinAngle + pos.getX();
            double yPremierProj = -point.getX() * sinAngle + point.getY() * cosAngle + pos.getY();
            Point pointProjette = new Point(xPremierProj, yPremierProj);
            points.add(pointProjette);
        }
        return points;
    }

    private static ArrayList<Point> calculPointGeneric(double angle, Position position, double la, double lo) {
        ArrayList<Point> points = new ArrayList<>();
        Point centre = new Point(position.getX(), position.getY());
        double sinus = Math.sin(angle);
        double cosinus = Math.cos(angle);
        points.add(new Point(la * cosinus + lo * sinus, -la * sinus + lo * cosinus).addPoint(centre));
        points.add(new Point(-la * cosinus + lo * sinus, la * sinus + lo * cosinus).addPoint(centre));
        points.add(new Point(la * cosinus - lo * sinus, -la * sinus - lo * cosinus).addPoint(centre));
        points.add(new Point(-la * cosinus - lo * sinus, la * sinus - lo * cosinus).addPoint(centre));
        return points;
    }

    public static ArrayList<VisibleEntitie> entitiesToEntitiesPolygone(ArrayList<VisibleEntitie> entities){
        ArrayList<VisibleEntitie> resultat = new ArrayList<>();
        ArrayList<Point> pointShape;
        for ( VisibleEntitie entitie : entities){
            if( entitie.getShape() instanceof Circle){
                resultat.add(entitie);
                continue;
            }
            pointShape = calculExtremityPoints(entitie.getShape(), entitie.getPosition());
            VisibleEntitie tmp = entitie.copy();
            tmp.setShape(new Polygone("polygone", entitie.getShape().getOrientation(), pointShape.toArray(new Point[pointShape.size()])));
            resultat.add(tmp);
        }
        return resultat;
    }
}
