package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

// Une cellule de la grille
public class Cell {

    // coordonnées
    final int i;
    final int j;
    // Cellule parent
    Cell parent;
    // cout heuristique de la cellule actuelle
    int heuristicCost;
    // cout final
    int finalCost; // G + H avec
    // G(n) le cout du chemin à partir de début de chemin
    // H(n) estime le cout heuristique du chemin le moins couteux à partir de n vers la cible

    // Dit si la cellule fait partie de la solution finale
    boolean solution;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return "[" + i + "," + j + "]";
    }
}
