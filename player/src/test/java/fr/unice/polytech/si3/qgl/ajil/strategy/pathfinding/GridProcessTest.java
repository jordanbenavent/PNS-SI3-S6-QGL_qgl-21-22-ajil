package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GridProcessTest {
    private ObstacleDetection obstacleDetection;
    private ArrayList<VisibleEntitie> reeefs;

    @BeforeEach
    void setUp() {
        obstacleDetection = new ObstacleDetection();
        reeefs = new ArrayList<>();
    }

    @Test
    void gridProcessTest() {
        GridCell[][] grid = obstacleDetection.gridCreation(
                6,3,100,new Point(-100,-100), // Origine à (-100, -100)
                new Position(50.0,50.0,0), // Départ du bateau
                new Position(450.0,50.0,0.0) // Position du Checkpoint
        );
        Assertions.assertEquals(1, obstacleDetection.getsX());
        Assertions.assertEquals(1, obstacleDetection.getsY());
        Assertions.assertEquals(5, obstacleDetection.geteX());
        Assertions.assertEquals(1, obstacleDetection.geteY());
        // Récif en (2, 1) et (3, 1)
        reeefs.add(new Reef("reef", new Position(20.0, 10.0,0.0),
                new Polygone("polygone",0.0,
                        new Point[]{new Point(117.4,40), new Point(212.0, 42.7),
                                new Point(117.1, 75.0), new Point(217.7, 75.2)})
        ));
        // Récif en (3, 1) et (4, 1)
        reeefs.add(new Reef("reef", new Position(20.0, 10.0,0.0),
                new Polygone("polygone",0.0,
                        new Point[]{new Point(270.4,40), new Point(372.0, 42.7),
                                new Point(269.1, 75.0), new Point(370.7, 75.2)})
        ));
        ArrayList<Point> cellsBlocked = obstacleDetection.gridProcess(grid, reeefs);
        Assertions.assertEquals(2,cellsBlocked.get(0).getX());
        Assertions.assertEquals(1,cellsBlocked.get(0).getY());
        Assertions.assertEquals(3,cellsBlocked.get(1).getX());
        Assertions.assertEquals(1,cellsBlocked.get(1).getY());
        Assertions.assertEquals(4,cellsBlocked.get(2).getX());
        Assertions.assertEquals(1,cellsBlocked.get(2).getY());
    }

    @Test
    void AStartestSimple() {
        // On utilise la config du haut
        AStar aStar = new AStar(6,3,1,1,5,1,
                new int[][]{
                        {2,1},{3,1},{3,2},{4,1}
                });
        ArrayList<Position> chemin = aStar.obtenirLeChemin();
        Assertions.assertEquals(1,chemin.get(0).getX());
        Assertions.assertEquals(1,chemin.get(0).getY());
        Assertions.assertEquals(2,chemin.get(1).getX());
        Assertions.assertEquals(0,chemin.get(1).getY());
        Assertions.assertEquals(3,chemin.get(2).getX());
        Assertions.assertEquals(0,chemin.get(2).getY());
        Assertions.assertEquals(4,chemin.get(3).getX());
        Assertions.assertEquals(0,chemin.get(3).getY());
        Assertions.assertEquals(5,chemin.get(4).getX());
        Assertions.assertEquals(1,chemin.get(4).getY());
    }
}