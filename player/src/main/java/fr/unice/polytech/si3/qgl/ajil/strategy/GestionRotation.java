package fr.unice.polytech.si3.qgl.ajil.strategy;

/*
Cette classe permet de calculer 2 parametres : l'angle de rotation nécessaire avec le gouvernail et l'angle de la rotation
qui doit être effectué avec les rameurs
 */

public class GestionRotation {

    private double angle; //L'angle duquel on doit tourner
    private int nbRameurs; //Le nombre de rameurs
    private boolean gouvernailDisponible;
    private double angleRotationRameur; //Rotation a faire avec les rameurs
    private double angleRotationGouvernail; //Rotation a faire avec le gouvernail



    GestionRotation(double angle, boolean gouvernailDisponible,int nbRameurs){
        this.angle=angle;
        this.gouvernailDisponible=gouvernailDisponible;
        this.nbRameurs=nbRameurs;
    }


    //Si l'angle a une valeur absolue supérieure a pi/4, il faudra aussi utiliser les marins pour ramer
    boolean valeurAngleSuperieurePiSur4(){
        return Math.abs(angle) > Math.PI / 4;
    }

    void calculRotation(){
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
            angleRotationRameur=0;
            angleRotationGouvernail = angle;
        }


    }


    void tournerSansGouvernail(){
        //Ce qu'on faisait avant

    }






    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isGouvernailDisponible() {
        return gouvernailDisponible;
    }

    public void setGouvernailDisponible(boolean gouvernailDisponible) {
        this.gouvernailDisponible = gouvernailDisponible;
    }

    public double getAngleRotationRameur() {
        return angleRotationRameur;
    }

    public void setAngleRotationRameur(double angleRotationRameur) {
        this.angleRotationRameur = angleRotationRameur;
    }

    public double getAngleRotationGouvernail() {
        return angleRotationGouvernail;
    }

    public void setAngleRotationGouvernail(double angleRotationGouvernail) {
        this.angleRotationGouvernail = angleRotationGouvernail;
    }
    public int getNbRameurs() {
        return nbRameurs;
    }
    public void setNbRameurs(int nbRameurs) {
        this.nbRameurs = nbRameurs;
    }





}
