package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import java.util.PriorityQueue;

public class AStar {

    // les couts pour un déplacement Vertical / Horizontal / Diagonal
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;

    // Cellules de la grille
    public Cell[][] grid;

    //On définit une queue de priorité
    //Open Cells : l'ensemble des nœuds à évaluer
    // On place les cellules qui coutent moins en premier
    private PriorityQueue<Cell> openCells;

    // Closed Cells : L'ensemble de noeuds déjà évalués
    private boolean[][] closedCells;

    //Start cell
    private int startI, startJ;
    //End cell
    private int endI, endJ;

    public AStar(int width, int height, int si, int sj, int ei, int ej, int[][] obstacles){
        grid = new Cell[width][height];
        closedCells = new boolean[width][height];
        openCells = new PriorityQueue<Cell>(
                (Cell c1, Cell c2) -> {
                    return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0 ;
                }
        );

        startCell(si, sj);
        endCell( ei, ej );

        // init heuristic et cells
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length ; j++){
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs( j - endJ);
                grid[i][j].solution = false;
            }
        }

        grid[startI][startJ].finalCost = 0;

        //On met les obstacles dans la grille
        for (int i = 0; i < obstacles.length; i++){
            addObstaclesSurCell(obstacles[i][0], obstacles[i][1]);
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

        }
    }
}
