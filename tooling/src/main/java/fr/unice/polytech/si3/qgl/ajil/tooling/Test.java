package fr.unice.polytech.si3.qgl.ajil.tooling;

import fr.unice.polytech.si3.qgl.ajil.Checkpoint;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        Checkpoint test = null;
        System.out.println(test);
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1); arrayList.add(2);
        System.out.println(arrayList);
        arrayList.remove((Integer) 3);
        System.out.println(arrayList);
        ArrayList<Point> points = new ArrayList<>();
        points.add(null);
        System.out.println(points);
    }

}

