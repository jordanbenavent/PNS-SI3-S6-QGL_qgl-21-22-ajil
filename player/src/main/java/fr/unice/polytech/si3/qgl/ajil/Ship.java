package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Ship {
    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private ArrayList<Entity> entities;
    private Shape shape;

    ArrayList<Entity> leftOars = new ArrayList<>();
    ArrayList<Entity> rightOars = new ArrayList<>();

    public Ship() {
    }

    public Ship(String type, int life, Position position, String name, Deck deck, ArrayList<Entity> entities, Shape shape) {
        this.type = type;
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shape = shape;
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

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public ArrayList<Entity> getOars() {
        ArrayList<Entity> res = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getType().equals("oar")) {
                res.add(e);
            }
        }
        return res;
    }

    public void placeOars() {
        ArrayList<Entity> oars = getOars();
        for (Entity oar : oars) {
            if (oar.getType().equals("oar")) {
                if (oar.getY() == 0) {
                    leftOars.add(oar);
                } else {
                    rightOars.add(oar);
                }
            }
        }
    }

    public Set<Double> getTurnRange() {
        Set<Double> range = new HashSet<>();
        int size = getOars().size();
        range.add(0.0);
        for (int i = 0; i <= size / 2; i++) {
            range.add(-Math.PI * i / size);
        }
        for (int i = 0; i <= size / 2; i++) {
            range.add(Math.PI * i / size);
        }
        range.remove(-0.0);
        return range;
    }
}
