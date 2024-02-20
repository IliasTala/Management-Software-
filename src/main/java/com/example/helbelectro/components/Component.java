package com.example.helbelectro.components;

public abstract class Component {

    private final String color;
    private final String typeComponent;
    private final String type;

    protected Component(String color, String typeComponent, String type) {
        this.color = color;
        this.typeComponent = typeComponent;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public String getTypeComponent() {
        return typeComponent;
    }

    public String getType() {
        return type;
    }

    public abstract String getDetails();
}

