//Submission: Rohit Shrestha - 3502400

public class Sensor {
    private String type; // possible values -> temperature, pressure, windspeed, humidity
    private int grade; // possible values -> 1 to 5, 1 being the highest and 5 being the lowest grade
    private int quantity;

    public Sensor(String typeVal, int gradeVal, int quantityVal) {
        type = typeVal;
        grade = gradeVal;
        quantity = quantityVal;
    }

    public String getType() {
        return type;
    }

    public int getGrade() {
        return grade;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantityVal) {
        quantity = quantityVal;
    }

    // this is set to public because I have used this same function in UAV as well
    public String sensorTitleUppercase(String type) {
        switch (type) {
            case "temperature":
                return "Temperature";
            case "humidity":
                return "Humidity";
            case "pressure":
                return "Pressure";
            case "windspeed":
                return "Windspeed";
            default:
                return type;
        }
    }

    public String sensorDetails() {
        return sensorTitleUppercase(type) + " sensor, Grade" + grade + ", Quantity " + quantity + ",\n";
    }
}
