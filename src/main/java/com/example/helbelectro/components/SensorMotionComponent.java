package com.example.helbelectro.components;

public class SensorMotionComponent extends Component {

    private final int range  ;
    private final String colorSensor;

    public SensorMotionComponent(int range, String colorSensor) {
        super("#8bfc96", "C-Type-2", "sensorMotion");
        this.colorSensor = colorSensor;
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public String getColorSensor() {
        return colorSensor;
    }

    @Override
    public String getDetails() {
        String detail = "Couleur : " + colorSensor + "\n"+"Portée : " + range+"m";
        return detail;
    }
}