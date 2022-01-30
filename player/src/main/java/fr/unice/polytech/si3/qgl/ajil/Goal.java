package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Goal {
    private String mode;
    private ArrayList<Checkpoint> checkpoints;

    public Goal(){}
    public Goal(String mode, ArrayList<Checkpoint> checkpoints){
        this.checkpoints = checkpoints;
        this.mode = mode;
    }

    public ArrayList<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public String getMode() {
        return mode;
    }

    void setCheckpoints(ArrayList<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    void setMode(String mode) {
        this.mode = mode;
    }
}
