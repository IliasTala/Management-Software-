package com.example.helbelectro.components;

public class MotorElectricalComponent extends Component {

    private final int power;

    public MotorElectricalComponent(int power) {
        super("#9b54f7", "C-Type-3", "motorElectrical");
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String getDetails() {
        return "Puissance : "+power+"W";
    }
}