package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.*;

import java.util.ArrayList;
import java.util.List;

public class CalculIntersection {

    /**
     * Méthode global pour calculer l'intersection entre deux formes
     *
     * @param shape1    première forme
     * @param position1 position de la forme 1
     * @param shape2    deuxième forme
     * @param position2 position de la forme 2
     * @return true s'il y a une intersection, false sinon
     */
    public static boolean intersection(Shape shape1, Position position1, Shape shape2, Position position2) {
        if (shape1 instanceof Circle && shape2 instanceof Circle) {
            return intersectionCircleCircle((Circle) shape1, position1, (Circle) shape2, position2);
        }
        if (shape1 instanceof Circle && shape2 instanceof Rectangle) {
            return intersectionCircleRectangle((Circle) shape1, position1, (Rectangle) shape2, position2);
        }
        if (shape1 instanceof Rectangle && shape2 instanceof Circle) {
            return intersectionCircleRectangle((Circle) shape2, position2, (Rectangle) shape1, position1);
        }
        if (shape1 instanceof Circle && shape2 instanceof Polygone) {
            return intersectionCirclePolygone((Circle) shape1, position1, (Polygone) shape2, position2);
        }
        if (shape1 instanceof Polygone && shape2 instanceof Circle) {
            return intersectionCirclePolygone((Circle) shape2, position2, (Polygone) shape1, position1);
        }
        return intersectionSegmentsSegments(shape1, position1, shape2, position2);
    }

    public static boolean intersectionCirclePolygone(Circle circle, Position position1, Polygone polygone, Position position2) {
        List<Point> pointsRectangle = CalculPoints.calculExtremityPoints(polygone, position2);
        return intersectionCircleSegments(circle, position1, pointsRectangle);
    }

    public static boolean intersectionCircleRectangle(Circle circle, Position position1, Rectangle rectangle, Position position2) {
        List<Point> pointsRectangle = CalculPoints.calculExtremityPoints(rectangle, position2);
        return intersectionCircleSegments(circle, position1, pointsRectangle);
    }

    public static boolean intersectionCircleSegments(Circle circle, Position position, List<Point> points) {
        int size = points.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                //equation cercle (x-cercle.x)^2 + (y-cercle.y)^2 = R^2
                // équation de la droite = y = ax+b
                double r = circle.getRadius();
                double xc = position.getX();
                double yc = position.getY();
                double xb1 = points.get(i).getX();
                double yb1 = points.get(i).getY();
                double xb2 = points.get(j).getX();
                double yb2 = points.get(j).getY();
                if (xb1 == xb2) {
                    System.out.println("vertical");
                    if (intersectionDroiteVerticaleSegments(new Point(xb1, yb1), new Point(xb2, yb2), circle, position)) {
                        return true;
                    }
                    continue;
                }
                double a = (yb2 - yb1) / (xb2 - xb1);
                double b = (yb1 - a * xb1);

                //Après simplification on obtient une équation du deuxième degré et on obtient donc un delta.
                //Equation : alpha x^2 + beta x + c = 0
                double beta = -2 * xc - 2 * a * b + 2 * a * yc;
                double alpha = (a * a + 1);
                double delta = beta * beta - 4 * alpha * (xc * xc + (b - yc) * (b - yc) - r * r);
                if (calculRoots(xb1, yb1, xb2, yb2, a, b, beta, alpha, delta)) return true;
            }
        }
        return false;
    }

    public static boolean calculRoots(double xb1, double yb1, double xb2, double yb2, double a, double b, double beta, double alpha, double delta) {
        double x1;
        double y1;
        double x2;
        double y2;
        if (delta >= 0) {
            x1 = (-beta - Math.sqrt(delta)) / (2 * alpha);
            y1 = a * x1 + b;
            x2 = (-beta + Math.sqrt(delta)) / (2 * alpha);
            y2 = a * x2 + b;
            // x1 appartient à [xb1 ; xb2] ou [xb2 ; xb1] et y1 appartient à [yb1 ; yb2] ou [yb2 ; yb1]
            if (((xb1 <= x1 && x1 <= xb2) || (xb1 >= x1 && x1 >= xb2)) && ((yb1 <= y1 && y1 <= yb2) || (yb1 >= y1 && y1 >= yb2))) {
                return true;
            }
            // x2 appartient à [xb1 ; xb2] ou [xb2 ; xb1] et y2 appartient à [yb1 ; yb2] ou [yb2 ; yb1]
            if (((xb1 <= x2 && x2 <= xb2) || (xb1 >= x2 && x2 >= xb2)) && ((yb1 <= y2 && y2 <= yb2) || (yb1 >= y2 && y2 >= yb2))) {
                return true;
            }
        }
        return false;
    }

    public static boolean intersectionDroiteVerticaleSegments(Point point1, Point point2, Circle cercle, Position positionCercle) {
        //Dans ce cas la droite du bateau est de la forme x=a;
        double a = point1.getX();
        double yb1 = point1.getY();
        double yb2 = point2.getY();
        double xc = positionCercle.getX();
        double yc = positionCercle.getY();
        double r = cercle.getRadius();
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

    public static boolean intersectionSegmentsSegments(Shape shape1, Position position1, Shape shape2, Position position2) {
        List<Point> pointsShape1 = CalculPoints.calculExtremityPoints(shape1, position1);
        List<Point> pointsShape2 = CalculPoints.calculExtremityPoints(shape2, position2);
        int sizePoint1 = pointsShape1.size();
        int sizePoint2 = pointsShape2.size();
        for (int i = 0; i < sizePoint1; i++) {
            for (int j = i; j < sizePoint1; j++) {
                for (int k = 0; k < sizePoint2; k++) {
                    for (int l = k; l < sizePoint2; l++) {
                        if (intersectionSegmentSegment(pointsShape1.get(i), pointsShape1.get(j), pointsShape2.get(k), pointsShape2.get(l))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean intersectionSegmentSegment(Point point1, Point point2, Point point3, Point point4) {
        //équation de droite y = ax+b et y = cx+d
        double x1 = point1.getX();
        double x2 = point2.getX();
        double x3 = point3.getX();
        double x4 = point4.getX();
        double y1 = point1.getY();
        double y2 = point2.getY();
        double y3 = point3.getY();
        double y4 = point4.getY();
        double a;
        double b;
        double c;
        double d;
        if (x1 == x2 && x2 != x3 && x3 == x4) {
            return false;
        }
        if (x1 == x2 && x2 == x3 && x3 == x4) {
            return ((y1 <= y4 && y1 >= y3)) || ((y1 >= y4) && (y1 <= y3)) || ((y2 <= y4) && (y2 >= y3)) || ((y2 >= y4) && (y2 <= y3));
        }
        double xtemp;
        double ytemp;
        if (x1 == x2 && x3 != x4) {
            c = (y4 - y3) / (x4 - x3);
            d = (y3 - c * x3);
            ytemp = c * x1 + d;
            return (ytemp <= y4 && ytemp >= y3) || (ytemp >= y4 && ytemp <= y3);
        }
        if (x1 != x2 && x3 == x4) {
            a = (y2 - y1) / (x2 - x1);
            b = (y1 - a * x1);
            ytemp = a * x3 + b;
            return (ytemp <= y2 && ytemp >= y1) || (ytemp >= y2 && ytemp <= y1);
        }
        a = (y2 - y1) / (x2 - x1);
        b = (y1 - a * x1);
        c = (y4 - y3) / (x4 - x3);
        d = (y3 - c * x3);
        if (a == c && b == d) {
            return ((y1 <= y4 && y1 >= y3)) || ((y1 >= y4) && (y1 <= y3)) || ((y2 <= y4) && (y2 >= y3)) || ((y2 >= y4) && (y2 <= y3));
        }
        if (a == c) {
            return false;
        }
        xtemp = (d - b) / (a - c);
        ytemp = a * xtemp + b;
        return ((ytemp <= y2 && ytemp >= y1) || (ytemp >= y2 && ytemp <= y1)) && ((ytemp <= y4 && ytemp >= y2) || (ytemp >= y4 && ytemp <= y2));
    }

    public static boolean intersectionCircleCircle(Circle circle1, Position position1, Circle circle2, Position position2) {
        Point pointCircle1 = new Point(position1.getX(), position1.getY());
        Point pointCircle2 = new Point(position2.getX(), position2.getY());
        double rs = circle1.getRadius();
        double rc = circle2.getRadius();
        return pointCircle1.distance(pointCircle2) <= (rs + rc);
    }

    public List<Point> equationSecondDegres(double a, double b, double c) { //ax^2+bx+c==0
        return new ArrayList<>();
    }
}
