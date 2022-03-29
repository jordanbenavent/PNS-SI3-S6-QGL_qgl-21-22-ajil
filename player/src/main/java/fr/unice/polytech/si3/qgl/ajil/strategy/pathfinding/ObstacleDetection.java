package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;

public class ObstacleDetection {

    // Pour des récifs de type Rectangle ou Polygone
    public static ArrayList<Segment> reefToSegments(VisibleEntitie reef){
        if (reef.getShape().getType().equals("polygone")){
            Point[] points = ((Polygone)reef.getShape()).getVertices();
            int size = points.length;
            return createSegments(points, size);

        } else { // Traitement des cercles
            Point[] points = rectangleToPoints((Circle)reef.getShape(), reef.getPosition() );
            int size = points.length;
            return createSegments(points, size);
        }
    }

    // Crée une forme de type losange (substitution temporaire pour le cercle)
    public static Point[] rectangleToPoints(Circle circle, Position pos){
        Point[] points = new Point[4];
        double r = circle.getRadius();
        points[0] = new Point(pos.getX() - r, pos.getY() - r);
        points[1] = new Point(pos.getX() - r, pos.getY() + r);
        points[2] = new Point(pos.getX() + r, pos.getY() + r);
        points[3] = new Point(pos.getX() + r, pos.getY() - r);
        return points;
    }

    public static ArrayList<Segment> createSegments(Point[] points, int size){
        ArrayList<Segment> resolution = new ArrayList<Segment>();
        for ( int i = 0; i<size-1; i++){
            resolution.add(new Segment(points[i].getX(),points[i].getY(),points[i+1].getX(),points[i+1].getY()));
        }
        resolution.add(new Segment(points[size-1].getX(),points[size-1].getY(),points[0].getX(),points[0].getY()));
        return resolution;
    }

    // Trouve l'origine pour le repère de la grille
    public static Point findOrigin(Point shipPosition, Point checkPointPosition){
        double minX = Math.min(shipPosition.getX(), checkPointPosition.getX());
        double minY = Math.min(shipPosition.getY(), checkPointPosition.getY());
        double margin = 50.0;
        return new Point(minX-margin,minY-margin);
    }

    public static GridCell[][] gridCreation(int x, int y, double sizeCell, Point origine){
        GridCell[][] grid = new GridCell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid[i][j] = new GridCell(new Point(
                        j*sizeCell + sizeCell/2 +origine.getX(),
                        i*sizeCell + sizeCell/2 + origine.getY()), sizeCell);
            }
        }
        return grid;
    }

    public static void gridProcess(GridCell[][] grid, ArrayList<VisibleEntitie> reefs){
        ArrayList<Segment> reefSegments = new ArrayList<Segment>();
        for (VisibleEntitie reef : reefs){
            reefSegments.addAll(reefToSegments(reef));
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].intersection(reefSegments);
            }
        }
    }

    // Couleurs pour affichage
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";

    // Affichage
    public static void main(String[] args) {
        GridCell[][] grid = gridCreation(10,10,10, new Point(0.0,0.0));
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isBlocked()){
                    System.out.print(ANSI_RED_BACKGROUND+ " x"+ANSI_RESET);
                } else System.out.print(ANSI_GREEN_BACKGROUND+ "  "+ANSI_RESET);
            }
            System.out.println(" ");
        }
        System.out.println(" ");
        System.out.println(" ");
        ArrayList<VisibleEntitie> recifs = new ArrayList<>();
        recifs.add(new Reef("reef", new Position(20.0, 10.0,0.0),
                new Polygone("polygone",0.0,
                        new Point[]{new Point(66.4,13), new Point(49.0, 33.7),
                                new Point(51.1, 50.0), new Point(89.7, 51.2)})
        ));
        gridProcess(grid, recifs);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isBlocked()) {
                    System.out.print(ANSI_RED_BACKGROUND + " x" + ANSI_RESET);
                } else System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
            }
            System.out.println(" ");
            //System.out.println(grid);
        }
    }
}