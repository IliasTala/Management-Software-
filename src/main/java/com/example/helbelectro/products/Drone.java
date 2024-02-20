package com.example.helbelectro.products;

import com.example.helbelectro.components.Component;

import java.util.List;

public class Drone extends Product {
    private int capacity;
    private int range;
    private String colorSensor;
    private int power;
    public Drone(List<Component> components, int power, int capacity, int range, String colorSensor) {
        super("Drone", "E", 60, 12, components);
    }


    @Override
    public String toString() {
        return "P7";
    }
    @Override
    public String getColor() { return "#a3a29e"; }

}
