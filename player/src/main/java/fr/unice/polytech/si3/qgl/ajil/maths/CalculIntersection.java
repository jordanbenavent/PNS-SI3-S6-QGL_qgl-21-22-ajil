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
        if (shape1 instanceof Circle circle1 && shape2 instanceof Circle circle2) {
            return intersectionCircleCircle(circle1, position1, circle2, position2);
        }
        if (shape1 instanceof Circle circle && shape2 instanceof Rectangle rectangle) {
            return intersectionCircleRectangle(circle, position1, rectangle, position2);
        }
        if (shape1 instanceof Rectangle rectangle2 && shape2 instanceof Circle circle2) {
            return intersectionCircleRectangle(circle2, position2, rectangle2, position1);
        }
        if (shape1 instanceof Circle circle && shape2 instanceof Polygone polygone) {
            return intersectionCirclePolygone(circle, position1, polygone, position2);
        }
        if (shape1 instanceof Polygone polygone && shape2 instanceof Circle circle2) {
            return intersectionCirclePolygone(circle2, position2, polygone, position1);
        }
        return intersectionSegmentsSegments(shape1, position1, shape2, position2);
    }



    public static boolean intersectionCirclePolygone(Circle circle, Position position1, Polygone polygone, Position position2) {
        List<Point> pointsPolygon = CalculPoints.calculExtremityPoints(polygone, position2);
        return intersectionCircleSegments(circle, position1, pointsPolygon);
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
        boolean condition_b1 = ((y1 <= y4 && y1 >= y3)) || ((y1 >= y4) && (y1 <= y3)) || ((y2 <= y4) && (y2 >= y3)) || ((y2 >= y4) && (y2 <= y3));
        if (x1 == x2 && x2 == x3 && x3 == x4) {
            return condition_b1;
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
            return condition_b1;
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

    // Nouvelle version

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


    //ne fonctionne pas à revoir
    public static List<Point> intersectionPointCircleCircle(Circle circle1, Position position1, Circle circle2, Position position2) {
        //cercle 1 (x-xc1) ^2 + (y-yc1) = r^2
        //cercle 2 (x-xc2) ^2 + (y-yc2) = R^2
        List<Point> result = new ArrayList<>();
        if(!intersection(circle1, position1, circle2, position2)) return result;
        double xb = position2.getX();
        double yb = position2.getY();
        double xa = position1.getX();
        double ya = position1.getY();
        double r = circle2.getRadius();
        double R = circle1.getRadius();
        //a = 2xB
        //b = 2yB
        //c = x2B + y2B − R2 + r2
        //l’´equation du second degree : alphax2 − betax + gamma = 0
        double a = -2*xa + 2*xb;
        System.out.println( "a = "+a );
        double b = -2*ya + 2*yb;
        System.out.println( "b = "+b );
        double c = (xa*xa) - (xb*xb) + (ya*ya) - (yb*yb) - (R*R) + (r*r);
        System.out.println( "c = "+c );
        double alpha = (1+((a*a)/(b*b)));
        double beta = (-2*xa + (2*a*c)/(b*b) + (2*a*ya)/b);
        double gamma = (c*c)/(b*b) + ya + (2*c*ya)/b - R*R;

        // delta = (2ac)2 − 4(a2 + b2)(c2 − b2r2)
        double delta = beta*beta - (4*alpha*gamma);
        System.out.println(delta);
        double x1 = (-beta + Math.sqrt(delta)) / 2*alpha;
        System.out.println(x1);
        double x2 = ((2*a*c) + Math.sqrt(delta)) / 2*((a*a)+(b*b));
        double y1;
        double y2;
        if(yb != 0) {
            y1 = (c-(a*x1))/b;
            y2 = (c-(a*x2))/b;
        } else {
            y1 = b/2 + Math.sqrt((R*R) - ((2*c) - (a*a))/(2*a));
            y2 = b/2 - Math.sqrt((R*R) - ((2*c) - (a*a))/(2*a));
        }
        result.add(new Point(x1, y1));
        result.add(new Point(x2, y2));
        return result;
    }

    /**
     *
     * @param circle
     * @param positionCircle
     * @param point1
     * @param point2
     * @return la liste des points de l'intersection entre le cercle et la droite passant par les deux points
     */
    public static List<Point> intersectionPointDroiteCircle(Circle circle, Position positionCircle, Point point1, Point point2){
        List<Point> result = new ArrayList<>();
        double r = circle.getRadius();
        double xc = positionCircle.getX();
        double yc = positionCircle.getY();
        double xb1 = point1.getX();
        double yb1 = point1.getY();
        double xb2 = point2.getX();
        double yb2 = point2.getY();
        if (xb1 == xb2) {
            System.out.println("vertical");
            return intersectionPointDroiteVerticaleCircle(point1, point2, circle, positionCircle);
        }
        double a = (yb2 - yb1) / (xb2 - xb1);
        double b = (yb1 - a * xb1);

        //Après simplification on obtient une équation du deuxième degré et on obtient donc un delta.
        //Equation : alpha x^2 + beta x + c = 0
        double beta = (-2 * xc) + (2 * a * b) -  (2 * a * yc);
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
            result.add(new Point(x1,y1));
            result.add(new Point(x2,y2));
        }
        return result;

    }

    /**
     *
     * @param point1
     * @param point2
     * @param cercle
     * @param positionCercle
     * @return la liste des points de l'intersection en une droite verticale et le cercle
     */
    private static List<Point> intersectionPointDroiteVerticaleCircle(Point point1, Point point2, Circle cercle, Position positionCercle) {
        //Dans ce cas la droite du bateau est de la forme x=a;
        List<Point> result = new ArrayList<>();
        double a = point1.getX();
        double xc = positionCercle.getX();
        double yc = positionCercle.getY();
        double r = cercle.getRadius();
        double b = -2 * yc;
        //On obtient une équation du deuxième degré et on obtient ce delta
        double delta = b * b - 4 * (a * a + xc * xc - 2 * a * xc + yc * yc - r * r);
        double y1;
        double y2;
        if (delta < 0) {
            return result;
        }
        y1 = (-b - Math.sqrt(delta)) / 2;
        y2 = (-b + Math.sqrt(delta)) / 2;
        result.add(new Point(point1.getX(), y1));
        result.add(new Point(point1.getX(), y2));
        return result;
    }

    /**
     *
     * @param circle
     * @param positionCircle
     * @param point1
     * @param point2
     * @return la liste des points entre le circle et le segment
     */
    public static List<Point> intersectionCircleSegment(Circle circle, Position positionCircle, Point point1, Point point2){
        List<Point> intersectionDroite = intersectionPointDroiteCircle(circle, positionCircle, point1, point2);
        List<Point> result = new ArrayList<>();
        double x1 = point1.getX();
        double y1 = point1.getY();
        double x2 = point2.getX();
        double y2 = point2.getY();
        double x;
        double y;
        for(Point point : intersectionDroite){
            x=point.getX();
            y=point.getY();
            if( ((x1 <= x && x <= x2) || (x1 >= x && x >= x2)) && ((y1 <= y && y <= y2) || (y1 >= y && y >= y2))){
                result.add(point);
            }
        }
        return result;
    }

    public static List<Point> intersectionPointCircleRectangle(Circle circle, Position position1, Rectangle rectangle, Position position2) {
        List<Point> result = new ArrayList<>();
        List<Point> pointsRectangle = CalculPoints.calculExtremityPoints(rectangle, position2);
        System.out.println(pointsRectangle);
        int size = pointsRectangle.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                result.addAll(intersectionCircleSegment(circle, position1, pointsRectangle.get(i), pointsRectangle.get(j)));
            }
        }
        return result;
    }

    public static List<Point> intersectionPointCirclePolygone(Circle circle, Position position1, Polygone polygone, Position position2) {
        List<Point> result = new ArrayList<>();
        List<Point> pointsPoygone = CalculPoints.calculExtremityPoints(polygone, position2);
        System.out.println(pointsPoygone);
        int size = pointsPoygone.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                result.addAll(intersectionCircleSegment(circle, position1, pointsPoygone.get(i), pointsPoygone.get(j)));
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

    public static  List<Point> intersectionPointSegmentSegment(Point point1, Point point2, Point point3, Point point4){
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
        List<Point> result = new ArrayList<>();
        //Deux segments parallèles et verticaux n'ayant pas le même X
        if (x1 == x2 && x2 != x3 && x3 == x4) {
            return result;
        }
        //Deux segments parallèles et verticaux ayant le même X
        if (x1 == x2 && x2 == x3 && x3 == x4) {
            // y1 appartient au segment entre p3 et p4
            if((y1 <= y4 && y1 >= y3) || ((y1 >= y4) && (y1 <= y3))){
                result.add(point1);
            }
            // y2 appartient au segment entre p3 et p4
            if(((y2 <= y4) && (y2 >= y3)) || ((y2 >= y4) && (y2 <= y3))){
                result.add(point2);
            }
            return result;
        }
        double xtemp;
        double ytemp;
        if (x1 == x2 && x3 != x4) {
            c = (y4 - y3) / (x4 - x3);
            d = (y3 - c * x3);
            ytemp = c * x1 + d;
            //une segment vertical et l'autre non. Vérifions si l'image de x1 par la droite passant par le segment 2
            // appartient au deux segments
            if((((x1<= x3) && (x1 >= x4)) || ((x1 >= x3) && (x1 <= x4)))){
                if((ytemp <= y1 && ytemp >= y2) || (ytemp >= y1 && ytemp <= y2)){
                    result.add(new Point(x1, ytemp));
                }
            }
            return result;
        }
        if (x1 != x2 && x3 == x4) {
            a = (y2 - y1) / (x2 - x1);
            b = (y1 - a * x1);
            ytemp = a * x3 + b;
            //une segment vertical et l'autre non. Vérifions si l'image de x1 par la droite passant par le segment 1
            // appartient au deux segments
            if((((x3<= x1) && (x3 >= x2)) || ((x3 >= x1) && (x3 <= x2)))){
                if((ytemp <= y4 && ytemp >= y3) || (ytemp >= y4 && ytemp <= y3)){
                    result.add(new Point(x3, ytemp));
                }
            }
            return result;
        }
        a = (y2 - y1) / (x2 - x1);
        b = (y1 - a * x1);
        c = (y4 - y3) / (x4 - x3);
        d = (y3 - c * x3);
        if (a == c && b == d) {
            if((((x1<= x3) && (x1 >= x4)) || ((x1 >= x3) && (x1 <= x4)))){
                result.add(point1);
            }
            if((((x2<= x3) && (x2 >= x4)) || ((x2 >= x3) && (x2 <= x4)))){
                result.add(point2);
            }
            if((((x3<= x1) && (x3 >= x2)) || ((x3 >= x1) && (x3 <= x2)))){
                result.add(point3);
            }
            if((((x4<= x1) && (x4 >= x2)) || ((x4 >= x1) && (x4 <= x2)))){
                result.add(point4);
            }
            return result;
        }
        if (a == c) {
            return result;
        }
        xtemp = (d - b) / (a - c);
        ytemp = a * xtemp + b;
        if(((ytemp <= y2 && ytemp >= y1) || (ytemp >= y2 && ytemp <= y1)) && ((ytemp <= y4 && ytemp >= y3) || (ytemp >= y4 && ytemp <= y3))){
            if(((xtemp <= x2 && xtemp >= x1) || (xtemp >= x2 && xtemp <= x1)) && ((xtemp <= x4 && xtemp >= x3) || (xtemp >= x4 && xtemp <= x3))){
                result.add(new Point(xtemp, ytemp));
            }
        }
        return result;

    }


    /*
    public List<Point> intersection(Ship ship, Vector v_ship, Checkpoint checkpoint) {
        // (1) équation cercle: (x-checkpoint.x)^2 + (y-checkpoint.y)^2 = R^2
        double r = ((Circle) checkpoint.getShape()).getRadius();
        double xc = checkpoint.getPosition().getX();
        double yc = checkpoint.getPosition().getY();

        // (2) équation de la droite du bateau: y = ax+b
        // Etape 1: On prend d'abord deux points (à partir du vecteur bateau) pour pouvoir calculer la droite
        double x1 = ship.getPosition().getX();
        double y1 = ship.getPosition().getY();
        double x2 = ship.getPosition().getX() + v_ship.getX();
        double y2 = ship.getPosition().getY() + v_ship.getY();
        if(Math.abs(x2 - x1) < 0.0001){
            return intersectionDroiteVerticaleCircle(ship, checkpoint);
        }

        // Etape 2: on calcule la pente si x1 != x2
        double a = (y2 - y1)/(x2 - x1);
        // Etape 3: On remplace dans l'équation a par la pente et x et y par un point pour trouver b
        double b = y1 - a*x1;

        // Maintenant on remplace (2) dans (1)
        // (x − xc)² + (a*x + b −yc)² = R²
        // x² − 2*x*xc + xc² + a²*x² + b² + yc² + 2(a*x*b − a*x*yc − b*yc) − R² = 0
        // x²(1 + a²) + x(−2*xc + 2*a*b − 2*a*yc) + (xc² + yc² + b²− 2*b*yc − R²) = 0
        // On a donc du second degré de la forme ax² + bx + c = 0 avec:
        ArrayList<Point> points_intersection = new ArrayList<>();
        double A = 1 + a*a;
        double B = 2 * (-xc + a*b - a*yc);
        double C = xc*xc + yc*yc + b*b - 2*b*yc - r*r;
        double delta = B*B - 4*A*C;

        if (delta > 0) {
            double x = (-B - Math.sqrt(delta)) / (2*A);
            double y = a*x + b;
            points_intersection.add(new Point(x, y));

            x = (-B + Math.sqrt(delta)) / (2*A);
            y = a*x + b;
            points_intersection.add(new Point(x, y));
        }
        else if (delta == 0) {
            double x = -B / (2*A);
            double y = a*x + b;
            points_intersection.add(new Point(x, y));
        }

        return points_intersection;
    }

    public List<Point> intersectionDroiteVerticaleCircle(Ship ship, Checkpoint checkpoint) {
        //Dans ce cas la droite du bateau est de la forme x = a;
        double a = ship.getPosition().getX();
        double xc = checkpoint.getPosition().getX();
        double yc = checkpoint.getPosition().getY();
        double r = ((Circle) checkpoint.getShape()).getRadius();

        // (a − xc)² + (y − yc)² = R²
        // a² − 2*a*xc + xc² + y² -2*y*yc + yc² − R² = 0
        // y² + y(−2*yc) + (a² - 2*a*xc + xc² + yc² − R²) = 0
        // On a donc du second degré de la forme ax² + bx + c = 0 avec:
        ArrayList<Point> points_intersection = new ArrayList<>();
        double A = 1;
        double B = -2*yc;
        double C = a*a - 2*a*xc + xc*xc + yc*yc - r*r;
        double delta = B*B - 4*A*C;

        if (delta > 0) {
            double x = a;
            double y = (-B - Math.sqrt(delta)) / (2*A);
            points_intersection.add(new Point(x, y));

            x = a;
            y = (-B + Math.sqrt(delta)) / (2*A);
            points_intersection.add(new Point(x, y));
        }
        else if (delta == 0) {
            double y = -B / (2*A);
            points_intersection.add(new Point(a, y));
        }
        return points_intersection;
    }
    */

}
