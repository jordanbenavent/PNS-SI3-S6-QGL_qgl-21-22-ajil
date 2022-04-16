package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Stream;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class NextRoundTest {

    Ship ship;
    Wind wind;
    List<VisibleEntitie> visibleEntities;
    NextRound nextRound;

    @BeforeEach
    void setUp(){
        ship = new Ship("ship", 100,
                new Position(0.0, 0.0, 0.0), "name",
                new Deck(2, 5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5, 5, 5));
        wind = new Wind(Math.PI/2, 40);
        nextRound = new NextRound(ship, wind, visibleEntities);
    }

    @Test
    void faceAuCourantTest(){
        // Cas 1 le courant n'est pas dans la trajectoire du bateau
        Stream stream1 = new Stream("stream", new Position(0, 50, 0), new Circle("circle", 2), 50);
        Assertions.assertEquals(false, nextRound.faceAuCourant(ship, stream1));
        // Cas 2 le courant est en face du bateau mais opposé au bateau
        Stream stream2 = new Stream("stream", new Position(10, 0, Math.PI), new Circle("circle", 2), 50);
        Assertions.assertEquals(true, nextRound.faceAuCourant(ship, stream2));
        // Cas 3 le courant est en face du bateau mais pas opposé au bateau
        Stream stream3 = new Stream("stream", new Position(10, 0, -0.2), new Circle("circle", 2), 50);
        Assertions.assertEquals(false, nextRound.faceAuCourant(ship, stream3));
        // Cas 4 cas limite à PI/4 (environ)
        Stream stream4 = new Stream("stream", new Position(10, 0, -3.1*Math.PI/4), new Circle("circle", 2), 50);
        Assertions.assertEquals(true, nextRound.faceAuCourant(ship, stream4));
    }

    @Test
    void streamToReefTest(){
        Set<Reef> reefs = new HashSet<>();
        Set<Stream> streams = new HashSet<>();
        // Cas 1 le courant n'est pas dans la trajectoire du bateau
        Stream stream1 = new Stream("stream", new Position(0, 50, 0), new Circle("circle", 2), 50);
        // Cas 2 le courant est en face du bateau mais opposé au bateau avec une force moindre
        Stream stream2 = new Stream("stream", new Position(10, 0, Math.PI), new Circle("circle", 2), 50);
        // Cas 3 le courant est en face du bateau mais pas opposé au bateau avec une force maximum
        Stream stream3 = new Stream("stream", new Position(10, 0, -0.2), new Circle("circle", 2), 165);
        // Cas 4 cas limite à PI/4 (environ) avec force limite
        Stream stream4 = new Stream("stream", new Position(10, 0, -3.1*Math.PI/4), new Circle("circle", 2), 82.5);
        streams.add(stream1);
        streams.add(stream2);
        streams.add(stream3);
        streams.add(stream4);
        // Récifs crée
        Reef reef1 = new Reef("reef", stream4.getPosition(), stream4.getShape());
        nextRound.streamToReef(streams, reefs);
        Assertions.assertEquals(1, reefs.size());
        Iterator<Reef> i = reefs.iterator();
        Assertions.assertEquals(stream4.getPosition().getOrientation(), i.next().getPosition().getOrientation());
    }

}
