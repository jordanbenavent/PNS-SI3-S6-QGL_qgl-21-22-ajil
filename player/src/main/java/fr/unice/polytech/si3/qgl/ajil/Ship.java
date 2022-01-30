package fr.unice.polytech.si3.qgl.ajil;

import java.util.ArrayList;

public class Ship {
    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private ArrayList<Entity> entities;
    private ArrayList<Sailor> sailors;
    private ShipShape shape;
    private int shipCount;

    public Ship(){}

    public Ship(String type, int life, Position position, String name, Deck deck, ArrayList<Entity> entities, ArrayList<Sailor> sailors, ShipShape shape, int shipCount) {
        this.type = type;
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.sailors = sailors;
        this.shape = shape;
        this.shipCount = shipCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public ArrayList<Sailor> getSailors() {
        return sailors;
    }

    public void setSailors(ArrayList<Sailor> sailors) {
        this.sailors = sailors;
    }

    public ShipShape getShape() {
        return shape;
    }

    public void setShape(ShipShape shape) {
        this.shape = shape;
    }

    public int getShipCount() {
        return shipCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }
}
