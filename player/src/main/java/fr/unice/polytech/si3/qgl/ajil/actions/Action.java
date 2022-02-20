package fr.unice.polytech.si3.qgl.ajil.actions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * Classe mère Action regroupant les méthodes de base d'une action, elle est déclarée abstract car on ne déclarera
 * jamais une action "par défaut".
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public abstract class Action {

    private int sailorId;
    private Actions type;

    Action (int sailorId, Actions type){
        this.sailorId = sailorId;
        this.type = type;
    }

    /**
     * @return l'id du marin qui fait l'action
     */
    public int getSailorId() {
        return sailorId;
    }

    /**
     * @return le type de l'action
     */
    public Actions getType() {
        return type;
    }

    /**
     * Modifie l'id du marin qui fait l'action
     * @param sailorId
     */
    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }

    /**
     * Modifie le type de l'action
     * @param type
     */
    public void setType(Actions type) {
        this.type = type;
    }

}
