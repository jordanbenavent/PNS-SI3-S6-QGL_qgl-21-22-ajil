package fr.unice.polytech.si3.qgl.ajil.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe du package maths représentant une matrice
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Matrice<AnyType> {

    private final List<List<AnyType>> matrix;

    public Matrice() {
        matrix = new ArrayList<>();
        matrix.add(new ArrayList<>());
    }

    /**
     * @return la matrice
     */
    public List<List<AnyType>> getMatrix() {
        return matrix;
    }

    /**
     * @param i
     * @return la ième colonne de la matrice
     */
    public List<AnyType> getColumn(int i) {
        return matrix.get(i);
    }

    /**
     * @param i
     * @param j
     * @return un élément spécifique de la matrice à la ième colonne et la jème ligne
     */
    public AnyType getElement(int i, int j) {
        return matrix.get(i).get(j);
    }

    /**
     * Ajoute un élément à la ième colonne et la jème ligne dans la matrice
     * @param i
     * @param j
     * @param element
     */
    public void addElement(int i, int j, AnyType element) {
        while (i >= matrix.size()) matrix.add(new ArrayList<>());
        matrix.get(i).add(j, element);
    }

    /**
     * @return un string représentant la matrice
     */
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
        return string.toString();
    }
}
