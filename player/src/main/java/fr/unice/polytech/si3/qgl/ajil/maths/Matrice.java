package fr.unice.polytech.si3.qgl.ajil.maths;

import java.util.ArrayList;
import java.util.List;

public class Matrice<AnyType> {

    private final List<List<AnyType>> matrix;

    public Matrice() {
        matrix = new ArrayList<>();
        matrix.add(new ArrayList<>());
    }

    public List<List<AnyType>> getMatrix() {
        return matrix;
    }

    public List<AnyType> getComlumn(int i) {
        return matrix.get(i);
    }

    public AnyType getElement(int i, int j) {
        return matrix.get(i).get(j);
    }

    public void addElement(int i, int j, AnyType element) {
        while (i >= matrix.size()) matrix.add(new ArrayList<>());
        matrix.get(i).add(j, element);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (List<AnyType> anyTypes : matrix) {
            System.out.println(anyTypes);
            for (AnyType element : anyTypes) {
                string.append(element).append(" ");
            }
        }
        string.append("\n");
        return "";
    }
}
