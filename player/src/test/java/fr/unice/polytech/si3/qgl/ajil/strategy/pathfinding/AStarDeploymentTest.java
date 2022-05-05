package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AStarDeploymentTest {
    private ObstacleDetection obstacleDetection;
    private AStarDeployment aStarDeployment;
    private Game game;

    @BeforeEach
    void setUp() {
        obstacleDetection = new ObstacleDetection();
        this.game = Mockito.mock(Game.class);
        aStarDeployment = new AStarDeployment(game, 50);
    }


    @Test
    void gridSizeXYTestSimple() {
        aStarDeployment = new AStarDeployment(game, 100);
        Point shipPosition = new Point(0,0);
        Point targetPosition = new Point(420,420);
        Point result = aStarDeployment.gridSizeXY(shipPosition, targetPosition, 100);
        Assertions.assertEquals(5 + aStarDeployment.MARGIN ,result.getX());
        Assertions.assertEquals(5 + aStarDeployment.MARGIN ,result.getY());
    }

    @Test
    void gridSizeXYTestNegativ() {
        aStarDeployment = new AStarDeployment(game, 100);
        Point shipPosition = new Point(300,300);
        Point targetPosition = new Point(-100,-100);
        Point result = aStarDeployment.gridSizeXY(shipPosition, targetPosition, 100);
        Assertions.assertEquals(4 + aStarDeployment.MARGIN ,result.getX());
        Assertions.assertEquals(4 + aStarDeployment.MARGIN ,result.getY());
    }

    @Test
    void pointsVersTableauTest(){
        List<Point> list = new ArrayList<>();
        Point point1 = new Point(1,1);
        Point point2 = new Point(2,2);
        list.add(point1); list.add(point2);
        int[][] array = aStarDeployment.pointsVersTableau(list);
        Assertions.assertEquals(2, array.length);
        Assertions.assertEquals(1, array[0][0]);
        Assertions.assertEquals(1, array[0][1]);
        Assertions.assertEquals(2, array[1][0]);
        Assertions.assertEquals(2, array[1][1]);
    }
}