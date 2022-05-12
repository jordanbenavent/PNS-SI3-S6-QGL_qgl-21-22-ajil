package fr.unice.polytech.si3.qgl.ajil.shape;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void testEquals() {
        Rectangle rectangle1 = new Rectangle("rectangle", 100,100,0);
        Rectangle rectangle2 = new Rectangle("rectangle", 100,100,0);
        Assertions.assertTrue(rectangle1.equals(rectangle2));
        Assertions.assertEquals(rectangle1.hashCode(), rectangle2.hashCode());
    }
}