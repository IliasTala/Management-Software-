package com.example.helbelectro.products;

import com.example.helbelectro.components.Component;
import com.example.helbelectro.products.Product;

import java.util.List;

public class Robot extends Product {

    private final String type = "Robot suiveur";
    private final int timeFactor = 6;
    private final int price = 40;
    private final String ecoScore = "B";
    private int power;
    private int range;
    private String colorSensor;

    public Robot(List<Component> components, int power, int range, String colorSensor) {
        super("Robot suiveur", "B", 40, 6, components);
        this.power = power;
        this.range = range;
        this.colorSensor = colorSensor;
    }

    @Override
    public int getTimeFactor() {
        return this.timeFactor;
    }
    @Override
    public int getPrice() {
        return this.price;
    }
    @Override
    public String getEcoScore() {
        return this.ecoScore;
    }
    @Override
    public String toString() { return "P6";  }
    @Override
    public String getTypeProduct() { return this.type; }
    @Override
    public String getColor() { return "#f2d94b"; }
}

