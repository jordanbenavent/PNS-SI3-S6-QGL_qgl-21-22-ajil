package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Goal;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;

public class AStarDeployment {
    private Game game;
    private double sizeCell;
    private Goal goal;
    private Ship ship;


    public AStarDeployment(Game game, double sizeCell){
        this.game = game;
        this.sizeCell = sizeCell;
        this.ship = this.game.getShip();
        this.goal = this.game.getGoal();
    }

    public Point gridSizeXY(Point shipPosition, Point checkPointPosition, double sizeCell){
        double x = Math.abs(shipPosition.getX()) + Math.abs(checkPointPosition.getX());
        double y = Math.abs(shipPosition.getY()) + Math.abs(checkPointPosition.getY());
        return new Point( Math.ceil(x/sizeCell), Math.ceil(y/sizeCell) );
    }



    public Point posToPoint(Position pos){
        return new Point(pos.getX(), pos.getY());
    }

    public void deployment(){
        Point shipPoint = posToPoint(ship.getPosition());
        Point checkPoint = posToPoint(goal.getCheckpoints().get(0).getPosition());

        Point sizeXY = gridSizeXY(shipPoint, checkPoint, this.sizeCell); //nombre de i et j dans la grille
        Point origineGrid = ObstacleDetection.findOrigin(shipPoint, checkPoint); // origine de la grille

        GridCell[][] grid = ObstacleDetection.gridCreation((int)sizeXY.getX(),(int)sizeXY.getY(),this.sizeCell,origineGrid);

    }
}
