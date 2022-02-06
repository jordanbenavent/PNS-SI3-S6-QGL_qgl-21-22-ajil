package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StrategieTest {
    Strategie strategie;
    Game jeu;
    Ship ship;
    Checkpoint checkpoint;
    Checkpoint checkpoint2;
    Checkpoint checkpoint3;

    @BeforeEach
    void setUp() {
        ship = new Ship();
        jeu = new Game();
        checkpoint = new Checkpoint();
        checkpoint2 = new Checkpoint();
        checkpoint3 = new Checkpoint();
        strategie = new Strategie(jeu);
    }

    @Test
    void placerSurRamesTest() {
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Entity(0,3,"oar",false)); // ( 0 , 3 )
        entities.add(new Entity(0,1,"oar",false)); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        Assertions.assertEquals(0, strategie.getListActions().get(0).getSailorId());
        Assertions.assertEquals(1, strategie.getListActions().get(0).getYdistance());
        Assertions.assertEquals(-1, strategie.getListActions().get(1).getXdistance());
    }

    @Test
    void distanceTest(){
        Position checkpoint_position = new Position(60, 90, 0);
        Position ship_position = new Position(60, 50, 0);
        checkpoint.setPosition(checkpoint_position);
        ship.setPosition(ship_position);
        Assertions.assertEquals(40, strategie.distance(checkpoint, ship));
        ship.getPosition().setX(50);
        Assertions.assertEquals(10 * Math.sqrt(17), strategie.distance(checkpoint, ship));
    }

    @Test
    void checkpointPlusProcheTest(){
        // Cas 1: le premier checkpoint de la liste est le plus proche
        Position checkpoint_position1 = new Position(60, 90, 0);
        Position checkpoint_position2 = new Position(60, 100, 0);
        Position ship_position = new Position(60, 50, 0);
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        checkpoints.add(checkpoint);
        checkpoints.add(checkpoint2);
        checkpoint.setPosition(checkpoint_position1);
        checkpoint2.setPosition(checkpoint_position2);
        ship.setPosition(ship_position);
        Assertions.assertEquals(checkpoint, strategie.checkpointPlusProche(checkpoints, ship));

        // Cas 2: le dernier checkpoint de la liste est le plus proche
        Position checkpoint_position3 = new Position(60, 60, 0);
        checkpoint3.setPosition(checkpoint_position3);
        checkpoints.add(checkpoint3);
        Assertions.assertEquals(checkpoint3, strategie.checkpointPlusProche(checkpoints, ship));
    }




}