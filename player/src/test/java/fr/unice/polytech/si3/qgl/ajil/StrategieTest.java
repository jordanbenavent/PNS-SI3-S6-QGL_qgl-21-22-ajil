package fr.unice.polytech.si3.qgl.ajil;


import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.shape.*;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;
import fr.unice.polytech.si3.qgl.ajil.shipentities.OarEntity;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
        checkpoint = new Checkpoint(new Position(5,5,0), new Circle("circle", 1));
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
        ship.setDeck(new Deck(3, 4));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 1, 0, "Sailor 0")); // ( 0 , 1 ) LEFT SAILOR
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 ) RIGHT SAILOR
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(3,0,"oar")); // ( 3 , 0 ) LEFT OAR
        entities.add(new OarEntity(0,2,"oar")); // ( 0 , 2 ) RIGHT OAR
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        Assertions.assertEquals(0, strategie.getListActions().get(0).getSailorId());
        Assertions.assertEquals(-1, ((Moving) strategie.getListActions().get(0)).getYdistance());
        Assertions.assertEquals(-1,((Moving)strategie.getListActions().get(1)).getXdistance());
    }

    @Test
    void placerSurRamesTest2() {
        ship.setDeck(new Deck(3, 10));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(3, 0, 0, "Sailor 0")); // ( 3 , 0 ) LEFT SAILOR
        sailors.add(new Sailor(1, 2, 1, "Sailor 1")); // ( 1 , 2 ) RIGHT SAILOR
        jeu.setSailors(sailors);
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(3,0,"oar")); // ( 3 , 0 ) LEFT OAR
        entities.add(new OarEntity(7,2,"oar")); // ( 7 , 2 ) RIGHT OAR
        ship.setEntities(entities);
        jeu.setShip(ship);
        strategie.getActions();
        Assertions.assertEquals(1, strategie.getListActions().get(0).getSailorId());
        Assertions.assertEquals(2,((Moving)strategie.getListActions().get(0)).getXdistance());
    }

    @Test
    void repartirLesMarins() {
        ship.setDeck(new Deck(2, 5));
        ArrayList<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "Sailor 0")); // ( 0 , 0 )
        sailors.add(new Sailor(1, 1, 1, "Sailor 1")); // ( 1 , 2 )
        jeu.setSailors(sailors);
        jeu.setShip(ship);
        strategie.repartirLesMarins();
        Assertions.assertEquals(1, strategie.getLeftSailors().size());
        Assertions.assertEquals(1, strategie.getRightSailors().size());
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
    void predictionAngleTourSuivantTest(){
        // Cas bateau à 4 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        Vector v_ship = new Vector(10, 0);
        Vector v_check = new Vector(60,45);
        System.out.println(strategie.predictionAngleTourSuivant(v_ship, v_check));
    }

    @Test
    void calculPointShipTest(){
        ship = new Ship("ship", 100,
                new Position(2, 2, 0), "BateauCarre",
                new Deck(2, 2),
                new ArrayList<>(),
                new Rectangle("rectangle", 2, 2, 0));

        ArrayList<Point> point = strategie.calculPointShip(ship);
        Assertions.assertEquals(new Point(3,3), point.get(0));
        Assertions.assertEquals(new Point(1,3), point.get(1));
        Assertions.assertEquals(new Point(3,1), point.get(2));
        Assertions.assertEquals(new Point(1,1), point.get(3));

        Point[] pointpolygone = {new Point(2,2), new Point(-2,2), new Point(-2,-2)};

        ship = new Ship("ship", 100,
                new Position(2, 2, 0), "BateauCarre",
                new Deck(2, 2),
                new ArrayList<>(),
                new Polygone("polygone",0, pointpolygone));
        point = strategie.calculPointShip(ship);
        Assertions.assertEquals(new Point(2,2), point.get(0));

    }

    @Test
    void dansLeCercleTest(){
        Checkpoint checkpoint = new Checkpoint( new Position(0,4,0), new Circle("circle" ,1));
        ship = new Ship("ship", 100,
                new Position(2.5, 2.5, 3*Math.PI/4), "BateauCarre",
                new Deck(2, 3),
                new ArrayList<>(),
                new Rectangle("rectangle", 2.5, 2.5, 3*Math.PI/4));
        ArrayList<Point> point2 = strategie.calculPointShip(ship);
        //Le bateau est loin du checkpoint
        Assertions.assertEquals(false, strategie.dansLeCercle(point2, checkpoint));
        checkpoint = new Checkpoint( new Position(0,4,0), new Circle("circle" ,4));
        //L'un des coins du bateau est dans le checkpoint
        Assertions.assertEquals(true, strategie.dansLeCercle(point2, checkpoint));
    }

    @Test
    void checkpointTargetTest(){
        ship = new Ship("ship", 100,
                new Position(2.5, 2.5, 3*Math.PI/4), "BateauCarre",
                new Deck(2, 3),
                new ArrayList<>(),
                new Rectangle("rectangle", 2, 3, 3*Math.PI/4));
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        ArrayList<Sailor> sailors = new ArrayList<>();
        jeu = new Game(
                new Goal("regatte",checkpoints),
                ship,
                sailors,
                4
        );
        strategie = new Strategie(jeu);
        checkpoint = new Checkpoint(new Position(0,7,0), new Circle("circle", 1));
        checkpoint2 = new Checkpoint(new Position(1,7,0), new Circle("circle", 4));
        checkpoints.add(checkpoint);checkpoints.add(checkpoint2);
        strategie = new Strategie(jeu);
        // Le bateau est loin donc le checkpoint reste le même
        Assertions.assertEquals(checkpoint, strategie.checkpointTarget(checkpoints));
        //Le bateau a avancé
        ship.setPosition(new Position(0.5, 4.8, 3*Math.PI/4));
        //Le bateau est assez proche du checkpoint, cela le valide est donc le deuxième checkpoint est visé.
        Assertions.assertEquals(checkpoint2, strategie.checkpointTarget(checkpoints));
        //Le bateau est assez proche du deuxième checkpoint, or la liste du checlpoint est finie. Cela retourne donc null.
        Assertions.assertEquals(null, strategie.checkpointTarget(checkpoints));
    }

    @Test
    void intersectionCircleShipTest(){
        ArrayList<Point> pointsShip = new ArrayList<>();
        pointsShip.add(new Point(4,1));
        pointsShip.add(new Point(6,1));
        pointsShip.add(new Point(4,5));
        pointsShip.add(new Point(6,5));
        Checkpoint checkpointPasValide = new Checkpoint(new Position(1,4,0), new Circle("circle", 1));
        Assertions.assertFalse(strategie.intersectionCircleShip(pointsShip, checkpointPasValide));
        Checkpoint checkpointValide = new Checkpoint(new Position(3.5,3,0), new Circle("circle", 1));
        Assertions.assertTrue(strategie.intersectionCircleShip(pointsShip, checkpointValide));
        Checkpoint checkpointValideDunPoint = new Checkpoint(new Position(5,6,0), new Circle("circle", 1));
        Assertions.assertTrue(strategie.intersectionCircleShip(pointsShip, checkpointValideDunPoint));
        Checkpoint checkpointValideDeuxPointMaisCotesDifferents = new Checkpoint(new Position(7,6,0), new Circle("circle", 2));
        Assertions.assertTrue(strategie.intersectionCircleShip(pointsShip, checkpointValideDeuxPointMaisCotesDifferents));
    }

    @Test
    void intersectionDroiteVerticaleCircleTest(){
        Point point1 = new Point(1,5);
        Point point2 = new Point(1,1);
        Checkpoint checkpointPasValide = new Checkpoint(new Position(-1,3,0), new Circle("circle", 1));
        Assertions.assertFalse(strategie.intersectionDroiteVerticaleCircle(point1,point2, checkpointPasValide));
        Checkpoint checkpointValide = new Checkpoint(new Position(0,3,0), new Circle("circle", 1.5));
        Assertions.assertTrue(strategie.intersectionDroiteVerticaleCircle(point1,point2, checkpointValide));
        Checkpoint checkpointValideUnPoint = new Checkpoint(new Position(2,3,0), new Circle("circle", 1));
        Assertions.assertTrue(strategie.intersectionDroiteVerticaleCircle(point1,point2, checkpointValideUnPoint));
    }
}