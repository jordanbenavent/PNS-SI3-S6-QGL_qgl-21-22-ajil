package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;

public class Intersection {

    public static Point SegIntersection(Segment segmentOne, Segment segmentTwo)
    {

        double dx1 = segmentOne.getEndX()-segmentOne.getStartX() ;
        double dy1 = segmentOne.getEndY()-segmentOne.getStartY() ;
        double dx2 = segmentTwo.getEndX()-segmentTwo.getStartX() ;
        double dy2 = segmentTwo.getEndY()-segmentTwo.getStartY() ;
        double dxx = segmentOne.getStartX() - segmentTwo.getStartX() ;
        double dyy = segmentOne.getStartY() - segmentTwo.getStartY() ;
        double div, t, s;

        div = dy2*dx1-dx2*dy1;
        if (Math.abs(div) < 1.0e-10)  //better to compare abs(div) with small Eps
            return null;  //collinear case

        t = (dx1*dyy-dy1*dxx) / div;
        if (t < 0 || t > 1.0)
            return null; //intersection outside the first segment

        s = (dx2*dyy-dy2*dxx) / div;
        if (s < 0 || s > 1.0)
            return null;  //intersection outside the second segment
        return new Point(segmentOne.getStartX() + s * dx1, segmentOne.getStartY() + s * dy1);
    }

    public static void main(String[] args) {
        if (Intersection.SegIntersection(new Segment(-1.14,3.39,-1.14,4.39), new Segment(0.48,1.15,4,2.35) ) != null){
            System.out.println("Intersection");
        }else System.out.println("Aucune Intersection");
    }

}
