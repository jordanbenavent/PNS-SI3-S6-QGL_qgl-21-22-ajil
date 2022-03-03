package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;
import fr.unice.polytech.si3.qgl.ajil.shipentities.OarEntity;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Rudder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GestionMarinsTest {

    Game jeu;
    Ship ship;
    Strategy strategy;
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
                new Goal("regatte", checkpoints),
                ship,
                sailors,
                4
        );
        checkpoint = new Checkpoint(new Position(5, 5, 0), new Circle("circle", 1));
        checkpoints.add(checkpoint);
        Sailor sailor = new Sailor(1, 1, 0, "sailor1");
        Sailor sailor2 = new Sailor(1, 1, 0, "sailor2");
        sailors.add(sailor);
        sailors.add(sailor2);
        checkpoint2 = new Checkpoint(new Position(1, 7, 0), new Circle("circle", 4));
        checkpoint3 = new Checkpoint(new Position(-1, 0, 1), new Circle("circle", 1));
        strategy = new Strategy(jeu);
        gestionMarins = strategy.getGestionMarins();
    }

    @Test
    void placerSurRamesTest() {
        ship.setDeck(new Deck(3, 4));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 1, 0, "Sailor 0")); // ( 0 , 1 ) LEFT SAILOR
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 ) RIGHT SAILOR
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(3, 0, "oar")); // ( 3 , 0 ) LEFT OAR
        entities.add(new OarEntity(0, 2, "oar")); // ( 0 , 2 ) RIGHT OAR
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategy.getActions();
        Assertions.assertEquals(0, strategy.getListActions().get(0).getSailorId());
        Assertions.assertEquals(-1, ((Moving) strategy.getListActions().get(0)).getYdistance());
        Assertions.assertEquals(-1, ((Moving) strategy.getListActions().get(1)).getXdistance());
    }

    @Test
    void placerSurRamesTest2() {
        ship.setDeck(new Deck(3, 10));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(3, 0, 0, "Sailor 0")); // ( 3 , 0 ) LEFT SAILOR
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 ) RIGHT SAILOR
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(3, 0, "oar")); // ( 3 , 0 ) LEFT OAR
        entities.add(new OarEntity(7, 2, "oar")); // ( 7 , 2 ) RIGHT OAR
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategy.getActions();
        Assertions.assertEquals(1, strategy.getListActions().get(0).getSailorId());
        Assertions.assertEquals(2, ((Moving) strategy.getListActions().get(0)).getXdistance());
    }

    @Test
    void repartirLesMarins() {
        ship.setDeck(new Deck(2, 5));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        jeu.setShip(ship);
        gestionMarins.repartirLesMarins();
        Assertions.assertEquals(1, gestionMarins.getLeftSailors().size());
        Assertions.assertEquals(1, gestionMarins.getRightSailors().size());
    }

    @DisplayName("Attribuer Marin qui est dans range de 5")
    @Test
    void attribuerBarreurTest1() {
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(3, 3, 0, "Sailor 0")); // ( 3 , 3 )
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(0, 1, 2, "Sailor 2"));
        sailors.add(new Sailor(0, 2, 3, "Sailor 3"));
        sailors.add(new Sailor(1, 1, 4, "Sailor 4"));
        sailors.add(new Sailor(1, 3, 5, "Sailor 5"));
        sailors.add(new Sailor(2, 1, 6, "Sailor 6"));
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Rudder(3,4,"rudder"));
        entities.add(new OarEntity(0,0,"oar"));
        entities.add(new OarEntity(1,0,"oar"));
        entities.add(new OarEntity(2,0,"oar"));
        entities.add(new OarEntity(0,4,"oar"));
        entities.add(new OarEntity(1,4,"oar"));
        entities.add(new OarEntity(2,4,"oar"));
        ship.setEntities(entities);
        gestionMarins.attribuerBarreur();
        gestionMarins.repartirLesMarins();
        Assertions.assertEquals(0,gestionMarins.getBarreur().getId());
        Assertions.assertEquals(0, strategy.getListActions().get(0).getSailorId());
        Assertions.assertEquals(1, ((Moving) strategy.getListActions().get(0)).getYdistance());
        Assertions.assertEquals(3, gestionMarins.getLeftSailors().size());
        Assertions.assertEquals(3, gestionMarins.getRightSailors().size());
        Assertions.assertTrue(gestionMarins.isPlacementBarreur());
    }

    @DisplayName("Attribuer Marin qui est hors son range de 5")
    @Test
    void attribuerBarreurTest2(){
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 4, 0, "Sailor 0")); // ( 0 , 4 )
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Rudder(6,4,"rudder"));
        ship.setEntities(entities);
        gestionMarins.attribuerBarreur();
        Assertions.assertEquals(0,gestionMarins.getBarreur().getId());
        Assertions.assertEquals(0, strategy.getListActions().get(0).getSailorId());
        Assertions.assertEquals(2, ((Moving) strategy.getListActions().get(0)).getXdistance());
        Assertions.assertFalse(gestionMarins.isPlacementBarreur());
    }
}