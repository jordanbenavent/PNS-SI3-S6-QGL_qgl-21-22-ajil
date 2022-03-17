package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

// Une cellule de la grille
public class Cell {

    // coordonnées
    public int i, j;
    // Cellule parent
    public Cell parent;
    // cout heuristique de la cellule actuelle
    public int heuristicCost;
}
