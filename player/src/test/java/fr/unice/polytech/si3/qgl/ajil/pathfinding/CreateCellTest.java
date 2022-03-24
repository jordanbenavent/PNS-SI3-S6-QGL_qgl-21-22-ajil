package fr.unice.polytech.si3.qgl.ajil.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.CreateCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CreateCellTest {

    private Ship ship;
    private Game jeu;

    @BeforeEach
    void setUp(){
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
    }

    @Test
    void calculAllCellTest(){
        jeu.getGoal().getCheckpoints().add(new Checkpoint(new Position(500,500,0), new Circle("circle",0)));
        CreateCell createCell = new CreateCell(jeu);
        createCell.calculAllCell();
        System.out.println(createCell.getCellules());
    }
}
