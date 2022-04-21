package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * Classe mère VisibleEntitie regroupant les méthodes de base d'une entité visible
 *
 * @author Alexis Roche
 * @author Louis Hattiger
 * @author Jordan Benavent
 * @author Igor Melnyk
 * @author Tobias Bonifay
 *
 */

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OtherShip.class, name = "ship"),
        @JsonSubTypes.Type(value = Reef.class, name = "reef"),
        @JsonSubTypes.Type(value = Stream.class, name = "stream")
})
public class VisibleEntitie {
    private String type;
    private Position position;
    private Shape shape;
    private VisibleEntities typeEntity;

    public VisibleEntitie() {
    }

    public VisibleEntitie(String type, Position position, Shape shape, VisibleEntities visibleEntities) {
        this.type = type;
        this.position = position;
        this.shape = shape;
    }

    /**
     * @return le type d'entité visible
     */
    public String getType() {
        return type;
    }

    /**
     * Modifie le type de l'entité visible
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return la position de l'entité visible
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Modifie la position de l'entité visible
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return la forme de l'entité visible
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Modifie la forme de l'entité visible
     * @param shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * @return le type de l'entité (format enum)
     */
    public VisibleEntities getTypeEntity() {
        return typeEntity;
    }

    /**
     * Modifie le type de l'entité (format enum)
     * @param typeEntity
     */
    public void setTypeEntity(VisibleEntities typeEntity) {
        this.typeEntity = typeEntity;
    }

    /**
     * @return un string composé du type, de la position et de la forme de l'entité
     */
    @Override
    public String toString() {
        return "VisibleEntitie{" +
                "type='" + type + '\'' +
                ", position=" + position +
                ", shape=" + shape +
                '}';
    }

    public VisibleEntitie copy() {
        return new VisibleEntitie(type, position, shape, typeEntity);
    }
}
