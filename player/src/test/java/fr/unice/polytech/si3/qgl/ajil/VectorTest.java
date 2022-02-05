package fr.unice.polytech.si3.qgl.ajil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.management.GarbageCollectorMXBean;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector v1;
    Vector v2;
    Vector v3;
    Vector v4;
    Vector v5;

    //Sauvegarde de l'ancienne valeur, car les operations peuvent modifier l'objet
    Vector ancienneValeurV1;
    Vector ancienneValeurV2;
    Vector ancienneValeurV3;
    Vector ancienneValeurV4;
    Vector ancienneValeurV5;


    @BeforeEach
    void setUp() {
        v1 = new Vector(5,8);
        v2 = new Vector(10,20);
        v3 = new Vector(-6,-7);
        v4 = new Vector(1.456,5.45);
        v5 = new Vector(0,0);

        ancienneValeurV1=v1.clone();
        ancienneValeurV2=v2.clone();
        ancienneValeurV3=v3.clone();
        ancienneValeurV4=v4.clone();
        ancienneValeurV5=v5.clone();

    }

    @Test
    void magnitudeAvecVectorEntier(){
        double n = v1.magnitude();
        double m = v2.magnitude();
        assertEquals(n,Math.sqrt(v1.getX()*v1.getX()+v1.getY()*v1.getY()));
        assertEquals(m,Math.sqrt(v2.getX()*v2.getX()+v2.getY()*v2.getY()));
    }

    @Test
    void magnitudeAvecVectorDecimal(){
        double k = v4.magnitude();
        assertEquals(k,Math.sqrt(31.822436));
    }

    @Test
    void magnitudeVectorNul(){
        double k = v5.magnitude();
        assertEquals(k,0);
    }

    //On vérifie que seul v1 est modifié
    @Test
    void addVectors(){
        v1.addVector(v2);
        assertEquals(v1.getX(),ancienneValeurV1.getX()+v2.getX());
        assertEquals(v1.getY(),ancienneValeurV1.getY()+v2.getY());

        assertEquals(v2,ancienneValeurV2);
    }

    //V5 est le vecteur null
    @Test
    void addNullVector(){
        v1.addVector(v5);
        assertEquals(v1,ancienneValeurV1);
        assertEquals(v5,ancienneValeurV5);
    }

    @Test
    void subVectors(){
        v1.subVector(v2);
        assertEquals(v1.getX(),ancienneValeurV1.getX()-v2.getX());
        assertEquals(v1.getY(),ancienneValeurV1.getY()-v2.getY());
    }

    @Test
    void subWithNegativeAVector(){
        v5.subVector(v3);
        assertEquals(v5.getX(),ancienneValeurV5.getX()-v3.getX());
        assertEquals(v5.getY(),ancienneValeurV5.getY()-v3.getY());
    }

    @Test
    void scalarMultiplying(){

        double p = -3.45;
        v4.scalerMultypling(p);
        assertEquals(v4.getX(),ancienneValeurV4.getX()*p);
        assertEquals(v4.getY(),ancienneValeurV4.getY()*p);
    }

    @Test
    void scalarMultiplyingBy0(){
        double p = 0;
        v4.scalerMultypling(p);
        assertEquals(v4.getX(),0);
        assertEquals(v4.getY(),0);
    }

    @Test
    void dotProduct(){
        double tmp = v1.dotProduct(v2);
        assertEquals(tmp,v1.getX()*v2.getX()+v1.getY()*v2.getY());

        tmp = v3.dotProduct(v4);
        assertEquals(tmp,v3.getX()*v4.getX()+v3.getY()*v4.getY());

        assertEquals(v4.dotProduct(v1),v1.dotProduct(v4));

    }

    @Test
    void isCollinear(){
        assertFalse(v1.isCollinear(v2));
        assertFalse(v1.isCollinear(v3));
        assertFalse(v1.isCollinear(v4));

        //Cas avec le vecteur nul
        assertTrue(v1.isCollinear(v5));

        //Cas avec une coordonnée nulle
        Vector v6 = new Vector(4,0);
        assertFalse(v1.isCollinear(v6));

        //Cas colineaire
        double p =-4.567;
        Vector v7 = new Vector(p*v3.getX(),p*v3.getY());
        assertTrue(v3.isCollinear(v7));

    }

    @Test
    void isNull(){
        assertTrue(v5.isNull());
        assertFalse(v1.isNull());
    }

    @Test
    void perdendiculaire(){
        assertFalse(v1.isPerpendicular(v2));
        assertTrue(v1.isPerpendicular(v5));

        Vector v6 = new Vector(2,2);
        Vector v7 = new Vector(-2,2);

        assertEquals(v6.dotProduct(v7),0);
        assertTrue(v6.isPerpendicular(v7));
        assertTrue(v7.isPerpendicular(v6));
    }

    @Test
    void testClonageVector(){
        Vector bis = v1.clone();
        assertEquals(v1,bis);
    }

    @Test
    void testEgalite(){
        assertTrue(v1.equals(v1));
        assertFalse(v1.equals(v2));
        assertFalse(v1.equals(v4));
        assertEquals(v1,v1.clone());
    }

    @Test
    void calculAngleSimple() {

        Vector v6 = new Vector(0, 1);
        Vector v7 = new Vector(0, 1);
        assertEquals(v6.angleBetweenVectors(v7), 0);

        v6 = new Vector(0, 1);
        v7 = new Vector(1, 0);

        double repRounded = (double) Math.round(Math.PI / 2 * 1000) / 1000;
        assertEquals(v6.angleBetweenVectors(v7), repRounded);
    }

    @Test
    void calculAngleComplexes() {

        //https://onlinemschool.com/math/assistance/vector/angl/ Pour verifier les valeurs

        double answer = 0.094956690902487;
        double repRounded = (double) Math.round(answer * 1000) / 1000;
        assertEquals(v1.angleBetweenVectors(v2), repRounded);

        answer =2.69402553;
        repRounded = (double) Math.round(answer * 1000) / 1000;
        assertEquals(v3.angleBetweenVectors(v4), repRounded);


    }


}