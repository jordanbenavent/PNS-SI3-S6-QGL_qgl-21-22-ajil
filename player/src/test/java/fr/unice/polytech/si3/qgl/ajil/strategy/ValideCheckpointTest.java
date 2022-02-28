package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class ValideCheckpointTest {

    Ship ship;
    Strategy strategy;
    Game jeu;
    ValideCheckpoint valideCheckpoint;

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
                new Goal("regatte", checkpoints),
                ship,
                sailors,
                4
        );
        strategy = new Strategy(jeu);
        valideCheckpoint = strategy.getValideCheckpoint();

        checkpoint = new Checkpoint(new Position(5, 5, 0), new Circle("circle", 1));
        checkpoint2 = new Checkpoint(new Position(1, 7, 0), new Circle("circle", 4));
        checkpoint3 = new Checkpoint(new Position(-1, 0, 1), new Circle("circle", 1));

    }

    @Test
    void calculPointShipTest() {
        ship = new Ship("ship", 100,
                new Position(2, 2, 0), "BateauCarre",
                new Deck(2, 2),
                new ArrayList<>(),
                new Rectangle("rectangle", 2, 2, 0));

        ArrayList<Point> point = valideCheckpoint.calculPointShip(ship);
        Assertions.assertEquals(new Point(3, 3), point.get(0));
        Assertions.assertEquals(new Point(1, 3), point.get(1));
        Assertions.assertEquals(new Point(3, 1), point.get(2));
        Assertions.assertEquals(new Point(1, 1), point.get(3));

        Point[] pointpolygone = {new Point(2,2), new Point(-2,2), new Point(-2,-2)};
        ship = new Ship("ship", 100,
                new Position(2, 2, 0), "BateauCarre",
                new Deck(2, 2),
                new ArrayList<>(),
                new Polygone("polygone",0, pointpolygone));
        point = valideCheckpoint.calculPointShip(ship);
        Assertions.assertEquals(new Point(2,2), point.get(0));
    }

    @Test
    void dansLeCercleTest() {
        Checkpoint checkpoint = new Checkpoint(new Position(0, 4, 0), new Circle("circle", 1));
        ship = new Ship("ship", 100,
                new Position(2.5, 2.5, 3 * Math.PI / 4), "BateauCarre",
                new Deck(2, 3),
                new ArrayList<>(),
                new Rectangle("rectangle", 2.5, 2.5, 3 * Math.PI / 4));
        ArrayList<Point> point2 = valideCheckpoint.calculPointShip(ship);
        //Le bateau est loin du checkpoint
        Assertions.assertFalse(valideCheckpoint.dansLeCercle(point2, checkpoint));
        checkpoint = new Checkpoint(new Position(0, 4, 0), new Circle("circle", 4));
        //L'un des coins du bateau est dans le checkpoint
        Assertions.assertTrue(valideCheckpoint.dansLeCercle(point2, checkpoint));
    }

    @Test
    void checkpointTargetTest() {
        ship = new Ship("ship", 100,
                new Position(2.5, 2.5, 3 * Math.PI / 4), "BateauCarre",
                new Deck(2, 3),
                new ArrayList<>(),
                new Rectangle("rectangle", 2, 3, 3 * Math.PI / 4));
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        ArrayList<Sailor> sailors = new ArrayList<>();
        jeu = new Game(
                new Goal("regatte", checkpoints),
                ship,
                sailors,
                4
        );
        strategy = new Strategy(jeu);
        checkpoint = new Checkpoint(new Position(0, 7, 0), new Circle("circle", 1));
        checkpoint2 = new Checkpoint(new Position(1, 7, 0), new Circle("circle", 4));
        checkpoints.add(checkpoint);
        checkpoints.add(checkpoint2);
        strategy = new Strategy(jeu);
        valideCheckpoint = strategy.getValideCheckpoint();
        // Le bateau est loin donc le checkpoint reste le même
        Assertions.assertEquals(checkpoint, valideCheckpoint.checkpointTarget(checkpoints));
        //Le bateau a avancé
        ship.setPosition(new Position(0.5, 4.8, 3 * Math.PI / 4));
        //Le bateau est assez proche du checkpoint, cela le valide est donc le deuxième checkpoint est visé.
        Assertions.assertEquals(checkpoint2, valideCheckpoint.checkpointTarget(checkpoints));
        //Le bateau est assez proche du deuxième checkpoint, or la liste du checlpoint est finie. Cela retourne donc null.
        Assertions.assertNull(valideCheckpoint.checkpointTarget(checkpoints));
    }

    @Test
    void intersectionCircleShipTest() {
        ArrayList<Point> pointsShip = new ArrayList<>();
        pointsShip.add(new Point(4, 1));
        pointsShip.add(new Point(6, 1));
        pointsShip.add(new Point(4, 5));
        pointsShip.add(new Point(6, 5));
        Checkpoint checkpointPasValide = new Checkpoint(new Position(1, 4, 0), new Circle("circle", 1));
        Assertions.assertFalse(valideCheckpoint.intersectionCircleShip(pointsShip, checkpointPasValide));
        Checkpoint checkpointValide = new Checkpoint(new Position(3.5, 3, 0), new Circle("circle", 1));
        Assertions.assertTrue(valideCheckpoint.intersectionCircleShip(pointsShip, checkpointValide));
        Checkpoint checkpointValideDunPoint = new Checkpoint(new Position(5, 6, 0), new Circle("circle", 1));
        Assertions.assertTrue(valideCheckpoint.intersectionCircleShip(pointsShip, checkpointValideDunPoint));
        Checkpoint checkpointValideDeuxPointMaisCotesDifferents = new Checkpoint(new Position(7, 6, 0), new Circle("circle", 2));
        Assertions.assertTrue(valideCheckpoint.intersectionCircleShip(pointsShip, checkpointValideDeuxPointMaisCotesDifferents));
    }

    @Test
    void intersectionDroiteVerticaleCircleTest() {
        Point point1 = new Point(1, 5);
        Point point2 = new Point(1, 1);
        Checkpoint checkpointPasValide = new Checkpoint(new Position(-1, 3, 0), new Circle("circle", 1));
        Assertions.assertFalse(valideCheckpoint.intersectionDroiteVerticaleCircle(point1, point2, checkpointPasValide));
        Checkpoint checkpointValide = new Checkpoint(new Position(0, 3, 0), new Circle("circle", 1.5));
        Assertions.assertTrue(valideCheckpoint.intersectionDroiteVerticaleCircle(point1, point2, checkpointValide));
        Checkpoint checkpointValideUnPoint = new Checkpoint(new Position(2, 3, 0), new Circle("circle", 1));
        Assertions.assertTrue(valideCheckpoint.intersectionDroiteVerticaleCircle(point1, point2, checkpointValideUnPoint));
    }

    @Test
    void pointShipPolygoneTest(){
        Point point[] = new Point[3];
        Point point1 = new Point(1,2);
        Point point2 = new Point(0,3);
        Point point3 = new Point(0,1);
        point[0] = point1; point[1] = point2; point[2]= point3;
        ship = new Ship("ship", 100,
                new Position(4, 4, Math.PI/4), "BateauCarre",
                new Deck(2, 2),
                new ArrayList<>(),
                new Polygone("polygone", Math.PI/4,point));
        System.out.println(valideCheckpoint.pointShipPolygone(ship));

    }
}

