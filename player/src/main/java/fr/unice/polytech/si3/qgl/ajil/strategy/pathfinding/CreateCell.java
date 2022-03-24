package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Checkpoint;
import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.maths.Matrice;

import java.util.ArrayList;
import java.util.List;

public class CreateCell {

    private Game game;
    private Position positionShip;
    private Checkpoint checkpoint;
    private List<Cell> badCell;
    private static final double MARGE = 1.1;

    public CreateCell(Game game){
        this.game = game;
        this.positionShip = game.getShip().getPosition();
        this.checkpoint = game.getGoal().getCheckpoints().get(0);
        this.badCell = new ArrayList<>();
    }

    public void calculAllCell(){
        Matrice<Cell> cellules = new Matrice<>();
        double xShip = positionShip.getX();
        double yShip = positionShip.getY();
        double xCheckpoint = checkpoint.getPosition().getX();
        double yCheckpoint = checkpoint.getPosition().getY();
        double differenceX = (xCheckpoint-xShip)*MARGE;
        double differenceY = (yCheckpoint-yShip)*MARGE;
        int xMax = (int) ((xCheckpoint-xShip) > 0 ? xCheckpoint + 10 : xShip + 10);
        int xMin = (int) ((xCheckpoint-xShip) < 0 ? xCheckpoint - 10 : xShip - 10);
        int yMax = (int) ((yCheckpoint-yShip) > 0 ? yCheckpoint + 10 : yShip + 10);
        int yMin = (int) ((yCheckpoint-yShip) < 0 ? yCheckpoint - 10 : yShip - 10);
        int iMatrice = 0;
        int jMatricce = 0;
        for(int i = xMin; i<xMax; i+=50 ){
            for (int j = yMin; i<yMax; i+=50){
                cellules.addElement(iMatrice,jMatricce,new Cell(i,j));
                jMatricce++;
            }
            iMatrice++;
            jMatricce=0;
        }
    }
}
