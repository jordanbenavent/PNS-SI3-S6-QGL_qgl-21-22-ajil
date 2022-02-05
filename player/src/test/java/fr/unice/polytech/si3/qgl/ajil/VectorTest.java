package fr.unice.polytech.si3.qgl.ajil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(n,Math.sqrt(89));
        assertEquals(m,Math.sqrt(500));
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




}