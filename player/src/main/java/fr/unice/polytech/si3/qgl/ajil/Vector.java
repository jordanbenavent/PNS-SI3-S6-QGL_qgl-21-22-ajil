package fr.unice.polytech.si3.qgl.ajil;

/**
 * Classe Vecteur permettant de pouvoir réaliser des calculs dans l'espace pour le déplacement du bateau
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y){
        this.x=x;
        this.y=y;
    }

    /**
     * @return la norme du vecteur
     */
    public double magnitude(){
        return Math.sqrt(x*x + y*y);
    }

    /**
     * @return true si le vecteur est nul, false sinon
     */
    public boolean isNull(){
        return this.magnitude() == 0;
    }

    /**
     * Ajoute les coordonnées d'un vecteur aux coordonnées de this
     * @param other
     */
    public void addVector(Vector other){
        this.x += other.getX();
        this.y += other.getY();
    }

    /**
     * Soustrait les coordonnées d'un vecteur aux coordonnées de this
     * @param other
     */
    public void subVector(Vector other){
        this.x -= other.getX();
        this.y -= other.getY();
    }

    /**
     * Multiplie les coordonnées de this par un réel
     * @param n
     */
    public void scalerMultypling(double n){
        this.x *= n;
        this.y *= n;
    }

    /**
     * @param other
     * @return le produit scalaire entre le vecteur this et un autre vecteur
     */
    public double dotProduct(Vector other){
        return this.x * other.getX() + this.y * other.getY();
    }


    //Il faudra gérer l'orientation car l'angle retourné n'est que entre -Pi et Pi (à cause d'arcos)

    /**
     * @param other
     * @return l'angle entre le vecteur this et un autre vecteur
     */
    public double angleBetweenVectors(Vector other){
        double a = this.x*other.y - this.y*other.x;
        double b = this.x*other.x + this.y*other.y;

        double ans=Math.atan2(a,b);
        double repARounded = (double) Math.round(ans * 1000) / 1000;
        return repARounded;
    }

    /**
     * @param other
     * @return true si les vecteurs sont colinéaires, false sinon
     */
    public boolean isCollinear(Vector other){
        return this.x*other.y- this.y*other.x == 0;
    }

    /**
     * @param other
     * @return true si les vecteurs sont perpendiculaires, false sinon
     */
    public boolean isPerpendicular(Vector other){
        return this.dotProduct(other)==0;
    }

    /**
     * @param o
     * @return true si les vecteurs sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0;
    }

    /**
     * @return un string avec les coordonnées du vecteur this
     */
    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * @return la coordonnée x du vecteur
     */
    public double getX() {
        return x;
    }

    /**
     * Modifie la coordonnée x du vecteur
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return la coordonnée y du vecteur
     */
    public double getY() {
        return y;
    }

    /**
     * Modifie la coordonée y du vecteur
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Permet de pouvoir cloner un vecteur, utile pour les tests
     * @return le vecteur cloné
     */
    public Vector clone(){
        Vector tmp = new Vector(this.getX(),this.getY());
        return tmp;
    }
}
