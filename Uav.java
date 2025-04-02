//Submission: Rohit Shrestha - 3502400

// You can add extra methods if you think it is necessary

public class Uav {
    private String name;
    private double operationalCost; // this is per hour cost
    private int availability; // possible values -> 0 to 5, where 0 is not available and 5 is available all
                              // the time.

    private Sensor[] sensors = new Sensor[3];

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
        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] != null && sensors[i].getType().equalsIgnoreCase(type)) {
                return sensors[i];
            }
        }
        return null;
    }

    private int getSensorsCount() {
        int count = 0;
        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] != null) {
                count++;
            }
        }
        return count;
    }

    public String addSensor(String sensorType, int grade, int quantity) {
        if (getSensorsCount() >= 3) {
            return "You already have added 3 types of sensors for this UAV. Cannot add more.";
        }
        if (getSensorByType(sensorType) != null) {
            return "This UAV already has " + sensorType + " sensor. Cannot add again.";
        }

        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] == null) {
                sensors[i] = new Sensor(sensorType, grade, quantity);
                break;
            }
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
            for (int i = 0; i < sensors.length; i++) {
                if (sensors[i] == sensorToRemove) {
                    sensors[i] = null;
                }
            }
        }
        return quantity + " " + type + " sensor has been removed from " + name + ".";
    }

    public String listSensors(String uavName) {
        if (sensors[0] == null && sensors[1] == null && sensors[2] == null) {
            return "No sensors on " + uavName + ".";
        }
        String infoAboutSensors = "";
        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] != null) {
                infoAboutSensors = infoAboutSensors + sensors[i].sensorDetails();
            }
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

    public String getUavDetails() {
        int SensorQuantity = 0;
        for (int i = 0; i < sensors.length; i++) {
            if (sensors[i] != null) {
                SensorQuantity += sensors[i].getQuantity();
            }
        }
        return "UAV " + name + " has " + SensorQuantity + " sensors.\n";
    }

    public void testUav() {
        System.out.println("========================================");

        System.out.println("Test Case 1: verify getName method");
        System.out.println("Expected output: Test Uav");
        System.out.println("Actual output: " + getName());

        System.out.println("========================================");

        System.out.println("Test Case 2: verify getOperationalCost method");
        System.out.println("Expected output: 100.0");
        System.out.println("Actual output: " + getOperationalCost());

        System.out.println("========================================");

        System.out.println("Test Case 3: verify getAvailability method");
        System.out.println("Expected output: 1");
        System.out.println("Actual output: " + getAvailability());

        System.out.println("========================================");

        System.out.println("Test Case 4: verify adding sensor successfully");
        System.out.println("Expected output: Sensor has been added");
        System.out.println("Actual output: " + addSensor("temperature", 1, 10));

        System.out.println("========================================");

        System.out.println("Test Case 5: verify removing sensor successfully");
        System.out.println("Expected output: 10 Temperature sensor has been removed from Test Uav.");
        System.out.println("Actual output: " + removeSensor("temperature", 10));

        System.out.println("========================================");

        Sensor testSensor = new Sensor("temperature", 3, 10);

        System.out.println("Test Case 6: verify getType method in sensor");
        System.out.println("Expected output: temperature");
        System.out.println("Actual output: " + testSensor.getType());

        System.out.println("========================================");

        System.out.println("Test Case 7: verify getGrade method in sensor");
        System.out.println("Expected output: 3");
        System.out.println("Actual output: " + testSensor.getGrade());

        System.out.println("========================================");

        System.out.println("Test Case 8: verify getQuantity method");
        System.out.println("Expected output: 10");
        System.out.println("Actual output: " + testSensor.getQuantity());

        System.out.println("========================================");

        System.out.println("Test Case 9: verify setQuantity method");
        System.out.println("Expected output: 20");
        testSensor.setQuantity(20);
        System.out.println("Actual output: " + testSensor.getQuantity());

        System.out.println("========================================");

        System.out.println("Test Case 10: verify sensorDetails method");
        System.out.println("Expected output: Temperature sensor, Grade 3, Quantity 20");
        System.out.println("Actual output: " + testSensor.sensorDetails());
    }
}