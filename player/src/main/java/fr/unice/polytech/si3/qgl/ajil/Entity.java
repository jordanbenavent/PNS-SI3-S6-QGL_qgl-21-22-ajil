package fr.unice.polytech.si3.qgl.ajil;

public class Entity {
    private int x;
    private int y;
    private String type;

    public Entity(){}
    public Entity(int x, int y, String type, boolean openned){
        this.type = type;
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

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    // Donne la distance entre entité e
    public int getDist(Entity e){
        int deplacementX = e.getX() - this.x;
        int deplacementY = e.getY() - this.y;
        int res = deplacementX + deplacementY;
        return (res > 0) ? res : res * (-1) ;
    }

}
