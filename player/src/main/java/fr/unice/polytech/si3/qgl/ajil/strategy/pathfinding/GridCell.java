package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;

import java.util.ArrayList;

public class GridCell {
    private final Point center;
    private final double size;
    private boolean blocked = false;
    private ArrayList<Segment> segments = new ArrayList<>();

    public GridCell(Point center, double size) {
        this.center = center;
        this.size = size;
        this.segments = createGridSegments();
    }

    public ArrayList<Segment> createGridSegments() {
        Point[] points = new Point[4];
        double delta = size/2;
        ObstacleDetection obstacleDetection = new ObstacleDetection();
        points[0] = new Point(center.getX() - delta, center.getY() - delta );
        points[1] = new Point(center.getX() - delta,center.getY() + delta );
        points[2] = new Point(center.getX() + delta,center.getY() + delta );
        points[3] = new Point(center.getX() + delta,center.getY() - delta );

        return obstacleDetection.createSegments(points, points.length);
    }

    public void intersection(ArrayList<Segment> segmentsToCheck){
        for (Segment seg : segments){
            for ( Segment segToCheck : segmentsToCheck ){
                if (Intersection.segmentIntersection(seg, segToCheck) != null) {
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

    public boolean contains(Position pos){
        double minx = center.getX()-size/2;
        double maxx = center.getX()+size/2;
        double miny = center.getY()-size/2;
        double maxy = center.getY()+size/2;
        return (pos.getX() >= minx && pos.getX()<maxx && pos.getY() >= miny && pos.getY() < maxy);
    }

    @Override
    public String toString() {
        return "GridCell{" +
                "center=" + center.toString() +
                ", blocked=" + blocked +
                '}';
    }
}
