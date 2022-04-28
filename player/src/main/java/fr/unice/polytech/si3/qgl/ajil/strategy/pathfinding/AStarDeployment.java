package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.maths.CalculPoints;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;
import java.util.List;

public class AStarDeployment {
    private final Game game;
    private final double sizeCell;
    private final Goal goal;
    private final Ship ship;
    public List<String> LOGGER = Cockpit.LOGGER;
    private Point origine;


    public AStarDeployment(Game game, double sizeCell) {
        this.game = game;
        this.sizeCell = sizeCell;
        this.ship = this.game.getShip();
        this.goal = this.game.getGoal();
        this.origine = new Point(0, 0);
    }

    public double getSizeCell() {
        return sizeCell;
    }

    // Fournit la taille de la grille à créer
    public Point gridSizeXY(Point shipPosition, Point checkPointPosition, double sizeCell) {
        double x = Math.abs(shipPosition.getX() - checkPointPosition.getX());
        double y = Math.abs(shipPosition.getY() - checkPointPosition.getY());
        return new Point(Math.ceil(x / sizeCell) + 15, Math.ceil(y / sizeCell) + 15);
    }

    // Récupère la liste des points donnés par AStar et crée une liste des Checkpoints
    public ArrayList<Checkpoint> convertPositionToCheckpoint(ArrayList<Position> listePos) {
        ArrayList<Checkpoint> res = new ArrayList<>();
        Position pos;
        Checkpoint nouv;
        Shape checkpointShape = new Circle("circle", sizeCell / 20);
        for (int i = 0; i < listePos.size(); i += 2) {
            pos = listePos.get(i);
            double a = (origine.getX() + pos.getX() * sizeCell) + sizeCell / 20;
            double b = (origine.getY() + pos.getY() * sizeCell) + sizeCell / 20;
            pos = new Position(a, b, 0);
            nouv = new Checkpoint(pos, checkpointShape);
            res.add(nouv);
        }

        return res;

    }


    public Point posToPoint(Position pos) {
        return new Point(pos.getX(), pos.getY());
    }

    public ArrayList<Checkpoint> deployment() {
        Point shipPoint = posToPoint(ship.getPosition());
        Point checkPoint = posToPoint(goal.getCheckpoints().get(0).getPosition());

        ObstacleDetection obstacleDetection = new ObstacleDetection();

        Point sizeXY = gridSizeXY(shipPoint, checkPoint, this.sizeCell); //nombre de i et j dans la grille
        origine = obstacleDetection.findOrigin(shipPoint, checkPoint); // origine de la grille

        GridCell[][] grid = obstacleDetection.gridCreation((int) sizeXY.getX(), (int) sizeXY.getY(), this.sizeCell,
                origine, ship.getPosition(), goal.getCheckpoints().get(0).getPosition());

        List<VisibleEntitie> mainList = new ArrayList<>(game.getReefs());
        LOGGER.add("on a nb recifs" + mainList.size());
        List<VisibleEntitie> visibleReefs = CalculPoints.entitiesToEntitiesPolygone(mainList, 0);

        int[][] cellsB = pointsVersTableau(obstacleDetection.gridProcess(grid, (ArrayList<VisibleEntitie>) visibleReefs));

        LOGGER.add("" + cellsB.length);
        System.out.println((int) sizeXY.getX());
        System.out.println((int) sizeXY.getY());

        AStar astar = new AStar((int) sizeXY.getX(), (int) sizeXY.getY(), obstacleDetection.getsX(), obstacleDetection.getsY(),
                obstacleDetection.geteX(), obstacleDetection.geteY(), cellsB);

        System.out.println("StartX: " + obstacleDetection.getsX() + ", StartY: " + obstacleDetection.getsY());
        System.out.println("EndX: " + obstacleDetection.geteX() + ", EndY: " + obstacleDetection.geteY());

        LOGGER.add("" + convertPositionToCheckpoint(astar.obtenirLeChemin()).size());

        return convertPositionToCheckpoint(astar.obtenirLeChemin());
    }


    public int[][] pointsVersTableau(ArrayList<Point> listPoints) {
        int[][] res = new int[listPoints.size()][2];

        for (int i = 0; i < listPoints.size(); i++) {
            res[i][0] = (int) listPoints.get(i).getX();
            res[i][1] = (int) listPoints.get(i).getY();
        }
        return res;
    }


}
