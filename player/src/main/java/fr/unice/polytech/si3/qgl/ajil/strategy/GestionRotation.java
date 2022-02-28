package fr.unice.polytech.si3.qgl.ajil.strategy;

public class GestionRotation {

    private double angle; //L'angle duquel on doit tourner
    private boolean gouvernailDisponible;

    GestionRotation(double angle, boolean gouvernailDisponible){
        this.angle=angle;
        this.gouvernailDisponible=gouvernailDisponible;
    }

    //Si l'angle a une valeur absolue supérieure a pi/4, il faudra aussi utiliser les marins pour ramer
    boolean doitTournerAvecRame(){
        return Math.abs(angle) > Math.PI / 4;
    }

    boolean PeutUtiliserGouvernail(){
        return gouvernailDisponible;
    }

    void rotation(){
        if(PeutUtiliserGouvernail() && doitTournerAvecRame()){
            


        }


    }





}
