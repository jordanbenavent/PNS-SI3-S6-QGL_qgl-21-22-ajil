package fr.unice.polytech.si3.qgl.ajil;

import java.util.ArrayList;

public class Game {
    private Goal goal;
    private Ship ship;
    private ArrayList<Sailor> sailors;
    private int shipCount;

    public Game(){}

    public Game(Goal goal, Ship ship, ArrayList<Sailor> sailors, int shipCount) {
        this.goal = goal;
        this.ship = ship;
        this.sailors = sailors;
        this.shipCount = shipCount;
    }

    public Goal getGoal() {
        return goal;
    }

    void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Ship getShip() {
        return ship;
    }

    void setShip(Ship ship) {
        this.ship = ship;
    }

    public ArrayList<Sailor> getSailors() {
        return sailors;
    }

    void setSailors(ArrayList<Sailor> sailors) {
        this.sailors = sailors;
    }

    public int getShipCount() {
        return shipCount;
    }

    void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }
}
