package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.shape.Point;

import java.util.ArrayList;

public class GridCell {
    private Point center;
    private double size;
    private boolean blocked = false;
    private ArrayList<Segment> segments = new ArrayList<>();

    public GridCell(Point center, double size) {
        this.center = center;
        this.size = size;
        this.segments = createGridSegments();
    }

    public ArrayList<Segment> createGridSegments(){
        Point[] points = new Point[4];
        double delta = size/2;
        points[0] = new Point(center.getX() - delta, center.getY() - delta );
        points[1] = new Point(center.getX() - delta,center.getY() + delta );
        points[2] = new Point(center.getX() + delta,center.getY() + delta );
        points[3] = new Point(center.getX() + delta,center.getY() - delta );

        return ObstacleDetection.createSegments(points, points.length);
    }

    public void intersection(ArrayList<Segment> segmentsToCheck){
        for (Segment seg : segments){
            for ( Segment segToCheck : segmentsToCheck ){
                if (Intersection.SegIntersection(seg, segToCheck) != null){
                    this.blocked = true;
                    return;
                }
            }
        }
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public String toString() {
        return "GridCell{" +
                "center=" + center.toString() +
                ", blocked=" + blocked +
                '}';
    }
}