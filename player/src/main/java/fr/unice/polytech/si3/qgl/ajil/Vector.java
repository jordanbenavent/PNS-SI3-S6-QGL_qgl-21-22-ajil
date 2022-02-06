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

    public boolean isNull(){
        return this.magnitude()==0;
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
        return this.x * other.getX() + this.y * other.getY();
    }


    //Il faudra gérer l'orientation car l'angle retourné n'est que entre -Pi et Pi (à cause d'arcos)


    public double angleBetweenVectors(Vector other){
        //cas ou une des coordonnée est nulle, Exception ou renvoyer 0 ?
        if(other.isNull()||this.isNull()){
            return 0;
        }
        double cos = this.dotProduct(other)/(this.magnitude()*other.magnitude());
        double ans =Math.acos(cos);
        double repARounded = (double) Math.round(ans * 1000) / 1000;
        return repARounded;

    }

    public boolean isCollinear(Vector other){
        return this.x*other.y- this.y*other.x == 0;
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
