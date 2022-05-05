package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AStarTest {
    AStar aStar;

    @BeforeEach
    void setUp(){
        aStar = new AStar(5,5, 0, 0, 3,2,
                new int[][]{
                        {0,4},{2,2},{3,1},{3,3},{2,1},{2,3}
                });
    }

    @Test
    void obtenirLeChemin() {
        List<Position> chemin = new ArrayList<>();
        chemin = aStar.obtenirLeChemin();
        Assertions.assertEquals(0, chemin.get(0).getX());
        Assertions.assertEquals(0, chemin.get(0).getY());
        Assertions.assertEquals(3, chemin.get(chemin.size()-1).getX());
        Assertions.assertEquals(2, chemin.get(chemin.size()-1).getY());
        Assertions.assertEquals(1, chemin.get(1).getX());
        Assertions.assertEquals(0, chemin.get(1).getY());
        Assertions.assertEquals(2, chemin.get(2).getX());
        Assertions.assertEquals(0, chemin.get(2).getY());
        Assertions.assertEquals(3, chemin.get(3).getX());
        Assertions.assertEquals(0, chemin.get(3).getY());
        Assertions.assertEquals(4, chemin.get(4).getX());
        Assertions.assertEquals(1, chemin.get(4).getY());
    }
}