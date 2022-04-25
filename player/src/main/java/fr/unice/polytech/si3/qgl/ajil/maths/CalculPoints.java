package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.*;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;
import java.util.List;

public class CalculPoints {

    public static List<Point> calculExtremityPoints(Shape shape, Position position) {
        double longueur = 0;
        double largeur = 0;

        switch (shape.getType()) {
            case "polygon":
                return calculPointPolygon((Polygone) shape, position);
            case "rectangle":
                largeur = ((Rectangle) shape).getWidth();
                longueur = ((Rectangle) shape).getHeight();
            default:
                return calculPointGeneric(shape, position, largeur / 2, longueur / 2);
        }
    }

    private static double calculAngleTotal(Shape shape, Position pos) {
        return switch (shape.getType()) {
            case "circle" -> pos.getOrientation();
            case "rectangle", "polygon" -> pos.getOrientation() + shape.getOrientation();
            default -> 0;
        };
    }

    private static List<Point> calculPointPolygon(Polygone poly, Position pos) {
        List<Point> points = new ArrayList<>();
        Point[] pointPolygone = poly.getVertices();
        double angleRotation = -calculAngleTotal(poly, pos);
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

    private static List<Point> calculPointGeneric(Shape shape, Position position, double la, double lo) {
        ArrayList<Point> points = new ArrayList<>();
        final double angle = CalculPoints.calculAngleTotal(shape, position);
        final Point centre = new Point(position.getX(), position.getY());
        final double sinus = Math.sin(angle);
        final double cosinus = Math.cos(angle);
        points.add(new Point(la * cosinus + lo * sinus, -la * sinus + lo * cosinus).addPoint(centre));
        points.add(new Point(-la * cosinus + lo * sinus, la * sinus + lo * cosinus).addPoint(centre));
        points.add(new Point(la * cosinus - lo * sinus, -la * sinus - lo * cosinus).addPoint(centre));
        points.add(new Point(-la * cosinus - lo * sinus, la * sinus - lo * cosinus).addPoint(centre));
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
