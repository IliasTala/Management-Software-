package com.example.helbelectro.products;

import com.example.helbelectro.components.Component;

import java.util.List;

public abstract class Product {
    protected final String typeProduct;
    protected final int timeFactor;
    protected final String ecoScore;
    protected final int price;
    protected List<Component> components;

    public Product(String typeProduct, String ecoScore, int price, int timeFactor, List<Component> components) {
        this.typeProduct = typeProduct;
        this.timeFactor = timeFactor;
        this.ecoScore = ecoScore;
        this.price = price;
        this.components = components;
    }

    public abstract String toString();
    public abstract String getColor();

    public String getTypeProduct() { return this.typeProduct; }
    public String getEcoScore() { return this.ecoScore; }
    public int getPrice() { return this.price; }
    public int getTimeFactor() { return this.timeFactor; }

    public List<Component> getComponents() { return components;  }
}
