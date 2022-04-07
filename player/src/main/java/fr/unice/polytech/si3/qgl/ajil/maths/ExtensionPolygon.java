package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Stream;

import java.util.Arrays;
import java.util.Collections;

public class ExtensionPolygon {

    public ExtensionPolygon(){};

    int calculArea (Polygone polygone){
        int size = polygone.getVertices().length;
        Point[] points = polygone.getVertices();
        int area = 0;
        for( int i = 0; i < size-1; i++ ) {
            /*if(i+1==size){
                System.out.println(points[i] + "" + points[0]);
                area += points[i].getX() * (points[0].getY()) - points[0].getX() * (points[i].getY());
            } else {
                System.out.println(points[i] + "" + points[i+1]);
                area += (points[i].getX() * points[i + 1].getY()) - (points[i + 1].getX() * (points[i].getY()));
            }*/
            System.out.println(area);
            area += (points[i].getX() * points[i + 1].getY()) - (points[i + 1].getX() * (points[i].getY()));
            //area += x[i+1]*(y[i+2]-y[i]) + y[i+1]*(x[i]-x[i+2]);
        }
        return area;
    }
}
