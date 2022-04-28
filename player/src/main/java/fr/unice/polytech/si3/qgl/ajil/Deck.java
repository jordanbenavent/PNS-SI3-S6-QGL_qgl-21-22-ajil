package fr.unice.polytech.si3.qgl.ajil;

/**
 * Classe Deck représentant le bateau sous forme de quadrillage de même longueur et même largeur que celles du bateau
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Deck {

    private int width;
    private int length;

    public Deck() {
    }

    public Deck(int width, int length) {
        this.length = length;
        this.width = width;
    }

    /**
     * @return la largeur du bateau
     */
    public int getWidth() {
        return width;
    }

    /**
     * Modifie la largeur du bateau
     *
     * @param width
     */
    void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return la longueur du bateau
     */
    public int getLength() {
        return length;
    }

    /**
     * Modifie la longueur du bateau
     *
     * @param length
     */
    void setLength(int length) {
        this.length = length;
    }
}
