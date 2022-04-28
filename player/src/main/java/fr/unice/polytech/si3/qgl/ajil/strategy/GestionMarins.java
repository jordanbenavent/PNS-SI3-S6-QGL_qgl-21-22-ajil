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
    private static final List<String> LOGGER = Cockpit.LOGGER;
    protected final StratData stratData;

    public GestionMarins(StratData stratData) {
        this.stratData = stratData;
    }

    private final List<Sailor> leftSailors = new ArrayList<>();
    private final List<Sailor> rightSailors = new ArrayList<>();
    // marins
    private Sailor coxswain; // celui qui gère le gouvernail
    private List<Sailor> sailManager; // whose who manage the sails

    /**
     * @return le marin attribué au gouvernail
     */
    public Sailor getCoxswain() {
        return stratData.coxswain;
    }

    /**
     * set SailorsManager for wind management using StratData class
     */
    void setSailorsManager(List<Sailor> sailManager) {
        if (sailManager != null) {
            this.stratData.sailorsManager = sailManager;
        } else LOGGER.add("SailManager not found");
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

    /**
     * @return boolean qui dit si oui ou non le marin a atteint la position fixée
     */
    public boolean hasSailorMoved(Sailor s, Entity entity) {
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

    /*
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
            sailManager = nearestSailor(sail);
            setSailorsManager(sailManager);
            sailors.remove(sailManager);
            LOGGER.add("Sail Manager est : " + sailManager.getId());
        }
        placementSailManagers = hasSailorMoved(sailManager, sail);
    }

    /*
     * Trouve le marin le plus proche du gouvernail et le déplace vers celui-ci
     */
    public void attribuerBarreur() {
        List<Sailor> sailors = stratData.jeu.getSailors();
        Entity rudder = stratData.jeu.getShip().getRudder();
        if (rudder == null) {
            LOGGER.add("Il n'y a pas de Gouvernail.");
            placementBarreur = true;
            return;
        }
        if (coxswain == null) {
            coxswain = nearestSailor(rudder);
            stratData.coxswain = coxswain;
            sailors.remove(coxswain);
            LOGGER.add("BarreurManageur est : " + coxswain.getId());
        }
        placementBarreur = hasSailorMoved(coxswain, rudder);
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

    public Sailor findSailorById(int id, List<Sailor> sailors) {
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
    void ramerSelonVitesse(Deplacement deplacement) {
        double angle = deplacement.getAngle();

        if (Math.abs(angle) < Math.PI / 4 && coxswain != null) {
            LOGGER.add("On tourne avec le gouvernail : " + angle);
            Turn tournerGouvernail = new Turn(coxswain.getId(), angle);
            stratData.actions.add(tournerGouvernail);
            for (Sailor sailor : stratData.jeu.getSailors()) {
                stratData.actions.add(new Oar(sailor.getId()));
            }
            return;
        }

        int rowingSailor = 0;
        double sailorsNb = nbrSailorsNecessaires(stratData.jeu.getShip().getOars().size(), deplacement.getVitesse());
        // Si le bateau doit avancer tout droit, l'angle vaut 0
        if (deplacement.getAngle() == 0.0) {
            for (Sailor sailor : leftSailors) {
                if (rowingSailor >= sailorsNb / 2) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailor++;
            }
            rowingSailor = 0;
            for (Sailor sailor : rightSailors) {
                if (rowingSailor >= sailorsNb / 2) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailor++;
            }
            return;
        }
        if (deplacement.getAngle() < 0) {
            for (Sailor sailor : leftSailors) {
                if (rowingSailor == sailorsNb) {
                    break;
                }
                stratData.actions.add(new Oar(sailor.getId()));
                rowingSailor++;
            }
        } else {
            for (Sailor sailor : rightSailors) {
                if (rowingSailor == sailorsNb) {
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
    public void placerSurRames() {
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

    public boolean deplacerRameurs(List<Entity> oars, List<Sailor> targetSide) {
        boolean allInRange;
        boolean bienplace = true;

        for (Sailor s : targetSide) {
            int distMin = 0;
            int index = -1;
            for (int i = 0; i < oars.size(); i++) {
                int dist = oars.get(i).getDist(s);
                if (dist >= distMin) {
                    distMin = dist;
                    index = i;
                }
            }
            if (distMin == 0) {
                oars.remove(index);
                continue;
            }
            allInRange = hasSailorMoved(findSailorById(s.getId(), targetSide), oars.get(index));
            if (!allInRange) {
                bienplace = false;
            }
            oars.remove(index);
        }
        return bienplace;
    }
}