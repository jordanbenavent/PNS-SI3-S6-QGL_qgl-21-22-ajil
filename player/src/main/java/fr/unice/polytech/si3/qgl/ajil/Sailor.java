package fr.unice.polytech.si3.qgl.ajil;

public class Sailor {
    private int x;
    private int y;
    private int id;
    private String name;

    public Sailor(){}
    public Sailor(int x, int y, int id, String name){
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
