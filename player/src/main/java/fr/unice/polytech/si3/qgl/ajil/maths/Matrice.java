package fr.unice.polytech.si3.qgl.ajil.maths;

import java.util.ArrayList;
import java.util.List;

public class Matrice < AnyType > {

    private List<List<AnyType>> matrice;

    public Matrice() {
        matrice = new ArrayList<>();
    }

    public List<List<AnyType>> getMatrice() {
        return matrice;
    }

    public List<AnyType> getComlumn(int i){
        return matrice.get(i);
    }

    public AnyType getElement(int i, int j){
        return matrice.get(i).get(j);
    }

    public void addElement(int i, int j, AnyType element){
        matrice.get(i).add(j, element);
    }
}
