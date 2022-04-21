package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Vector;
import fr.unice.polytech.si3.qgl.ajil.shape.Droite;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtensionPolygon {

    public ExtensionPolygon(){};

    public static double calculArea(Polygone polygone){
        int size = polygone.getVertices().length;
        List<Point> pointList = Arrays.asList(polygone.getVertices());
        ArrayList<Point> test = new ArrayList<>(pointList);
        test.add(test.get(0));
        int area = 0;
        for( int i = 0; i < size; i++ ) {
            area += test.get(i).getX() * test.get(i + 1).getY() - (test.get(i).getY() * test.get(i + 1).getX());
        }
        return area;
    }

    double calculArea (List<Point> pointList){
        int size = pointList.size();
        ArrayList<Point> points = new ArrayList<>(pointList);
        points.add(points.get(0));
        int area = 0;
        for( int i = 0; i < size; i++ ) {
            area += points.get(i).getX() * points.get(i + 1).getY() - (points.get(i).getY() * points.get(i + 1).getX());
        }
        return area;
    }

    public static Polygone extension(Polygone polygone, double margin){
        double area = calculArea(polygone);
        List<Point> pointList = Arrays.asList(polygone.getVertices());
        ArrayList<Point> points = new ArrayList<>(pointList);
        points.add(points.get(0));
        List<Point> result = new ArrayList<>();
        int size = points.size();
        Vector vector;
        Point point1;
        Point point2;
        Point point3;
        for( int i = 0; i < size; i++ ) {
            point1 = points.get(i);
            if(i+1 == size) point2 = points.get(0);
            else    point2 = points.get(i+1);
            if(i+2 >= size) {
                if (i + 2 == size) point3 = points.get(0);
                else point3 = points.get(1);
            }
            else{
                point3 = points.get(i + 2);
            }
            if(area >= 0){
                vector = new Vector(0,0);
            }
            result.add(CalculIntersection.intersectionDroite(point1, point2, point2, point3));
        }
        return null;
    }

    private static Point intersectionDroite(Point point1, Point point2) {
        double x1 = point1.getX();
        double y1 = point1.getY();
        double x2 = point2.getX();
        double y2 = point2.getY();
        return null;
    }

}
