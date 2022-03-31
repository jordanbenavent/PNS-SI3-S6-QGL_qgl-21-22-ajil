package fr.unice.polytech.si3.qgl.ajil.shape;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Circle.class, name = "circle"),
        @JsonSubTypes.Type(value=Rectangle.class, name = "rectangle"),
        @JsonSubTypes.Type(value=Polygone.class, name = "polygon")
})

/**
 * Classe mère Shape représentant une forme
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Shape {

    private String type;

    public Shape(){}

    public Shape(String type){
        this.type = type;
    }

    /**
     * @return le type de la forme
     */
    public String getType() {
        return type;
    }

    /**
     * Modifie le type de la forme
     * @param type
     */
    void setType(String type) {
        this.type = type;
    }

    /**
     * @return un string composé du type de la forme
     */
    @Override
    public String toString() {
        return "Shape{" +
                "type='" + type + '\'' +
                '}';
    }

    public double getOrientation(){
        return 0;
    }
}
