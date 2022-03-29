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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculDeplacementTest {

    CalculDeplacement calculDeplacement;
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
        calculDeplacement = new CalculDeplacement(strategie.stratData);
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

    @Test void deplacementPourLeTourRefactorTest(){
        // Cas où le checkpoint est à un angle supérieur ou égal à PI/2 par rapport au bateau
        // Cas bateau à 4 rames
        ship = new Ship("ship", 100,
        new Position(10.0, 10.0, 0.0), "name",
        new Deck(2, 5),
        new ArrayList<>(),
        new Rectangle("rectangle", 5, 5, 5));
        jeu.setShip(ship);
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        strategie = new Strategy(jeu);
        Checkpoint checkpoint_angle_positif = new Checkpoint(new Position(10, 242.5, 0), new Circle("circle", 1));
        Checkpoint checkpoint_angle_negatif = new Checkpoint(new Position(10, -242.5, 0), new Circle("circle", 1));
        Checkpoint checkpoint_aligne = new Checkpoint(new Position(10, 200, 0), new Circle("circle", 1));
        Checkpoint checkpoint_angle_sup_PI_2 = new Checkpoint(new Position(-40, -200, 0), new Circle("circle", 1));
        Deplacement deplacement_PI_sur_2 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
        Assertions.assertEquals(Math.PI/2, deplacement_PI_sur_2.getAngle());
        Assertions.assertEquals(82.5, deplacement_PI_sur_2.getVitesse());
        Deplacement deplacement_moins_PI_sur_2 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_negatif);
        Assertions.assertEquals(-Math.PI/2, deplacement_moins_PI_sur_2.getAngle());
        Assertions.assertEquals(82.5, deplacement_moins_PI_sur_2.getVitesse());
        ship.getPosition().setOrientation(0.2);
        Deplacement deplacement_PI_sur_4 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
        Assertions.assertEquals(Math.PI/4, deplacement_PI_sur_4.getAngle());
        Assertions.assertEquals(41.25, deplacement_PI_sur_4.getVitesse());
        ship.getPosition().setOrientation(-0.4);
        Deplacement deplacement_moins_PI_sur_4 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_negatif);
        Assertions.assertEquals(-Math.PI/4, deplacement_moins_PI_sur_4.getAngle());
        Assertions.assertEquals(41.25, deplacement_PI_sur_4.getVitesse());
        // On avance tout droit
        ship.getPosition().setOrientation(Math.PI/2);
        System.out.println(ship.getPosition());
        Deplacement deplacement_tout_droit = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_aligne);
        Assertions.assertEquals(0, deplacement_tout_droit.getAngle());
        Assertions.assertEquals(165, deplacement_tout_droit.getVitesse());
        // Partie où on prédit
        ship.getPosition().setOrientation(Math.PI/3);
        Deplacement deplacement_opti = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
        Assertions.assertEquals(0, deplacement_opti.getAngle());
        Assertions.assertEquals(82.5, deplacement_opti.getVitesse());
        ship.getPosition().setOrientation(0.0);
        Deplacement deplacement_1 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_sup_PI_2);
        Assertions.assertEquals(-Math.PI/2, deplacement_1.getAngle());
        Assertions.assertEquals(82.5, deplacement_1.getVitesse());
        ship.getPosition().setOrientation(-Math.PI/2);
        Deplacement deplacement_2 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_sup_PI_2);
        Assertions.assertEquals(0, deplacement_2.getAngle());
        Assertions.assertEquals(165.0, deplacement_2.getVitesse());
    }

    @Test
    void deplacementPourLeTourAvecBarreurTest(){
        // Cas où le checkpoint est à un angle supérieur ou égal à PI/2 par rapport au bateau
        // Cas bateau à 4 rames et 1 barreur
        ship = new Ship("ship", 100,
                new Position(10.0, 10.0, 0.0), "name",
                new Deck(2, 5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5, 5, 5));
        jeu.setShip(ship);
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        strategie = new Strategy(jeu);
        Sailor barreur = new Sailor(0, 0, 1, "Jean");
        strategie.stratData.setBarreur(barreur);
        Checkpoint checkpoint_angle_positif = new Checkpoint(new Position(10, 242.5, 0), new Circle("circle", 1));
        ship.getPosition().setOrientation(Math.PI/3);
        // déplacement pour un angle inférieur à PI/4 entre le bateau et le checkpoint
        Deplacement deplacement_barreur = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
        Vector v_ship = calculDeplacement.calculVecteurBateau(ship);
        Vector v_check = calculDeplacement.calculVecteurCheckpoint(checkpoint_angle_positif, ship);
        double angle = v_ship.angleBetweenVectors(v_check);
        Assertions.assertEquals(angle, deplacement_barreur.getAngle());
        Assertions.assertEquals(165.0, deplacement_barreur.getVitesse());

        //Cas d'un bateau avec 8 rames où l'angle du bateau par rapport au checkpoint est supérieur à PI/4
        jeu.getShip().getEntities().add(new OarEntity(2, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(2, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(3, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(3, 2, "oar"));
        ship.getPosition().setOrientation(Math.PI/6 - 0.03);
        Deplacement deplacement_test = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
        Assertions.assertEquals(Math.PI/4, deplacement_test.getAngle());
        Assertions.assertEquals(41.25, deplacement_test.getVitesse());
    }

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
    void deplacementSiGouvernailTest(){
        Deplacement deplacement1 = new Deplacement(0, 0);
        calculDeplacement.deplacementSiGouvernail(0.5, deplacement1);
        Assertions.assertEquals(0.5, deplacement1.getAngle());
        Assertions.assertEquals(165.0, deplacement1.getVitesse());
    }

    @Test
    void deplacementSelonPredictionTest(){
        // Cas d'un bateau à 4 rames donc possibilité de ramer d'un angle de PI/2 ou PI/4
        ArrayList<Deplacement> futurs_angles = new ArrayList<>();
        Double angle_maximum = Math.PI/4;
        Deplacement deplacement_test = new Deplacement();
        Deplacement deplacement_futur1 = new Deplacement(82.5, 0.5);
        Deplacement deplacement_futur2 = new Deplacement(41.25, 0.3);
        Deplacement deplacement_futur3 = new Deplacement(120.0, 0.8);
        futurs_angles.add(deplacement_futur1);
        futurs_angles.add(deplacement_futur2);
        futurs_angles.add(deplacement_futur3);
        calculDeplacement.deplacementSelonPrediction(angle_maximum, futurs_angles, deplacement_test);
        Assertions.assertEquals(120.0, deplacement_test.getVitesse());
        Assertions.assertEquals(0.0, deplacement_test.getAngle());
        Deplacement deplacement_futur4 = new Deplacement(100.0, -0.79);
        futurs_angles.add(deplacement_futur4);
        calculDeplacement.deplacementSelonPrediction(angle_maximum, futurs_angles, deplacement_test);
        Assertions.assertEquals(100.0, deplacement_test.getVitesse());
        Assertions.assertEquals(0.0, deplacement_test.getAngle());
    }

    @Test
    void viseExtremiteCheckpointTest(){
        Checkpoint checkpoint1 = new Checkpoint(new Position(0, 10, 0), new Circle("circle", 2));
        Checkpoint checkpoint2 = new Checkpoint(new Position(15, 10, 0), new Circle("circle", 2));
        Checkpoint checkpoint3 = new Checkpoint(new Position(5, 15, 0), new Circle("circle", 2));
        Checkpoint checkpoint4 = new Checkpoint(new Position(0, 15, 0), new Circle("circle", 2));
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().clear();
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().add(checkpoint1);

        // Cas où le prochain checkpoint est tout droit
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().add(checkpoint4);
        Checkpoint test = new Checkpoint(new Position(0, 12, 0), new Circle("circle", 2));
        Assertions.assertEquals(test.getPosition().getX(), calculDeplacement.viseExtremiteCheckpoint(checkpoint1).getPosition().getX(), 0.01);
        Assertions.assertEquals(test.getPosition().getY(), calculDeplacement.viseExtremiteCheckpoint(checkpoint1).getPosition().getY(), 0.01);
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().remove(checkpoint4);

        // Cas où le prochain checkpoint est à 90° sur la droite
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().add(checkpoint2);
        Checkpoint test2 = new Checkpoint(new Position(2, 10, 0), new Circle("circle", 2));
        Assertions.assertEquals(test2.getPosition().getX(), calculDeplacement.viseExtremiteCheckpoint(checkpoint1).getPosition().getX(), 0.01);
        Assertions.assertEquals(test2.getPosition().getY(), calculDeplacement.viseExtremiteCheckpoint(checkpoint1).getPosition().getY(), 0.01);
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().remove(checkpoint2);

        // Cas où le prochain checkpoint est à 45° sur la droite
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().add(checkpoint3);
        Checkpoint test3 = new Checkpoint(new Position(Math.sqrt(2), 10 + Math.sqrt(2), 0), new Circle("circle", 2));
        Assertions.assertEquals(test3.getPosition().getX(), calculDeplacement.viseExtremiteCheckpoint(checkpoint1).getPosition().getX(), 0.01);
        Assertions.assertEquals(test3.getPosition().getY(), calculDeplacement.viseExtremiteCheckpoint(checkpoint1).getPosition().getY(), 0.01);
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().remove(checkpoint3);
    }

    @Test
    void vitesseSelonDistanceTest(){
        // On prend le cas de 8 rames
        Assertions.assertEquals(82.5, calculDeplacement.vitesseSelonDistance(90.0, 8));
        Assertions.assertEquals(165.0, calculDeplacement.vitesseSelonDistance(165.0, 8));
        Assertions.assertEquals(41.25, calculDeplacement.vitesseSelonDistance(10.0, 8));
    }

    @Test
    void test(){
        Checkpoint checkpoint1 = new Checkpoint(new Position(0, 10, 0), new Circle("circle", 2));
        Checkpoint checkpoint2 = new Checkpoint(new Position(-5, -10, 0), new Circle("circle", 2));
        Checkpoint checkpoint3 = new Checkpoint(new Position(-5, 10, 0), new Circle("circle", 2));
        Checkpoint checkpoint4 = new Checkpoint(new Position(0, 15, 0), new Circle("circle", 2));
        Vector v_checkpoint1 = new Vector(Math.cos(checkpoint1.getPosition().getOrientation()), Math.sin(checkpoint1.getPosition().getOrientation()));
        System.out.println(v_checkpoint1);
        Vector v_checkpoint2 = new Vector(checkpoint2.getPosition().getX() - checkpoint1.getPosition().getX(), checkpoint2.getPosition().getY() - checkpoint1.getPosition().getY());
        System.out.println(v_checkpoint2);
        Vector v_checkpoint3 = new Vector(checkpoint3.getPosition().getX() - checkpoint1.getPosition().getX(), checkpoint3.getPosition().getY() - checkpoint1.getPosition().getY());
        System.out.println(v_checkpoint3);
        Vector v_checkpoint4 = new Vector(checkpoint4.getPosition().getX() - checkpoint1.getPosition().getX(), checkpoint4.getPosition().getY() - checkpoint1.getPosition().getY());
        System.out.println(v_checkpoint4);
        double angle1 = v_checkpoint1.angleBetweenVectors(v_checkpoint2);
        System.out.println(angle1);
        double angle2 = v_checkpoint1.angleBetweenVectors(v_checkpoint3);
        System.out.println(angle2);
        double angle3 = v_checkpoint1.angleBetweenVectors(v_checkpoint4);
        System.out.println(angle3);
        System.out.println("Coordonnées du centre: x = " + checkpoint1.getPosition().getX() + ", y = " + checkpoint1.getPosition().getY());
        System.out.println("Coordonnées du nouveau centre: x = " + (checkpoint1.getPosition().getX() + (2 * Math.cos(angle1))) + ", y = " + (checkpoint1.getPosition().getY() + (2 * Math.sin(angle1))));
        System.out.println("Coordonnées du nouveau centre: x = " + (checkpoint1.getPosition().getX() + (2 * Math.cos(angle3))) + ", y = " + (checkpoint1.getPosition().getY() + (2 * Math.sin(angle3))));

    }
}