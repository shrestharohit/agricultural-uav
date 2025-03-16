import javax.swing.*;

public class UavInterface {
    // Built the system to support 4 uavs only at max and defined the variables to
    // store them since we cannot use arrays yet.
    private Uav uav1 = null;
    private Uav uav2 = null;
    private Uav uav3 = null;
    private Uav uav4 = null;

    public void run() {
        while (true) {
            String input = JOptionPane.showInputDialog(
                    null,
                    "Select one of the options to proceed:\n" +
                            "1. Add UAV\n" +
                            "2. Remove UAV\n" +
                            "3. Add Sensor to UAV\n" +
                            "4. Remove Sensor from UAV\n" +
                            "5. Find Best UAV for Task\n" +
                            "6. List Sensors on UAV\n" +
                            "7. List UAVs with Sensor Type\n" +
                            "8. Exit\n",
                    "UAV Management",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null)
                System.exit(0);

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        addUAV();
                        break;
                    case 2:
                        removeUAV();
                        break;
                    case 3:
                        addSensorToUAV();
                        break;
                    case 4:
                        removeSensorFromUAV();
                        break;
                    case 5:
                        findBestUAVForTask();
                        break;
                    case 6:
                        listSensorsOnUAV();
                        break;
                    case 7:
                        listUAVsWithSensorType();
                        break;
                    case 8:
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,
                                "Invalid selection! Please enter a number between 1 and 8.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a number between 1 and 8.");
            }
        }
    }

    private boolean isUAVNameTaken(String name) {
        return (uav1 != null && uav1.getName().equals(name)) ||
                (uav2 != null && uav2.getName().equals(name)) ||
                (uav3 != null && uav3.getName().equals(name)) ||
                (uav4 != null && uav4.getName().equals(name));
    }

    private void addUAV() {
        if (uav1 != null && uav2 != null && uav3 != null && uav4 != null) {
            JOptionPane.showMessageDialog(null, "You already have added 4 UAVs. Cannot add more.");
        } else {
            String name = JOptionPane.showInputDialog("Enter UAV name:");
            if (isUAVNameTaken(name)) {
                JOptionPane.showMessageDialog(null, "This UAV name already exists. Please enter a different name.");
                return;
            }

            double operationalCost = -1;
            while (operationalCost <= 0) {
                String input = JOptionPane.showInputDialog("Enter operational cost per hour (positive value only):");
                try {
                    operationalCost = Double.parseDouble(input);
                    if (operationalCost <= 0) {
                        JOptionPane.showMessageDialog(null, "Operational cost must be positive.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for operational cost.");
                }
            }

            int availability = -1;
            // Validate availability to be between 1 and 5
            while (availability < 1 || availability > 5) {
                String input = JOptionPane.showInputDialog("Enter availability (1-5): 1 is the highest");
                try {
                    availability = Integer.parseInt(input);
                    if (availability < 1 || availability > 5) {
                        JOptionPane.showMessageDialog(null, "Availability must be between 1 and 5.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for availability.");
                }
            }

            if (uav1 == null) {
                uav1 = new Uav(name, operationalCost, availability);
                JOptionPane.showMessageDialog(null, "UAV added: " + name);
            } else if (uav2 == null) {
                uav2 = new Uav(name, operationalCost, availability);
                JOptionPane.showMessageDialog(null, "UAV added: " + name);
            } else if (uav3 == null) {
                uav3 = new Uav(name, operationalCost, availability);
                JOptionPane.showMessageDialog(null, "UAV added: " + name);
            } else if (uav4 == null) {
                uav4 = new Uav(name, operationalCost, availability);
                JOptionPane.showMessageDialog(null, "UAV added: " + name);
            }
        }
    }

    private void removeUAV() {
        String name = JOptionPane.showInputDialog("Enter UAV name to remove:");
        if (uav1 != null && uav1.getName().equals(name)) {
            uav1 = null;
            JOptionPane.showMessageDialog(null, "UAV removed: " + name);
        } else if (uav2 != null && uav2.getName().equals(name)) {
            uav2 = null;
            JOptionPane.showMessageDialog(null, "UAV removed: " + name);
        } else if (uav3 != null && uav3.getName().equals(name)) {
            uav3 = null;
            JOptionPane.showMessageDialog(null, "UAV removed: " + name);
        } else if (uav4 != null && uav4.getName().equals(name)) {
            uav4 = null;
            JOptionPane.showMessageDialog(null, "UAV removed: " + name);
        } else {
            JOptionPane.showMessageDialog(null, "UAV not found.");
        }
    }

    private void addSensorToUAV() {
        String uavName = JOptionPane.showInputDialog("Enter the name of the UAV to which you want to add the sensor:");

        Uav selectedUAV = null;
        if (uav1 != null && uav1.getName().equals(uavName)) {
            selectedUAV = uav1;
        } else if (uav2 != null && uav2.getName().equals(uavName)) {
            selectedUAV = uav2;
        } else if (uav3 != null && uav3.getName().equals(uavName)) {
            selectedUAV = uav3;
        } else if (uav4 != null && uav4.getName().equals(uavName)) {
            selectedUAV = uav4;
        }

        if (selectedUAV == null) {
            JOptionPane.showMessageDialog(null, "UAV not found.");
            return;
        }

        String sensorType = "";
        while (!sensorType.equals("temperature") && !sensorType.equals("pressure") &&
                !sensorType.equals("windspeed") && !sensorType.equals("humidity")) {
            sensorType = JOptionPane.showInputDialog("Enter sensor type (temperature, pressure, windspeed, humidity):");

            if (!sensorType.equals("temperature") && !sensorType.equals("pressure") &&
                    !sensorType.equals("windspeed") && !sensorType.equals("humidity")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid sensor type. Please enter one of the following: temperature, pressure, windspeed, humidity.");
            }
        }

        int grade = -1;
        while (grade < 1 || grade > 5) {
            String gradeInput = JOptionPane.showInputDialog("Enter sensor grade (1-5, where 1 is the highest):");
            try {
                grade = Integer.parseInt(gradeInput);
                if (grade < 1 || grade > 5) {
                    JOptionPane.showMessageDialog(null, "Grade must be between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for grade.");
            }
        }

        int quantity = -1;
        while (quantity <= 0) {
            String quantityInput = JOptionPane.showInputDialog("Enter sensor quantity (positive number):");
            try {
                quantity = Integer.parseInt(quantityInput);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Quantity must be a positive number.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for quantity.");
            }
        }

        // Add the sensor to the selected UAV
        String result = selectedUAV.addSensor(sensorType, grade, quantity);
        JOptionPane.showMessageDialog(null, result);
    }

    private void removeSensorFromUAV() {
        String uavName = JOptionPane.showInputDialog("Enter the name of the UAV to which you want to add the sensor:");

        Uav selectedUAV = null;
        if (uav1 != null && uav1.getName().equals(uavName)) {
            selectedUAV = uav1;
        } else if (uav2 != null && uav2.getName().equals(uavName)) {
            selectedUAV = uav2;
        } else if (uav3 != null && uav3.getName().equals(uavName)) {
            selectedUAV = uav3;
        } else if (uav4 != null && uav4.getName().equals(uavName)) {
            selectedUAV = uav4;
        }

        if (selectedUAV == null) {
            JOptionPane.showMessageDialog(null, "UAV not found.");
            return;
        }

        String sensorType = "";
        while (!sensorType.equals("temperature") && !sensorType.equals("pressure") &&
                !sensorType.equals("windspeed") && !sensorType.equals("humidity")) {
            sensorType = JOptionPane.showInputDialog("Enter sensor type (temperature, pressure, windspeed, humidity):");

            if (!sensorType.equals("temperature") && !sensorType.equals("pressure") &&
                    !sensorType.equals("windspeed") && !sensorType.equals("humidity")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid sensor type. Please enter one of the following: temperature, pressure, windspeed, humidity.");
            }
        }

        int quantity = -1;
        while (quantity <= 0) {
            String quantityInput = JOptionPane.showInputDialog("Enter sensor quantity (positive number):");
            try {
                quantity = Integer.parseInt(quantityInput);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Quantity must be a positive number.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for quantity.");
            }
        }

        String result = selectedUAV.removeSensor(sensorType, quantity);
        JOptionPane.showMessageDialog(null, result);
    }

    private void findBestUAVForTask() {
        String sensorType = "";
        while (!sensorType.equals("temperature") && !sensorType.equals("pressure") &&
                !sensorType.equals("windspeed") && !sensorType.equals("humidity")) {
            sensorType = JOptionPane.showInputDialog("Enter sensor type (temperature, pressure, windspeed, humidity):");

            if (!sensorType.equals("temperature") && !sensorType.equals("pressure") &&
                    !sensorType.equals("windspeed") && !sensorType.equals("humidity")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid sensor type. Please enter one of the following: temperature, pressure, windspeed, humidity.");
            }
        }

        int grade = -1;
        while (grade < 1 || grade > 5) {
            String gradeInput = JOptionPane.showInputDialog("Enter sensor grade (1-5, where 1 is the highest):");
            try {
                grade = Integer.parseInt(gradeInput);
                if (grade < 1 || grade > 5) {
                    JOptionPane.showMessageDialog(null, "Grade must be between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for grade.");
            }
        }

        int quantity = -1;
        while (quantity <= 0) {
            String quantityInput = JOptionPane.showInputDialog("Enter min sensor quantity (positive number):");
            try {
                quantity = Integer.parseInt(quantityInput);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Quantity must be a positive number.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for quantity.");
            }
        }

        Uav bestUav = null;
        double lowestCost = 0;
        int bestAvailability = 0;

        // Here, I defined the bestUav and parsed through each UAVs available to find if
        // the requirement suits any UAV in particular and then filter the best fit
        // among
        // them based on the operational cost and the availabilty of the UAV

        // Check if uav1 is a match
        if (uav1 != null) {
            Sensor sensor1 = uav1.validateSensorAvailability(sensorType, grade, quantity);
            if (sensor1 != null) {

                bestUav = uav1;
                lowestCost = uav1.getOperationalCost();
                bestAvailability = uav1.getAvailability();
            }
        }

        // Check if uav2 is a match
        if (uav2 != null) {
            Sensor sensor2 = uav2.validateSensorAvailability(sensorType, grade, quantity);

            if (sensor2 != null) {
                if (uav2.getOperationalCost() < lowestCost
                        || uav2.getOperationalCost() == lowestCost && uav2.getAvailability() < bestAvailability) {
                    bestUav = uav2;
                    lowestCost = uav2.getOperationalCost();
                    bestAvailability = uav2.getAvailability();
                }
            }
        }

        // Check if uav3 is a match
        if (uav3 != null) {
            Sensor sensor3 = uav3.validateSensorAvailability(sensorType, grade, quantity);

            if (sensor3 != null) {
                if (uav3.getOperationalCost() < lowestCost
                        || uav3.getOperationalCost() == lowestCost && uav3.getAvailability() < bestAvailability) {
                    bestUav = uav3;
                    lowestCost = uav3.getOperationalCost();
                    bestAvailability = uav3.getAvailability();
                }
            }
        }

        // Check if uav4 is a match
        if (uav4 != null) {
            Sensor sensor4 = uav4.validateSensorAvailability(sensorType, grade, quantity);

            if (sensor4 != null) {
                if (uav4.getOperationalCost() < lowestCost
                        || uav4.getOperationalCost() == lowestCost && uav4.getAvailability() < bestAvailability) {
                    bestUav = uav4;
                    lowestCost = uav4.getOperationalCost();
                    bestAvailability = uav4.getAvailability();
                }
            }
        }

        if (bestUav == null) {
            JOptionPane.showMessageDialog(null, "No UAV meets the requirement.");
        } else {
            String msg = "The best UAV suggestion for your requirements is \n" + bestUav.getName() + " with "
                    + bestUav.getOperationalCost() + " operational cost and " + bestUav.getAvailability()
                    + " availability.";
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    private void listSensorsOnUAV() {
        String uavName = JOptionPane.showInputDialog("Enter the name of the UAV to which you want to add the sensor:");

        Uav selectedUAV = null;
        if (uav1 != null && uav1.getName().equals(uavName)) {
            selectedUAV = uav1;
        } else if (uav2 != null && uav2.getName().equals(uavName)) {
            selectedUAV = uav2;
        } else if (uav3 != null && uav3.getName().equals(uavName)) {
            selectedUAV = uav3;
        } else if (uav4 != null && uav4.getName().equals(uavName)) {
            selectedUAV = uav4;
        }

        if (selectedUAV == null) {
            JOptionPane.showMessageDialog(null, "UAV not found.");
            return;
        }

        String result = selectedUAV.listSensors(uavName);
        JOptionPane.showMessageDialog(null, result);
    }

    private void listUAVsWithSensorType() {
        String sensorType = "";
        while (!sensorType.equals("temperature") && !sensorType.equals("pressure") &&
                !sensorType.equals("windspeed") && !sensorType.equals("humidity")) {
            sensorType = JOptionPane.showInputDialog("Enter sensor type (temperature, pressure, windspeed, humidity):");

            if (!sensorType.equals("temperature") && !sensorType.equals("pressure") &&
                    !sensorType.equals("windspeed") && !sensorType.equals("humidity")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid sensor type. Please enter one of the following: temperature, pressure, windspeed, humidity.");
            }
        }

        String result = "";

        if (uav1 != null) {
            result = result + uav1.filterBySensor(sensorType);
        }
        if (uav2 != null) {
            result = result + uav2.filterBySensor(sensorType);
        }
        if (uav3 != null) {
            result = result + uav3.filterBySensor(sensorType);
        }
        if (uav4 != null) {
            result = result + uav4.filterBySensor(sensorType);
        }

        if (result == "") {
            JOptionPane.showMessageDialog(null, "No UAVs found with the specified sensor.");
        } else {
            JOptionPane.showMessageDialog(null, result);
        }
    }

    public static void main(String[] args) {
        UavInterface myControl = new UavInterface();
        myControl.run();
    }
}
