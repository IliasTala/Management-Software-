package com.example.helbelectro.products;

import com.example.helbelectro.components.Component;

import java.util.List;

public class Alarm extends Product {

    private int capacity;
    private int range;
    private String colorSensor;

    public Alarm(List<Component> components, int capacity, int range, String colorSensor) {
        super("Alarme de sécurité", "C", 20, 4, components);
        this.capacity = capacity;
        this.range = range;
        this.colorSensor = colorSensor;

    }


    @Override
    public String toString() { return "P4";  }

    @Override
    public String getColor() { return "#a84848"; }


}

