package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.actions.Oar;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;

import java.util.ArrayList;

public class Strategie {
    private Game jeu;
    private final ArrayList<Action> actions;
    private final ObjectMapper objectMapper;
    private boolean placementInit = false; // Placement des marins sur les rames au debut de partie
    private static final int t = 770;

    // marins
    private final ArrayList<Sailor> leftSailors = new ArrayList<>();
    private final ArrayList<Sailor> rightSailors = new ArrayList<>();

    public Strategie(Game jeu) {
        this.jeu = jeu;
        actions = new ArrayList<>();
        objectMapper = new ObjectMapper();
    }

    public Game getJeu() {
        return jeu;
    }

    public void setJeu(Game jeu) {
        this.jeu = jeu;
    }

    public ArrayList<Action> getListActions(){
        return actions;
    }

    public String getActions(){
        actions.clear();
        effectuerActions();
        try {
            return objectMapper.writeValueAsString(actions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    void effectuerActions() {
        if (!placementInit){
            placerSurRames();
        }
        whereAreSailors();
        Checkpoint c = jeu.getGoal().getCheckpoints().get(0);
        Deplacement deplacement =  deplacementPourLeTour(c);
        ramer(deplacement);

    }

    void ramer(Deplacement deplacement) {
        if (deplacement.getAngle() < 0) {
            if (deplacement.getAngle() == -Math.PI / 2) {
                for (Sailor sailor : leftSailors) {
                    actions.add(new Oar(sailor.getId()));
                }
                return;
            }
            if (deplacement.getAngle() == -Math.PI / 4) {
                for (int i = 0; i < leftSailors.size() / 2; i++) {
                    actions.add(new Oar(leftSailors.get(i).getId()));
                }
                return;
            }
        }
        if (deplacement.getAngle() > 0) {
            if (deplacement.getAngle() == Math.PI / 2) {
                for (Sailor sailor : rightSailors) {
                    actions.add(new Oar(sailor.getId()));
                }
                return;
            }
            if (deplacement.getAngle() == Math.PI / 4) {
                for (int i = 0; i < rightSailors.size() / 2; i++) {
                    actions.add(new Oar(rightSailors.get(i).getId()));
                }
                return;
            }
        }
        if (deplacement.getVitesse() == 165) {
            for (Sailor sailor : jeu.getSailors()) {
                actions.add(new Oar(sailor.getId()));
            }
        } else {
            for (int i = 0; i < rightSailors.size() / 2; i++) {
                actions.add(new Oar(rightSailors.get(i).getId()));
            }
            for (int i = 0; i < leftSailors.size() / 2; i++) {
                actions.add(new Oar(leftSailors.get(i).getId()));
            }
        }
    }

    void ramerSelonVitesse(Deplacement deplacement){

    }

    // Placement initial
    void placerSurRames() {
        ArrayList<Entity> oars = jeu.getShip().getOars();
        ArrayList<Sailor> sailors = jeu.getSailors();
        int distMin;
        for (Sailor s : sailors){
            distMin = 6;
            int indexMin = 0;
            for (int i =0 ; i< oars.size(); i++){
                int dist = oars.get(i).getDist(s);
                if (dist < distMin){
                    distMin = dist;
                    indexMin = i;
                }
            }
            int movX = oars.get(indexMin).getX() - s.getX();
            int movY = oars.get(indexMin).getY() - s.getY();
            oars.remove(indexMin);
            actions.add(new Moving(s.getId(), movX, movY));
        }
        this.placementInit = true;
    }

    void avancer(){
        actions.add(new Oar(0));
        actions.add(new Oar(1));
    }

    Deplacement deplacementPourLeTour(Checkpoint c){
        Ship s = jeu.getShip();
        Vector v_ship = new Vector(Math.cos(s.getPosition().getOrientation()), Math.sin(s.getPosition().getOrientation()));
        Vector v_check = new Vector(c.getPosition().getX() - s.getPosition().getX(),c.getPosition().getY()-s.getPosition().getY());
        double angle = v_ship.angleBetweenVectors(v_check);
        ArrayList<Deplacement> futur_angle = predictionAngleTourSuivant(v_ship, v_check);
        Deplacement deplacement = new Deplacement(); //vitesse en premier, angle en deuxième
        if(Math.abs(angle) >= Math.PI/2){
            // Faire une rotation de PI/2
            deplacement.setVitesse(82.5);
            if(angle < 0){
                deplacement.setAngle(-Math.PI/2);
            }
            else{
                deplacement.setAngle(Math.PI/2);
            }
            return deplacement;
        }
        else if(Math.abs(angle) < Math.PI/2 && Math.abs(angle) > Math.PI/4){
            // Faire une rotation de PI/4
            deplacement.setVitesse(41.25);
            if(angle < 0){
                deplacement.setAngle(-Math.PI/4);
            }
            else{
                deplacement.setAngle(Math.PI/4);
            }
            return deplacement;
        }
        else if (Math.abs(angle) < Math.PI/4 && Math.abs(angle) > 0){
            double vitesse_opti = 0;
            double diffMin = -1;
            for(Deplacement d: futur_angle){
                double diff = Math.abs(Math.PI/4 - d.getAngle());
                if(diffMin == -1 || diffMin > diff){
                    diffMin = diff;
                    vitesse_opti = d.getVitesse();
                }
            }
            deplacement.setVitesse(vitesse_opti);
            deplacement.setAngle(0);
            return deplacement;
            // Regarder toutes les sous-listes de futur_angle, aller tout droit à la vitesse rapprochant au plus de l'angle PI/4
            // à savoir la plus petite différence entre l'angle futur et PI/4
        }
        else {
            // Avancer tout droit à la vitesse maximale
            deplacement.setVitesse(165);
            deplacement.setAngle(0);
            return deplacement;
        }
    }

    /**
     * Le bateau avance droit, on calcule les différents angles qu'on obtiendra en fonction de la vitesse du bateau qu'on peut appliquer
     * au bateau
     * @param ship
     * @param checkpoint
     * @return une liste de liste de double où chaque sous-liste contient une vitesse associée à un angle
     */
    ArrayList<Deplacement> predictionAngleTourSuivant(Vector ship, Vector checkpoint){
        int nbr_oars = jeu.getShip().getOars().size();
        ArrayList<Deplacement> prediction = new ArrayList<>();
        double vitesse = 165;
        double angle_init = ship.angleBetweenVectors(checkpoint);
        System.out.println("angle init = " + angle_init);
        double angle_apres_deplacement = 0;
        double positionX_init = ship.getX();
        double positionY_init = ship.getY();
        System.out.println("Boucle:");
        for(int i = 0; i < jeu.getShip().getOars().size()/2; i++){
            vitesse = (vitesse * (nbr_oars-2*i))/nbr_oars;
            ship.setX(positionX_init + (vitesse * Math.cos(angle_init))); //On modifie la coordonée en X du bateau en fonction de son orientation
            ship.setY(positionY_init + (vitesse * Math.sin(angle_init))); //On modifie la coordonée en Y du bateau en fonction de son orientation
            angle_apres_deplacement = ship.angleBetweenVectors(checkpoint);
            prediction.add(new Deplacement(vitesse, angle_apres_deplacement));
        }
        return prediction;
    }

    double distance(Checkpoint c, Ship s){
        double distance_horizontale = Math.pow((c.getPosition().getX() - s.getPosition().getX()), 2);
        double distance_verticale = Math.pow((c.getPosition().getY() - s.getPosition().getY()), 2);
        return Math.sqrt(distance_horizontale + distance_verticale);
    }

    public ArrayList<Sailor> getLeftSailors() {
        return leftSailors;
    }

    public ArrayList<Sailor> getRightSailors() {
        return rightSailors;
    }

    public void whereAreSailors() {
        ArrayList<Sailor> sailors = jeu.getSailors();
        for (Sailor sailor : sailors){
            if (sailor.getY() < (jeu.getShip().getDeck().getWidth()/2)) {
                leftSailors.add(sailor);
            } else {
                rightSailors.add(sailor);
            }
        }
    }
}
