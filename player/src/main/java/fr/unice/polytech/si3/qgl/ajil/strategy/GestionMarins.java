package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Cockpit;
import fr.unice.polytech.si3.qgl.ajil.Sailor;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.actions.Oar;
import fr.unice.polytech.si3.qgl.ajil.actions.Turn;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;

import java.util.ArrayList;

public class GestionMarins {

    private boolean placementInit = false;
    private boolean placementBarreur = false;
    protected StratData stratData;
    public ArrayList<String> LOGGER = Cockpit.LOGGER;
    private int idMarinGouvernail = 0;

    public GestionMarins(StratData stratData) {
        this.stratData = stratData;
    }

    // marins
    private Sailor barreur; // celui qui gère le gouvernail
    private final ArrayList<Sailor> leftSailors = new ArrayList<>();
    private final ArrayList<Sailor> rightSailors = new ArrayList<>();

    /**
    * @return le marin attribué au gouvernail
    * */
    public Sailor getBarreur() {
        return barreur;
    }

    /**
     * @return la liste des marins à gauche du bateau
     */
    public ArrayList<Sailor> getLeftSailors() {
        return leftSailors;
    }

    /**
     * @return la liste des marins à droite du bateau
     */
    public ArrayList<Sailor> getRightSailors() {
        return rightSailors;
    }

    /*
    * Trouve le marin le plus proche du gouvernail et le déplace vers celui-ci
    */
    public void attribuerBarreur() {
        ArrayList<Sailor> sailors = stratData.jeu.getSailors();
        Entity rudder = stratData.jeu.getShip().getRudder();
        if (rudder == null){
            LOGGER.add("Il n'y a pas de Gouvernail.");
            placementBarreur = true;
            return;
        }
        int distMin = 15;
        int index = -1;
        for (int i = 0; i < sailors.size(); i++) {
            if ( barreur != null ) {
                break;
            }
            int dist = rudder.getDist(sailors.get(i));
            if (dist < distMin){
                distMin = dist;
                index = i;
            }
            if (  dist == 0 ) {
                barreur = sailors.get(i);
                LOGGER.add("Barreur est : " + sailors.get(i));
                idMarinGouvernail = sailors.get(i).getId();
                sailors.remove(i);
                placementBarreur = true;
                return;
            }
        }
        if ( barreur == null ){
            barreur = sailors.get(index);
            LOGGER.add("Barreur est : " + sailors.get(index));
            sailors.remove(index);
        }
        if ( distMin > 5 ) {
            int movX = rudder.getX() - barreur.getX();
            int movY = rudder.getY() - barreur.getY();
            LOGGER.add("Barreur mouvement :  X:" + movX +"  Y:" + movY);
            int depX = (movX < -2) ? -2 : Math.min(movX, 2);
            int depY = (movY < -2) ? -2 : Math.min(movY, 2);
            barreur.updatePos(depX, depY); // met à jour les (x , y) de ce sailor
            stratData.actions.add(new Moving(barreur.getId(), depX, depY));
            return;
        }
        placementBarreur = true;
        int movX = rudder.getX() - barreur.getX();
        int movY = rudder.getY() - barreur.getY();
        barreur.updatePos(movX, movY);
        stratData.actions.add(new Moving(barreur.getId(), movX, movY));
    }

    /**
     * Ajoute les marins dans la liste de marins à gauche ou à droite du bateau en fonction de leur position sur ce dernier
     */
    public void repartirLesMarins() {
        leftSailors.clear();
        rightSailors.clear();
        ArrayList<Sailor> sailors = stratData.jeu.getSailors();
        for (Sailor s : sailors) {
            if (leftSailors.size() < sailors.size() / 2) {
                leftSailors.add(s);
                continue;
            }
            if (rightSailors.size() < sailors.size() / 2) {
                rightSailors.add(s);
                continue;
            }
            break;
        }
    }

    public Sailor findSailorById(int id, ArrayList<Sailor> sailors){
        for ( int i = 0; i < sailors.size(); i++ ){
            if (sailors.get(i).getId() == id ){
                return sailors.get(i);
            }
        }
        LOGGER.add("Sailor n'a pas été trouvé");
        return null;
    }

    public boolean isPlacementInit() {
        return placementInit;
    }

    public boolean isPlacementBarreur() {
        return placementBarreur;
    }

    /**
     * Ajoute à la liste d'actions une ou plusieurs ramer en fonction de la vitesse et de l'angle voulu.
     * @param deplacement
     */
    public void ramer(Deplacement deplacement) {

        double angle = deplacement.getAngle();

        if(Math.abs(angle)< Math.PI / 4 && barreur!=null){

            Turn tournerGouvernail = new Turn(barreur.getId(),angle);
            stratData.actions.add(tournerGouvernail);
                for (Sailor sailor : stratData.jeu.getSailors()) {
                    stratData.actions.add(new Oar(sailor.getId()));
                }
                return;
        }


        if (deplacement.getAngle() < 0) {
            if (deplacement.getAngle() == -Math.PI / 2) {
                for (Sailor sailor : leftSailors) {
                    stratData.actions.add(new Oar(sailor.getId()));
                }
                return;
            }
            if (deplacement.getAngle() == -Math.PI / 4) {
                for (int i = 0; i < leftSailors.size() / 2; i++) {
                    stratData.actions.add(new Oar(leftSailors.get(i).getId()));
                }
                return;
            }
        }
        if (deplacement.getAngle() > 0) {
            if (deplacement.getAngle() == Math.PI / 2) {
                for (Sailor sailor : rightSailors) {
                    stratData.actions.add(new Oar(sailor.getId()));
                }
                return;
            }
            if (deplacement.getAngle() == Math.PI / 4) {
                for (int i = 0; i < rightSailors.size() / 2; i++) {
                    stratData.actions.add(new Oar(rightSailors.get(i).getId()));
                }
                return;
            }
        }
        if (deplacement.getVitesse() == 165) {
            for (Sailor sailor : stratData.jeu.getSailors()) {
                stratData.actions.add(new Oar(sailor.getId()));
            }
        } else {
            for (int i = 0; i < rightSailors.size() / 2; i++) {
                stratData.actions.add(new Oar(rightSailors.get(i).getId()));
            }
            for (int i = 0; i < leftSailors.size() / 2; i++) {
                stratData.actions.add(new Oar(leftSailors.get(i).getId()));
            }
        }
    }

    /**
     * Rame selon la vitesse indiquée dans le déplacement
     * @param deplacement
     */
    void ramerSelonVitesse(Deplacement deplacement){
        int sailor_qui_rame = 0;
        double nbr_sailors = nbrSailorsNecessaires(stratData.jeu.getShip().getOars().size(), deplacement.getVitesse());
        // Si le bateau doit avancer tout droit, l'angle vaut 0
        if (deplacement.getAngle() == 0.0) {
            for(Sailor sailor : leftSailors){
                if(sailor_qui_rame >= nbr_sailors/2){
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                sailor_qui_rame++;
            }
            sailor_qui_rame = 0;
            for(Sailor sailor : rightSailors){
                if(sailor_qui_rame >= nbr_sailors/2){
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                sailor_qui_rame++;
            }
            return;
        }
        if (deplacement.getAngle() < 0) {
            for (Sailor sailor : leftSailors) {
                if(sailor_qui_rame == nbr_sailors){
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                sailor_qui_rame++;
            }
        }
        else {
            for (Sailor sailor : rightSailors) {
                if(sailor_qui_rame == nbr_sailors){
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                sailor_qui_rame++;
            }
        }
        return;
    }

    /**
     * Calcul le nombre de marins nécessaire pour adopté la vitesse en paramètre
     * @param nbr_rames
     * @param vitesse
     * @return le nombre de marins
     */
    public double nbrSailorsNecessaires(double nbr_rames, double vitesse){
        double vitesse_une_rame = 165/nbr_rames;
        double marin_necessaire = vitesse/vitesse_une_rame;
        return marin_necessaire;
    }

    /**
     * Ajoute à la liste d'actions les déplacement que doivent effectuer les marins pour se placer sur les rames
     */
    public void placerSurRames() {
        ArrayList<Entity> oars = stratData.jeu.getShip().getOars();
        boolean allInRange = true;
        for (Sailor s : leftSailors) {
            int distMin = 0;
            int index = -1;
            for (int i = 0; i < oars.size(); i++) {
                if (oars.get(i).getY() == 0) {
                    int dist = oars.get(i).getDist(s);
                    if (dist >= distMin) {
                        distMin = dist;
                        index = i;
                    }
                }
            }
            if (distMin == 0) {
                oars.remove(index);
                continue;
            }
            if (oars.get(index).getDist(s) > 5) {
                allInRange = false;
                int movX = oars.get(index).getX() - s.getX();
                int movY = oars.get(index).getY() - s.getY();
                oars.remove(index);
                int depX = (movX < -2) ? -2 : Math.min(movX, 2);
                int depY = (movY < -2) ? -2 : Math.min(movY, 2);
                findSailorById(s.getId(), leftSailors).updatePos(depX, depY); // met à jour les (x , y) de ce sailor
                stratData.actions.add(new Moving(s.getId(), depX, depY));
                continue;
            }
            int movX = oars.get(index).getX() - s.getX();
            int movY = oars.get(index).getY() - s.getY();
            oars.remove(index);
            findSailorById(s.getId(), leftSailors).updatePos(movX, movY);
            stratData.actions.add(new Moving(s.getId(), movX, movY));
        }
        for (Sailor s : rightSailors) {
            int distMin = 0;
            int index = -1;
            for (int i = 0; i < oars.size(); i++) {
                if (oars.get(i).getY() > 0) {
                    int dist = oars.get(i).getDist(s);
                    if (dist >= distMin) {
                        distMin = dist;
                        index = i;
                    }
                }
            }
            if (distMin == 0) {
                oars.remove(index);
                continue;
            }
            if (oars.get(index).getDist(s) > 5) {
                allInRange = false;
                int movX = oars.get(index).getX() - s.getX();
                int movY = oars.get(index).getY() - s.getY();
                oars.remove(index);
                int depX = (movX < -2) ? -2 : Math.min(movX, 2);
                int depY = (movY < -2) ? -2 : Math.min(movY, 2);
                findSailorById(s.getId(), rightSailors).updatePos(depX, depY); // met à jour les (x , y) de ce sailor
                stratData.actions.add(new Moving(s.getId(), depX, depY));
                continue;
            }
            int movX = oars.get(index).getX() - s.getX();
            int movY = oars.get(index).getY() - s.getY();
            oars.remove(index);
            findSailorById(s.getId(), rightSailors).updatePos(movX, movY);
            stratData.actions.add(new Moving(s.getId(), movX, movY));
        }
        if (!allInRange) {
            this.placementInit = false;
            return;
        }
        this.placementInit = true;
    }
}
