package fr.unice.polytech.si3.qgl.ajil.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Classe fille de Shape représentant un cercle
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Circle extends Shape{

    private double radius;

    public Circle(){
        setType("circle");
    }

    public Circle( String type, double radius){
        super(type);
        this.radius = radius;
    }

    /**
     * @return le rayon du cercle
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Modifie le rayon du cercle
     * @param radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @return un string composé du rayon du cercle
     */
    @Override
    public String toString() {
        return super.toString() + "Circle{" +
                "radius=" + radius +
                '}';
    }
}
