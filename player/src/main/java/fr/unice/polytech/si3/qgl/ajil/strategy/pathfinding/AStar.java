package fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding;

import fr.unice.polytech.si3.qgl.ajil.Cockpit;
import fr.unice.polytech.si3.qgl.ajil.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    private ArrayList<Position> chemin;
    public List<String> LOGGER = Cockpit.LOGGER;

    // les couts pour un déplacement Vertical / Horizontal / Diagonal
    public static final int DIAGONAL_COST = 11;
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
        if ( i != startI && i != endJ && j != startJ && j != endJ ){
            grid[i][j] = null;
        }
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
        System.out.println("Grid i length : " + grid.length);
        System.out.println("Grid j length : " + grid[0].length);
        System.out.println("Algo AStar startI : " + startJ);
        System.out.println("Algo AStar startJ : " + startI);
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

    // Visualisation
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";

    // Affichage
    public void display(){
        System.out.println("Griille / Map :");
        for (int i = 0; i< grid.length; i++){
            for (int j = 0; j<grid[i].length; j++){
                if (i == startI && j == startJ){
                    System.out.print(ANSI_WHITE_BACKGROUND +ANSI_CYAN + "O   " + ANSI_RESET); // Origine
                }
                else if ( i == endI && j == endJ ){
                    System.out.print(ANSI_YELLOW_BACKGROUND + "TA  " + ANSI_RESET); // Target
                }
                else if ( grid[i][j] != null){
                    System.out.printf(ANSI_BLUE_BACKGROUND + "%-3d "+ANSI_RESET, 0);
                }
                else {
                    System.out.print(ANSI_RED_BACKGROUND +"BL  "+ ANSI_RESET);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayScores(){
        System.out.println("Score de chaque cellule :");
        for (int i = 0; i< grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null){
                    System.out.printf("%-3d ", grid[i][j].finalCost);
                }
                else {
                    System.out.print(ANSI_RED_BACKGROUND +"BL  "+ ANSI_RESET);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Calcul et return une liste de Positions
    public ArrayList<Position> obtenirLeChemin(){
        chemin = new ArrayList<>();
        process();
        if (closedCells[endI][endJ]){
            Cell current = grid[endI][endJ];
            chemin.add(new Position((double) current.i, (double) current.j, 0));
            grid[current.i][current.j].solution = true;
            while (current.parent != null){
                chemin.add(new Position((double) current.parent.i, (double) current.parent.j, 0));
                grid[current.parent.i][current.parent.j].solution = true;
                current = current.parent;
            }
        } else {
            LOGGER.add("Il n'y a pas de Chemin. ");
        }
        Collections.reverse(chemin);
        return chemin;
    }

    public void displaySolution(){
        if (closedCells[endI][endJ]){
            // On retrace le chemin !!!
            System.out.println("Chemin :");
            Cell current = grid[endI][endJ];
            System.out.println(current);
            grid[current.i][current.j].solution = true;

            while (current.parent != null){
                System.out.print(" -> " + current.parent);
                grid[current.parent.i][current.parent.j].solution = true;
                current = current.parent;
            }
            System.out.println();
            for (int i = 0; i< grid.length; i++){
                for (int j = 0; j<grid[i].length; j++){
                    if (i == startI && j == startJ){
                        System.out.print(ANSI_WHITE_BACKGROUND +ANSI_CYAN + "O   " + ANSI_RESET); // Origine
                    }
                    else if ( i == endI && j == endJ ){
                        System.out.print(ANSI_YELLOW_BACKGROUND + "TA  " + ANSI_RESET); // Target
                    }
                    else if ( grid[i][j] != null){
                        System.out.printf("%-3s", grid[i][j].solution ? ANSI_WHITE_BACKGROUND +ANSI_CYAN + "X   " + ANSI_RESET : (ANSI_BLUE_BACKGROUND + "O   "+ANSI_RESET));
                    }
                    else {
                        System.out.print(ANSI_RED_BACKGROUND +"BL  "+ ANSI_RESET);
                    }
                }
                System.out.println();
            }
            System.out.println();
        } else {
            System.out.println("Pas de chemin disponible !");
        }
    }
}
