package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.maths.CalculPoints;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AStarDeployment {
    private Game game;
    private double sizeCell;
    private Goal goal;
    private Ship ship;
    private Point origine;


    public AStarDeployment(Game game, double sizeCell){
        this.game = game;
        this.sizeCell = sizeCell;
        this.ship = this.game.getShip();
        this.goal = this.game.getGoal();
        this.origine = new Point(0,0);
    }

    public Point gridSizeXY(Point shipPosition, Point checkPointPosition, double sizeCell){
        double x = Math.abs(shipPosition.getX()) + Math.abs(checkPointPosition.getX());
        double y = Math.abs(shipPosition.getY()) + Math.abs(checkPointPosition.getY());
        return new Point( Math.ceil(x/sizeCell), Math.ceil(y/sizeCell) );
    }

    public ArrayList<Checkpoint> convertPositionToCheckpoint(ArrayList<Position> listePos){
        ArrayList<Checkpoint> res = new ArrayList<>();
        Position pos;
        Checkpoint nouv;
        Shape checkpointShape = new Circle("circle",sizeCell);
        for(int i=0;i<listePos.size();i++){
            pos = listePos.get(i);
            double a = (origine.getX()+ pos.getX()* sizeCell) +sizeCell/2;
            double b = (origine.getY()+ pos.getY()* sizeCell) +sizeCell/2;
            pos = new Position(a,b,0);
            nouv = new Checkpoint(pos,checkpointShape);
            res.add(nouv);
        }

        return res;

    }



    public Point posToPoint(Position pos){
        return new Point(pos.getX(), pos.getY());
    }

    public ArrayList<Checkpoint> deployment(){
        Point shipPoint = posToPoint(ship.getPosition());
        Point checkPoint = posToPoint(goal.getCheckpoints().get(0).getPosition());

        Point sizeXY = gridSizeXY(shipPoint, checkPoint, this.sizeCell); //nombre de i et j dans la grille
        origine = ObstacleDetection.findOrigin(shipPoint, checkPoint); // origine de la grille

        GridCell[][] grid = ObstacleDetection.gridCreation((int)sizeXY.getX(),(int)sizeXY.getY(),this.sizeCell,
                origine, ship.getPosition(), goal.getCheckpoints().get(0).getPosition());

        ArrayList<VisibleEntitie> mainList = new ArrayList<>(game.getReefs());
        ArrayList<VisibleEntitie> visibleReefs = CalculPoints.entitiesToEntitiesPolygone(mainList);

        int[][] cellsB = pointsVersTableau( ObstacleDetection.gridProcess(grid, visibleReefs));

        AStar astar = new AStar((int)sizeXY.getX(), (int)sizeXY.getY(), ObstacleDetection.startX, ObstacleDetection.startY,
                ObstacleDetection.endX,ObstacleDetection.endY, cellsB);

        return convertPositionToCheckpoint(astar.obtenirLeChemin());
    }



    public int[][] pointsVersTableau(ArrayList<Point> listPoints){
        int [][] res = new int[listPoints.size()][2];

        for(int i=0;i<listPoints.size();i++){
            res[i][0] = (int) listPoints.get(i).getX();
            res[i][1] = (int) listPoints.get(i).getY();
        }
        return res;
    }



}
