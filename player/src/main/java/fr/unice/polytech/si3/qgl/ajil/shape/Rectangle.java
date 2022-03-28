package fr.unice.polytech.si3.qgl.ajil.shape;

/**
 * Classe fille de Shape représentant un rectangle
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Rectangle extends Shape{

    private double width;
    private double height;
    private double orientation;

    public Rectangle(){
        setType("rectangle");
    }

    public Rectangle(String type, double width, double height, double orientation){
        super(type);
        this.height = height;
        this.width = width;
        this.orientation = orientation;
    }

    /**
     * @return l'orientation du rectangle
     */
    @Override
    public double getOrientation() {
        return orientation;
    }

    /**
     * @return la hauteur du rectangle
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return la largeur du rectangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * Modifie l'orientation du rectangle
     * @param orientation
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * Modifie la hauteur du rectangle
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Modifie la largeur du rectangle
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return un string composé de la largeur, la longueur ainsi que de l'orientation du rectangle
     */
    @Override
    public String toString() {
        return super.toString()+ "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", orientation=" + orientation +
                '}';
    }
}
