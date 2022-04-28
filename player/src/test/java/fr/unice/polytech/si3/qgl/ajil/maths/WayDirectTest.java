package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WayDirectTest {

    private Position positionCheckpoint;
    private Position bateau;

    private Ship ship;
    private Set<Reef> reefs;
    private Reef reef1;
    private Reef reef2;
    private Reef reefs3;

//String type, Position position, Shape shape
    @BeforeEach
    void setUp() {
        positionCheckpoint = new Position(5,0,0);
        bateau = new Position(0,0,0);
        //String type, int life, Position position, String name, Deck deck, List<Entity> entities, Shape shape
        ship = new Ship("ship",10,bateau,"IC&O",null,null,null);
        reefs.clear();
        reef1 = new Reef("rectangle",new Position(0,4,0),new Shape());

    }


    @Test
    void ligneDroiteAucunRecif(){
        boolean res = WayDirect.wayDirect(positionCheckpoint,ship,reefs);
        assertTrue(res);
    }
}