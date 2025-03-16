// You can add extra methods if you think it is necessary

public class Uav {
    private String name;
    private double operationalCost; // this is per hour cost
    private int availability; // possible values -> 0 to 5, where 0 is not available and 5 is available all
                              // the time.

    // There can only be 3 sensors in a Uav and since we dont use arrays now, we
    // define vairables for each one of them
    private Sensor sensor1;
    private Sensor sensor2;
    private Sensor sensor3;

    public Uav(String nameVal, double operationalCostVal, int availabilityVal) {
        name = nameVal;
        operationalCost = operationalCostVal;
        availability = availabilityVal;
    }

    public String getName() {
        return name;
    }

    public double getOperationalCost() {
        return operationalCost;
    }

    public int getAvailability() {
        return availability;
    }

    private Sensor getSensorByType(String type) {
        if (sensor1 != null && sensor1.getType().equalsIgnoreCase(type)) {
            return sensor1;
        } else if (sensor2 != null && sensor2.getType().equalsIgnoreCase(type)) {
            return sensor2;
        } else if (sensor3 != null && sensor3.getType().equalsIgnoreCase(type)) {
            return sensor3;
        } else {
            return null;
        }
    }

    public String addSensor(String sensorType, int grade, int quantity) {
        if (sensor1 != null && sensor2 != null && sensor3 != null) {
            return "You already have added 3 types of sensors for this UAV. Cannot add more.";
        }

        if (getSensorByType(sensorType) != null) {
            return "This UAV already has " + sensorType + " sensor. Cannot add again.";
        }

        Sensor sensor = new Sensor(sensorType, grade, quantity);
        if (sensor1 == null) {
            sensor1 = sensor;
        } else if (sensor2 == null) {
            sensor2 = sensor;
        } else if (sensor3 == null) {
            sensor3 = sensor;
        }

        return "Sensor has been added";
    }

    public String removeSensor(String type, int quantity) {
        Sensor sensorToRemove = getSensorByType(type);

        if (sensorToRemove == null) {
            return "Sensor type does not exist on this UAV.";
        }

        if (sensorToRemove.getQuantity() < quantity) {
            return "Remove quantity is more than the existing number of sensors.";
        }

        sensorToRemove.setQuantity(sensorToRemove.getQuantity() - quantity);

        if (sensorToRemove.getQuantity() == 0) {
            if (sensor1 == sensorToRemove) {
                sensor1 = null;
            } else if (sensor2 == sensorToRemove) {
                sensor2 = null;
            } else if (sensor3 == sensorToRemove) {
                sensor3 = null;
            }
        }

        return quantity + " " + type + " sensor has been removed.";
    }

    public String listSensors(String uavName) {
        if (sensor1 == null && sensor2 == null && sensor3 == null) {
            return "No sensors on " + uavName + ".";
        }
        String infoAboutSensors = "";
        if (sensor1 != null) {
            infoAboutSensors = sensor1.sensorDetails();
        }
        if (sensor2 != null) {
            infoAboutSensors = infoAboutSensors + sensor2.sensorDetails();
        }
        if (sensor3 != null) {
            infoAboutSensors = infoAboutSensors + sensor3.sensorDetails();
        }
        return infoAboutSensors;
    }

    public String filterBySensor(String type) {
        Sensor selectedSensor = getSensorByType(type);
        if (selectedSensor != null) {
            int quantity = selectedSensor.getQuantity();
            int grade = selectedSensor.getGrade();
            String sensorName = selectedSensor.sensorTitleUppercase(type);

            return sensorName + " sensor is on " + name + ", Grade " + grade + ", Quantity " + quantity + ".\n";
        }
        return "";
    }

    public Sensor validateSensorAvailability(String type, int minGrade, int minQuantity) {
        Sensor selectedSensor = getSensorByType(type);

        if (selectedSensor.getGrade() < minGrade && selectedSensor.getQuantity() > minQuantity) {
            return selectedSensor;
        }
        return null;
    }
}