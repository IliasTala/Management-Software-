package com.example.helbelectro.products;

import com.example.helbelectro.components.Component;
import com.example.helbelectro.products.Product;

import java.util.List;

public class RemoteCar extends Product {
    private final String type = "Voiture télécommandée";
    private final int timeFactor = 8;
    private final int price = 30;
    private final String ecoScore = "B";

    private int capacity;
    private int power;
    public RemoteCar(List<Component> components, int power, int capacity) {
        super("Voiture télécommandée", "D", 30, 8, components);
        this.power = power;
        this.capacity=capacity;
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
    public String toString() { return "P5";  }

    @Override
    public String getColor() {
        return "#bf7128";
    }
    @Override
    public String getTypeProduct() { return this.type; }
}

