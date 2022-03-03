package fr.unice.polytech.si3.qgl.ajil.tooling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.strategy.Strategy;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /*
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new OarEntity(1,1,"oar"));
        Ship ship = new Ship("Pirate", 100, new Position(1,2,3.1415),"Ic&o", new Deck(1,2), entities, new Shape("carre"));
        String test = om.writeValueAsString(ship);
        //System.out.println(test);
        Moving moving = new Moving(1,1,1);
        Moving moving1 = new Moving(2, 0,0);
        Oar oar = new Oar(1);
        Turn turn = new Turn(1, 5);
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(moving); actions.add(moving1); actions.add(oar); actions.add(turn);
        //System.out.println(om.writeValueAsString(actions));
        ArrayList<Moving> list = new ArrayList<>();
        list.add(moving); list.add(moving1);
        test = om.writeValueAsString(list);
        //System.out.println(test);
        String json = "{\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"visibleEntities\": [\n    {\n      \"type\": \"stream\",\n      \"position\": {\n        \"x\": 500,\n        \"y\": 0,\n        \"orientation\": 0\n      },\n      \"shape\": {\n        \"type\": \"rectangle\",\n        \"width\": 50,\n        \"height\": 500,\n        \"orientation\": 0\n      },\n      \"strength\": 40\n    }\n  ],\n  \"wind\": {\n    \"orientation\": 0,\n    \"strength\": 110\n  }\n}";
        NextRound nextRound = om.readValue(json, NextRound.class);
        ArrayList<VisibleEntitie> entities1 = new ArrayList<VisibleEntitie>();
        entities1.add(new Reef(VisibleEntities.REEF.toString(), new Position(1,1,1), new Circle()));
        //NextRound nextRound = new NextRound(ship,new Wind(1,10), entities1);
        //System.out.println(nextRound.getShip().getShape().toString());
        //System.out.println(om.writeValueAsString(nextRound));
        String jsonInit = "{\n  \"goal\": {\n    \"mode\": \"REGATTA\",\n    \"checkpoints\": [\n      {\n        \"position\": {\n          \"x\": 1000,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      },\n      {\n        \"position\": {\n          \"x\": 0,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      }\n    ]\n  },\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"sailors\": [\n    {\n      \"x\": 0,\n      \"y\": 0,\n      \"id\": 0,\n      \"name\": \"Edward Teach\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 1,\n      \"id\": 1,\n      \"name\": \"Edward Pouce\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 2,\n      \"id\": 2,\n      \"name\": \"Tom Pouce\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 0,\n      \"id\": 3,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 1,\n      \"id\": 4,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 2,\n      \"id\": 5,\n      \"name\": \"Tom Pouce\"\n    }\n  ],\n  \"shipCount\": 1\n}";
        Game game = om.readValue(jsonInit, Game.class);
        //System.out.println(game.getShip().getEntities());
        //System.out.println(om.writeValueAsString(game));

        String shape = om.writeValueAsString(new Circle("circle", 1));
        //System.out.println(shape);
        //Circle la = om.readValue(shape, Circle.class);

        // Test WEEK2
        Cockpit cockpitW2 = new Cockpit();
        String initialiser2 = "{\n  \"goal\": {\n    \"mode\": \"REGATTA\",\n    \"checkpoints\": [\n      {\n        \"position\": {\n          \"x\": 1000,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      },\n      {\n        \"position\": {\n          \"x\": 0,\n          \"y\": 0,\n          \"orientation\": 0\n        },\n        \"shape\": {\n          \"type\": \"circle\",\n          \"radius\": 50\n        }\n      }\n    ]\n  },\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"sailors\": [\n    {\n      \"x\": 0,\n      \"y\": 0,\n      \"id\": 0,\n      \"name\": \"Edward Teach\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 1,\n      \"id\": 1,\n      \"name\": \"Edward Pouce\"\n    },\n    {\n      \"x\": 0,\n      \"y\": 2,\n      \"id\": 2,\n      \"name\": \"Tom Pouce\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 0,\n      \"id\": 3,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 1,\n      \"id\": 4,\n      \"name\": \"Jack Teach\"\n    },\n    {\n      \"x\": 1,\n      \"y\": 2,\n      \"id\": 5,\n      \"name\": \"Tom Pouce\"\n    }\n  ],\n  \"shipCount\": 1\n}";
        cockpitW2.initGame(initialiser2);
        String nextRound2 = "{\n  \"ship\": {\n    \"type\": \"ship\",\n    \"life\": 100,\n    \"position\": {\n      \"x\": 0,\n      \"y\": 0,\n      \"orientation\": 0\n    },\n    \"name\": \"Les copaings d'abord!\",\n    \"deck\": {\n      \"width\": 3,\n      \"length\": 6\n    },\n    \"entities\": [\n      {\n        \"x\": 1,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 1,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 3,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 0,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 4,\n        \"y\": 2,\n        \"type\": \"oar\"\n      },\n      {\n        \"x\": 2,\n        \"y\": 1,\n        \"type\": \"sail\",\n        \"openned\": false\n      },\n      {\n        \"x\": 5,\n        \"y\": 0,\n        \"type\": \"rudder\"\n      }\n    ],\n    \"shape\": {\n      \"type\": \"rectangle\",\n      \"width\": 3,\n      \"height\": 6,\n      \"orientation\": 0\n    }\n  },\n  \"visibleEntities\": [\n    {\n      \"type\": \"stream\",\n      \"position\": {\n        \"x\": 500,\n        \"y\": 0,\n        \"orientation\": 0\n      },\n      \"shape\": {\n        \"type\": \"rectangle\",\n        \"width\": 50,\n        \"height\": 500,\n        \"orientation\": 0\n      },\n      \"strength\": 40\n    }\n  ],\n  \"wind\": {\n    \"orientation\": 0,\n    \"strength\": 110\n  }\n}";
        String actionsW2 = cockpitW2.nextRound(nextRound2);
        System.out.println(actionsW2);
        NextRound nr = om.readValue(nextRound2, NextRound.class);
        //System.out.println();
        //System.out.println(om.writeValueAsString(nr));

        Vector vector = new Vector(0,3);
        Vector vector1 = new Vector(2,-1);
        Vector vector2 = new Vector(-2,-1);
        //System.out.println(vector.angleBetweenVectors(vector1));
        //System.out.println(vector.angleBetweenVectors(vector2));

         */

        /*
        Ship ship = new Ship("ship", 100,
                new Position(0.0, 0.0, 0.0), "name",
                new Deck(2, 5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5, 5, 5));
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        ArrayList<Sailor> sailors = new ArrayList<>();
        Game jeu = new Game(
                new Goal("regatte", checkpoints),
                ship,
                sailors,
                4
        );
        Checkpoint checkpoint = new Checkpoint(new Position(-2, 2, 0), new Circle("circle", 1));
        checkpoints.add(checkpoint);
        Sailor sailor = new Sailor(1, 1, 0, "sailor1");
        Sailor sailor2 = new Sailor(1, 1, 0, "sailor2");
        sailors.add(sailor);
        sailors.add(sailor2);
        Strategy strategy = new Strategy(jeu);

        Vector vector_ship = new Vector(1, 1);
        Vector vector_chechpoint = new Vector(2, -2);
        double angle = vector_ship.angleBetweenVectors(vector_chechpoint);
        //System.out.println(strategie.checkpointEstAGauche(checkpoint, angle));
        Vector vector_ship2 = new Vector(1, 1);
        Vector vector_chechpoint2 = new Vector(2, -2);
        double angle2 = Math.atan(vector_chechpoint2.getY() / vector_chechpoint2.getX()) - Math.atan(vector_ship2.getY() / vector_ship2.getX());
        // checkpoint = new Checkpoint(new Position(2, -2, 0), new Circle("circle", 1));
        //System.out.println(Math.atan(vector_chechpoint2.getY()/vector_chechpoint2.getX()) - Math.atan(vector_ship2.getY()/vector_ship2.getX()));
        //System.out.println(strategie.checkpointEstAGauche(checkpoint, angle2));
        Vector vector_ship3 = new Vector(1, 0);
        Vector vector_chechpoint3 = new Vector(-1, -1);
        //System.out.println(Math.atan(vector_chechpoint3.getY()/vector_chechpoint3.getX()) - Math.atan(vector_ship3.getY()/vector_ship3.getX()));
        //System.out.println(vector_chechpoint3.angleBetweenVectors(vector_ship3));

        // ArrayList<Point> pointsShip = new ArrayList<>();
        // pointsShip.add(new Point(4, 1));
        // pointsShip.add(new Point(6, 1));
        // pointsShip.add(new Point(4, 5));
        // pointsShip.add(new Point(6, 5));
        Checkpoint checkpointValide = new Checkpoint(new Position(3.5,3,0), new Circle("circle", 1));
        Checkpoint checkpointValideDunPoint = new Checkpoint(new Position(5,6,0), new Circle("circle", 1));
        //System.out.println(strategie.intersectionCircleShip(pointsShip, checkpointValideDunPoint));

        boolean test = 1 < 2;
        System.out.println(test);
        test = false || test;
        System.out.println( test);

    */

    }

}

