package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Deck;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class WayDirectTest {

    private Position positionCheckpoint;
    private Position bateau;
    private Deck deck;

    private Ship ship;
    private Set<Reef> reefs;
    private Reef reef1;
    private Reef reef2;
    private Shape shape;

    @BeforeEach
    void setUp() {
        reefs = new HashSet<>();
        bateau = new Position(0,0,0);

        shape = new Rectangle("rectangle",50,100,0);
        Deck deck = new Deck(50,50);
        positionCheckpoint = new Position(500,0,0);
        ship = new Ship("ship",10,bateau,"IC&O",deck,null,null);
        reef1 = new Reef("rectangle",new Position(0,400,0),shape);
        reef2 = new Reef("rectangle",new Position(100,0,0),shape);


    }


    @Test
    void ligneDroiteAucunRecif(){
        assertTrue(WayDirect.wayDirect(positionCheckpoint,ship,reefs));
    }

    @Test
    void recifTresLoin(){
        reefs.add(reef1);
        assertTrue(WayDirect.wayDirect(positionCheckpoint,ship,reefs));
    }

    @Test
    void obstacleEnFace(){
        reefs.add(reef2);
        assertFalse(WayDirect.wayDirect(positionCheckpoint,ship,reefs));
    }

    @Test
    void obstacleMinuscule(){
        shape = new Rectangle("rectangle",1,1,0);
        reef1 = new Reef("rectangle",new Position(200,0,0),shape);
        reefs.add(reef1);
        assertFalse(WayDirect.wayDirect(positionCheckpoint,ship,reefs));
    }

    @Test
    void obstacleLoinQuiDepasse(){
        shape = new Rectangle("rectangle",200,200,0);
        reef1 = new Reef("rectangle",new Position(200,150,0),shape);
        reefs.add(reef1);
        assertFalse(WayDirect.wayDirect(positionCheckpoint,ship,reefs));
    }


    @Test
    void obstacleLoinFinQuiDepasse(){
        shape = new Rectangle("rectangle",5,200,0);
        reef1 = new Reef("rectangle",new Position(200,150,0),shape);
        reefs.add(reef1);
        assertFalse(WayDirect.wayDirect(positionCheckpoint,ship,reefs));
    }

    @Test
    void obstacleLoinFinOrienteDemiTour() {
        shape = new Rectangle("rectangle", 5, 200, Math.PI / 2);
        reef1 = new Reef("rectangle", new Position(200, 150, 0), shape);
        reefs.add(reef1);
        assertTrue(WayDirect.wayDirect(positionCheckpoint, ship, reefs));
    }

    @Test
    void obstacleLoinFinOrientationPi4() {
        shape = new Rectangle("rectangle", 5, 160, Math.PI / 4);
        reef1 = new Reef("rectangle", new Position(200, 150, 0), shape);
        assertTrue(WayDirect.wayDirect(positionCheckpoint, ship, reefs));
    }

    @Test
    void obstacleLoinFinOrientationPi16() {
        shape = new Rectangle("rectangle", 5, 200, Math.PI/16);
        reef1 = new Reef("rectangle", new Position(200, 150, 0), shape);
        reefs.add(reef1);
        assertFalse(WayDirect.wayDirect(positionCheckpoint, ship, reefs));
    }

    @Test
    void cercleAuMilieu() {
        shape = new Circle("cercle",100);
        reef1 = new Reef("cercle", new Position(200, 0, 0), shape);
        reefs.add(reef1);
        assertFalse(WayDirect.wayDirect(positionCheckpoint, ship, reefs));
    }

    @Test
    void cercleDecaleMiRadius() {
        shape = new Circle("cercle",100);
        reef1 = new Reef("cercle", new Position(200, 50, 0), shape);
        reefs.add(reef1);
        assertFalse(WayDirect.wayDirect(positionCheckpoint, ship, reefs));
    }

    @Test
    void cercleLoin() {
        shape = new Circle("cercle",100);
        reef1 = new Reef("cercle", new Position(200, 200, 0), shape);
        reefs.add(reef1);
        assertFalse(WayDirect.wayDirect(positionCheckpoint, ship, reefs));
    }









}