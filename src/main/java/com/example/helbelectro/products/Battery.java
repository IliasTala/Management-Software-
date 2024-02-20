package com.example.helbelectro.products;

import com.example.helbelectro.components.Component;

import java.util.List;

public class Battery extends Product {
    private int capacity;

    public Battery(List<Component> components, int capacity) {
        super("Batterie", "C", 5, 3, components);
        this.capacity = capacity;
    }

    @Override
    public String toString() { return "P1"; }
    @Override
    public String getColor() { return "#78c4fa"; }
}
