package com.example.helbelectro.components;


public class BatteryComponent extends Component {

    private final int capacity;

    public BatteryComponent(int capacity) {
        super("#78c4fa", "C-Type-1", "battery");
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getDetails() {
        return "Charge : "+capacity+"%";
    }
}


