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

/**
 * Classe mère Entity regroupant les méthodes de base d'une entité, elle est déclarée abstract car on ne déclarera
 * jamais une entité "par défaut".
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

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

    /**
     * @return la coordonnée x de l'entité
     */
    public int getX() {
        return x;
    }

    /**
     * Modifie la coordonnée x de l'entité
     * @param x
     */
    void setX(int x) {
        this.x = x;
    }

    /**
     * @return la coordonnée y de l'entité
     */
    public int getY() {
        return y;
    }

    /**
     * Modifie la coordonnée y de l'entité
     * @param y
     */
    void setY(int y) {
        this.y = y;
    }

    /**
     * @return le type de l'entité (si c'est une rame, une voile...)
     */
    public String getType() {
        return type;
    }

    /**
     * Modifie le type de l'entité
     * @param type
     */
    void setType(String type) {
        this.type = type;
    }

    /**
     * Calcul la distance entre l'entité et un marin
     * @param sailor
     * @return la distance entre l'entité this et le marin
     */
    public int getDist(Sailor sailor){
        int deplacementX = sailor.getX() - this.x;
        int deplacementY = sailor.getY() - this.y;
        int res = deplacementX + deplacementY;
        return (res > 0) ? res : res * (-1) ;
    }

    /**
     * @return un string composé des coordonnées de l'entité ainsi que de son type
     */
    @Override
    public String toString() {
        return "Entity{" +
                "x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                '}';
    }
}
