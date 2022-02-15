package fr.unice.polytech.si3.qgl.ajil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.ajil.actions.Action;
import fr.unice.polytech.si3.qgl.ajil.actions.Deplacement;
import fr.unice.polytech.si3.qgl.ajil.actions.Moving;
import fr.unice.polytech.si3.qgl.ajil.actions.Oar;
import fr.unice.polytech.si3.qgl.ajil.shape.Circle;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
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

    public void effectuerActions() {
        if (!placementInit){
            placerSurRames();
        }
        whereAreSailors();//tester la création de branch
        Checkpoint c = checkpointTarget(jeu.getGoal().getCheckpoints());
        Deplacement deplacement =  deplacementPourLeTour(c);
        ramer(deplacement);

    }

    /**
     * Retourne le checkpoint à viser
     * @param checkpoints
     * @return
     */
    static int test = 1;
    public Checkpoint checkpointTarget(ArrayList<Checkpoint> checkpoints) {
        boolean estDedans = false;
        if(checkpoints.isEmpty()) {
            return null;
        }
        Checkpoint checkpointCurrent = checkpoints.get(0);
        Ship ship = jeu.getShip();
        ArrayList<Point> pointsDuBateau = calculPointShip(ship);
        if(checkpointCurrent.getShape() instanceof Circle) {
            estDedans = checkpointValide(pointsDuBateau, checkpointCurrent);
            if(estDedans){
                checkpoints.remove(checkpointCurrent);
                if(checkpoints.isEmpty()) {
                    checkpointCurrent = null;
                } else {
                    checkpointCurrent = checkpoints.get(0);
                }
            }
        }
        return checkpointCurrent;
    }

    /**
     * Calcule les quatres points des quatres coin du bateau.
     * @param ship
     * @return
     */
    public ArrayList<Point> calculPointShip(Ship ship){
        Point centre = new Point(ship.getPosition().getX(), ship.getPosition().getY());
        double largeur = ship.getDeck().getWidth();
        double longueur = ship.getDeck().getLength();
        double sinus = Math.sin(ship.getPosition().getOrientation());
        double cosinus = Math.cos(ship.getPosition().getOrientation());
        ArrayList<Point> pointShip = new ArrayList<>();

        //Matrice changement de référentiel
        ArrayList<ArrayList<Double>> matrice = new ArrayList<>();
        ArrayList<Double> firstColumn = new ArrayList<>();
        firstColumn.add(Math.cos(ship.getPosition().getOrientation()));
        firstColumn.add(Math.sin(ship.getPosition().getOrientation()));
        ArrayList<Double> secondColumn = new ArrayList<>();
        secondColumn.add(-1 * Math.sin(ship.getPosition().getOrientation()));
        secondColumn.add(Math.cos(ship.getPosition().getOrientation()));
        matrice.add(firstColumn);
        matrice.add(secondColumn);

        pointShip.add( new Point(largeur/2*cosinus+longueur/2*sinus, -largeur/2*sinus+longueur/2*cosinus).addPoint(centre));
        pointShip.add( new Point(-largeur/2*cosinus+longueur/2*sinus, largeur/2*sinus+longueur/2*cosinus).addPoint(centre));
        pointShip.add( new Point(largeur/2*cosinus-longueur/2*sinus, -largeur/2*sinus-longueur/2*cosinus).addPoint(centre));
        pointShip.add( new Point(-largeur/2*cosinus-longueur/2*sinus, largeur/2*sinus-longueur/2*cosinus).addPoint(centre));
        return pointShip;
    }

    /**
     * Dit si l'un des points du bateau est dans le checkpoint
     * @param pointsDuBateau
     * @param checkpoint
     * @return true si l'un est dedans, false sinon
     */
    public boolean dansLeCercle(ArrayList<Point> pointsDuBateau, Checkpoint checkpoint){
        Point centreCheckpoint = new Point(checkpoint.getPosition().getX(), checkpoint.getPosition().getY());
        for(Point point : pointsDuBateau){
            if(point.distance(centreCheckpoint) <= ((Circle)(checkpoint.getShape())).getRadius()){
                return true;
            }
        }
        return false;
    }

    /**
     * Dit si le bateau a un point d'intection
     * @param pointDuBateau
     * @param checkpoint
     * @return
     */
    public boolean intersectionCircleShip( ArrayList<Point> pointDuBateau, Checkpoint checkpoint){
        for(int i=0; i<pointDuBateau.size()-1; i++ ){
            for(int j=i+1; j< pointDuBateau.size(); j++){
                //equation cercle (x-checkpoint.x)^2 + (y-checkpoint.y)^2 = R^2
                // équation de la droite du bateau = y = ax+b
                double r = ((Circle) checkpoint.getShape()).getRadius();
                double xc = checkpoint.getPosition().getX();
                double yc = checkpoint.getPosition().getY();
                double xb1 = pointDuBateau.get(i).getX();
                double yb1 = pointDuBateau.get(i).getY();
                double xb2 = pointDuBateau.get(j).getX();
                double yb2 = pointDuBateau.get(j).getY();
                if(xb1==xb2) {
                    if(intersectionDroiteVerticaleCircle(new Point(xb1, yb1), new Point(xb2, yb2), checkpoint)){
                        return true;
                    }
                    continue;
                }
                double a = (yb2 - yb1)/(xb2-xb1);
                double b = (yb1 - a*xb1);
                //Les points pour les résultats
                double x1;
                double y1;
                double x2;
                double y2;

                //Après simplification on obtient une équation du deuxième degré et on obtient donc un delta.
                //Equation : alpha x^2 + beta x + c = 0
                double beta = -2 * xc - 2 * a * b + 2 * a * yc;
                double alpha = (a*a +1);
                double delta = beta * beta - 4*alpha *(xc*xc + (b-yc)*(b-yc) -r*r);
                if(delta<0){
                    continue;
                } else {
                    x1 = (-beta - Math.sqrt(delta)) / (2*alpha);
                    y1 = a*x1 + b;
                    x2 = (-beta + Math.sqrt(delta)) / (2*alpha);
                    y2 = a*x2+b;
                    // x1 appartient à [xb1 ; xb2] ou [xb2 ; xb1] et y1 appartient à [yb1 ; yb2] ou [yb2 ; yb1]
                    if( ((xb1<=x1 && x1<=xb2) || (xb1>=x1 && x1>=xb2)) && ((yb1<=y1 && y1<=yb2) || (yb1>=y1 && y1>=yb2))){
                        return true;
                    }
                    // x2 appartient à [xb1 ; xb2] ou [xb2 ; xb1] et y2 appartient à [yb1 ; yb2] ou [yb2 ; yb1]
                    if(((xb1<=x2 && x2<=xb2) || (xb1>=x2 && x2>=xb2)) && ((yb1<=y2 && y2<=yb2) || (yb1>=y2 && y2>=yb2)) ){
                        System.out.println(alpha+"x^2 + " + beta+"x + " + (xc*xc + (b-yc)*(b-yc) -r*r) );
                        System.out.println(delta);
                        System.out.println(x1 +" "+ y1+ " "+ x2 + " " + y2);
                        System.out.println(checkpoint);
                        System.out.println(pointDuBateau.get(i));
                        System.out.println(pointDuBateau.get(j));
                        System.out.println(2);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Lors d'une droite verticale, l'équation de droite change
     * @param point1
     * @param point2
     * @param checkpoint
     * @return true si le côté du bateau coupe le checkpoint
     */
    boolean intersectionDroiteVerticaleCircle(Point point1, Point point2, Checkpoint checkpoint){
        //Dans ce cas la droite du bateau est de la forme x=a;
        double a = point1.getX();
        double xb1 = point1.getX();
        double yb1 = point1.getY();
        double xb2 = point2.getX();
        double yb2 = point2.getY();
        double xc = checkpoint.getPosition().getX();
        double yc = checkpoint.getPosition().getY();
        double r = ((Circle) checkpoint.getShape()).getRadius();
        double b = -2*yc;
        //On obtient une équation du deuxième degré et on obtient ce delta
        double delta = b*b - 4*(a*a + xc*xc -2*a*xc + yc*yc -r*r);
        double y1;
        double y2;
        if( delta < 0){
            return false;
        }
        y1 = (-b - Math.sqrt(delta)) / 2;
        y2 = (-b + Math.sqrt(delta)) / 2;
        if( (yb1<=y1 && y1<=yb2) || (yb2<=y1 && y1<=yb1)){
            return true;
        }
        if( (yb1<=y2 && y2<=yb2) || (yb2<=y2 && y2<=yb1)){
            return true;
        }
        return false;
    }

    /**
     * Nous dit si le checkpoint est validé.
     * @param pointsDuBateau
     * @param checkpoint
     * @return true si validé, false sinon
     */
    boolean checkpointValide(ArrayList<Point> pointsDuBateau, Checkpoint checkpoint){
        return dansLeCercle(pointsDuBateau, checkpoint) || intersectionCircleShip(pointsDuBateau, checkpoint);
    }

    public void ramer(Deplacement deplacement) {
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
    public void placerSurRames() {
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

    public Deplacement deplacementPourLeTour(Checkpoint c){
        Ship s = jeu.getShip();
        Vector v_ship = new Vector(Math.cos(s.getPosition().getOrientation()), Math.sin(s.getPosition().getOrientation()));
        Vector v_check = new Vector(c.getPosition().getX() - s.getPosition().getX(),c.getPosition().getY()-s.getPosition().getY());
        double angle = v_ship.angleBetweenVectors(v_check);
        boolean g_or_d = checkpointEstAGauche(c, angle);
        ArrayList<Deplacement> futur_angle = predictionAngleTourSuivant(v_ship, v_check);
        Deplacement deplacement = new Deplacement(); //vitesse en premier, angle en deuxième
        //System.out.println(g_or_d);
        if(angle >= Math.PI/2){
            // Faire une rotation de PI/2
            deplacement.setVitesse(82.5);
            if(g_or_d){
                deplacement.setAngle(-Math.PI/2);
            }
            else{
                deplacement.setAngle(Math.PI/2);
            }
            return deplacement;
        }
        else if(angle < Math.PI/2 && angle > Math.PI/4){
            // Faire une rotation de PI/4
            deplacement.setVitesse(41.25);
            if(g_or_d){
                deplacement.setAngle(-Math.PI/4);
            }
            else{
                deplacement.setAngle(Math.PI/4);
            }
            return deplacement;
        }
        else if (angle < Math.PI/4 && angle > 0){
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
     * Analyse si le checkpoint est à gauche ou à droite du bateau
     * @param c
     * @param angle
     * @return true si le checkpoint est à gauche du bateau, false sinon
     */
    public boolean checkpointEstAGauche(Checkpoint c, double angle){
        Ship s = jeu.getShip();
        double x = s.getPosition().getX();
        double y = s.getPosition().getY();
        double dst = distance(c, s);
        if(((x + (dst * Math.cos(angle))) != c.getPosition().getX()) || ((y + (dst * Math.sin(angle))) != c.getPosition().getY())){
            return true;
        }
        return false;
    }

    /**
     * Le bateau avance droit, on calcule les différents angles qu'on obtiendra en fonction de la vitesse du bateau qu'on peut appliquer
     * au bateau
     * @param ship
     * @param checkpoint
     * @return une liste de liste de double où chaque sous-liste contient une vitesse associée à un angle
     */
    public ArrayList<Deplacement> predictionAngleTourSuivant(Vector ship, Vector checkpoint){
        int nbr_oars = jeu.getShip().getOars().size();
        ArrayList<Deplacement> prediction = new ArrayList<>();
        double vitesse = 165;
        double angle_init = ship.angleBetweenVectors(checkpoint);
        double angle_apres_deplacement = 0;
        double positionX_init = ship.getX();
        double positionY_init = ship.getY();
        for(int i = 0; i < jeu.getShip().getOars().size()/2; i++){
            vitesse = (vitesse * (nbr_oars-2*i))/nbr_oars;
            ship.setX(positionX_init + (vitesse * Math.cos(angle_init))); //On modifie la coordonée en X du bateau en fonction de son orientation
            ship.setY(positionY_init + (vitesse * Math.sin(angle_init))); //On modifie la coordonée en Y du bateau en fonction de son orientation
            angle_apres_deplacement = ship.angleBetweenVectors(checkpoint);
            prediction.add(new Deplacement(vitesse, angle_apres_deplacement));
        }
        return prediction;
    }

    void analyseCheminASuivre(Goal g, Ship ship){
        ArrayList<Checkpoint> checkpoints = g.getCheckpoints();
    }

    public Checkpoint checkpointPlusProche(ArrayList<Checkpoint> checkpoints, Ship ship){
        double distMin = -1;
        Checkpoint proche = checkpoints.get(0);
        for (Checkpoint c: checkpoints){
            double dst = distance(c, ship);
            if(distMin == -1 || distMin > dst){
                distMin = dst;
                proche = c;
            }
        }
        return proche;
    }

    public double distance(Checkpoint c, Ship s){
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
