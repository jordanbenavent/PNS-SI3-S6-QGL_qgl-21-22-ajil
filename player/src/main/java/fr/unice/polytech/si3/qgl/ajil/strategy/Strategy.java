package fr.unice.polytech.si3.qgl.ajil.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.Checkpoint;
import fr.unice.polytech.si3.qgl.ajil.Cockpit;
import fr.unice.polytech.si3.qgl.ajil.Game;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;

import java.util.ArrayList;

/**
 * Classe Stratégie regroupant des méthodes qui s'occupent de coordonnées les différentes autre classes ce ce package
 * pour effectuer les actions (gestion des marins et des déplacements)
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class Strategy {


    private final ObjectMapper objectMapper;
    protected StratData stratData;
    public ArrayList<String> LOGGER = Cockpit.LOGGER;

    public ValideCheckpoint getValideCheckpoint() {
        return valideCheckpoint;
    }

    //Autre classes ayant chacune une responsabilite pour calculer et mettre en place la strategie
    private final ValideCheckpoint valideCheckpoint;
    private final GestionMarins gestionMarins;

    public CalculDeplacement getCalculDeplacement() {
        return calculDeplacement;
    }

    private final CalculDeplacement calculDeplacement;


    public GestionMarins getGestionMarins() {
        return gestionMarins;
    }

    public Strategy(Game jeu) {
        this.stratData = new StratData(jeu);

        objectMapper = new ObjectMapper();

        valideCheckpoint = new ValideCheckpoint(jeu);
        gestionMarins = new GestionMarins(stratData);
        calculDeplacement = new CalculDeplacement(jeu);
    }

    /**
     * @return la stratData
     */
    public StratData getStratData(){
        return stratData;
    }

    /**
     * @return la partie en cours
     */
    public Game getJeu() {
        return stratData.jeu;
    }

    /**
     * Modifie la partie en cours
     *
     * @param jeu Game object
     */
    public void setJeu(Game jeu) {
        stratData.jeu = jeu;
    }

    /**
     * @return une liste d'actions à effectuer
     */
    public ArrayList<Action> getListActions() {
        return stratData.actions;
    }

    /**
     * @return la liste des actions sous la forme d'un string
     */
    public String getActions() {
        stratData.actions.clear();
        effectuerActions();
        LOGGER.add(stratData.actions.toString());
        try {
            return objectMapper.writeValueAsString(stratData.actions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Effectue les actions dans l'ordre qu'il faut
     */
    public void effectuerActions() {
        // d'abord on place le Barreur
        if (!gestionMarins.isPlacementBarreur()){
            gestionMarins.attribuerBarreur();
        }
        // ensuite on place le marin responsable de la voile
        if (!gestionMarins.isPlacementSailManagers()){
            gestionMarins.attribuerSailManager();
        }

        gestionMarins.repartirLesMarins();

        if (!gestionMarins.isPlacementInit()) {
            gestionMarins.placerSurRames();
        }
        Checkpoint c = valideCheckpoint.checkpointTarget(stratData.jeu.getGoal().getCheckpoints());
        Deplacement deplacement = calculDeplacement.deplacementPourLeTourRefactor(c);
        gestionMarins.ramerSelonVitesse(deplacement);
    }
}
