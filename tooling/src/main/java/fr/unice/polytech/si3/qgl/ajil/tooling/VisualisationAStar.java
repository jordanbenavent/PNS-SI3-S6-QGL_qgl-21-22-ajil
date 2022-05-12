package fr.unice.polytech.si3.qgl.ajil.tooling;

import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.AStar;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.Cell;

public class VisualisationAStar {
    private AStar aStar;

    public VisualisationAStar(AStar aStar){
        this.aStar = aStar;
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
        Cell[][] grid = aStar.getGrid();
        for (int i = 0; i< grid[0].length; i++){
            for (int j = 0; j<grid.length; j++){
                if (i == aStar.getStartI() && j == aStar.getStartJ()){
                    System.out.print(ANSI_WHITE_BACKGROUND +ANSI_CYAN + "O   " + ANSI_RESET); // Origine
                }
                else if ( i == aStar.getEndI() && j == aStar.getEndJ()){
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

    public void displayScores() {
        System.out.println("Score de chaque cellule :");
        Cell[][] grid = aStar.getGrid();
        for (Cell[] cells : grid){
            for (Cell cell : cells) {
                if (cell != null) {
                    System.out.printf("%-3d ", cell.finalCost);
                } else {
                    System.out.print(ANSI_RED_BACKGROUND + "BL  " + ANSI_RESET);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displaySolution(){
        Cell[][] grid = aStar.getGrid();
        int endI = aStar.getEndI();
        int endJ = aStar.getEndJ();
        int startI = aStar.getStartI();
        int startJ = aStar.getStartJ();
        boolean[][] closedCells = aStar.getClosedCells();
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
