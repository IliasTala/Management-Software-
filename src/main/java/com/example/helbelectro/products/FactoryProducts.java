package com.example.helbelectro.products;

import com.example.helbelectro.components.BatteryComponent;
import com.example.helbelectro.components.Component;
import com.example.helbelectro.components.MotorElectricalComponent;
import com.example.helbelectro.components.SensorMotionComponent;

import java.util.List;

public class FactoryProducts {

    private static volatile FactoryProducts instance = null;
    private FactoryProducts() {super();}
    public static FactoryProducts getInstance() {
        synchronized (FactoryProducts.class) {
            if (FactoryProducts.instance == null) {
                FactoryProducts.instance = new FactoryProducts();
            }
        }
        return FactoryProducts.instance;
    }

    public Product createProduct(String type, List<Component> components) {
        switch (type) {
            case "Batterie" -> {
                int batteryCapacity = 0;
                for (Component component : components) {
                    if (component instanceof BatteryComponent) {
                        batteryCapacity = ((BatteryComponent) component).getCapacity();
                        break;
                    }
                }
                return new Battery(components, batteryCapacity);
            }
            case "Capteur de mouvement" -> {
                int range = 0;
                String colorSensor = null;
                for (Component component : components) {
                    if (component instanceof SensorMotionComponent) {
                        range = ((SensorMotionComponent) component).getRange();
                        colorSensor = ((SensorMotionComponent) component).getColorSensor();
                        break;
                    }
                }
                return new SensorMotion(components, range, colorSensor);
            }
            case "Moteur électrique" -> {
                int power = 0;
                for (Component component : components) {
                    if (component instanceof MotorElectricalComponent) {
                        power = ((MotorElectricalComponent) component).getPower();
                        break;
                    }
                }
                return new MotorElectrical(components, power);
            }
            case "Alarme de sécurité" -> {
                int alarmBatteryCapacity = 0;
                int alarmRange = 0;
                String alarmColorSensor = null;
                for (Component component : components) {
                    if (component instanceof BatteryComponent) {
                        alarmBatteryCapacity = ((BatteryComponent) component).getCapacity();
                    } else if (component instanceof SensorMotionComponent) {
                        alarmRange = ((SensorMotionComponent) component).getRange();
                        alarmColorSensor = ((SensorMotionComponent) component).getColorSensor();
                    }
                }
                return new Alarm(components, alarmBatteryCapacity, alarmRange, alarmColorSensor);
            }
            case "Voiture télécommandée" -> {
                int carPower = 0;
                int carBatteryCapacity = 0;
                for (Component component : components) {
                    if (component instanceof MotorElectricalComponent) {
                        carPower = ((MotorElectricalComponent) component).getPower();
                    } else if (component instanceof BatteryComponent) {
                        carBatteryCapacity = ((BatteryComponent) component).getCapacity();
                    }
                }
                return new RemoteCar(components, carPower, carBatteryCapacity);
            }
            case "Robot suiveur" -> {
                int robotPower = 0;
                int robotRange = 0;
                String robotColorSensor = null;
                for (Component component : components) {
                    if (component instanceof MotorElectricalComponent) {
                        robotPower = ((MotorElectricalComponent) component).getPower();
                    } else if (component instanceof SensorMotionComponent) {
                        robotRange = ((SensorMotionComponent) component).getRange();
                        robotColorSensor = ((SensorMotionComponent) component).getColorSensor();
                    }
                }
                return new Robot(components, robotPower, robotRange, robotColorSensor);
            }
            case "Drone de surveillance" -> {
                int dronePower = 0;
                int droneBatteryCapacity = 0;
                int droneRange = 0;
                String droneColorSensor = null;
                for (Component component : components) {
                    if (component instanceof MotorElectricalComponent) {
                        dronePower = ((MotorElectricalComponent) component).getPower();
                    } else if (component instanceof BatteryComponent) {
                        droneBatteryCapacity = ((BatteryComponent) component).getCapacity();
                    } else if (component instanceof SensorMotionComponent) {
                        droneRange = ((SensorMotionComponent) component).getRange();
                        droneColorSensor = ((SensorMotionComponent) component).getColorSensor();
                    }
                }
                return new Drone(components, dronePower, droneBatteryCapacity, droneRange, droneColorSensor);
            }
            default -> throw new IllegalArgumentException("Invalid product type: " + type);
        }
    }
}
