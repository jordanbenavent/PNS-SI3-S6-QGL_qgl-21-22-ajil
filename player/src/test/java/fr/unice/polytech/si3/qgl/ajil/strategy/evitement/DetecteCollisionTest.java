package fr.unice.polytech.si3.qgl.ajil.strategy.evitement;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Mockito

class DetecteCollisionTest {

    DetecteCollision detecteCollision;
    private Position initiale ;
    private Position tourneDemiTourSensTrigo;
    private Position tourneDemiTourSensAntiTrigo;
    private Position orientationRetourne;
    private Deplacement nouveau;
    private Deplacement toutDroit;

    @BeforeEach
    void setUp(){
        detecteCollision = new DetecteCollision(null,null);
       initiale = new Position(0,0,0);
       tourneDemiTourSensTrigo = new Position(0,0,Math.PI/2);
       tourneDemiTourSensAntiTrigo = new Position(0,0,-Math.PI/2);
       orientationRetourne = new Position(0,0,Math.PI);
       nouveau = new Deplacement(0,0);
       toutDroit = new Deplacement(165,0);

    }


    @Test
    void toutDroitFuturePosition(){
        Position res = detecteCollision.futurePosition(initiale,toutDroit);
        System.out.println(res);
        Position attente = new Position(165,0,0);
        assertEquals(attente,res);
    }

    @Test
    void retournerDePiSurDeuxFuturePosition(){
        Position res = detecteCollision.futurePosition(tourneDemiTourSensTrigo,toutDroit);
        Position attente = new Position(0,165,0);
        assertEquals(attente.getX(),res.getX(),0.1);
        assertEquals(attente.getY(),res.getY(),0.1);

        //Meme test mais en commencant avec une orientation de -pi/2 au lieu de +pi/2
        res = detecteCollision.futurePosition(tourneDemiTourSensAntiTrigo,toutDroit);
        attente = new Position(0,-165,0);
        assertEquals(attente.getX(),res.getX(),0.1);
        assertEquals(attente.getY(),res.getY(),0.1);
    }


    @Test
    void bateauRetourneTotalement(){
        Position res = detecteCollision.futurePosition(orientationRetourne,toutDroit);
        Position attente = new Position(-165,0,0);
        assertEquals(attente.getX(),res.getX(),0.1);
        assertEquals(attente.getY(),res.getY(),0.1);

    }

}