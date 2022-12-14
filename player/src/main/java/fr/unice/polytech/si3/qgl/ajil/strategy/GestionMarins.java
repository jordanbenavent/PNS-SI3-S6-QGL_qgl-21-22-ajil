package fr.unice.polytech.si3.qgl.ajil.strategy;

import fr.unice.polytech.si3.qgl.ajil.Cockpit;
import fr.unice.polytech.si3.qgl.ajil.Sailor;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.actions.Oar;
import fr.unice.polytech.si3.qgl.ajil.actions.Turn;
import fr.unice.polytech.si3.qgl.ajil.shipentities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe regroupant toutes les actions des marins durant le tour, ils doivent se déplacer sur le bateau, ramer, aller
 * sur le gouvernail, se répartir sur les rames
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 */

public class GestionMarins {

    private final ArrayList<Sailor> leftSailors = new ArrayList<>();
    private final ArrayList<Sailor> rightSailors = new ArrayList<>();
    private static final List<String> LOGGER = Cockpit.LOGGER;
    protected StratData stratData;
    private boolean placementInit = false;
    private boolean placementCoxswain = false;
    private boolean placementSailManagers = false;
    private boolean placementVigie = false;
    private boolean marinRepartie = false;

    // marins
    private Sailor coxswain; // celui qui gère le gouvernail
    private List<Sailor> sailManager; // celui qui gère la voile
    private Sailor vigie; // celui qui gère la vigie

    public GestionMarins(StratData stratData) {
        this.stratData = stratData;
    }

    /**
     * @return le marin attribué au gouvernail
     */
    public Sailor getCoxswain() {
        return coxswain;
    }

    /**
     * @return le marin attribué à la vigie
     */
    public Sailor getVigie() {
        return vigie;
    }

    /**
     * set SailorsManager for wind management using StratData class
     */
    void setSailorsManager(List<Sailor> sailManager) {
        if (sailManager != null) this.stratData.sailorsManager = sailManager;
        else LOGGER.add("Il n'y a pas de SailManager.");
    }

    void setVigie(Sailor vigie) {
        this.vigie = vigie;
    }

    /**
     * @return la liste des marins à gauche du bateau
     */
    public List<Sailor> getLeftSailors() {
        return leftSailors;
    }

    /**
     * @return la liste des marins à droite du bateau
     */
    public List<Sailor> getRightSailors() {
        return rightSailors;
    }

    public boolean deplacerMarins(List<Sailor> crew, List<Entity> entities) {
        AtomicBoolean isOk = new AtomicBoolean(false);
        entities.forEach(e -> isOk.set(deplacerMarin(crew.get(0), e)));
        return isOk.get();
    }

    /**
     * @return boolean qui dit si oui ou non le marin a atteint la position fixée
     */
    public boolean deplacerMarin(Sailor s, Entity entity) {
        int dist = entity.getDist(s);
        int movX = entity.getX() - s.getX();
        int movY = entity.getY() - s.getY();

        if (dist == 0) {
            return true;
        }
        if (dist > 5) {
            int depX = (movX < -2) ? -2 : Math.min(movX, 2);
            int depY = (movY < -2) ? -2 : Math.min(movY, 2);
            s.updatePos(depX, depY); // met à jour les (x , y) de ce sailor
            stratData.actions.add(new Moving(s.getId(), depX, depY));
            return false;
        }
        s.updatePos(movX, movY);
        stratData.actions.add(new Moving(s.getId(), movX, movY));
        return true;
    }

    /**
     * @param entity
     * @return le marin le plus proche d'une entité
     */
    public Sailor marinLePlusProche(Entity entity) {
        List<Sailor> sailors = stratData.jeu.getSailors();
        if (sailors.isEmpty()) {
            return null;
        } //Comment on gere les cas ou y a une liste de sailors vide ?
        int distMin = entity.getDist(sailors.get(0));
        Sailor plusProche = sailors.get(0);
        int nouvelleDistance;

        for (Sailor s : sailors) {
            nouvelleDistance = entity.getDist(s);
            if (nouvelleDistance < distMin) {
                distMin = nouvelleDistance;
                plusProche = s;
            }
        }
        return plusProche;
    }

    /**
     * Trouve le marin le plus proche de la voile et le déplace vers celle-ci
     */
    public void attribuerSailManager() {
        List<Sailor> sailors = stratData.jeu.getSailors();
        List<Entity> sails = stratData.jeu.getShip().getSails();
        if (sails.isEmpty()) {
            LOGGER.add("Il n'y a pas de Voile.");
            placementSailManagers = true;
            return;
        }
        if (sailManager == null) {
            sailManager = new ArrayList<>();
            sails.forEach(sail -> sailManager.add(marinLePlusProche(sail)));
            setSailorsManager(sailManager);
            sailors.removeAll(sailManager);
        }
        placementSailManagers = deplacerMarins(sailManager, sails);
    }

    /**
     * Trouve le marin le plus proche de la vigie et le déplace vers celle-ci
     */
    public void attribuerVigie() {
        List<Sailor> sailors = stratData.jeu.getSailors();
        Entity watch = stratData.jeu.getShip().getWatch();
        if (watch == null) {
            LOGGER.add("Il n'y a pas de Vigie.");
            placementVigie = true;
            return;
        }
        if (vigie == null) {
            vigie = marinLePlusProche(watch);
            setVigie(vigie);
            sailors.remove(vigie);
            LOGGER.add("Vigie est : " + vigie.getId());
        }
        placementVigie = deplacerMarin(vigie, watch);
    }

    /**
     * Trouve le marin le plus proche du gouvernail et le déplace vers celui-ci
     */
    public void attribuerCoxswain() {
        List<Sailor> sailors = stratData.jeu.getSailors();
        Entity rudder = stratData.jeu.getShip().getRudder();
        if (rudder == null) {
            LOGGER.add("Il n'y a pas de Gouvernail.");
            placementCoxswain = true;
            return;
        }
        if (coxswain == null) {
            coxswain = marinLePlusProche(rudder);
            stratData.coxswain = coxswain;
            sailors.remove(coxswain);
            LOGGER.add("CoxswainManageur est : " + coxswain.getId());
        }
        placementCoxswain = deplacerMarin(coxswain, rudder);
    }

    /**
     * Ajoute les marins dans la liste de marins à gauche ou à droite du bateau en fonction de leur position sur ce dernier
     */
    public void repartirLesMarins() {
        List<Sailor> sailors = stratData.jeu.getSailors();
        if (sailors.size() % 2 != 0) {
            Sailor marinNeFaitRien = sailors.remove(0);
        }

        int mid = sailors.size() / 2;
        for (int i = 0; i < mid; i++) {
            leftSailors.add(sailors.get(i));
        }
        for (int i = mid; i < sailors.size(); i++) {
            rightSailors.add(sailors.get(i));
        }
        this.marinRepartie = true;
    }

    /**
     * Find a sailor by id in a list of sailors
     *
     * @param id      Sailor id to find
     * @param sailors Sailors List
     * @return Sailor according to his id
     */
    public Sailor findSailorById(int id, List<Sailor> sailors) {
        for (Sailor sailor : sailors) {
            if (sailor.getId() == id) {
                return sailor;
            }
        }
        LOGGER.add("No sailor found");
        return null;
    }

    public boolean isPlacementInit() {
        return placementInit;
    }

    public boolean isPlacementCoxswain() {
        return placementCoxswain;
    }

    public boolean isPlacementSailManagers() {
        return placementSailManagers;
    }

    public boolean isPlacementVigie() {
        return placementVigie;
    }

    public boolean isMarinRepartie() {
        return marinRepartie;
    }


    /**
     * Rame selon la vitesse indiquée dans le déplacement
     *
     * @param deplacement deplacement
     */
    void ramerSelonVitesse(Deplacement deplacement) {

        double angle = deplacement.getAngle();

        if (Math.abs(angle) < Math.PI / 4 && coxswain != null) {
            Turn tournerGouvernail = new Turn(coxswain.getId(), angle);
            stratData.actions.add(tournerGouvernail);
            for (Sailor sailor : stratData.jeu.getSailors()) {
                stratData.actions.add(new Oar(sailor.getId()));
            }
            return;
        }

        int rowingSailor = 0;
        double sailors = nbrSailorsNecessaires(stratData.jeu.getShip().getOars().size(), deplacement.getVitesse());
        // Si le bateau doit avancer tout droit, l'angle vaut 0
        if (deplacement.getAngle() == 0.0) {
            for (Sailor sailor : leftSailors) {
                if (rowingSailor >= sailors / 2) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailor++;
            }
            rowingSailor = 0;
            for (Sailor sailor : rightSailors) {
                if (rowingSailor >= sailors / 2) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailor++;
            }
            return;
        }
        if (deplacement.getAngle() < 0) {
            for (Sailor sailor : leftSailors) {
                if (rowingSailor == sailors) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailor++;
            }
        } else {
            for (Sailor sailor : rightSailors) {
                if (rowingSailor == sailors) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailor++;
            }
        }
    }

    /**
     * Calcul le nombre de marins nécessaire pour adopté la speed en paramètre
     *
     * @param oars  nb rames
     * @param speed speed
     * @return le nombre de marins
     */
    public double nbrSailorsNecessaires(double oars, double speed) {
        double speedPerOar = 165 / oars;
        return speed / speedPerOar;
    }


    /**
     * Ajoute à la liste d'actions les déplacements que doivent effectuer les marins pour se placer sur les rames
     */
    public void placeOnOars() {
        List<Entity> oars = stratData.jeu.getShip().getOars();
        List<Entity> leftOars = new ArrayList<>();
        List<Entity> rightOars = new ArrayList<>();
        for (Entity oar : oars) {
            if (oar.getY() == 0) {
                leftOars.add(oar);
            } else rightOars.add(oar);
        }
        boolean bienPlaceGauche = deplacerRameurs(leftOars, leftSailors);
        boolean bienPlaceDroite = deplacerRameurs(rightOars, rightSailors);
        if (!bienPlaceGauche || !bienPlaceDroite) {
            this.placementInit = false;
            return;
        }
        this.placementInit = true;
    }

    /**
     * Vérifie si toutes les rames sont occupées
     *
     * @param oars
     * @param targetSide
     * @return true si toutes les rame sont occupées par des marins, false sinon
     */
    public boolean deplacerRameurs(List<Entity> oars, List<Sailor> targetSide) {

        boolean allInRange;
        boolean bienplace = true;

        List<Sailor> tmpTargetSide = new ArrayList<>(targetSide);


        for (int j = 0; j < tmpTargetSide.size(); j++) {
            Sailor s = targetSide.get(j);

            for (int i = 0; i < oars.size(); i++) {
                int dist = oars.get(i).getDist(s);
                if (dist == 0) {
                    oars.remove(i);
                    tmpTargetSide.remove(s);
                }
            }
        }

        for (Sailor s : tmpTargetSide) {
            int distMin = 0;
            int index = -1;
            for (int i = 0; i < oars.size(); i++) {
                int dist = oars.get(i).getDist(s);
                if (dist >= distMin) {
                    distMin = dist;
                    index = i;
                }

            }

            allInRange = deplacerMarin(findSailorById(s.getId(), tmpTargetSide), oars.get(index));
            if (!allInRange) {
                bienplace = false;
            }
            oars.remove(index);
        }
        return bienplace;
    }


}