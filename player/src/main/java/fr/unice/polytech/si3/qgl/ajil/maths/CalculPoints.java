

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
        if (shape instanceof Polygone polygone) {
            return calculPointPolygon(polygone, position);
        }
        double largeur = 0;
        double longueur = 0;
        if (shape instanceof Rectangle rectangle) {
            largeur = rectangle.getWidth();
            longueur = rectangle.getHeight();
        }
        double angle = CalculPoints.calculAngleTotal(shape, position);
        return calculPointGeneric(angle, position, longueur / 2, largeur / 2);
    }

    private static double calculAngleTotal(Shape shape, Position pos) {
        if (shape instanceof Circle) {
            return pos.getOrientation();
        }
        if (shape instanceof Rectangle || shape instanceof Polygone) {
            return pos.getOrientation() + shape.getOrientation();
        }
        return 0;
    }

    private static List<Point> calculPointPolygon(Polygone polygone, Position pos) {
        List<Point> points = new ArrayList<>();
        Point[] pointPolygone = polygone.getVertices();
        double angleRotation = -calculAngleTotal(polygone, pos);
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
        List<VisibleEntitie> result = new ArrayList<>();
        for (VisibleEntitie entity : entities) {
            if (entity.getShape() instanceof Circle) {
                result.add(entity);
                continue;
            }
            Shape shape = entity.getShape();
            Position position = entity.getPosition();
            List<Point> pointShape = calculExtremityPoints(shape, position);
            List<Point> pointShapePadded = calculExtremityPointsBigger(shape, position, 80);
            VisibleEntitie tmp = entity.copy();
            VisibleEntitie tmpPadded = entity.copy();
            tmp.setShape(new Polygone("polygone", shape.getOrientation(), pointShape.toArray(new Point[0])));
            tmpPadded.setShape(new Polygone("polygone", shape.getOrientation(), pointShapePadded.toArray(new Point[0])));
            result.add(tmp);
            result.add(tmpPadded);
        }
        return result;
    }

    public static List<Point> calculExtremityPointsBigger(Shape shape, Position position, double widthShip) {
        if (shape instanceof Polygone polygone) {
            return calculPointPolygon(polygone, position);
        }
        double largeur = 0;
        double longueur = 0;
        if (shape instanceof Rectangle rectangle) {
            largeur = rectangle.getWidth() + (widthShip / 2);
            longueur = rectangle.getHeight() + (widthShip / 2);
        }
        double angle = CalculPoints.calculAngleTotal(shape, position);
        return calculPointGeneric(angle, position, longueur / 2, largeur / 2);
    }
}
