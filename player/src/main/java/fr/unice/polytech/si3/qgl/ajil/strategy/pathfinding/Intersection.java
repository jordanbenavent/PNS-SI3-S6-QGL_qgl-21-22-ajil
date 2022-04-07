package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;

public class Intersection {

    public static Point segmentIntersection(Segment s1, Segment s2) {

        double dx1 = s1.getEndX() - s1.getStartX();
        double dy1 = s1.getEndY() - s1.getStartY();
        double dx2 = s2.getEndX() - s2.getStartX();
        double dy2 = s2.getEndY() - s2.getStartY();
        double dxx = s1.getStartX() - s2.getStartX();
        double dyy = s1.getStartY() - s2.getStartY();
        double div, t, s;

        div = dy2 * dx1 - dx2 * dy1;
        if (Math.abs(div) < 1.0e-10)  //better to compare abs(div) with small Eps
            return null;  //collinear case

        t = (dx1 * dyy - dy1 * dxx) / div;
        if (t < 0 || t > 1.0)
            return null; //intersection outside the first segment

        s = (dx2 * dyy - dy2 * dxx) / div;
        if (s < 0 || s > 1.0)
            return null;  //intersection outside the second segment
        return new Point(s1.getStartX() + s * dx1, s1.getStartY() + s * dy1);
    }
}
