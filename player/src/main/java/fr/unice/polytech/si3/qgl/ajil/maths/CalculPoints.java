package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.*;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;
import java.util.List;

public class CalculPoints {

    public static List<Point> calculExtremityPoints(Shape shape, Position position) {
        switch (shape.getType()) {
            case "polygon":
                return calculPointPolygon(shape, position);
            case "rectangle":
                final double largeur = ((Rectangle) shape).getWidth();
                final double longueur = ((Rectangle) shape).getHeight();
                return calculPointGeneric(shape, position, largeur / 2, longueur / 2);
            default:
                return calculPointGeneric(shape, position, 0, 0);
        }
    }

    private static double calculAngleTotal(Shape shape, Position pos) {
        return switch (shape.getType()) {
            case "circle" -> pos.getOrientation();
            case "rectangle", "polygon" -> pos.getOrientation() + shape.getOrientation();
            default -> 0;
        };
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

    public static List<VisibleEntitie> entitiesToEntitiesPolygone(List<VisibleEntitie> entities) {
        List<VisibleEntitie> resultat = new ArrayList<>();
        List<Point> pointShape;
        for (VisibleEntitie entitie : entities) {
            if (entitie.getShape() instanceof Circle) {
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
