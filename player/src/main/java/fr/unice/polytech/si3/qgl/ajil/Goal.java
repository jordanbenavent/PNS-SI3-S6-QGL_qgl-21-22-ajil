package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Goal représentant "l'objectif" de la partie, incluant le mode de jeu et la liste de checkpoints à atteindre
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Goal {

    private String mode;
    private List<Checkpoint> checkpoints;

    public Goal(){}

    public Goal(String mode, List<Checkpoint> checkpoints){
        this.checkpoints = checkpoints;
        this.mode = mode;
    }

    /**
     * @return la liste des checkpoints
     */
    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    /**
     * @return le mode de jeu
     */
    public String getMode() {
        return mode;
    }

    /**
     * Modifie la liste des checkpoints
     * @param checkpoints
     */
    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    /**
     * Modifie le mode de jeu
     * @param mode
     */
    void setMode(String mode) {
        this.mode = mode;
    }
}
