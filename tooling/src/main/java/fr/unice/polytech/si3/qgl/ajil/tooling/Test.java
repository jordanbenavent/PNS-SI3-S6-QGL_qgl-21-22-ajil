package fr.unice.polytech.si3.qgl.ajil.tooling;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(new Entity());
        Ship ship = new Ship("Pirate", 100, new Position(1,2,3.1415),"Ic&o", new Deck(1,2), entities, new ShipShape("carre",1,1,1 ));
        String test = om.writeValueAsString(ship);
        System.out.println(test);
        Moving moving = new Moving(1,1,1);
        Moving moving1 = new Moving(2, 0,0);
        ArrayList<Moving> list = new ArrayList<>();
        list.add(moving); list.add(moving1);
        test = om.writeValueAsString(list);
        System.out.println(test);
    }
}
