package fr.unice.polytech.si3.qgl.ajil.shape;

public class DroiteLambda implements Droite {

    private double alpha;
    private  double beta;

    public DroiteLambda(double alpha, double beta){
        this.alpha = alpha;
        this.beta = beta;
    }

    public DroiteLambda(Point point1, Point point2){
        double alpha = (point2.getY() - point1.getY())/(point2.getX() - point1.getX());
        double beta = point1.getY() - (point1.getY())*alpha;
        this.alpha = alpha;
        this.beta = beta;
    }
}
