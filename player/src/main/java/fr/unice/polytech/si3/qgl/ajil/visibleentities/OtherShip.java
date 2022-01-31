package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Shape;

public class OtherShip extends VisibleEntitie{
    private int life;

    OtherShip(VisibleEntities visibleEntitie, Position position, Shape shape, int life) {
        super(visibleEntitie, position, shape);
        this.life=life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
