package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.*;

import java.util.ArrayList;
import java.util.List;

public class CalculIntersection {

    private CalculIntersection() {
    }

    public static boolean intersectionCircleCircle(Circle circle1, Position position1, Circle circle2, Position position2) {
        Point pointCircle1 = new Point(position1.getX(), position1.getY());
        Point pointCircle2 = new Point(position2.getX(), position2.getY());
        double rs = circle1.getRadius();
        double rc = circle2.getRadius();
        return pointCircle1.distance(pointCircle2) <= (rs + rc);
    }

    // Nouvelle version

    public static boolean intersectionShapes(Shape shape1, Position position1, Shape shape2, Position position2) {
        return !intersection2(shape1, position1, shape2, position2).isEmpty();
    }

    public static List<Point> intersection2(Shape shape1, Position position1, Shape shape2, Position position2) {
        if (shape1 instanceof Circle circle1 && shape2 instanceof Circle circle2) {
            return intersectionPointCircleCircle(circle1, position1, circle2, position2);
        }

        if (shape1 instanceof Circle circle && shape2 instanceof Rectangle rectangle) {
            return intersectionPointCircleRectangle(circle, position1, rectangle, position2);
        }
        if (shape1 instanceof Rectangle rectangle2 && shape2 instanceof Circle circle2) {
            return intersectionPointCircleRectangle(circle2, position2, rectangle2, position1);
        }

        if (shape1 instanceof Circle circle && shape2 instanceof Polygone polygone) {
            return intersectionPointCirclePolygone(circle, position1, polygone, position2);
        }
        if (shape1 instanceof Polygone polygone && shape2 instanceof Circle circle2) {
            return intersectionPointCirclePolygone(circle2, position2, polygone, position1);
        }


        return intersectionPointSegmentsSegments(shape1, position1, shape2, position2);
    }


    //ne fonctionne pas ?? revoir
    public static List<Point> intersectionPointCircleCircle(Circle circle1, Position position1, Circle circle2, Position position2) {
        //cercle 1 (x-xc1) ^2 + (y-yc1) = r^2
        //cercle 2 (x-xc2) ^2 + (y-yc2) = R^2
        List<Point> result = new ArrayList<>();
        if (intersectionCircleCircle(circle1, position1, circle2, position2)) result.add(new Point());
        return result;
    }

    /**
     * @param circle
     * @param positionCircle
     * @param point1
     * @param point2
     * @return la liste des points de l'intersection entre le cercle et la droite passant par les deux points
     */
    public static List<Point> intersectionPointDroiteCircle(Circle circle, Position positionCircle, Point point1, Point point2) {
        List<Point> result = new ArrayList<>();
        double r = circle.getRadius();
        double xc = positionCircle.getX();
        double yc = positionCircle.getY();
        double xb1 = point1.getX();
        double yb1 = point1.getY();
        double xb2 = point2.getX();
        double yb2 = point2.getY();
        if (xb1 == xb2) {
            return intersectionPointDroiteVerticaleCircle(point1, point2, circle, positionCircle);
        }
        double a = (yb2 - yb1) / (xb2 - xb1);
        double b = (yb1 - a * xb1);

        //Apr??s simplification on obtient une ??quation du deuxi??me degr?? et on obtient donc un delta.
        //Equation : alpha x^2 + beta x + c = 0
        double beta = (-2 * xc) + (2 * a * b) - (2 * a * yc);
        double alpha = (a * a + 1);
        double c = (xc * xc) + (b - yc) * (b - yc) - (r * r);
        double delta = beta * beta - 4 * alpha * c;
        double x1;
        double y1;
        double x2;
        double y2;
        if (delta >= 0) {
            x1 = (-beta - Math.sqrt(delta)) / (2 * alpha);
            y1 = a * x1 + b;
            x2 = (-beta + Math.sqrt(delta)) / (2 * alpha);
            y2 = a * x2 + b;
            result.add(new Point(x1, y1));
            result.add(new Point(x2, y2));
        }
        return result;

    }

    /**
     * @param point1
     * @param point2
     * @param cercle
     * @param positionCercle
     * @return la liste des points de l'intersection en une droite verticale et le cercle
     */
    private static List<Point> intersectionPointDroiteVerticaleCircle(Point point1, Point point2, Circle cercle, Position positionCercle) {
        List<Point> result = new ArrayList<>();         //Dans ce cas la droite du bateau est de la forme x=a;
        double a = point1.getX();
        double xc = positionCercle.getX();
        double yc = positionCercle.getY();
        double r = cercle.getRadius();
        double b = -2 * yc;
        //On obtient une ??quation du deuxi??me degr?? et on obtient ce delta
        double delta = b * b - 4 * (a * a + xc * xc - 2 * a * xc + yc * yc - r * r);
        double y1;
        double y2;
        if (delta < 0) {
            return result;
        } else {
            y1 = (-b - Math.sqrt(delta)) / 2;
            y2 = (-b + Math.sqrt(delta)) / 2;
            result.add(new Point(point1.getX(), y1));
            result.add(new Point(point1.getX(), y2));
            return result;
        }
    }

    /**
     * @param circle
     * @param positionCircle
     * @param point1
     * @param point2
     * @return la liste des points entre le circle et le segment
     */
    public static List<Point> intersectionCircleSegment(Circle circle, Position positionCircle, Point point1, Point point2) {
        List<Point> intersectionDroite = intersectionPointDroiteCircle(circle, positionCircle, point1, point2);
        List<Point> result = new ArrayList<>();
        double x1 = point1.getX();
        double y1 = point1.getY();
        double x2 = point2.getX();
        double y2 = point2.getY();
        for (Point point : intersectionDroite) {
            double x = point.getX();
            double y = point.getY();
            boolean xinBorne = ((x1 <= x && x <= x2) || (x1 >= x && x >= x2));
            boolean yinBorne = ((y1 <= y && y <= y2) || (y1 >= y && y >= y2));
            if (xinBorne && yinBorne) {
                result.add(point);
            }
        }
        return result;
    }

    public static List<Point> intersectionPointCircleRectangle(Circle circle, Position position1, Rectangle rectangle, Position position2) {
        List<Point> result = new ArrayList<>();
        List<Point> pointsRectangle = CalculPoints.calculExtremityPoints(rectangle, position2);
        Point pointCircle = new Point(position1.getX(), position1.getY());
        int size = pointsRectangle.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (pointInCircle(circle, position1, pointsRectangle.get(i))) {
                    result.add(pointCircle);
                }
                result.addAll(intersectionCircleSegment(circle, position1, pointsRectangle.get(i), pointsRectangle.get(j)));
            }
        }
        return result;
    }

    public static boolean pointInCircle(Circle circle, Position position, Point point) {
        Point pointCircle = new Point(position.getX(), position.getY());
        return point.distance(pointCircle) <= circle.getRadius();
    }

    public static List<Point> intersectionPointCirclePolygone(Circle circle, Position position1, Polygone polygone, Position position2) {
        List<Point> result = new ArrayList<>();
        List<Point> pointsPoygone = CalculPoints.calculExtremityPoints(polygone, position2);
        Point pointCircle = new Point(position1.getX(), position1.getY());
        int size = pointsPoygone.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                result.addAll(intersectionCircleSegment(circle, position1, pointsPoygone.get(i), pointsPoygone.get(j)));
                if (pointInCircle(circle, position1, pointsPoygone.get(i))) {
                    result.add(pointCircle);
                }
            }
        }
        return result;
    }

    public static List<Point> intersectionPointSegmentsSegments(Shape shape1, Position position1, Shape shape2, Position position2) {
        List<Point> pointsShape1 = CalculPoints.calculExtremityPoints(shape1, position1);
        List<Point> pointsShape2 = CalculPoints.calculExtremityPoints(shape2, position2);
        List<Point> result = new ArrayList<>();
        int sizePoint1 = pointsShape1.size();
        int sizePoint2 = pointsShape2.size();
        for (int i = 0; i < sizePoint1; i++) {
            for (int j = i; j < sizePoint1; j++) {
                for (int k = 0; k < sizePoint2; k++) {
                    for (int l = k; l < sizePoint2; l++) {
                        result.addAll(intersectionPointSegmentSegment(pointsShape1.get(i), pointsShape1.get(j), pointsShape2.get(k), pointsShape2.get(l)));
                    }
                }
            }
        }
        return result;
    }

    public static List<Point> intersectionPointSegmentSegment(Point point1, Point point2, Point point3, Point point4) {
        //??quation de droite y = ax+b et y = cx+d
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
        List<Point> result = new ArrayList<>();
        //Deux segments parall??les et verticaux n'ayant pas le m??me X
        if (x1 == x2 && x2 != x3 && x3 == x4) {
            return result;
        }
        //Deux segments parall??les et verticaux ayant le m??me X
        if (x1 == x2 && x2 == x3 && x3 == x4) {
            // y1 appartient au segment entre p3 et p4
            boolean y1isInSegment = (y1 <= y4 && y1 >= y3) || ((y1 >= y4) && (y1 <= y3));
            if (y1isInSegment) {
                result.add(point1);
            }
            // y2 appartient au segment entre p3 et p4
            boolean y2isInSegment = ((y2 <= y4) && (y2 >= y3)) || ((y2 >= y4) && (y2 <= y3));
            if (y2isInSegment) {
                result.add(point2);
            }
            return result;
        }
        double xtemp;
        double ytemp;
        if (x1 == x2) {
            Point point = intersectionVerticalSegmentAndNoVerticalSegment(point1, point2, point3, point4);
            if (point != null) result.add(point);
            return result;
        }
        if (x3 == x4) {
            Point point = intersectionVerticalSegmentAndNoVerticalSegment(point3, point4, point1, point2);
            if (point != null) result.add(point);
            return result;
        }
        a = (y2 - y1) / (x2 - x1);
        b = (y1 - a * x1);
        c = (y4 - y3) / (x4 - x3);
        d = (y3 - c * x3);
        if (a == c && b == d) {
            boolean x1inBornX3X4 = (((x1 <= x3) && (x1 >= x4)) || ((x1 >= x3) && (x1 <= x4)));
            if (x1inBornX3X4) {
                result.add(point1);
            }
            boolean x2inBornX3X4 = (((x2 <= x3) && (x2 >= x4)) || ((x2 >= x3) && (x2 <= x4)));
            if (x2inBornX3X4) {
                result.add(point2);
            }
            boolean x3inBornX1X2 = (((x3 <= x1) && (x3 >= x2)) || ((x3 >= x1) && (x3 <= x2)));
            if (x3inBornX1X2) {
                result.add(point3);
            }
            boolean x4inBornX1X2 = (((x4 <= x1) && (x4 >= x2)) || ((x4 >= x1) && (x4 <= x2)));
            if (x4inBornX1X2) {
                result.add(point4);
            }
            return result;
        }
        if (a == c) {
            return result;
        }
        xtemp = (d - b) / (a - c);
        ytemp = a * xtemp + b;
        boolean yInBorn = ((ytemp <= y2 && ytemp >= y1) || (ytemp >= y2 && ytemp <= y1)) && ((ytemp <= y4 && ytemp >= y3) || (ytemp >= y4 && ytemp <= y3));
        if (yInBorn) {
            boolean xInBorn = ((xtemp <= x2 && xtemp >= x1) || (xtemp >= x2 && xtemp <= x1)) && ((xtemp <= x4 && xtemp >= x3) || (xtemp >= x4 && xtemp <= x3));
            if (xInBorn) {
                result.add(new Point(xtemp, ytemp));
            }
        }
        return result;

    }

    private static Point intersectionVerticalSegmentAndNoVerticalSegment(Point pointVertical1, Point pointVertical2, Point point1, Point point2) {
        double x1 = pointVertical1.getX();
        double x3 = point1.getX();
        double x4 = point2.getX();
        double y1 = pointVertical1.getY();
        double y2 = pointVertical2.getY();
        double y3 = point1.getY();
        double y4 = point2.getY();
        double a;
        double b;
        double ytemp;
        a = (y4 - y3) / (x4 - x3);
        b = (y3 - a * x3);
        ytemp = a * x1 + b;
        //une segment vertical et l'autre non. V??rifions si l'image de x1 par la droite passant par le segment 2
        // appartient au deux segments
        boolean xinBorn = (((x1 <= x3) && (x1 >= x4)) || ((x1 >= x3) && (x1 <= x4)));
        boolean yinborn = (ytemp <= y1 && ytemp >= y2) || (ytemp >= y1 && ytemp <= y2);
        if (xinBorn && yinborn) {
            return new Point(x1, ytemp);
        }
        return null;
    }


}
