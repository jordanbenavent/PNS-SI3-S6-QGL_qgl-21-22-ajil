package fr.unice.polytech.si3.qgl.ajil.shipentities;

/**
 * Classe fille de Entity représentant une rame
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class OarEntity extends Entity {

    public OarEntity(){
        setType("oar");
    }

    public OarEntity(int x, int y,String type){
        super(x, y, type);
    }
}
