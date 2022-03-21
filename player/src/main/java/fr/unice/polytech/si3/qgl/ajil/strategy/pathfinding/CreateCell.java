package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Position;

import java.util.ArrayList;
import java.util.List;

public class CreateCell {

    private Game game;
    private Position positionShip;
    private List<Cell> badCell;

    public CreateCell(Game game){
        this.game = game;
        this.positionShip = game.getShip().getPosition();
        this.badCell = new ArrayList<>();
    }
    
}
