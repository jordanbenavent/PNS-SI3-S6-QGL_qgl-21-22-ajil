package fr.unice.polytech.si3.qgl.ajil;


public class Vector {
    private double x;
    private double y;


    public Vector(double x, double y){
        this.x=x;
        this.y=y;
    }

    //Retourne la norme du vecteur
    public double magnitude(){
        return Math.sqrt(x*x+y*y);
    }

    public void addVector(Vector other){
        this.x+= other.getX();
        this.y+=other.getY();
    }

    public void subVector(Vector other){
        this.x-=other.getX();
        this.y-=other.getY();
    }

    public void scalerMultypling(double n){
        this.x*=n;
        this.y*=n;
    }

    //Produit scalaire
    public double dotProduct(Vector other){
        return this.x * other.getY() + this.y * other.getX();
    }

    public double angleBetweenVectors(Vector other){
        double cos = this.dotProduct(other)/(this.magnitude()*other.magnitude());
        return Math.acos(cos);
    }

    public boolean isCollinear(Vector other){
        double propX = this.x/other.getX();
        double propY = this.y/other.getY();
        return propX==propY;
    }

    public boolean isPerpendicular(Vector other){
        return this.dotProduct(other)==0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0;
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

    //Pouvoir cloner un vector (pour les tests)
    public Vector clone(){
        Vector tmp = new Vector(this.getX(),this.getY());
        return tmp;
    }



}
