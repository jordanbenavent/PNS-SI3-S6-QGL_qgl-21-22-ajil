package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void positionTest1() {
        Position pos = new Position();
        String json = " {\n          \"x\": 1000,\n          \"y\": 0,\n          \"orientation\": 0\n}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            pos = objectMapper.readValue(json, Position.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1000, pos.getX());
    }
}