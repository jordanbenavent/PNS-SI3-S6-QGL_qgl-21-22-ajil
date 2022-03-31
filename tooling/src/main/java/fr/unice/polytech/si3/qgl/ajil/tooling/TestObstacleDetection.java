package fr.unice.polytech.si3.qgl.ajil.tooling;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Polygone;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.GridCell;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.ObstacleDetection;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;

public class TestObstacleDetection {

    // Couleurs pour affichage
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";

    // Affichage
    public static void main(String[] args) {
        ObstacleDetection obstacleDetection = new ObstacleDetection();
        GridCell[][] grid = obstacleDetection.gridCreation(10,10,10, new Point(0.0,0.0),
                new Position(0.0,0.0,0.0),new Position(1.0,1.0,0.0));
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isBlocked()){
                    System.out.print(ANSI_RED_BACKGROUND+ " x"+ANSI_RESET);
                } else System.out.print(ANSI_GREEN_BACKGROUND+ "  "+ANSI_RESET);
            }
            System.out.println(" ");
        }
        System.out.println(" ");
        System.out.println(" ");
        ArrayList<VisibleEntitie> recifs = new ArrayList<>();
        recifs.add(new Reef("reef", new Position(20.0, 10.0,0.0),
                new Polygone("polygone",0.0,
                        new Point[]{new Point(66.4,13), new Point(49.0, 33.7),
                                new Point(51.1, 50.0), new Point(89.7, 51.2)})
        ));
        obstacleDetection.gridProcess(grid, recifs);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[j][i].isBlocked()) {
                    System.out.print(ANSI_RED_BACKGROUND + " x" + ANSI_RESET);
                } else System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
            }
            System.out.println(" ");
            //System.out.println(grid);
        }
    }
}
