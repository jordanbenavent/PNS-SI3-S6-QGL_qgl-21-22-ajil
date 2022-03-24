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
    private Matrice<Cell> cellules = new Matrice<>();
    private static final double MARGE = 1.1;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPositionShip(Position positionShip) {
        this.positionShip = positionShip;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public void setBadCell(List<Cell> badCell) {
        this.badCell = badCell;
    }

    public void setCellules(Matrice<Cell> cellules) {
        this.cellules = cellules;
    }

    public Game getGame() {
        return game;
    }

    public Position getPositionShip() {
        return positionShip;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public List<Cell> getBadCell() {
        return badCell;
    }

    public Matrice<Cell> getCellules() {
        return cellules;
    }

    public CreateCell(Game game){
        this.game = game;
        this.positionShip = game.getShip().getPosition();
        this.checkpoint = game.getGoal().getCheckpoints().get(0);
        this.badCell = new ArrayList<>();
    }

    public void calculAllCell(){
        double xShip = positionShip.getX();
        double yShip = positionShip.getY();
        double xCheckpoint = checkpoint.getPosition().getX();
        double yCheckpoint = checkpoint.getPosition().getY();
        int xMax = (int) ((xCheckpoint-xShip) > 0 ? xCheckpoint + 100 : xShip + 100);
        int xMin = (int) ((xCheckpoint-xShip) < 0 ? xCheckpoint - 100 : xShip - 100);
        int yMax = (int) ((yCheckpoint-yShip) > 0 ? yCheckpoint + 100 : yShip + 100);
        int yMin = (int) ((yCheckpoint-yShip) < 0 ? yCheckpoint - 100 : yShip - 100);
        int iMatrice = 0;
        int jMatrice = 0;
        for(int i = xMin; i<=xMax; i+=50 ){
            for (int j = yMin; j<=yMax; j+=50){
                cellules.addElement(iMatrice,jMatrice,new Cell(i,j));
                jMatrice++;
            }
            iMatrice++;
            jMatrice=0;
        }
    }
}
