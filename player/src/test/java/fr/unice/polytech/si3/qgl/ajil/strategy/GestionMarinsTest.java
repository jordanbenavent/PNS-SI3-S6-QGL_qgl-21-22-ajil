package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;
import fr.unice.polytech.si3.qgl.ajil.shipentities.OarEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXNotRecognizedException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GestionMarinsTest {

    Game jeu;
    Ship ship;
    Strategie strategie;
    Checkpoint checkpoint;
    Checkpoint checkpoint2;
    Checkpoint checkpoint3;
    GestionMarins gestionMarins;

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
        checkpoint = new Checkpoint(new Position(5,5,0), new Circle("circle", 1));
        checkpoints.add(checkpoint);
        Sailor sailor = new Sailor(1,1,0,"sailor1");
        Sailor sailor2 = new Sailor(1,1,0,"sailor2");
        sailors.add(sailor); sailors.add(sailor2);
        checkpoint2 = new Checkpoint(new Position(1,7,0), new Circle("circle", 4));
        checkpoint3 = new Checkpoint(new Position(-1,0,1), new Circle("circle", 1));
        strategie = new Strategie(jeu);
        gestionMarins = strategie.getGestionMarins();
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

        gestionMarins.placerSurRames();

        Assertions.assertEquals(0, strategie.getListActions().get(0).getSailorId());
        Assertions.assertEquals(1, ((Moving) strategie.getListActions().get(0)).getYdistance());
        Assertions.assertEquals(-1,((Moving)strategie.getListActions().get(1)).getXdistance());
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

        gestionMarins.whereAreSailors();

        Assertions.assertEquals(1, gestionMarins.getLeftSailors().size());
        Assertions.assertEquals(1, gestionMarins.getRightSailors().size());
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
        Assertions.assertEquals(0, gestionMarins.getLeftSailors().size());
        Assertions.assertEquals(3, gestionMarins.getRightSailors().size());
    }

}