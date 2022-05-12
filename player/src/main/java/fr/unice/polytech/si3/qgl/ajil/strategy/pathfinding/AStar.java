package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Cockpit;
import fr.unice.polytech.si3.qgl.ajil.Position;

import java.util.*;

public class AStar {

    private static final List<String> LOGGER = Cockpit.LOGGER;

    // les couts pour un déplacement Vertical / Horizontal / Diagonal
    public static final int DIAGONAL_COST = 11;
    public static final int V_H_COST = 10;

    // Cellules de la grille
    private final Cell[][] grid;

    //On définit une queue de priorité
    //Open Cells : l'ensemble des nœuds à évaluer
    // On place les cellules qui coutent moins en premier
    private final PriorityQueue<Cell> openCells;

    // Closed Cells : L'ensemble de noeuds déjà évalués
    private final boolean[][] closedCells;

    //Start cell
    private int startI;
    private int startJ;
    //End cell
    private int endI;
    private int endJ;

    public AStar(int width, int height, int si, int sj, int ei, int ej, int[][] obstacles) {
        grid = new Cell[width][height];
        closedCells = new boolean[width][height];
        openCells = new PriorityQueue<>(
                Comparator.comparingInt((Cell c) -> c.finalCost)
        );

        startCell(si, sj);
        endCell(ei, ej);

        // init heuristic et cells
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
                grid[i][j].solution = false;
            }
        }

        grid[startI][startJ].finalCost = 0;

        //On met les obstacles dans la grille
        for (int[] obstacle : obstacles) {
            addObstaclesSurCell(obstacle[0], obstacle[1]);
        }

    }

    private void endCell(int ei, int ej) {
        this.endI = ei;
        this.endJ = ej;
    }

    private void startCell(int si, int sj) {
        this.startI = si;
        this.startJ = sj;
    }

    private void addObstaclesSurCell(int i, int j) {
        if ( i == startI && j == startJ ){
            return;
        }
        if (i == endI && j == endJ){
            return;
        }
        grid[i][j] = null;
    }

    public void updateCostIfNeeded(Cell currentCell, Cell target, int cost){
        if (target == null || closedCells[target.i][target.j]){
            return;
        }
        int targetFinalCost = target.heuristicCost + cost;
        boolean isOpen = openCells.contains(target);

        if (!isOpen || targetFinalCost < target.finalCost){
            target.finalCost = targetFinalCost;
            target.parent = currentCell;
            if (!isOpen){
                openCells.add(target);
            }
        }

    }

    public void process(){
        // on ajoute le placement de départ dans openCells
        openCells.add(grid[startI][startJ]);
        Cell current;

        while (true){
            current = openCells.poll();
             if (current == null){
                 break;
             }

             closedCells[current.i][current.j] = true;

             if (current.equals(grid[endI][endJ])){
                 return;
             }
             Cell target;
             if(current.i -1 >= 0){
                 target = grid[current.i -1][current.j];
                 updateCostIfNeeded(current, target, current.finalCost + V_H_COST);

                 if (current.j -1 >= 0){
                     target = grid[current.i -1][current.j - 1];
                     updateCostIfNeeded(current, target, current.finalCost + DIAGONAL_COST);
                 }
                 if (current.j + 1 < grid[0].length){
                     target = grid[current.i -1][current.j + 1];
                     updateCostIfNeeded(current, target, current.finalCost + DIAGONAL_COST);
                 }
             }
             if (current.j - 1 >= 0){
                 target = grid[current.i][current.j - 1];
                 updateCostIfNeeded(current, target, current.finalCost + V_H_COST);
             }

            if (current.j + 1 < grid[0].length){
                target = grid[current.i][current.j + 1];
                updateCostIfNeeded(current, target, current.finalCost + V_H_COST);
            }

            if (current.i + 1 < grid.length){
                target = grid[current.i + 1][current.j];
                updateCostIfNeeded(current, target, current.finalCost + V_H_COST);

                if (current.j - 1 >= 0){
                    target = grid[current.i + 1][current.j - 1];
                    updateCostIfNeeded(current, target, current.finalCost + DIAGONAL_COST);
                }

                if (current.j + 1 < grid[0].length){
                    target = grid[current.i + 1][current.j + 1];
                    updateCostIfNeeded(current, target, current.finalCost + DIAGONAL_COST);
                }
            }
        }
    }

    // Calcul et return une liste de Positions
    public List<Position> obtenirLeChemin() {
        List<Position> chemin = new ArrayList<>();
        process();
        if (closedCells[endI][endJ]) {
            Cell current = grid[endI][endJ];
            chemin.add(new Position(current.i, current.j, 0));
            grid[current.i][current.j].solution = true;
            while (current.parent != null) {
                chemin.add(new Position(current.parent.i, current.parent.j, 0));
                grid[current.parent.i][current.parent.j].solution = true;
                current = current.parent;
            }
        } else {
            LOGGER.add("Il n'y a pas de Chemin. ");
        }
        Collections.reverse(chemin);
        return chemin;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public boolean[][] getClosedCells() {
        return closedCells;
    }

    public int getStartI() {
        return startI;
    }

    public int getStartJ() {
        return startJ;
    }

    public int getEndI() {
        return endI;
    }

    public int getEndJ() {
        return endJ;
    }
}
