package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.actions.Oar;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shipentities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GestionMarinsTest {

    Game jeu;
    Ship ship;
    Strategy strategy;
    GestionMarins gestionMarins;
    ArrayList<Sailor> sailors;
    Checkpoint checkpoint;


    @BeforeEach
    void setUp() {
        ship = new Ship("ship", 100, new Position(0.0, 0.0, 0.0), "name", new Deck(2, 5), new ArrayList<>(), new Rectangle("rectangle", 5, 5, 5));
        List<Checkpoint> checkpoints = new ArrayList<>();
        checkpoint = new Checkpoint(new Position(5, 5, 0), new Circle("circle", 1));
        checkpoints.add(checkpoint);

        sailors = new ArrayList<>();
        jeu = new Game(new Goal("regatte", checkpoints), ship, sailors, 4, new Wind(0, 50));
        Sailor sailor = new Sailor(1, 1, 1, "sailor1");
        Sailor sailor2 = new Sailor(1, 2, 2, "sailor2");
        Sailor sailor3 = new Sailor(3, 3, 3, "sailor3");

        sailors.add(sailor);
        sailors.add(sailor2);
        sailors.add(sailor3);
        strategy = new Strategy(jeu);
        gestionMarins = strategy.getGestionMarins();
    }

    @Test
    void placerSurRamesTest() {
        ship.setDeck(new Deck(3, 4));
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 1, 0, "Sailor 0")); // ( 0 , 1 ) LEFT SAILOR
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 ) RIGHT SAILOR
        jeu.setSailors(sailors);
        List<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(3, 0, "oar")); // ( 3 , 0 ) LEFT OAR
        entities.add(new OarEntity(0, 2, "oar")); // ( 0 , 2 ) RIGHT OAR
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategy.getActions();

        Assertions.assertEquals(0, strategy.getListActions().get(0).getSailorId());

        Assertions.assertEquals(-1, ((Moving) strategy.getListActions().get(0)).getYdistance());
        Assertions.assertEquals(-1, ((Moving) strategy.getListActions().get(1)).getXdistance());
        Assertions.assertTrue(gestionMarins.isPlacementInit());
    }

    @Test
    void placerSurRamesTest2() {
        ship.setDeck(new Deck(3, 10));
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(3, 0, 0, "Sailor 0")); // ( 3 , 0 ) LEFT SAILOR
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 ) RIGHT SAILOR
        jeu.setSailors(sailors);
        List<Entity> entities = new ArrayList<>();
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
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        jeu.setShip(ship);
        gestionMarins.repartirLesMarins();
        Assertions.assertEquals(1, gestionMarins.getLeftSailors().size());
        Assertions.assertEquals(1, gestionMarins.getRightSailors().size());
        Assertions.assertTrue(gestionMarins.isMarinRepartie());
    }

    @DisplayName("Attribuer Marin qui est dans range de 5")
    @Test
    void attribuerCoxswainTest1() {

        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(3, 3, 0, "Sailor 0")); // ( 3 , 3 )
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(0, 1, 2, "Sailor 2"));
        sailors.add(new Sailor(0, 2, 3, "Sailor 3"));
        sailors.add(new Sailor(1, 1, 4, "Sailor 4"));
        sailors.add(new Sailor(1, 3, 5, "Sailor 5"));
        sailors.add(new Sailor(2, 1, 6, "Sailor 6"));
        jeu.setSailors(sailors);
        List<Entity> entities = new ArrayList<>();
        entities.add(new Rudder(3, 4, "rudder"));
        entities.add(new OarEntity(0, 0, "oar"));
        entities.add(new OarEntity(1, 0, "oar"));
        entities.add(new OarEntity(2, 0, "oar"));
        entities.add(new OarEntity(0, 4, "oar"));
        entities.add(new OarEntity(1, 4, "oar"));
        entities.add(new OarEntity(2, 4, "oar"));
        ship.setEntities(entities);

        gestionMarins.attribuerCoxswain();
        System.out.println(gestionMarins.isPlacementCoxswain());

        gestionMarins.repartirLesMarins();

        Assertions.assertEquals(0, gestionMarins.getCoxswain().getId());
        Assertions.assertEquals(0, strategy.getListActions().get(0).getSailorId());
        Assertions.assertEquals(1, ((Moving) strategy.getListActions().get(0)).getYdistance());
        Assertions.assertEquals(3, gestionMarins.getLeftSailors().size());
        Assertions.assertEquals(3, gestionMarins.getRightSailors().size());
        Assertions.assertTrue(gestionMarins.isPlacementCoxswain());
    }

    @DisplayName("Attribuer Marin qui est hors son range de 5")
    @Test
    void attribuerCoxswainTest2() {
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 4, 0, "Sailor 0")); // ( 0 , 4 )
        jeu.setSailors(sailors);
        List<Entity> entities = new ArrayList<>();
        entities.add(new Rudder(6, 4, "rudder"));
        ship.setEntities(entities);
        gestionMarins.attribuerCoxswain();
        Assertions.assertEquals(0, gestionMarins.getCoxswain().getId());
        Assertions.assertEquals(0, strategy.getListActions().get(0).getSailorId());
        Assertions.assertEquals(2, ((Moving) strategy.getListActions().get(0)).getXdistance());
        Assertions.assertFalse(gestionMarins.isPlacementCoxswain());
    }

    @Test
    void findSailorByIdTest() {
        List<Sailor> sailors = new ArrayList<>();
        List<Sailor> sailorsVide = new ArrayList<>();
        Sailor s = new Sailor(1, 4, 0, "Sailor 0"); // ( 1 , 4 )
        sailors.add(s);
        Assertions.assertEquals(s, gestionMarins.findSailorById(s.getId(), sailors));
        Assertions.assertNull(gestionMarins.findSailorById(10, sailors));
        Assertions.assertNull(gestionMarins.findSailorById(0, sailorsVide));
    }

    @Test
    void findMarinLePlusProche() {
        Rudder rudder = new Rudder(1, 2, "Rudder");
        List<Entity> tmp = gestionMarins.stratData.jeu.getShip().getEntities();
        tmp.add(rudder);
        gestionMarins.stratData.jeu.getShip().setEntities(tmp);
        Sailor res = gestionMarins.marinLePlusProche(rudder);
        Assertions.assertEquals(res, sailors.get(1));
    }

    @Test
    void findMarinLePlusProcheDuSail() {
        Sail sail = new Sail(2, 4, "Sail", false);
        List<Entity> tmp = gestionMarins.stratData.jeu.getShip().getEntities();
        tmp.add(sail);
        gestionMarins.stratData.jeu.getShip().setEntities(tmp);
        Sailor res = gestionMarins.marinLePlusProche(sail);
        Assertions.assertEquals(res, sailors.get(2));
    }

    @Test
    void deplacerMarinTest() {
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(3, 3, 0, "Sailor 0")); // ( 3 , 3 )
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(7, 2, 1, "Sailor 1")); // ( 7 , 2 )
        jeu.setSailors(sailors);

        List<Entity> entities = new ArrayList<>();
        entities.add(new Rudder(4, 4, "rudder"));
        entities.add(new OarEntity(7, 0, "oar"));
        entities.add(new OarEntity(5, 0, "oar"));
        ship.setEntities(entities);

        Assertions.assertTrue(gestionMarins.deplacerMarin(sailors.get(0), entities.get(0)));
        Assertions.assertEquals(1, ((Moving) strategy.getListActions().get(0)).getXdistance());
        Assertions.assertEquals(1, ((Moving) strategy.getListActions().get(0)).getYdistance());
        Assertions.assertFalse(gestionMarins.deplacerMarin(sailors.get(1), entities.get(1)));
        Assertions.assertEquals(2, ((Moving) strategy.getListActions().get(1)).getXdistance());
        Assertions.assertEquals(-2, ((Moving) strategy.getListActions().get(1)).getYdistance());
        Assertions.assertTrue(gestionMarins.deplacerMarin(sailors.get(2), entities.get(2)));
        Assertions.assertEquals(-2, ((Moving) strategy.getListActions().get(2)).getXdistance());
        Assertions.assertEquals(-2, ((Moving) strategy.getListActions().get(2)).getYdistance());
        Assertions.assertEquals(4, sailors.get(0).getX());
        Assertions.assertEquals(4, sailors.get(0).getY());
        Assertions.assertEquals(5, sailors.get(2).getX());
        Assertions.assertEquals(0, sailors.get(2).getY());
    }

    @Test
    void deplacerMarinTest2() {
        Sailor sailor = new Sailor(3, 3, 0, "Sailor 0"); // ( 3 , 3 )
        Entity entity = new OarEntity(3, 3, "oar");
        Assertions.assertTrue(gestionMarins.deplacerMarin(sailor, entity));
        Sailor sailor1 = new Sailor(0, 0, 0, "Sailor 0"); // ( 0 , 0 )
        Entity entity1 = new OarEntity(6, 0, "oar");
        Assertions.assertFalse(gestionMarins.deplacerMarin(sailor1, entity1));
        Sailor sailor2 = new Sailor(0, 0, 0, "Sailor 0"); // ( 0 , 0 )
        Entity entity2 = new OarEntity(0, 4, "oar");
        Assertions.assertTrue(gestionMarins.deplacerMarin(sailor2, entity2));
        Assertions.assertEquals(2, sailor1.getX());
        Assertions.assertEquals(0, sailor1.getY());
    }

    @Test
    void ramerSelonVitesseTest() {
        List<Action> actions = new ArrayList<Action>();
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 0, 1, "Sailor 1")); // ( 1 , 0 )
        sailors.add(new Sailor(2, 0, 2, "Sailor 2"));
        sailors.add(new Sailor(0, 4, 3, "Sailor 3"));
        sailors.add(new Sailor(1, 4, 4, "Sailor 4"));
        sailors.add(new Sailor(2, 4, 5, "Sailor 5"));
        jeu.setSailors(sailors);
        strategy.getGestionMarins().repartirLesMarins();
        List<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(0, 0, "oar"));
        entities.add(new OarEntity(1, 0, "oar"));
        entities.add(new OarEntity(2, 0, "oar"));
        entities.add(new OarEntity(0, 4, "oar"));
        entities.add(new OarEntity(1, 4, "oar"));
        entities.add(new OarEntity(2, 4, "oar"));
        ship.setEntities(entities);
        // D??placement tout droit ?? vitesse maximale
        Deplacement deplacement_tout_droit = new Deplacement(165.0, 0.0);
        for (Sailor s : sailors) {
            actions.add(new Oar(s.getId()));
        }
        strategy.getGestionMarins().ramerSelonVitesse(deplacement_tout_droit);
        System.out.println(actions);
        System.out.println(strategy.getStratData().actions);
        Assertions.assertEquals(actions.size(), strategy.getStratData().actions.size());
        strategy.getStratData().actions.clear();
        actions.clear();
        // D??placement tout droit ?? la plus petite vitesse
        Deplacement deplacement_tout_droit_55 = new Deplacement(55.0, 0.0);
        actions.add(new Oar(gestionMarins.getLeftSailors().get(0).getId()));
        actions.add(new Oar(gestionMarins.getRightSailors().get(0).getId()));
        strategy.getGestionMarins().ramerSelonVitesse(deplacement_tout_droit_55);
        System.out.println(actions);
        System.out.println(strategy.getStratData().actions);
        Assertions.assertEquals(actions.size(), strategy.getStratData().actions.size());
        strategy.getStratData().actions.clear();
        actions.clear();
        // D??placement ?? -PI/2
        Deplacement deplacement_angle_droit_negatif = new Deplacement(82.5, -Math.PI / 2);
        for (Sailor s : strategy.getGestionMarins().getLeftSailors()) {
            actions.add(new Oar(s.getId()));
        }
        strategy.getGestionMarins().ramerSelonVitesse(deplacement_angle_droit_negatif);
        System.out.println(actions);
        System.out.println(strategy.getStratData().actions);
        Assertions.assertEquals(actions.size(), strategy.getStratData().actions.size());
        strategy.getStratData().actions.clear();
        actions.clear();
        // D??placement ?? PI/2
        Deplacement deplacement_angle_droit_positif = new Deplacement(82.5, Math.PI / 2);
        for (Sailor s : strategy.getGestionMarins().getRightSailors()) {
            actions.add(new Oar(s.getId()));
        }
        strategy.getGestionMarins().ramerSelonVitesse(deplacement_angle_droit_positif);
        System.out.println(actions);
        System.out.println(strategy.getStratData().actions);
        Assertions.assertEquals(actions.size(), strategy.getStratData().actions.size());
        strategy.getStratData().actions.clear();
        actions.clear();
    }

    @Test
    void setSailManagerTest() {
        gestionMarins.setSailorsManager(null);
        Assertions.assertNull(gestionMarins.stratData.getSailorsManager());
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 4, 0, "Sailor 0")); // ( 0 , 4 )
        jeu.setSailors(sailors);
        List<Entity> entities = new ArrayList<>();
        entities.add(new Sail(3, 0, "sail", false)); // ( 3 , 0 ) SAIL
        ship.setEntities(entities);
        jeu.setShip(ship);
        gestionMarins.attribuerSailManager();
        Assertions.assertNotNull(gestionMarins.stratData.getSailorsManager());
        gestionMarins.attribuerSailManager(); // se place au second tour
        Assertions.assertTrue(gestionMarins.isPlacementSailManagers());
    }

    @DisplayName("Attribuer Marin qui est dans range de 5")
    @Test
    void attribuerVigieTest1() {

        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(3, 3, 0, "Sailor 0")); // ( 3 , 3 )
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 )
        sailors.add(new Sailor(0, 1, 2, "Sailor 2"));
        sailors.add(new Sailor(0, 2, 3, "Sailor 3"));
        sailors.add(new Sailor(1, 1, 4, "Sailor 4"));
        sailors.add(new Sailor(1, 3, 5, "Sailor 5"));
        sailors.add(new Sailor(2, 1, 6, "Sailor 6"));
        jeu.setSailors(sailors);
        List<Entity> entities = new ArrayList<>();
        entities.add(new Watch(3, 4, "watch"));
        entities.add(new OarEntity(0, 0, "oar"));
        entities.add(new OarEntity(1, 0, "oar"));
        entities.add(new OarEntity(2, 0, "oar"));
        entities.add(new OarEntity(0, 4, "oar"));
        entities.add(new OarEntity(1, 4, "oar"));
        entities.add(new OarEntity(2, 4, "oar"));
        ship.setEntities(entities);

        gestionMarins.attribuerVigie();
        System.out.println(gestionMarins.isPlacementVigie());

        gestionMarins.repartirLesMarins();

        Assertions.assertEquals(0, gestionMarins.getVigie().getId());
        Assertions.assertEquals(0, strategy.getListActions().get(0).getSailorId());
        Assertions.assertEquals(1, ((Moving) strategy.getListActions().get(0)).getYdistance());
        Assertions.assertEquals(3, gestionMarins.getLeftSailors().size());
        Assertions.assertEquals(3, gestionMarins.getRightSailors().size());
        Assertions.assertTrue(gestionMarins.isPlacementVigie());
    }

    @DisplayName("Attribuer Marin qui est hors son range de 5")
    @Test
    void attribuerVigieTest2() {
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 4, 0, "Sailor 0")); // ( 0 , 4 )
        jeu.setSailors(sailors);
        List<Entity> entities = new ArrayList<>();
        entities.add(new Watch(6, 4, "watch"));
        ship.setEntities(entities);
        gestionMarins.attribuerVigie();
        Assertions.assertEquals(0, gestionMarins.getVigie().getId());
        Assertions.assertEquals(0, strategy.getListActions().get(0).getSailorId());
        Assertions.assertEquals(2, ((Moving) strategy.getListActions().get(0)).getXdistance());
        Assertions.assertFalse(gestionMarins.isPlacementVigie());
    }
}