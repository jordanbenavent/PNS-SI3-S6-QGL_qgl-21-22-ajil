package fr.unice.polytech.si3.qgl.ajil;

/**
 * Classe Sailor représentant un marin
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Sailor {

    private int x;
    private int y;
    private int id;
    private String name;

    public Sailor(){}
    public Sailor(int x, int y, int id, String name){
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * @return la coordonnée en x du marin
     */
    public int getX() {
        return x;
    }

    /**
     * Modifie la coordonnée en x du marin
     * @param x
     */
    void setX(int x) {
        this.x = x;
    }

    /**
     * @return la coordonnée en y du marin
     */
    public int getY() {
        return y;
    }

    /**
     * Modifie la coordonnée en y du marin
     * @param y
     */
    void setY(int y) {
        this.y = y;
    }

    /**
     * @return l'id du marin
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'id du marin
     * @param id
     */
    void setId(int id) {
        this.id = id;
    }

    /**
     * @return le nom du marin
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du marin
     * @param name
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * @return un string composé des coordonnées du marin ainsi que de son id et de son nom
     */
    @Override
    public String toString() {
        return "Sailor{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
