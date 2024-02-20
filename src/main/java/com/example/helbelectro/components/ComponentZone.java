package com.example.helbelectro.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ComponentZone {
    public final int maxComponentCanBeCreated = 8;
    private ObservableList<Component> components;

    public ComponentZone() {
        this.components = FXCollections.observableArrayList();
    }

    public ObservableList<Component> getComponents() {
        return this.components;
    }

    public void addComponent(Component component) {
        if (this.components.size() < maxComponentCanBeCreated) {
            this.components.add(component);
        }
    }
    public void removeComponent(Component component) {
        this.components.remove(component);
    }

    public int getMaxComponentCanBeCreated(){
        return maxComponentCanBeCreated;
    }
}
