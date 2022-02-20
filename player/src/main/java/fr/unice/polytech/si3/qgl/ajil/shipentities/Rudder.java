package fr.unice.polytech.si3.qgl.ajil.shipentities;

/**
 * Classe fille de Entity représentant le gouvernail
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Rudder extends Entity{

    public Rudder(){
        setType("rudder");
    }

    public Rudder( int x, int y, String type){
        super(x, y,type);
    }
}
