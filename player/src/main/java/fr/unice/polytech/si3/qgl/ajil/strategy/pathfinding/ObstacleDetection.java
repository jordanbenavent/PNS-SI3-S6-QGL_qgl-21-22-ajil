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
        double margin = 250.0;
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
                        i*sizeCell + sizeCell/2 +origine.getY(),
                        j*sizeCell + sizeCell/2 + origine.getX()), sizeCell);
                System.out.println("startfound: "+ startFound);
                System.out.println("endfound: "+ endFound);
                System.out.println(grid[i][j]);
                System.out.println("i : " + i+ " j :" + j);
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
        return blockedCells;
    }

    // Couleurs pour affichage
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";

    // Affichage
    public static void main(String[] args) {
        ObstacleDetection obstacleDetection = new ObstacleDetection();
        GridCell[][] grid = obstacleDetection.gridCreation(10,10,10, new Point(0.0,0.0),
                new Position(0.0,0.0,0.0),new Position(1.0,1.0,0.0));
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
        obstacleDetection.gridProcess(grid, recifs);
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
