package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExtensionPolygon {

    public ExtensionPolygon(){};

    double calculArea (Polygone polygone){
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
}
