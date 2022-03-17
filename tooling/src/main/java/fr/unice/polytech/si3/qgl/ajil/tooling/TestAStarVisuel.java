package fr.unice.polytech.si3.qgl.ajil.tooling;

import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.AStar;

public class TestAStarVisuel {

    public static void main(String[] args) {
        AStar astar = new AStar(5,5, 0, 0, 3,2,
                new int[][]{
                        {0,4},{2,2},{3,1},{3,3},{2,1},{2,3}
                });
        astar.display();
        astar.process();
        astar.displayScores();
        astar.displaySolution();
    }
}
