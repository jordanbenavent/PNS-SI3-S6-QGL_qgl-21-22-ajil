package fr.unice.polytech.si3.qgl.ajil;

public class Checkpoint {
    private Position position;
    private Shape shape;

    public Checkpoint(){}
    public Checkpoint(Position position, Shape shape){
        this.position = position;
        this.shape = shape;
    }

    public Position getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
