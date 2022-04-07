package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.*;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;
import java.util.List;

public class CalculPoints {

    /**
     * HitBox calculation depending on the shape
     *
     * @param shape    Shape object can be rectangular, polygonal, circular...
     * @param position Position of the object X and Y
     * @return An efficient list of points representing the shape in the space
     */
    public static List<Point> calculExtremityPoints(Shape shape, Position position) {
        final double angle = CalculPoints.calculAngleTotal(shape, position);

        return switch (shape.getType()) {
            case "polygon" -> calculPointPolygon(((Polygone) shape).getVertices(), position, angle);
            case "rectangle" -> calculPointGeneric(angle, position, ((Rectangle) shape).getWidth(), ((Rectangle) shape).getHeight());
            default -> calculPointGeneric(angle, position, 0, 0);
        };
    }

    /**
     * @param shape Shape orientation is needed for polygone shape
     * @param pos   Position object to get the current orientation
     * @return Angle in radians
     */
    private static double calculAngleTotal(Shape shape, Position pos) {
        return switch (shape.getType()) {
            case "circle" -> pos.getOrientation();
            case "rectangle" -> pos.getOrientation() + shape.getOrientation();
            case "polygon" -> -(pos.getOrientation() + shape.getOrientation());
            default -> 0;
        };
    }

    private static List<Point> calculPointPolygon(Point[] pointPolygone, Position pos, double angle) {
        List<Point> points = new ArrayList<>();
        final double cosAngle = Math.cos(angle);
        final double sinAngle = Math.sin(angle);
        for (Point point : pointPolygone) {
            double xPremierProj = point.getX() * cosAngle + point.getY() * sinAngle + pos.getX();
            double yPremierProj = -point.getX() * sinAngle + point.getY() * cosAngle + pos.getY();
            points.add(new Point(xPremierProj, yPremierProj));
        }
        return points;
    }

    private static List<Point> calculPointGeneric(double angle, Position position, double la, double lo) {
        ArrayList<Point> points = new ArrayList<>();
        final Point centre = new Point(position.getX(), position.getY());
        final double sinus = Math.sin(angle);
        final double cosinus = Math.cos(angle);
        la /= 2;
        lo /= 2;
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
            tmp.setShape(new Polygone("polygon", entitie.getShape().getOrientation(), pointShape.toArray(new Point[0])));
            resultat.add(tmp);
        }
        return resultat;
    }
}
