package fr.unice.polytech.si3.qgl.ajil.maths;

import java.util.ArrayList;
import java.util.List;

public class Matrice<AnyType> {

    private final List<List<AnyType>> matrice;

    public Matrice() {
        matrice = new ArrayList<>();
        matrice.add(new ArrayList<>());
    }

    public List<List<AnyType>> getMatrice() {
        return matrice;
    }

    public List<AnyType> getColumn(int i) {
        return matrice.get(i);
    }

    public AnyType getElement(int i, int j) {
        return matrice.get(i).get(j);
    }

    public void addElement(int i, int j, AnyType element) {
        while (i >= matrice.size()) matrice.add(new ArrayList<>());
        matrice.get(i).add(j, element);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (List<AnyType> anyTypes : matrice) {
            System.out.println(anyTypes);
            for (AnyType element : anyTypes) {
                string.append(element).append(" ");
            }
        }
        string.append("\n");
        return string.toString();
    }
}
