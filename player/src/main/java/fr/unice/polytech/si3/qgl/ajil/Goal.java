package fr.unice.polytech.si3.qgl.ajil;

import java.util.ArrayList;

public class Goal {
    private String goal;
    private ArrayList<Checkpoint> checkpoints;

    public Goal(){}
    public Goal(String goal, ArrayList<Checkpoint> checkpoints){
        this.checkpoints = checkpoints;
        this.goal = goal;
    }

    public ArrayList<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public String getGoal() {
        return goal;
    }

    void setCheckpoints(ArrayList<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    void setGoal(String goal) {
        this.goal = goal;
    }
}
