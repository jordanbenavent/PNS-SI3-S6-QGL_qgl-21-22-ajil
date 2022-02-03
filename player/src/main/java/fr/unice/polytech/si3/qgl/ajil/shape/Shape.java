package fr.unice.polytech.si3.qgl.ajil.shape;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;


@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Circle.class, name = "circle"),
        @JsonSubTypes.Type(value=Rectangle.class, name = "rectangle"),
        @JsonSubTypes.Type(value=Polygone.class, name = "polygone")
})
public class Shape {
    private String type;

    public Shape(){}
    public Shape(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "type='" + type + '\'' +
                '}';
    }
}
