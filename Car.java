public class Car {
    // Validation for carId
    public static boolean isValidCarId(String carId) {
        return carId != null && !carId.trim().isEmpty(); // Basic validation
    }
    private String carId;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private int kmReading;

    public Car(String carId, String make, String model, int year, String licensePlate, int kmReading) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.kmReading = kmReading;
    }

    public String getCarId() {
        return carId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getKmReading() {
        return kmReading;
    }

    public static boolean isValidLicensePlate(String licensePlate) {
        // Example validation logic
        return licensePlate.matches("[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}");
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setKmReading(int kmReading) {
        this.kmReading = kmReading;
    }
}
