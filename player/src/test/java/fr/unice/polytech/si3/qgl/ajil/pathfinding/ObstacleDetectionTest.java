package fr.unice.polytech.si3.qgl.ajil.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.maths.CalculPoints;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.AStar;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.AStarDeployment;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.GridCell;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.ObstacleDetection;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class ObstacleDetectionTest {
    private ObstacleDetection obstacleDetection;
    private Game game;

    @BeforeEach
    void setUp(){
        obstacleDetection = new ObstacleDetection();
        this.game = Mockito.mock(Game.class);
    }

    @Test
    void findOriginTest(){
        Point positionShip = new Point(55,55);
        Point positionCheckpoint = new Point(100,100);
        Point point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-obstacleDetection.margin + positionShip.getX(), -obstacleDetection.margin + positionShip.getY()), point);
        positionCheckpoint = new Point(100,-100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-obstacleDetection.margin + positionShip.getX(), -obstacleDetection.margin + positionCheckpoint.getY()), point);
        positionCheckpoint = new Point(-100,-100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-obstacleDetection.margin + positionCheckpoint.getX(), -obstacleDetection.margin + positionCheckpoint.getY()), point);
        positionCheckpoint = new Point(-100,100);
        point = obstacleDetection.findOrigin(positionShip, positionCheckpoint);
        Assertions.assertEquals(new Point(-obstacleDetection.margin - positionCheckpoint.getY(), -obstacleDetection.margin + positionShip.getY()), point);
    }

    @Test
    void rotatedReefTest() {
        // Création de la grille
        AStarDeployment aStarDeployment = new AStarDeployment(game, 50);

        GridCell[][] grid = obstacleDetection.gridCreation(8,8,50, new Point(0.0,0.0),
                new Position(25.0,25.0,0.0),new Position(100.0,50.0,0.0));

        // margin = 250 => 5 Cells
        ArrayList<VisibleEntitie> recif = new ArrayList<>();
        recif.add(new Reef("reef", new Position(200.0, 200.0,0.52), // 0,52 environ 30 degrés
                new Rectangle("rectangle", 96.0, 180.0, 0.0)
        ));

        ArrayList<VisibleEntitie> visibleReefs = CalculPoints.entitiesToEntitiesPolygone(recif);

        System.out.println("Les vertices : " + visibleReefs.get(0).toString());

        ArrayList<Point> cellsB = obstacleDetection.gridProcess(grid, visibleReefs);

        int[][] cellB = aStarDeployment.pointsVersTableau( obstacleDetection.gridProcess(grid, visibleReefs));

        AStar aStar = new AStar(8,8,0,0,6,3, cellB);
        aStar.display();

        Assertions.assertEquals(14, cellsB.size());
    }


}
