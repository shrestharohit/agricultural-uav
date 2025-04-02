//Submission: Rohit Shrestha - 3502400

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;

public class UavInterface {
    private Uav[] uavs = new Uav[4];

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
                            "8. List all UAVs\n" +
                            "9. Export UAV details\n" +
                            "10. Test Cases\n" +
                            "11. Exit\n",
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
                        listUAVs();
                        break;
                    case 9:
                        exportUAVsInFile();
                        break;
                    case 10:
                        testUAV();
                        break;
                    case 11:
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,
                                "Invalid selection! Please enter a number between 1 and 8.");
                }
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a number between 1 and 8.");
            }
        }
    }

    private boolean isUAVNameTaken(String name) {
        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null && uavs[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private int getUavCount() {
        int count = 0;
        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null) {
                count++;
            }
        }
        return count;
    }

    private void addUAV() {
        if (getUavCount() >= 4) {
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

            for (int i = 0; i < uavs.length; i++) {
                if (uavs[i] == null) {
                    uavs[i] = new Uav(name, operationalCost, availability);
                    JOptionPane.showMessageDialog(null, "UAV added: " + name);
                    break;
                }
            }
        }
    }

    private void removeUAV() {
        String name = JOptionPane.showInputDialog("Enter UAV name to remove:");
        boolean found = false;

        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null && uavs[i].getName().equals(name)) {
                uavs[i] = null;
                JOptionPane.showMessageDialog(null, "UAV removed: " + name);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "UAV not found.");
        }
    }

    private void addSensorToUAV() {
        String uavName = JOptionPane.showInputDialog("Enter the name of the UAV to which you want to add the sensor:");

        Uav selectedUAV = null;
        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null && uavs[i].getName().equals(uavName)) {
                selectedUAV = uavs[i];
                break;
            }
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
        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null && uavs[i].getName().equals(uavName)) {
                selectedUAV = uavs[i];
                break;
            }
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

        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null) {
                Sensor sensor = uavs[i].validateSensorAvailability(sensorType, grade, quantity);

                if (sensor != null) {
                    double operationalCost = uavs[i].getOperationalCost();
                    int availability = uavs[i].getAvailability();

                    if (operationalCost < lowestCost
                            || (operationalCost == lowestCost && availability < bestAvailability)) {
                        bestUav = uavs[i];
                        lowestCost = uavs[i].getOperationalCost();
                        bestAvailability = uavs[i].getAvailability();
                        ;
                    }
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
        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null && uavs[i].getName().equals(uavName)) {
                selectedUAV = uavs[i];
                break;
            }
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

        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null) {
                result = result + uavs[i].filterBySensor(sensorType);
            }
        }

        if (result == "") {
            result = "No UAVs found with the specified sensor.";
        }
        JOptionPane.showMessageDialog(null, result);
    }

    private void sortUAVAlphabetically() {
        for (int i = 0; i < uavs.length - 1; i++) {
            for (int j = i + 1; j < uavs.length; j++) {
                if (uavs[i] != null && uavs[j] != null) {
                    if (uavs[i].getName().compareTo(uavs[j].getName()) > 0) {
                        Uav temp = uavs[i];
                        uavs[i] = uavs[j];
                        uavs[j] = temp;
                    }
                }
            }
        }
    }

    private void listUAVs() {
        String result = "";

        sortUAVAlphabetically();

        for (int i = 0; i < uavs.length; i++) {
            if (uavs[i] != null) {
                result = result + uavs[i].getUavDetails();
            }
        }

        if (result == "") {
            result = "No UAVs exists.";
        }
        JOptionPane.showMessageDialog(null, result);

    }

    private void exportUAVsInFile() {
        String filePath = "agricultral_uavs_using_arrays.txt";
        File file = new File(filePath);

        try (PrintWriter writer = new PrintWriter(file)) {
            boolean uavExists = false;

            sortUAVAlphabetically();

            for (int i = 0; i < uavs.length; i++) {
                if (uavs[i] != null) {
                    writer.println(uavs[i].getUavDetails());
                    uavExists = true;
                }
            }

            if (!uavExists) {
                writer.println("No UAV exists.");
            }

            JOptionPane.showMessageDialog(null, "UAV data successfully exported to " + filePath);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while writing to the file.");
        }
    }

    private void testUAV() {
        Uav testingUav = new Uav("Test Uav", 100, 1);
        testingUav.testUav();
    }

    public static void main(String[] args) {
        UavInterface myControl = new UavInterface();
        myControl.run();
    }
}
