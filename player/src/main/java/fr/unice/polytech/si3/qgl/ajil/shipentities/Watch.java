package fr.unice.polytech.si3.qgl.ajil.shipentities;

/**
 * Classe fille de Entity représentant la vigie
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Watch extends Entity{

    public Watch(){
        setType("watch");
    }

    public Watch(int x, int y, String type){
        super(x, y, type);
    }
}
