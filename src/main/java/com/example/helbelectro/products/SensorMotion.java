package com.example.helbelectro.products;

import com.example.helbelectro.components.Component;

import java.util.List;

public class SensorMotion extends Product {

    private final String type = "Capteur de mouvement";
    private final int timeFactor = 3;
    private final int price = 10;
    private final String ecoScore = "B";
    private int range;
    private String colorSensor;
    public SensorMotion(List<Component> components, int range, String colorSensor) {
        super("SensorMotion", "B", 10, 3, components);
        this.range = range;
        this.colorSensor = colorSensor;
    }

    @Override
    public int getTimeFactor() {
        return this.timeFactor;
    }
    @Override
    public int getPrice(){
        return this.price;
    }
    @Override
    public String getEcoScore(){ return this.ecoScore; }
    @Override
    public String toString() { return "P2"; }
    @Override
    public String getColor() { return "#8bfc96"; }
    @Override
    public String getTypeProduct() { return this.type; }
}
