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
    private int sX;
    private int sY;
    private int eX;
    private int eY;
    public static final int margin = 250;

    public int getsX() {
        return sX;
    }

    public int getsY() {
        return sY;
    }

    public int geteX() {
        return eX;
    }

    public int geteY() {
        return eY;
    }

    // Pour des récifs de type Rectangle ou Polygone
    public ArrayList<Segment> reefToSegments(VisibleEntitie reef){
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
    public Point[] rectangleToPoints(Circle circle, Position pos){
        Point[] points = new Point[4];
        double r = circle.getRadius();
        points[0] = new Point(pos.getX() - r, pos.getY() - r);
        points[1] = new Point(pos.getX() - r, pos.getY() + r);
        points[2] = new Point(pos.getX() + r, pos.getY() + r);
        points[3] = new Point(pos.getX() + r, pos.getY() - r);
        return points;
    }

    // Prend un ensemble de points du polygone et crée les segments correspondants
    public ArrayList<Segment> createSegments(Point[] points, int size){
        ArrayList<Segment> resolution = new ArrayList<Segment>();
        for ( int i = 0; i<size-1; i++){
            resolution.add(new Segment(points[i].getX(),points[i].getY(),points[i+1].getX(),points[i+1].getY()));
        }
        resolution.add(new Segment(points[size-1].getX(),points[size-1].getY(),points[0].getX(),points[0].getY()));
        return resolution;
    }

    // Trouve l'origine pour le repère de la grille
    public Point findOrigin(Point shipPosition, Point checkPointPosition){
        double minX = Math.min(shipPosition.getX(), checkPointPosition.getX());
        double minY = Math.min(shipPosition.getY(), checkPointPosition.getY());
        return new Point(minX-margin,minY-margin);
    }

    // Création de la grille imaginaire
    public GridCell[][] gridCreation(int x, int y, double sizeCell, Point origine, Position ship, Position target){
        GridCell[][] grid = new GridCell[x][y];
        boolean startFound = false;
        boolean endFound = false;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid[i][j] = new GridCell(new Point(
                        i*sizeCell + sizeCell/2 +origine.getX(),
                        j*sizeCell + sizeCell/2 + origine.getY()), sizeCell);
                if (!startFound){
                    startFound = grid[i][j].contains(ship);
                    if(startFound){
                        this.sX = i;
                        this.sY = j;
                    }
                }
                if (!endFound){
                    endFound = grid[i][j].contains(target);
                    if(endFound){
                        this.eX = i;
                        this.eY = j;
                    }
                }
            }
        }
        System.out.println("Start i trouvé : " + sX);
        System.out.println("Start j trouvé : " + sY);
        System.out.println("End i trouvé : " + eX);
        System.out.println("End j trouvé : " + eY);
        return grid;
    }

    // Traitement de la grille = grisement des cellules bloquantes
    public ArrayList<Point> gridProcess(GridCell[][] grid, ArrayList<VisibleEntitie> reefs){
        ArrayList<Segment> reefSegments = new ArrayList<Segment>();
        ArrayList<Point> blockedCells = new ArrayList<Point>();
        for (VisibleEntitie reef : reefs){
            reefSegments.addAll(reefToSegments(reef));
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].intersection(reefSegments);
                if (grid[i][j].isBlocked()){
                    blockedCells.add(new Point(i, j));
                }
            }
        }
        System.out.println("La taille de la liste : " + blockedCells.size());
        System.out.println("La taille de la liste reefSegments : " + reefSegments.size());
        return blockedCells; // La liste peut être vide s'il n'y a pas obstacles
    }
}
