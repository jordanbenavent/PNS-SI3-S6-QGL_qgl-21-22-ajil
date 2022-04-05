package fr.unice.polytech.si3.qgl.ajil.visibleentities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.ajil.Position;
import fr.unice.polytech.si3.qgl.ajil.shape.Shape;
import fr.unice.polytech.si3.qgl.ajil.shipentities.*;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value= OtherShip.class, name = "ship"),
        @JsonSubTypes.Type(value= Reef.class, name = "reef"),
        @JsonSubTypes.Type(value= Stream.class, name = "stream")
})
public class VisibleEntitie {
    private String type;
    private Position position;
    private Shape shape;
    private VisibleEntities typeEntity;

    public VisibleEntitie(){}

    public VisibleEntitie(String type, Position position, Shape shape, VisibleEntities visibleEntities){
        this.type=type;
        this.position=position;
        this.shape=shape;
    }

    public String getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setType (String type) {
        this.type=type;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public VisibleEntities getTypeEntity() {
        return typeEntity;
    }

    public void setTypeEntity(VisibleEntities typeEntity) {
        this.typeEntity = typeEntity;
    }

    @Override
    public String toString() {
        return "VisibleEntitie{" +
                "type='" + type + '\'' +
                ", position=" + position +
                ", shape=" + shape +
                '}';
    }

    public VisibleEntitie copy(){
        return new VisibleEntitie(type,position, shape,typeEntity);
    }
}
