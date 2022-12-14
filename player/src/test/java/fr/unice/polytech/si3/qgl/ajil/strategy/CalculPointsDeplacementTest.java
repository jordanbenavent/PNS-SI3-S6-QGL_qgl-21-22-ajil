package fr.unice.polytech.si3.qgl.ajil.strategy;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.*;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.shape.Rectangle;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;
import fr.unice.polytech.si3.qgl.ajil.shipentities.OarEntity;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CalculPointsDeplacementTest {

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
        // Cas bateau ?? 4 rames
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
        // Cas bateau ?? 4 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        Set<Double> angles = ship.getTurnRange();
        Assertions.assertEquals(Math.PI / 2, strategie.getCalculDeplacement().getMaxAngle(angles));
        // On supprime l'angle PI/2 et -PI/2
        angles.remove(Math.PI / 2);
        angles.remove(-Math.PI / 2);
        Assertions.assertEquals(Math.PI / 4, strategie.getCalculDeplacement().getMaxAngle(angles));
        // On supprime l'angle PI/4 et -PI/4
        angles.remove(Math.PI / 4);
        angles.remove(-Math.PI / 4);
        // Si on a pas de rame on ne peut pas tourner et donc changer notre angle, l'angle maximum est donc 0
        Assertions.assertEquals(0, strategie.getCalculDeplacement().getMaxAngle(angles));
    }

    @Test
    void vitesseAdapteTest() {
        // Cas bateau ?? 4 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        Assertions.assertEquals(41.25, strategie.getCalculDeplacement().vitesseAdapte(Math.PI / 4, ship.getOars().size()));
        // Cas bateau ?? 6 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 3, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 3, "oar"));
        Assertions.assertEquals(55, strategie.getCalculDeplacement().vitesseAdapte(Math.PI / 3, ship.getOars().size()));
    }

    @Test void deplacementPourLeTourRefactorTest(){
        // Cas o?? le checkpoint est ?? un angle sup??rieur ou ??gal ?? PI/2 par rapport au bateau
        // Cas bateau ?? 4 rames
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
        /*
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

         */
        // On avance tout droit
        ship.getPosition().setOrientation(Math.PI/2);
        System.out.println(ship.getPosition());


        Deplacement deplacement_tout_droit = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_aligne);
        Assertions.assertEquals(0, deplacement_tout_droit.getAngle());
        Assertions.assertEquals(165, deplacement_tout_droit.getVitesse());
        // Partie o?? on pr??dit
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
    void deplacementPourLeTourCasLimiteTest(){
        // Cas bateau ?? 4 rames
        ship = new Ship("ship", 100,
                new Position(0.0, 0.0, 0.0), "name",
                new Deck(2, 5),
                new ArrayList<>(),
                new Rectangle("rectangle", 5, 5, 5));
        jeu.setShip(ship);
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        strategie = new Strategy(jeu);
        Checkpoint checkpoint_PI_2 = new Checkpoint(new Position(0, 20, 0), new Circle("circle", 1));
        Checkpoint checkpoint_aligne = new Checkpoint(new Position(10, 0, 0), new Circle("circle", 1));
        Deplacement deplacement_PI_2 = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_PI_2);
        Assertions.assertEquals(Math.PI/2, deplacement_PI_2.getAngle());
        Deplacement deplacement_aligne = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_aligne);
        Assertions.assertEquals(0, deplacement_aligne.getAngle());
    }

    @Test
    void deplacementPourLeTourAvecCoxswainTest() {
        // Cas o?? le checkpoint est ?? un angle sup??rieur ou ??gal ?? PI/2 par rapport au bateau
        // Cas bateau ?? 4 rames et 1 coxswain
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
        Sailor coxswain = new Sailor(0, 0, 1, "Jean");
        strategie.stratData.setCoxswain(coxswain);
        Checkpoint checkpoint_angle_positif = new Checkpoint(new Position(10, 242.5, 0), new Circle("circle", 1));
        ship.getPosition().setOrientation(Math.PI / 3);
        // d??placement pour un angle inf??rieur ?? PI/4 entre le bateau et le checkpoint
        Deplacement deplacement_coxswain = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
        Vector v_ship = calculDeplacement.calculVecteurBateau(ship);
        Vector v_check = calculDeplacement.calculVecteurCheckpoint(checkpoint_angle_positif, ship);
        double angle = v_ship.angleBetweenVectors(v_check);
        Assertions.assertEquals(angle, deplacement_coxswain.getAngle());
        Assertions.assertEquals(165.0, deplacement_coxswain.getVitesse());

        //Cas d'un bateau avec 8 rames o?? l'angle du bateau par rapport au checkpoint est sup??rieur ?? PI/4
        jeu.getShip().getEntities().add(new OarEntity(2, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(2, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(3, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(3, 2, "oar"));
        ship.getPosition().setOrientation(Math.PI / 6 - 0.03);
        Deplacement deplacement_test = strategie.getCalculDeplacement().deplacementPourLeTourRefactor(checkpoint_angle_positif);
        Assertions.assertEquals(Math.PI / 4, deplacement_test.getAngle());
        Assertions.assertEquals(41.25, deplacement_test.getVitesse());
    }

    @Test
    void nbrSailorsNecessairesTest() {
        // Cas bateau ?? 6 rames
        jeu.getShip().getEntities().add(new OarEntity(0, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 1, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 2, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(0, 3, "oar"));
        jeu.getShip().getEntities().add(new OarEntity(1, 3, "oar"));
        int nb_oars = jeu.getShip().getOars().size();
        // On tourne ?? un angle de PI/3
        Assertions.assertEquals(2, strategie.getGestionMarins().nbrSailorsNecessaires(nb_oars, 55));
        // On tourne ?? un angle de PI/2
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
        // Cas d'un bateau ?? 4 rames donc possibilit?? de ramer d'un angle de PI/2 ou PI/4
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
        Circle shape_checkpoint1 = (Circle) checkpoint1.getShape();
        Checkpoint checkpoint2 = new Checkpoint(new Position(15, 10, 0), new Circle("circle", 2));
        Checkpoint checkpoint3 = new Checkpoint(new Position(5, 15, 0), new Circle("circle", 2));
        Checkpoint checkpoint4 = new Checkpoint(new Position(0, 15, 0), new Circle("circle", 2));
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().clear();
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().add(checkpoint1);

        // Cas o?? le prochain checkpoint est tout droit
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().add(checkpoint4);
        Checkpoint test = new Checkpoint(new Position(0, 12, 0), new Circle("circle", 2));
        Assertions.assertEquals(test.getPosition().getX(), calculDeplacement.targetCheckpointBorder(checkpoint1).getPosition().getX(), 1);
        Assertions.assertEquals(test.getPosition().getY(), calculDeplacement.targetCheckpointBorder(checkpoint1).getPosition().getY(), 1);
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().remove(checkpoint4);

        // Cas o?? le prochain checkpoint est ?? 90?? sur la droite
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().add(checkpoint2);
        Checkpoint test2 = new Checkpoint(new Position(2, 10, 0), new Circle("circle", 2));

        Assertions.assertEquals(test2.getPosition().getX(), checkpoint1.getPosition().getX() + (shape_checkpoint1.getRadius() * 0.9 * Math.cos(0)), 0.5);
        Assertions.assertEquals(test2.getPosition().getY(), checkpoint1.getPosition().getY() + (shape_checkpoint1.getRadius() * 0.9 * Math.sin(0)), 0.5);
        Assertions.assertEquals(test2.getPosition().getX(), calculDeplacement.targetCheckpointBorder(checkpoint1).getPosition().getX(), 1);
        Assertions.assertEquals(test2.getPosition().getY(), calculDeplacement.targetCheckpointBorder(checkpoint1).getPosition().getY(), 1);
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().remove(checkpoint2);

        // Cas o?? le prochain checkpoint est ?? 45?? sur la droite
        calculDeplacement.stratData.jeu.getGoal().getCheckpoints().add(checkpoint3);
        Checkpoint test3 = new Checkpoint(new Position(Math.sqrt(2), 10 + Math.sqrt(2), 0), new Circle("circle", 2));
        Assertions.assertEquals(test3.getPosition().getX(), calculDeplacement.targetCheckpointBorder(checkpoint1).getPosition().getX(), 1);
        Assertions.assertEquals(test3.getPosition().getY(), calculDeplacement.targetCheckpointBorder(checkpoint1).getPosition().getY(), 1);
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
    void pointIntersectionTest(){
        Vector v_ship = calculDeplacement.calculVecteurBateau(ship);
        // Exemple d'un checkpoint align?? ?? l'horizontale
        Checkpoint checkpoint_en_face = new Checkpoint(new Position(15, 0, 0), new Circle("circle", 2));
        List<Point> point_intersection = calculDeplacement.intersection(ship, v_ship, checkpoint_en_face);
        Assertions.assertEquals(13, point_intersection.get(0).getX());
        Assertions.assertEquals(0, point_intersection.get(0).getY());
        Assertions.assertEquals(17, point_intersection.get(1).getX());
        Assertions.assertEquals(0, point_intersection.get(1).getY());

        // Exemple pris sur internet
        ship.setPosition(new Position(0, -2, Math.PI/4));
        v_ship = calculDeplacement.calculVecteurBateau(ship);
        Checkpoint internet = new Checkpoint(new Position(5, 4, 0), new Circle("circle", Math.sqrt(8)));
        List<Point> point_intersection2 = calculDeplacement.intersection(ship, v_ship, internet);
        Assertions.assertEquals(3.56, point_intersection2.get(0).getX(), 0.01);
        Assertions.assertEquals(1.56, point_intersection2.get(0).getY(), 0.01);
        Assertions.assertEquals(7.44, point_intersection2.get(1).getX(), 0.01);
        Assertions.assertEquals(5.44, point_intersection2.get(1).getY(), 0.01);

        // Sym??trique de l'exemple pr??c??dent
        ship.setPosition(new Position(0, -2, -3*Math.PI/4));
        v_ship = calculDeplacement.calculVecteurBateau(ship);
        Checkpoint internet_symetrique = new Checkpoint(new Position(-5, -8, 0), new Circle("circle", Math.sqrt(8)));
        List<Point> point_intersection3 = calculDeplacement.intersection(ship, v_ship, internet_symetrique);
        Assertions.assertEquals(-7.44, point_intersection3.get(0).getX(), 0.01);
        Assertions.assertEquals(-9.44, point_intersection3.get(0).getY(), 0.01);
        Assertions.assertEquals(-3.56, point_intersection3.get(1).getX(), 0.01);
        Assertions.assertEquals(-5.56, point_intersection3.get(1).getY(), 0.01);

        // Exemple d'un checkpoint align?? ?? la verticale
        ship.setPosition(new Position(0, 2, Math.PI/2));
        v_ship = calculDeplacement.calculVecteurBateau(ship);
        Checkpoint checkpoint_vertical = new Checkpoint(new Position(0, 15, 0), new Circle("circle", 2));
        List<Point> point_intersection4 = calculDeplacement.intersection(ship, v_ship, checkpoint_vertical);
        Assertions.assertEquals(0, point_intersection4.get(0).getX());
        Assertions.assertEquals(13, point_intersection4.get(0).getY());
        Assertions.assertEquals(0, point_intersection4.get(1).getX());
        Assertions.assertEquals(17, point_intersection4.get(1).getY());
    }

    @Test
    void DistanceIntersectionTest(){
        ArrayList<Point> points = new ArrayList<>();
        // Cas o?? le deuxi??me point est le plus proche
        points.add(new Point(-7.44, -9.44));
        points.add(new Point(-3.56, -5.56));
        double distance_min = Math.sqrt(Math.pow((points.get(1).getX() - ship.getPosition().getX()), 2) + Math.pow((points.get(1).getY() - ship.getPosition().getY()), 2));
        Assertions.assertEquals(distance_min, calculDeplacement.getDistancePointIntersection(points, ship));
        points.clear();

        // Cas o?? le premier point est le plus proche
        points.add(new Point(13, 0));
        points.add(new Point(17, 0));
        distance_min = Math.sqrt(Math.pow((points.get(0).getX() - ship.getPosition().getX()), 2) + Math.pow((points.get(0).getY() - ship.getPosition().getY()), 2));
        Assertions.assertEquals(distance_min, calculDeplacement.getDistancePointIntersection(points, ship));
    }

    @Test
    void getStreamsTest(){
        Set<Stream> streams = new HashSet<>();
        Stream stream1 = new Stream("stream", new Position(0, 50, Math.PI/2), new Circle("circle", 2), 50);
        Stream stream2 = new Stream("stream", new Position(0, -50, -Math.PI/2), new Circle("circle", 2), 60);
        streams.add(stream1);
        streams.add(stream2);
        jeu.setStreams(streams);
        System.out.println(jeu.getStreams());
    }

    @Test
    void getDistance(){
        Checkpoint checkpoint1 = new Checkpoint(new Position(0, 10, 0), new Circle("circle", 2));
        Checkpoint checkpoint2 = new Checkpoint(new Position(10, 10, 0), new Circle("circle", 2));
        ship.setPosition(new Position(0, 0, 0));
        Assertions.assertEquals(10, calculDeplacement.getDistance(ship, checkpoint1));
        ship.setPosition(new Position(10, 10, 0));
        Assertions.assertEquals(0, calculDeplacement.getDistance(ship, checkpoint2));
    }
}