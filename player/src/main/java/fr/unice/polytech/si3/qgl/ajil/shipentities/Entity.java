package fr.unice.polytech.si3.qgl.ajil.shipentities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.Sailor;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value= Rudder.class, name = "rudder"),
        @JsonSubTypes.Type(value= Watch.class, name = "watch"),
        @JsonSubTypes.Type(value= Canon.class, name = "canon"),
        @JsonSubTypes.Type(value = OarEntity.class, name = "oar"),
        @JsonSubTypes.Type(value = Sail.class, name = "sail")
})
public abstract class Entity {
    private int x;
    private int y;
    private String type;

    public Entity(){}
    public Entity(int x, int y, String type){
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
    public int getDist(Sailor sailor){
        int deplacementX = sailor.getX() - this.x;
        int deplacementY = sailor.getY() - this.y;
        int res = deplacementX + deplacementY;
        return (res > 0) ? res : res * (-1) ;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                '}';
    }
}
