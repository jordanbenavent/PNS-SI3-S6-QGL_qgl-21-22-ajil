package fr.unice.polytech.si3.qgl.ajil.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.maths.CalculPoints;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.AStarDeployment;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.Intersection;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.ObstacleDetection;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.Segment;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

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


    private static final List<String> LOGGER = Cockpit.LOGGER;
    private final ObjectMapper objectMapper;
    //Autre classes ayant chacune une responsabilite pour calculer et mettre en place la strategie
    private final ValideCheckpoint valideCheckpoint;
    private final GestionMarins gestionMarins;
    private final GestionSail gestionSail;
    private final CalculDeplacement calculDeplacement;
    private final List<Checkpoint> listeCheckpoints;
    protected StratData stratData;
    private List<Checkpoint> listeCheckpoints;
    private int listCheckpointsSize;
    private boolean premierCalculA;
    private int tailleRecifAvant = 0;
    private Checkpoint prochainVraiCheckpoint;


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
    public List<Action> getListActions() {
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


    public boolean wayDirect() {
        System.out.println("calcule WayDirect");
        Position checkpointCiblePosition = this.listeCheckpoints.get(0).getPosition();
        Ship ship = stratData.jeu.getShip();
        Segment bateauCheckpoint = new Segment(ship.getPosition().getX(), ship.getPosition().getY(), checkpointCiblePosition.getX(), checkpointCiblePosition.getY());


        List<VisibleEntitie> listeReef = new ArrayList(stratData.jeu.getReefs());
        List<VisibleEntitie> listePolygoneReef = CalculPoints.entitiesToEntitiesPolygone(listeReef, ship.getDeck().getWidth());
        List<Segment> segments;
        ObstacleDetection obstacleDetection = new ObstacleDetection();

        for (int i = 0; i < listePolygoneReef.size(); i += 3) { //3 car les 2 autres on une marge, a changer !!
            segments = obstacleDetection.reefToSegments(listePolygoneReef.get(i));
            for (Segment s : segments) {
                if (Intersection.SegIntersection(s, bateauCheckpoint) != null) {
                    System.out.println("Intersection repere");
                    return false;
                }
            }
        }
        System.out.println("aucune inter repere");
        return true; //si on est la c'est qu'on a croise aucune intersection
    }


    /**
     * Effectue les actions dans l'ordre qu'il faut
     */
    public void effectuerActions() {

        if (listeCheckpoints.isEmpty()) {
            LOGGER.add("Aucun Checkpoint");
            return;
        }

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


        //si ligne touche => lance a Star (et met viggie)
        //sinon on set fakechecpoint a rien
        //plus besoin de boolean et truc static nextround
        //manque cercle a checker

        if (!wayDirect()) {
            System.out.println("On calcule AStar car non wayDirect");
            calculAStar();
            //si direct setfake a nnull
        } else {
            System.out.println("on ne calcule pas a Star");
            valideCheckpoint.setFakeCheckpoint(null);
        }


        //Validation Checkpoint et Deplacement
        c = valideCheckpoint.nextCheckpointTarget(listeCheckpoints);

        deplacement = calculDeplacement.deplacementPourLeTourRefactor(c);
        System.out.println("Deplacement: " + deplacement);
        gestionMarins.ramerSelonVitesse(deplacement);
        Ship ship = stratData.jeu.getShip();
        Wind wind = stratData.jeu.getWind();
        gestionSail.putSail(ship, wind);

    }


    void calculAStar() {
        LOGGER.add("Calcule A Star : Avant A star on a nbcheckpo = " + listeCheckpoints.size());

        AStarDeployment deploy = new AStarDeployment(this.stratData.jeu, 150);
        List<Checkpoint> fauxCheckpoints = deploy.deployment();
        valideCheckpoint.setFakeCheckpoint(fauxCheckpoints);
        LOGGER.add("On vient de set nb FauxCheck : " + fauxCheckpoints.size());

    }


}
