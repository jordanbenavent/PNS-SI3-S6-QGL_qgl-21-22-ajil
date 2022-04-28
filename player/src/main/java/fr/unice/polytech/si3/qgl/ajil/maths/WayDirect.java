package fr.unice.polytech.si3.qgl.ajil.maths;

import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.Ship;
import fr.unice.polytech.si3.qgl.ajil.shape.Point;
import fr.unice.polytech.si3.qgl.ajil.strategy.pathfinding.ObstacleDetection;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.Reef;
import fr.unice.polytech.si3.qgl.ajil.visibleentities.VisibleEntitie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class  WayDirect {


    public static boolean wayDirect(Position checkpointCiblePosition,Ship ship, Set<Reef> setReef) {
        System.out.println("calcule WayDirect");
        Point shipPoint = new Point(ship.getPosition().getX(), ship.getPosition().getY());
        Point checkpointPoint = new Point(checkpointCiblePosition.getX(), checkpointCiblePosition.getY());
        Segment bateauCheckpoint = new Segment(shipPoint, checkpointPoint);


        List<VisibleEntitie> listeReef = new ArrayList(setReef);
        List<VisibleEntitie> listePolygoneReef = CalculPoints.entitiesToEntitiesPolygone(listeReef, ship.getDeck().getWidth());
        List<Segment> segments;
        ObstacleDetection obstacleDetection = new ObstacleDetection();

        for (int i = 0; i < listePolygoneReef.size(); i += 1) { //A CHECKER
            segments = obstacleDetection.reefToSegments(listePolygoneReef.get(i));
            for (Segment s : segments) {
                if (Intersection.segmentIntersection(s, bateauCheckpoint) != null) {
                    System.out.println("Intersection repere");
                    return false;
                }
            }
        }
        System.out.println("aucune inter repere");
        return true; //si on est la c'est qu'on a croise aucune intersection
    }



}
