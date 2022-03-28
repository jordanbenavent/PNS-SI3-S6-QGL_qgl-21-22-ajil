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

public class GestionMarins {

    private boolean placementInit = false;
    private boolean placementBarreur = false;
    private boolean placementSailManagers = false;
    protected StratData stratData;
    public List<String> LOGGER = Cockpit.LOGGER;

    public GestionMarins(StratData stratData) {
        this.stratData = stratData;
    }

    // marins
    private Sailor coxswain; // celui qui gère le gouvernail
    private Sailor sailManager; // celui qui gère la voile
    private final ArrayList<Sailor> leftSailors = new ArrayList<>();
    private final ArrayList<Sailor> rightSailors = new ArrayList<>();

    /**
     * @return le marin attribué au gouvernail
     */
    public Sailor getCoxswain() {
        return stratData.barreur;
    }

    /**
     * set SailorsManager for wind management using StratData class
     */
    void setSailorsManager(Sailor sailManager) {
        if (sailManager != null) this.stratData.sailorsManager = sailManager;
        else System.out.println("sailManager is null");
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

    /**
     * @return boolean qui dit si oui ou non le marin a atteint la position fixée
     */
    public boolean moveSailor(Sailor s, Entity entity) {
        LOGGER.add("Marin :  " + s.getId() + "veut aller vers " + entity.toString());
        int dist = entity.getDist(s);
        int movX = entity.getX() - s.getX();
        int movY = entity.getY() - s.getY();

        if (dist == 0) {
            return true;
        }
        if (dist > 5) {
            LOGGER.add("Marin mouvement :  X:" + movX + "  Y:" + movY);
            int depX = (movX < -2) ? -2 : Math.min(movX, 2);
            int depY = (movY < -2) ? -2 : Math.min(movY, 2);
            s.updatePos(depX, depY); // met à jour les (x , y) de ce sailor
            stratData.actions.add(new Moving(s.getId(), depX, depY));
            return false;
        }
        LOGGER.add("Marin mouvement :  X:" + movX + "  Y:" + movY);
        s.updatePos(movX, movY);
        stratData.actions.add(new Moving(s.getId(), movX, movY));
        return true;
    }


    public Sailor nearestSailor(Entity entity) {
        List<Sailor> sailors = stratData.jeu.getSailors();
        if (sailors.isEmpty()) {
            return null;
        } //Comment on gere les cas ou y a une liste de sailors vide ?
        int distMin = entity.getDist(sailors.get(0));
        Sailor nearest = sailors.get(0);
        int newDistance;

        for (Sailor s : sailors) {
            newDistance = entity.getDist(s);
            if (newDistance < distMin) {
                distMin = newDistance;
                nearest = s;
            }
        }
        return nearest;
    }

    /*
     * Trouve le marin le plus proche de la voile et le déplace vers celle-ci
     */
    public void attribuerSailManager() {
        List<Sailor> sailors = stratData.jeu.getSailors();
        Entity sail = stratData.jeu.getShip().getSail();
        if (sail == null) {
            LOGGER.add("Il n'y a pas de Voile.");
            placementSailManagers = true;
            return;
        }
        if (sailManager == null) {
            sailManager = nearestSailor(sail);
            setSailorsManager(sailManager);
            sailors.remove(sailManager);
            LOGGER.add("Sail Manager est : " + sailManager.getId());
        }
        placementSailManagers = moveSailor(sailManager, sail);
    }

    /*
     * Trouve le marin le plus proche du gouvernail et le déplace vers celui-ci
     */
    public void setCoxswain() {
        List<Sailor> sailors = stratData.jeu.getSailors();
        Entity rudder = stratData.jeu.getShip().getRudder();
        if (rudder == null) {
            LOGGER.add("Il n'y a pas de Gouvernail.");
            placementBarreur = true;
            return;
        }
        if (coxswain == null) {
            coxswain = nearestSailor(rudder);
            stratData.barreur = coxswain;
            sailors.remove(coxswain);
            LOGGER.add("BarreurManageur est : " + coxswain.getId());
        }
        placementBarreur = moveSailor(coxswain, rudder);
    }

    /**
     * Ajoute les marins dans la liste de marins à gauche ou à droite du bateau en fonction de leur position sur ce dernier
     */
    public void repartirLesMarins() {
        leftSailors.clear();
        rightSailors.clear();
        List<Sailor> sailors = stratData.jeu.getSailors();
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

    public Sailor findSailorById(int id, ArrayList<Sailor> sailors) {
        for (Sailor sailor : sailors) {
            if (sailor.getId() == id) {
                return sailor;
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

    public boolean isPlacementSailManagers() {
        return placementSailManagers;
    }

    /**
     * Rame selon la vitesse indiquée dans le déplacement
     *
     * @param deplacement deplacement
     */
    void rowingAccordingToSpeed(Deplacement deplacement) {
        double angle = deplacement.getAngle();

        if (Math.abs(angle) < Math.PI / 4 && coxswain != null) {
            System.out.println(angle);
            LOGGER.add("On tourne avec le gouvernail : " + angle);
            Turn turnRudder = new Turn(coxswain.getId(), angle);
            stratData.actions.add(turnRudder);
            for (Sailor sailor : stratData.jeu.getSailors()) {
                stratData.actions.add(new Oar(sailor.getId()));
            }
            return;
        }

        int rowingSailors = 0;
        int nbOars = stratData.jeu.getShip().getOars().size();
        double nbSailors = howManySailorsNeeded(nbOars, deplacement.getSpeed());
        // Si le bateau doit avancer tout droit, l'angle vaut 0
        if (deplacement.getAngle() == 0.0) {
            for (Sailor sailor : leftSailors) {
                if (rowingSailors >= nbSailors / 2) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailors++;
            }
            rowingSailors = 0;
            for (Sailor sailor : rightSailors) {
                if (rowingSailors >= nbSailors / 2) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailors++;
            }
            return;
        }
        if (deplacement.getAngle() < 0) {
            for (Sailor sailor : leftSailors) {
                if (rowingSailors == nbSailors) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailors++;
            }
        } else {
            for (Sailor sailor : rightSailors) {
                if (rowingSailors == nbSailors) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailors++;
            }
        }
    }

    /**
     * Calcul le nombre de marins nécessaire pour adopté la vitesse en paramètre
     *
     * @param nbOars nb rames
     * @param speed  speed
     * @return le nombre de marins
     */
    public double howManySailorsNeeded(double nbOars, double speed) {
        double speedPerOar = 165 / nbOars;
        return speed / speedPerOar;
    }

    /**
     * Ajoute à la liste d'actions les déplacements que doivent effectuer les marins pour se placer sur les rames
     */
    public void placerSurRames() {
        List<Entity> oars = stratData.jeu.getShip().getOars();
        boolean allInRange;
        boolean wellPlaced = true;
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

            wellPlaced = isAlreadyPlaced(oars, wellPlaced, s, distMin, index, leftSailors);

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
            wellPlaced = isAlreadyPlaced(oars, wellPlaced, s, distMin, index, rightSailors);
        }
        if (!wellPlaced) {
            this.placementInit = false;
            return;
        }
        this.placementInit = true;
    }

    private boolean isAlreadyPlaced(List<Entity> oars, boolean wellPlaced, Sailor s, int distMin, int index, ArrayList<Sailor> rightSailors) {
        boolean allInRange;
        if (distMin == 0) {
            oars.remove(index);
            return wellPlaced;
        }

        allInRange = moveSailor(findSailorById(s.getId(), rightSailors), oars.get(index));
        if (!allInRange) {
            wellPlaced = false;
        }
        oars.remove(index);
        return wellPlaced;
    }
}
