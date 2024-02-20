package com.example.helbelectro.products;

import com.example.helbelectro.components.Component;

import java.util.List;

public class MotorElectrical extends Product {

    private final String type = "Moteur électrique";
    private final int timeFactor = 3;
    private final int price = 15;
    private final String ecoScore = "A";
    private int power;

    public MotorElectrical(List<Component> components, int power)
    {
        super("Moteur électrique", "A", 15, 3, components);
        this.power = power;
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
    public String toString() { return "P3"; }
    @Override
    public String getColor() { return "#9b54f7"; }
    @Override
    public String getTypeProduct() { return this.type; }
}
