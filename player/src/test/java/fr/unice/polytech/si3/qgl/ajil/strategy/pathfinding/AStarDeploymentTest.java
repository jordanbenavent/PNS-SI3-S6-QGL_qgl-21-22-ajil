package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AStarDeploymentTest {
    private ObstacleDetection obstacleDetection;
    private AStarDeployment aStarDeployment;
    private Game game;

    @BeforeEach
    void setUp() {
        obstacleDetection = new ObstacleDetection();
        this.game = Mockito.mock(Game.class);
    }


    @Test
    void gridSizeXYTestSimple() {
        aStarDeployment = new AStarDeployment(game, 100);
        Point shipPosition = new Point(0,0);
        Point targetPosition = new Point(420,420);
        Point result = aStarDeployment.gridSizeXY(shipPosition, targetPosition, 100);
        Assertions.assertEquals(15 ,result.getX());
        Assertions.assertEquals(15 ,result.getY());
    }

    @Test
    void gridSizeXYTestNegativ() {
        aStarDeployment = new AStarDeployment(game, 100);
        Point shipPosition = new Point(300,300);
        Point targetPosition = new Point(-100,-100);
        Point result = aStarDeployment.gridSizeXY(shipPosition, targetPosition, 100);
        Assertions.assertEquals(14 ,result.getX());
        Assertions.assertEquals(14 ,result.getY());
    }
}