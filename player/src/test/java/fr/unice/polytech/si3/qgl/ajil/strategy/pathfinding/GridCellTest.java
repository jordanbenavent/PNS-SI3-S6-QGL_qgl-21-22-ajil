package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.maths.Segment;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class GridCellTest {
    private GridCell gridCell;

    @BeforeEach
    void setUp(){
        gridCell = new GridCell(new Point(0, 0), 100);
    }

    @Test
    void createGridSegmentsTest() {
        List<Segment> segments = gridCell.createGridSegments();
        Assertions.assertEquals(-50 , segments.get(0).getStartX());
        Assertions.assertEquals(-50 , segments.get(0).getStartY());
        Assertions.assertEquals(-50 , segments.get(1).getStartX());
        Assertions.assertEquals(50 , segments.get(1).getStartY());
        Assertions.assertEquals(50 , segments.get(1).getEndX());
        Assertions.assertEquals(50 , segments.get(1).getEndY());
        Assertions.assertEquals(50 , segments.get(2).getEndX());
        Assertions.assertEquals(-50 , segments.get(2).getEndY());
    }

    @Test
    void containsTest() {
        Position pos = new Position(0,0,0);
        Assertions.assertTrue(gridCell.contains(pos));
        pos = new Position(-25,-25,0);
        Assertions.assertTrue(gridCell.contains(pos));
        pos = new Position(-250,-250,0);
        Assertions.assertFalse(gridCell.contains(pos));
        pos = new Position(-50,-50,0);
        Assertions.assertTrue(gridCell.contains(pos));
    }
}