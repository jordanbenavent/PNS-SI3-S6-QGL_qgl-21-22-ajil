package fr.unice.polytech.si3.qgl.ajil.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.AStarDeployment;

import java.util.ArrayList;
import java.util.List;

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
    //Autre classes ayant chacune une responsabilite pour calculer et mettre en place la strategie
    private final ValideCheckpoint valideCheckpoint;
    private final GestionMarins gestionMarins;
    private final GestionSail gestionSail;
    private final CalculDeplacement calculDeplacement;
    private final List<String> LOGGER = Cockpit.LOGGER;
    protected StratData stratData;
    private List<Checkpoint> listeCheckpoints;
    private int tailleRecifAvant=0;


    public Strategy(Game jeu) {
        this.stratData = new StratData(jeu);
        objectMapper = new ObjectMapper();
        valideCheckpoint = new ValideCheckpoint(jeu);
        gestionMarins = new GestionMarins(stratData);
        gestionSail = new GestionSail(stratData);
        calculDeplacement = new CalculDeplacement(stratData);
        listeCheckpoints = stratData.jeu.getGoal().getCheckpoints();
    }

    public ValideCheckpoint getValideCheckpoint() {
        return valideCheckpoint;
    }

    public CalculDeplacement getCalculDeplacement() {
        return calculDeplacement;
    }

    public GestionMarins getGestionMarins() {
        return gestionMarins;
    }

    /**
     * @return la stratData
     */
    public StratData getStratData() {
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
        //LOGGER.add(stratData.actions.toString());
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

        if(listeCheckpoints.isEmpty()){LOGGER.add("Aucun Checkpoint");return;}

        Deplacement deplacement;
        Checkpoint c;
        // d'abord on place le Barreur
        if (!gestionMarins.isPlacementBarreur()) {
            gestionMarins.attribuerBarreur();
        }
        // ensuite on place le marin responsable de la voile
        if (!gestionMarins.isPlacementSailManagers()) {
            gestionMarins.attribuerSailManager();
        }

        gestionMarins.repartirLesMarins();

        if (!gestionMarins.isPlacementInit()) {
            gestionMarins.placerSurRames();
        }

        //------------------Fin placement marin, début actions deplacement

        //Test A Star


        LOGGER.add("le jeu voit"+stratData.jeu.getReefs().size()+"recif et avant on avait "+tailleRecifAvant);


        if(stratData.jeu.getReefs().size()!=tailleRecifAvant){
            LOGGER.add("Nouveau recif calcul aSTAR");
            calculAStar();
        }

        c = valideCheckpoint.nextCheckpointTarget(listeCheckpoints);

        deplacement = calculDeplacement.deplacementPourLeTourRefactor(c);
        System.out.println("Deplacement: " + deplacement);
        gestionMarins.ramerSelonVitesse(deplacement);
        Ship ship = stratData.jeu.getShip();
        Wind wind = stratData.jeu.getWind();
        gestionSail.putSail(ship, wind);


        tailleRecifAvant = stratData.jeu.getReefs().size();
    }


    void calculAStar(){
        LOGGER.add("Calcule ASTAR");

        LOGGER.add("Avant A star"+listeCheckpoints.size());

        AStarDeployment deploy = new AStarDeployment(this.stratData.jeu,250);
        ArrayList<Checkpoint> fauxCheckpoints = deploy.deployment();
        //appelle astart deployement et ajoute nouveaux checkpojt au debut
        //fauxCheckpoints.addAll(listeCheckpoints);
        valideCheckpoint.setFakeCheckpoint(fauxCheckpoints);
        //listeCheckpoints = (ArrayList<Checkpoint>) fauxCheckpoints.clone();
        LOGGER.add("Apres A star"+listeCheckpoints.size());
        LOGGER.add("Apres A star on va rajouter "+valideCheckpoint.getFakeCheckpoint().size()+ "faux checpoints");

        //stratData.jeu.getGoal().setCheckpoints(fauxCheckpoints);

    }


}
