package fr.unice.polytech.si3.qgl.ajil;

public class Vector {
    private double x;
    private double y;

    Vector(double x, double y){
        this.x=x;
        this.y=y;
    }

    //Retourne la norme du vecteur
    double magnitude(){
        return Math.sqrt(x*x+y*y);
    }

    void addVector(Vector other){
        this.x+= other.getX();
        this.y+=other.getY();
    }

    void subVector(Vector other){
        this.x-=other.getX();
        this.y-=other.getY();
    }

    void scalerMultypling(double n){
        this.x*=n;
        this.y*=n;
    }


    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }




    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }



}
