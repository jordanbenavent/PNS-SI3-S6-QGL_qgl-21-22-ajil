package fr.unice.polytech.si3.qgl.ajil.strategy;

public class GestionRotation {

    private double angle; //L'angle duquel on doit tourner
    private boolean gouvernailDisponible;

    GestionRotation(double angle, boolean gouvernailDisponible){
        this.angle=angle;
        this.gouvernailDisponible=gouvernailDisponible;
    }


    //Si l'angle a une valeur absolue supérieure a pi/4, il faudra aussi utiliser les marins pour ramer
    boolean valeurAngleSuperieurePiSur4(){
        return Math.abs(angle) > Math.PI / 4;
    }

    void typeRotation(){
        if(gouvernailDisponible){
            tournerAvecGouvernail();
        }
        else{
            tournerSansGouvernail();
        }
    }


    void tournerAvecGouvernail(){


        if(valeurAngleSuperieurePiSur4()){
            //Retrancher parmi les valeurs d'angles de rotation possibles et ajuster avec le gouvernail

        }


        else{ //On utilise que le gouvernail

        }


    }

    


    void tournerSansGouvernail(){
        //Ce qu'on faisait avant

    }





}
