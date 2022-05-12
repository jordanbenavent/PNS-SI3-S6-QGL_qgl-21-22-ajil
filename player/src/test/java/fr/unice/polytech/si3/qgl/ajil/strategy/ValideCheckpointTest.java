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
import java.util.List;


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
                4,
                new Wind(0, 50)
        );
        strategy = new Strategy(jeu);
        valideCheckpoint = strategy.getValideCheckpoint();

        checkpoint = new Checkpoint(new Position(5, 5, 0), new Circle("circle", 1));
        checkpoint2 = new Checkpoint(new Position(1, 7, 0), new Circle("circle", 4));
        checkpoint3 = new Checkpoint(new Position(-1, 0, 1), new Circle("circle", 1));

    }



    @Test
    void checkpointTargetTest() {

        ship = new Ship("ship", 100,
                new Position(2.5, 2.5, 2 * Math.PI / 4), "BateauCarre",
                new Deck(2, 3),
                new ArrayList<>(),
                new Rectangle("rectangle", 2, 3, Math.PI / 4));
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        ArrayList<Sailor> sailors = new ArrayList<>();
        jeu = new Game(
                new Goal("regatte", checkpoints),
                ship,
                sailors,
                4,
                new Wind(0, 50)
        );
        strategy = new Strategy(jeu);
        checkpoint = new Checkpoint(new Position(0, 7, 0), new Circle("circle", 1));
        checkpoint2 = new Checkpoint(new Position(7, 7, 0), new Circle("circle", 4));
        checkpoints.add(checkpoint);
        checkpoints.add(checkpoint2);
        strategy = new Strategy(jeu);
        valideCheckpoint = strategy.getValideCheckpoint();
        System.out.println(checkpoints);
        // Le bateau est loin donc le checkpoint reste le même
        Assertions.assertEquals(checkpoint, valideCheckpoint.nextCheckpointTarget(checkpoints));
        //Le bateau a avancé
        ship.setPosition(new Position(0.5, 4.8, 2 * Math.PI / 4));
        //Le bateau est assez proche du checkpoint, cela le valide est donc le deuxième checkpoint est visé.
        Assertions.assertEquals(checkpoint2, valideCheckpoint.nextCheckpointTarget(checkpoints));
        //Le bateau est assez proche du deuxième checkpoint, or la liste du checlpoint est finie. Cela retourne donc null.
        ship.setPosition(new Position(7.5, 6.8, 2 * Math.PI / 4));
        Assertions.assertNull(valideCheckpoint.nextCheckpointTarget(checkpoints));
        Assertions.assertNull(valideCheckpoint.nextCheckpointTarget(checkpoints));
        //Bateau en cercle
        ship = new Ship("ship", 100,
                new Position(2.5, 2.5, 2 * Math.PI / 4), "BateauCarre",
                new Deck(2, 3),
                new ArrayList<>(),
                new Circle("circle", 2));
        checkpoint = new Checkpoint(new Position(2.5, 2.5, 0), new Circle("circle", 1));
        jeu.setShip(ship);
        checkpoints.add(checkpoint);
        Assertions.assertNull(valideCheckpoint.nextCheckpointTarget(checkpoints));
        checkpoints.clear();


        //Version PathFinding avec faux checkpoints !
        checkpoint =  new Checkpoint(new Position(8, 6, 0), new Circle("circle", 1));
        checkpoint2 =  new Checkpoint(new Position(8, 6, 0), new Circle("circle", 1));
        checkpoints.add(checkpoint);
        checkpoints.add(checkpoint2);
        Checkpoint fakeCheckpoint1 =  new Checkpoint(new Position(1, 4, 0), new Circle("circle", 1));
        Checkpoint fakeCheckpoint2 =  new Checkpoint(new Position(1, 6, 0), new Circle("circle", 1));
        Checkpoint fakeCheckpoint3 =  new Checkpoint(new Position(7, 6, 0), new Circle("circle", 1));
        ArrayList<Checkpoint> fakeCheckpoints = new ArrayList<>();
        fakeCheckpoints.add(fakeCheckpoint1); fakeCheckpoints.add(fakeCheckpoint2); fakeCheckpoints.add(fakeCheckpoint3);
        ship = new Ship("ship", 100,
                new Position(2, 2, Math.PI / 4), "BateauCarre",
                new Deck(1, 1),
                new ArrayList<>(),
                new Rectangle("rectangle", 1, 2, 0));
        jeu = new Game(
                new Goal("regatte", checkpoints),
                ship,
                sailors,
                4,
                new Wind(0, 50)
        );
        valideCheckpoint = new ValideCheckpoint(jeu);
        valideCheckpoint.setFakeCheckpoint(fakeCheckpoints);
        System.out.println(checkpoints);
        System.out.println(fakeCheckpoints);
        Assertions.assertEquals(fakeCheckpoint2, valideCheckpoint.nextCheckpointTarget(checkpoints));
        ship.setPosition(new Position(1,3, Math.PI/4));
        System.out.println("SECOND TEST");
        Assertions.assertEquals(fakeCheckpoint3, valideCheckpoint.nextCheckpointTarget(checkpoints));
        ship.setPosition(new Position(0,5, Math.PI/4));
        Assertions.assertEquals(checkpoint, valideCheckpoint.nextCheckpointTarget(checkpoints));
        ship.setPosition(new Position(5.5,6, 0));
        Assertions.assertEquals(checkpoint2, valideCheckpoint.nextCheckpointTarget(checkpoints));
        ship.setPosition(new Position(6.5,6, Math.PI/4));
        Assertions.assertNull(valideCheckpoint.nextCheckpointTarget(checkpoints));
    }

    @Test
    void realOrFakeTest(){
        Checkpoint fakeCheckpoint1 =  new Checkpoint(new Position(1, 4, 0), new Circle("circle", 1));
        Checkpoint fakeCheckpoint2 =  new Checkpoint(new Position(1, 6, 0), new Circle("circle", 1));
        Checkpoint fakeCheckpoint3 =  new Checkpoint(new Position(7, 6, 0), new Circle("circle", 1));
        ArrayList<Checkpoint> fakeCheckpoints = new ArrayList<>();
        fakeCheckpoints.add(fakeCheckpoint1); fakeCheckpoints.add(fakeCheckpoint2); fakeCheckpoints.add(fakeCheckpoint3);
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        checkpoint = new Checkpoint(new Position(5, 5, 0), new Circle("circle", 1));
        checkpoint2 = new Checkpoint(new Position(1, 7, 0), new Circle("circle", 4));
        checkpoint3 = new Checkpoint(new Position(-1, 0, 1), new Circle("circle", 1));
        valideCheckpoint.setFakeCheckpoint(fakeCheckpoints);
        Assertions.assertEquals(fakeCheckpoints, valideCheckpoint.realOrFakeCheckpoint(checkpoints));
        Assertions.assertEquals(fakeCheckpoints, valideCheckpoint.realOrFakeCheckpoint(null));
        valideCheckpoint.setFakeCheckpoint(new ArrayList<>());
        Assertions.assertEquals(checkpoints, valideCheckpoint.realOrFakeCheckpoint(checkpoints));
        valideCheckpoint.setFakeCheckpoint(null);
        Assertions.assertEquals(checkpoints, valideCheckpoint.realOrFakeCheckpoint(checkpoints));
    }


}

