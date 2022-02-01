package fr.unice.polytech.si3.qgl.ajil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CockpitTest {

    Cockpit cockpit;

    @BeforeEach
    void setUp() {
        this.cockpit = new Cockpit();
    }

    @Test
    void initGameTest(){
        cockpit = new Cockpit();
        String game = "{\n  \"goal\": {\n    \"mode\": \"REGATTA\",\n    \"checkpoints\": [\n      {\n        \"position\": {\n          \"x\": \"1000\",\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      },\n      {\n        \"position\": {\n          \"x\": 0,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      }\n    ]\n  },\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"sailors\": [\n    {\n      \"x\": 0,\n      \"y\": 0,\n      \"id\": 0,\n      \"name\": \"Edward Teach\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 1,\n      \"id\": 1,\n      \"name\": \"Edward Pouce\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 2,\n      \"id\": 2,\n      \"name\": \"Tom Pouce\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 0,\n      \"id\": 3,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 1,\n      \"id\": 4,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 2,\n      \"id\": 5,\n      \"name\": \"Tom Pouce\"\n    }\n  ],\n  \"shipCount\": 1\n}";
        cockpit.initGame(game);
        Assertions.assertEquals("REGATTA",cockpit.getJeu().getGoal().getMode());
        Assertions.assertEquals(100, cockpit.getJeu().getShip().getLife());
        Assertions.assertEquals(1, cockpit.getJeu().getShipCount());
    }
}