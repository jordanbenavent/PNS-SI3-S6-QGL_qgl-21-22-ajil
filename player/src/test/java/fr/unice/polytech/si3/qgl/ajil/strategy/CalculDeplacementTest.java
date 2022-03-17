package fr.unice.polytech.si3.qgl.ajil.strategy;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shipentities.OarEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

class CalculDeplacementTest {

    Strategy strategie;
    Game jeu;
    Ship ship;
    Checkpoint checkpoint;
    Checkpoint checkpoint2;
    Checkpoint checkpoint3;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ship = new Ship("ship", 100,
                new Position(0.0, 0.0, 0.0), "name",
                new Deck(2, 5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5, 5, 5));
        ArrayList<Checkpoint> checkpoints = new ArrayList<>();
        ArrayList<Sailor> sailors = new ArrayList<>();
        jeu = new Game(
                new Goal("regatte", checkpoints),
                ship,
                sailors,
                4,
                new Wind(0, 50)
        );
        strategie = new Strategy(jeu);
        checkpoint = new Checkpoint(new Position(5, 5, 0), new Circle("circle", 1));
        checkpoints.add(checkpoint);
        Sailor sailor = new Sailor(0, 1, 0, "sailor1");
        Sailor sailor2 = new Sailor(1, 1, 1, "sailor2");
        sailors.add(sailor);
        sailors.add(sailor2);
        checkpoint2 = new Checkpoint(new Position(1, 7, 0), new Circle("circle", 4));
        checkpoint3 = new Checkpoint(new Position(-1, 0, 1), new Circle("circle", 1));
        strategie = new Strategy(jeu);
        objectMapper = new ObjectMapper();
    }


    @Test
    void predictionAngleTourSuivantTest() {
        // Cas bateau à 4 rames
        ship = new Ship("ship", 100,
                new Position(10.0, 10.0, Math.PI / 4), "name",
                new Deck(2, 5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5, 5, 5));
        ArrayList<Deplacement> deplacements;
        Deplacement deplacement_165 = new Deplacement(165, Math.PI / 2);
        Deplacement deplacement_82_5 = new Deplacement(82.5, 1.117);
        jeu.setShip(ship);
        Ship s = jeu.getShip();
        s.getEntities().add(new OarEntity(0, 1, "oar"));
        s.getEntities().add(new OarEntity(0, 2, "oar"));
        s.getEntities().add(new OarEntity(1, 1, "oar"));
        s.getEntities().add(new OarEntity(1, 2, "oar"));
        strategie = new Strategy(jeu);
        Vector v_ship = new Vector(0.707, 0.707);
        // Vector v_check = new Vector(0,232);
        Checkpoint checkpoint = new Checkpoint(new Position(10, 242.5, 0), new Circle("circle", 1));
        //deplacements = strategie.getCalculDeplacement().predictionAngleTourSuivant(s.getPosition(), checkpoint.getPosition(), v_ship, s.getOars().size());
        //System.out.println(deplacements);
        //Assertions.assertEquals(deplacement_165.getAngle(), deplacements.get(0).getAngle(), 0.01);
        //Assertions.assertEquals(deplacement_82_5.getAngle(), deplacements.get(1).getAngle(), 0.01);
    }


    @Test
    void quelEstLangleMaximumTest() {
        // Cas bateau à 4 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        Set<Double> angles = ship.getTurnRange();
        Assertions.assertEquals(Math.PI / 2, strategie.getCalculDeplacement().quelEstLangleMaximum(angles));
        // On supprime l'angle PI/2 et -PI/2
        angles.remove(Math.PI / 2);
        angles.remove(-Math.PI / 2);
        Assertions.assertEquals(Math.PI / 4, strategie.getCalculDeplacement().quelEstLangleMaximum(angles));
    }

    @Test
    void vitesseAdapteTest() {
        // Cas bateau à 4 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        Assertions.assertEquals(41.25, strategie.getCalculDeplacement().vitesseAdapte(Math.PI / 4, ship.getOars().size()));
        // Cas bateau à 6 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 3, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 3, "oar"));
        Assertions.assertEquals(55, strategie.getCalculDeplacement().vitesseAdapte(Math.PI / 3, ship.getOars().size()));
    }

    /**
     * @Test void deplacementPourLeTourRefactorTest(){
     * // Cas où le checkpoint est à un angle supérieur ou égal à PI/2 par rapport au bateau
     * // Cas bateau à 4 rames
     * ship = new Ship("ship", 100,
     * new Position(10.0, 10.0, 0.0), "name",
     * new Deck(2, 5),
     * new ArrayList<>(),
     * new Rectangle("rectangle", 5, 5, 5));
     * jeu.setShip(ship);
     * jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
     * jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
     * jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
     * jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
     * strategie = new Strategy(jeu);
     * Checkpoint checkpoint_angle_positif = new Checkpoint(new Position(10, 242.5, 0), new Circle("circle", 1));
     * Checkpoint checkpoint_angle_negatif = new Checkpoint(new Position(10, -242.5, 0), new Circle("circle", 1));
     * Checkpoint checkpoint_aligne = new Checkpoint(new Position(10, 200, 0), new Circle("circle", 1));
     * Checkpoint checkpoint_angle_sup_PI_2 = new Checkpoint(new Position(-40, -200, 0), new Circle("circle", 1));
     * Deplacement deplacement_PI_sur_2 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
     * Assertions.assertEquals(Math.PI/2, deplacement_PI_sur_2.getAngle());
     * Assertions.assertEquals(82.5, deplacement_PI_sur_2.getVitesse());
     * Deplacement deplacement_moins_PI_sur_2 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_negatif);
     * Assertions.assertEquals(-Math.PI/2, deplacement_moins_PI_sur_2.getAngle());
     * Assertions.assertEquals(82.5, deplacement_moins_PI_sur_2.getVitesse());
     * ship.getPosition().setOrientation(0.2);
     * Deplacement deplacement_PI_sur_4 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
     * Assertions.assertEquals(Math.PI/4, deplacement_PI_sur_4.getAngle());
     * Assertions.assertEquals(41.25, deplacement_PI_sur_4.getVitesse());
     * ship.getPosition().setOrientation(-0.4);
     * Deplacement deplacement_moins_PI_sur_4 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_negatif);
     * Assertions.assertEquals(-Math.PI/4, deplacement_moins_PI_sur_4.getAngle());
     * Assertions.assertEquals(41.25, deplacement_PI_sur_4.getVitesse());
     * // On avance tout droit
     * ship.getPosition().setOrientation(Math.PI/2);
     * System.out.println(ship.getPosition());
     * Deplacement deplacement_tout_droit = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_aligne);
     * Assertions.assertEquals(0, deplacement_tout_droit.getAngle());
     * Assertions.assertEquals(165, deplacement_tout_droit.getVitesse());
     * // Partie où on prédit
     * ship.getPosition().setOrientation(Math.PI/3);
     * Deplacement deplacement_opti = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
     * //Assertions.assertEquals(0, deplacement_opti.getAngle());
     * Assertions.assertEquals(82.5, deplacement_opti.getVitesse());
     * ship.getPosition().setOrientation(0.0);
     * Deplacement deplacement_1 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_sup_PI_2);
     * Assertions.assertEquals(-Math.PI/2, deplacement_1.getAngle());
     * Assertions.assertEquals(82.5, deplacement_1.getVitesse());
     * ship.getPosition().setOrientation(-Math.PI/2);
     * Deplacement deplacement_2 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_sup_PI_2);
     * Assertions.assertEquals(0, deplacement_2.getAngle());
     * Assertions.assertEquals(82.5, deplacement_2.getVitesse());
     * <p>
     * <p>
     * }
     **/

    @Test
    void nbrSailorsNecessairesTest() {
        // Cas bateau à 6 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 3, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 3, "oar"));
        int nb_oars = jeu.getShip().getOars().size();
        // On tourne à un angle de PI/3
        Assertions.assertEquals(2, strategie.getGestionMarins().nbrSailorsNecessaires(nb_oars, 55));
        // On tourne à un angle de PI/2
        Assertions.assertEquals(3, strategie.getGestionMarins().nbrSailorsNecessaires(nb_oars, 82.5));
    }

    @Test
    void ramerSelonVitesseTest() {
        // Cas bateau à 4 rames avec 4 marins
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        Sailor sailor3 = new Sailor(0, 0, 2, "sailor3");
        Sailor sailor4 = new Sailor(1, 0, 3, "sailor4");
        jeu.getSailors().add(sailor3);
        jeu.getSailors().add(sailor4);
        strategie.getGestionMarins().repartirLesMarins();
        // Si le bateau doit aller tout droit
        Deplacement deplacement_toutdroit = new Deplacement(165, 0);
        strategie.getGestionMarins().repartirLesMarins();
        try {
            System.out.println(objectMapper.writeValueAsString(strategie.getListActions()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        strategie.getListActions().clear();
        // Si le bateau doit aller à droite
        Deplacement deplacement_droite = new Deplacement(41.25, 42.5);
        strategie.getGestionMarins().ramerSelonVitesse(deplacement_droite);
        try {
            System.out.println(objectMapper.writeValueAsString(strategie.getListActions()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        strategie.getListActions().clear();
        // Si le bateau doit aller à gauche
        Deplacement deplacement_gauche = new Deplacement(41.25, -42.5);
        strategie.getGestionMarins().ramerSelonVitesse(deplacement_gauche);
        try {
            System.out.println(objectMapper.writeValueAsString(strategie.getListActions()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        strategie.getListActions().clear();
    }
}