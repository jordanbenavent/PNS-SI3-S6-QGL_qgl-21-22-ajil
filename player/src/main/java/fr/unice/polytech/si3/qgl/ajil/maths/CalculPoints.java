package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.*;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;
import java.util.List;

public class CalculPoints {

    private CalculPoints() {
    }

    public static List<Point> calculExtremityPoints(Shape shape, Position position) {
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
        return calculPointGeneric(angle, position, longueur / 2, largeur / 2);    }

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

    private static List<Point> calculPointPolygon(Shape shape, Position pos) {
        List<Point> points = new ArrayList<>();
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

    private static ArrayList<Point> calculPointGeneric(double angle, Position position, double lo, double la) {
        ArrayList<Point> points = new ArrayList<>();
        Point centre = new Point(position.getX(), position.getY());
        double sinus = Math.sin(-angle);
        double cosinus = Math.cos(-angle);
        points.add(new Point(lo * cosinus + la * sinus, -lo * sinus + la * cosinus).addPoint(centre));
        points.add(new Point(-lo * cosinus + la * sinus, lo * sinus + la * cosinus).addPoint(centre));
        points.add(new Point(-lo * cosinus - la * sinus, lo * sinus - la * cosinus).addPoint(centre));
        points.add(new Point(lo * cosinus - la * sinus, -lo * sinus - la * cosinus).addPoint(centre));
        return points;

    }


    public static List<VisibleEntitie> entitiesToEntitiesPolygone(List<VisibleEntitie> entities, int widthShip) {
        List<VisibleEntitie> resultat = new ArrayList<>();
        List<Point> pointShape;
        List<Point> pointShape2;
        List<Point> pointShape3;
        for (VisibleEntitie entitie : entities) {
            if (entitie.getShape() instanceof Circle) {
                resultat.add(entitie);
                continue;
            }
            pointShape = calculExtremityPoints(entitie.getShape(), entitie.getPosition());
            pointShape2 = calculExtremityPointsBigger(entitie.getShape(), entitie.getPosition(), 200);
            VisibleEntitie tmp = entitie.copy();
            VisibleEntitie tmp2 = entitie.copy();
            tmp.setShape(new Polygone("polygone", entitie.getShape().getOrientation(), pointShape.toArray(new Point[0])));
            tmp2.setShape(new Polygone("polygone", entitie.getShape().getOrientation(), pointShape2.toArray(new Point[0])));
            resultat.add(tmp);
            resultat.add(tmp2);
        }
        return resultat;
    }

    public static List<Point> calculExtremityPointsBigger(Shape shape, Position position, double widthShip) {
        if (shape instanceof Polygone) {
            return calculPointPolygon(shape, position);
        }
        double largeur = 0;
        double longueur = 0;
        if (shape instanceof Rectangle) {
            largeur = ((Rectangle) shape).getWidth() + (widthShip / 2);
            longueur = ((Rectangle) shape).getHeight() + (widthShip / 2);
        }
        double angle = CalculPoints.calculAngleTotal(shape, position);
        return calculPointGeneric(angle, position, largeur / 2, longueur / 2);
    }
}
