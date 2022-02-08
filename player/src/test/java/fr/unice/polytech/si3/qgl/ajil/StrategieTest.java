package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;
import fr.unice.polytech.si3.qgl.ajil.shipentities.OarEntity;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class StrategieTest {
    Strategie strategie;
    Game jeu;
    Ship ship;
    Checkpoint checkpoint;
    Checkpoint checkpoint2;
    Checkpoint checkpoint3;

    @BeforeEach
    void setUp() {
        ship = new Ship("ship", 100,
                new Position(0.0, 0.0, 0.0), "name",
                new Deck(2, 5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5, 5, 5));
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        ArrayList<Sailor> sailors = new ArrayList<>();
        jeu = new Game(
                new Goal("regatte",checkpoints),
                ship,
                sailors,
                4
        );
        strategie = new Strategie(jeu);
        checkpoint = new Checkpoint(new Position(0,0,0), new Circle("circle", 1));
        checkpoints.add(checkpoint);
        Sailor sailor = new Sailor(1,1,0,"sailor1");
        Sailor sailor2 = new Sailor(1,1,0,"sailor2");
        sailors.add(sailor); sailors.add(sailor2);
        checkpoint2 = new Checkpoint(new Position(1,7,0), new Circle("circle", 4));
        checkpoint3 = new Checkpoint(new Position(-1,0,1), new Circle("circle", 1));
        strategie = new Strategie(jeu);
    }

    @Test
    void placerSurRamesTest() {
        ship.setDeck(new Deck(2, 5));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(0,3,"oar")); // ( 0 , 3 )
        entities.add(new OarEntity(0,1,"oar")); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        Assertions.assertEquals(0, strategie.getListActions().get(0).getSailorId());
        //Assertions.assertEquals(1, strategie.getListActions().get(0).getYdistance());
        //Assertions.assertEquals(-1, strategie.getListActions().get(1).getXdistance());
    }

    @Test
    void whereAreSailors() {
        ship.setDeck(new Deck(2, 5));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(0,0,"oar")); // ( 0 , 3 )
        entities.add(new OarEntity(0,1,"oar")); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        Assertions.assertEquals(1, strategie.getLeftSailors().size());
        Assertions.assertEquals(1, strategie.getRightSailors().size());
    }

    @Test
    void whereAreSailors2() {
        ship.setDeck(new Deck(2, 5));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 1, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(0,1,"oar")); // ( 0 , 3 )
        entities.add(new OarEntity(0,1,"oar")); // ( 0 , 1 )
        entities.add(new OarEntity(0,1,"oar")); // ( 0 , 1 )
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        Assertions.assertEquals(0, strategie.getLeftSailors().size());
        Assertions.assertEquals(3, strategie.getRightSailors().size());
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

    @Test
    void positionCheckpointTest(){
        /*
        Position checkpoint_position1 = new Position(60, 90, 0);
        Position checkpoint_position2 = new Position(20, 100, 0);
        Position ship_position = new Position(40, 50, 0);
        checkpoint.setPosition(checkpoint_position1);
        checkpoint2.setPosition(checkpoint_position2);
        ship.setPosition(ship_position);
        jeu.setShip(ship);
        Assertions.assertEquals(false, strategie.positionCheckpoint(checkpoint));
        Assertions.assertEquals(true, strategie.positionCheckpoint(checkpoint2));

         */
    }

    @Test
    void predictionAngleTourSuivantTest(){

    }



}