package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.maths.Segment;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;
import java.util.List;

public class ObstacleDetection {
    private int sX;
    private int sY;
    private int eX;
    private int eY;
    public static final int MARGIN = 2800;
    public static final int MARGIN_CIRCLE = 60;

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
    public List<Segment> reefToSegments(VisibleEntitie reef) {
        if (reef.getShape().getType().equals("polygone")) {
            Point[] points = ((Polygone) reef.getShape()).getVertices();
            int size = points.length;
            return createSegments(points, size);

        } else { // Traitement des cercles
            Point[] points = rectangleToPoints((Circle) reef.getShape(), reef.getPosition());
            int size = points.length;
            return createSegments(points, size);
        }
    }

    // Crée une forme de type octogone (simplification de circle)
    public Point[] rectangleToPoints(Circle circle, Position pos) {
        Point[] points = new Point[8];
        double r = circle.getRadius() + MARGIN_CIRCLE;
        double r45 = 0.7 * r;
        points[0] = new Point(pos.getX() - r, pos.getY());
        points[1] = new Point(pos.getX() - r45, pos.getY() - r45);
        points[2] = new Point(pos.getX(), pos.getY() - r);
        points[3] = new Point(pos.getX() + r45, pos.getY() - r45);
        points[4] = new Point(pos.getX() + r, pos.getY());
        points[5] = new Point(pos.getX() + r45, pos.getY() + r45);
        points[6] = new Point(pos.getX(), pos.getY() + r);
        points[7] = new Point(pos.getX() - r45, pos.getY() + r45);
        return points;
    }

    // Prend un ensemble de points du polygone et crée les segments correspondants
    public List<Segment> createSegments(Point[] points, int size) {
        List<Segment> resolution = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            Point a = new Point(points[i].getX(), points[i].getY());
            Point b = new Point(points[i + 1].getX(), points[i + 1].getY());
            resolution.add(new Segment(a, b));
        }
        Point oldA = new Point(points[size - 1].getX(), points[size - 1].getY());
        Point oldB = new Point(points[0].getX(), points[0].getY());
        resolution.add(new Segment(oldA, oldB));
        return resolution;
    }

    // Trouve l'origine pour le repère de la grille
    public Point findOrigin(Point shipPosition, Point checkPointPosition) {
        double minX = Math.min(shipPosition.getX(), checkPointPosition.getX());
        double minY = Math.min(shipPosition.getY(), checkPointPosition.getY());
        return new Point(minX - MARGIN, minY - MARGIN);
    }

    // Création de la grille imaginaire
    public GridCell[][] gridCreation(int x, int y, double sizeCell, Point origine, Position ship, Position target) {
        GridCell[][] grid = new GridCell[x][y];
        boolean startFound = false;
        boolean endFound = false;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid[i][j] = new GridCell(new Point(i * sizeCell + sizeCell / 2 + origine.getX(), j * sizeCell + sizeCell / 2 + origine.getY()), sizeCell);
                if (!startFound) {
                    startFound = grid[i][j].contains(ship);
                    if (startFound) {
                        this.sX = i;
                        this.sY = j;
                    }
                }
                if (!endFound) {
                    endFound = grid[i][j].contains(target);
                    if (endFound) {
                        this.eX = i;
                        this.eY = j;
                    }
                }
            }
        }
        return grid;
    }

    // Traitement de la grille = grisement des cellules bloquantes
    public List<Point> gridProcess(GridCell[][] grid, List<VisibleEntitie> reefs) {
        List<Segment> reefSegments = new ArrayList<>();
        List<Point> blockedCells = new ArrayList<>();
        for (VisibleEntitie reef : reefs) {
            reefSegments.addAll(reefToSegments(reef));
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].intersection(reefSegments);
                if (grid[i][j].isBlocked()) {
                    blockedCells.add(new Point(i, j));
                }
            }
        }
        return blockedCells; // La liste peut être vide s'il n'y a pas obstacles
    }
}
