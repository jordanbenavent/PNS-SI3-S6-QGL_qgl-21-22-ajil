package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ExtensionPolygonTest {

    @Test
    void calculAreaTest(){
        ArrayList<Point> points = new ArrayList<>();
        Point point1 = new Point(-1,0);
        Point point2 = new Point(0,1);
        Point point3 = new Point(1,0);
        Point point4 = new Point(0,-1);
        Point point5 = new Point(2,-2);
        Point point6 = new Point(-2,-2);
        points.add(point4); points.add(point3); points.add(point2);
        points.add(point1);
        Polygone polygone = new Polygone("polytech", 0 , points.toArray(new Point[points.size()]));
        ExtensionPolygon extensionPolygon = new ExtensionPolygon();
        System.out.println(extensionPolygon.calculArea(polygone));
        points.clear();
        point1 = new Point(0,0);
        point2 = new Point(1,2);
        point3 = new Point(2,0);
        points.add(point1); points.add(point2); points.add(point3);
        polygone = new Polygone("polytech", 0 , points.toArray(new Point[points.size()]));
        extensionPolygon = new ExtensionPolygon();
        System.out.println(extensionPolygon.calculArea(polygone));
    }
}
