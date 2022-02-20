package fr.unice.polytech.si3.qgl.ajil;

import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe Ship représentant le bateau
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

public class Ship {

    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private ArrayList<Entity> entities;
    private Shape shape;

    ArrayList<Entity> leftOars = new ArrayList<>();
    ArrayList<Entity> rightOars = new ArrayList<>();

    public Ship() {
    }

    public Ship(String type, int life, Position position, String name, Deck deck, ArrayList<Entity> entities, Shape shape) {
        this.type = type;
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shape = shape;
    }

    /**
     * @return le type du bateau
     */
    public String getType() {
        return type;
    }

    /**
     * Modifie le type du bateau
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return les points de vie du bateau
     */
    public int getLife() {
        return life;
    }

    /**
     * Modifie les points de vie du bateau
     * @param life
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * @return la position du bateau
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Modifie la position du bateau
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return le nom du bateau
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du bateau
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return le deck du bateau (à savoir sa largeur et sa longueur)
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Modifie le deck du bateau
     * @param deck
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**
     * @return la liste des entités dans le bateau (les rames, voiles, gouvernail)
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Modifie la liste des entités
     * @param entities
     */
    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    /**
     * @return la forme du bateau (rectangle de longueur, de largeur et d'orientation tant)
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Modifie la forme du bateau
     * @param shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * @return la liste des rames présentes sur le bateau
     */
    public ArrayList<Entity> getOars() {
        ArrayList<Entity> res = new ArrayList<>();
        for (Entity e : entities) {
            if (e.getType().equals("oar")) {
                res.add(e);
            }
        }
        return res;
    }

    /**
     * Ajoute les rames dans les listes leftOars et rightOars en fonction de leur position dans le bateau
     */
    public void placeOars() {
        ArrayList<Entity> oars = getOars();
        for (Entity oar : oars) {
            if (oar.getType().equals("oar")) {
                if (oar.getY() == 0) {
                    leftOars.add(oar);
                } else {
                    rightOars.add(oar);
                }
            }
        }
    }

    /**
     * Renvoie toutes les possibilités d'angle que le bateau peut adopter en tournant avec un certain nombre de rames
     * @return un Set<Double> regroupant tous les angles possiblement effectuables
     */
    public Set<Double> getTurnRange() {
        Set<Double> range = new HashSet<>();
        int size = getOars().size();
        range.add(0.0);
        for (int i = 0; i <= size / 2; i++) {
            range.add(-Math.PI * i / size);
        }
        for (int i = 0; i <= size / 2; i++) {
            range.add(Math.PI * i / size);
        }
        range.remove(-0.0);
        return range;
    }
}
