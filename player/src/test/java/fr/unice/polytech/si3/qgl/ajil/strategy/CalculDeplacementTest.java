package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shipentities.OarEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class CalculDeplacementTest {


    Strategy strategie;
    Game jeu;
    Ship ship;
    Checkpoint checkpoint;
    Checkpoint checkpoint2;
    Checkpoint checkpoint3;
    CalculDeplacement calculDeplacement;

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
        strategie = new Strategy(jeu);
        checkpoint = new Checkpoint(new Position(5, 5, 0), new Circle("circle", 1));
        checkpoints.add(checkpoint);
        checkpoint2 = new Checkpoint(new Position(1, 7, 0), new Circle("circle", 4));
        checkpoint3 = new Checkpoint(new Position(-1, 0, 1), new Circle("circle", 1));
        strategie = new Strategy(jeu);
        calculDeplacement = strategie.getCalculDeplacement();

    }


    @Test
    void distanceTest() {
        Position checkpoint_position = new Position(60, 90, 0);
        Position ship_position = new Position(60, 50, 0);
        checkpoint.setPosition(checkpoint_position);
        ship.setPosition(ship_position);
        Assertions.assertEquals(40, calculDeplacement.distance(checkpoint, ship));
        ship.getPosition().setX(50);
        Assertions.assertEquals(10 * Math.sqrt(17), calculDeplacement.distance(checkpoint, ship));
    }

    @Test
    void predictionAngleTourSuivantTest() {
        // Cas bateau à 4 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        Vector v_ship = new Vector(10, 0);
        Vector v_check = new Vector(60, 45);
        //System.out.println(calculDeplacement.predictionAngleTourSuivant(v_ship, v_check));
    }

}