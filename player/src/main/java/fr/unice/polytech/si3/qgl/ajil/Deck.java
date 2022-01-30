package fr.unice.polytech.si3.qgl.ajil;

public class Deck {
    private int width;
    private int length;

    public Deck(){}
    public Deck(int width, int length){
        this.length = length;
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    void setLength(int length) {
        this.length = length;
    }
}
