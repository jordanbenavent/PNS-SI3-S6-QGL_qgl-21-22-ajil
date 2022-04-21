package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

/**
 * Classe fille de VisibleEntitie représentant un autre bateau (un ennemi)
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class OtherShip extends VisibleEntitie {
    private int life;

    public OtherShip() {
        setType("ship");
    }

    public OtherShip(String type, Position position, Shape shape, int life) {
        super(type, position, shape, VisibleEntities.SHIP);
        this.life = life;
    }

    /**
     * @return la vie du bateau
     */
    public int getLife() {
        return life;
    }

    /**
     * Modifie la vie du bateau
     * @param life
     */
    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public VisibleEntitie copy() {
        return new OtherShip(this.getType(), this.getPosition(), this.getShape(), this.life);
    }
}
