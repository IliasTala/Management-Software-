package com.example.helbelectro.components;


public class FactoryComponents {

    private static volatile FactoryComponents instance = null;
    private static final String BATTERIE = "Batterie";
    private static final String MOTEUR = "Moteur";
    private static final String CAPTEUR = "Capteur";
    public static FactoryComponents getInstance() {
        synchronized (FactoryComponents.class) {
            if (FactoryComponents.instance == null) {
                FactoryComponents.instance = new FactoryComponents();
            }
        }
        return FactoryComponents.instance;
    }
    public Component createComponent(String type, String value, String color) {
        int numericValue = parseValue(value);
        switch (type) {
            case BATTERIE:
                return new BatteryComponent(numericValue);
            case MOTEUR:
                return new MotorElectricalComponent(numericValue);
            case CAPTEUR:
                return new SensorMotionComponent(numericValue, color);
            default:
                throw new IllegalArgumentException("Invalid component type: " + type);
        }
    }

    private int parseValue(String value) {
        return Integer.parseInt(value.substring(0, value.length()-1));
    }
}
