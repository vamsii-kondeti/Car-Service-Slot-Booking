import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class CarManagement {

    //color coding 
    private static final String DEF = "\u001b[0;1m";
    private static final String BLINK = "\u001b[5m";
    private static final String RED = "\u001b[31;1m";
    private static final String GREEN = "\u001b[32;1m";
    private static final String YELLOW = "\u001b[33;1m";
    private static final String BLUE = "\u001b[34;1m";
    private static final String PURPLE = "\u001b[35;1m";
    private static final String SKYBLUE = "\u001b[36;1m";
    private static final String BG_RED = "\u001b[101;1m";
    private static final String BG_GREEN = "\u001b[102;1m";
    private static final String BG_YELLOW = "\u001b[103;1m";
    private static final String BG_BLUE = "\u001b[104;1m";
    private static final String BG_PURPLE = "\u001b[105;1m";
    private static final String BG_SKYBLUE = "\u001b[106;1m";

    private static Scanner scanner = new Scanner(System.in);
    static String carId; 

    static ArrayList<String> carIds = new ArrayList<>();

    public void manageCars(User user) {
        while (true) {
            System.out.println(BG_BLUE + "\n📱 Car Management System" + DEF);
            System.out.println(YELLOW + "╔═════════════════════════╗");
            System.out.println("║ 1. Add Car              ║");
            System.out.println("║ 2. Update Car Details   ║");
            System.out.println("║ 3. Remove Car           ║");
            System.out.println("║ 4. Display Car Details  ║");
            System.out.println("║ 5. Back to Main Menu    ║");
            System.out.println("╚═════════════════════════╝" + DEF);
            System.out.print(GREEN + "👉 Choose an option: " + DEF);
            
            int choice;
            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    break;
                } catch (InputMismatchException e) {
                    System.out.println(RED + "❌ Invalid input. Please enter a number." + DEF);
                    scanner.nextLine(); // Clear the invalid input
                }
            }

            switch (choice) {
                case 1:
                    addCar(user);
                    break;
                case 2:
                    updateCarDetails(user);
                    break;
                case 3:
                    removeCar(user);
                    break;
                case 4:
                    user.displayUserCarInfo();
                    break;
                case 5:
                    System.out.println(BLUE + "Returning to Main Menu..." + DEF);
                    return;
                default:
                    System.out.println(RED + "❌ Invalid choice. Please try again." + DEF);
            }
        }
    }

    public String getcarid() {
        return carId;
    }

    public static boolean getCarId(String carId) {
        for(String s : carIds) {
            if(s.equals(carId)) {
                return true;
            }
        }
        return false;
    }

    private void addCar(User user) {
        while (true) {
            System.out.print(BLUE + "Enter Car ID: " + DEF);
            carId = scanner.nextLine();
            
            if (!Car.isValidCarId(carId)) {
                System.out.println(RED + "❌ Invalid Car ID. Please enter a valid Car ID." + DEF);
                continue;
            }
            
            boolean carExists = false;
            for (Car car : user.getCars()) {
                if (car.getCarId().equals(carId)) {
                    carExists = true;
                    break;
                }
            }

            if (carExists) {
                System.out.println(RED + "❌ This Car ID is already assigned. Please enter a unique Car ID." + DEF);
            } else {
                break;
            }
        }

        System.out.println(BG_BLUE + "\n🚗 Let's get your car details!" + DEF);
        System.out.println();
        
        System.out.println(SKYBLUE + "🔹 Available Car Brands: " + YELLOW + "Toyota, Honda, Ford, BMW, Audi, Hyundai, Tata, Mahindra" + DEF);
        System.out.println();
        System.out.print(GREEN + "👉 Enter your car's make (e.g., Toyota, Honda): " + DEF);
        String make = scanner.nextLine();
        
        System.out.println(SKYBLUE + "\n🔹 Popular Models for " + YELLOW + make + SKYBLUE + ": Camry, Civic, Mustang, X5, A4, Creta, Nexon, Thar" + DEF);
        System.out.print(GREEN + "👉 Enter your car's model: " + DEF);
        String model = scanner.nextLine();
        
        int year;
        while (true) {
            System.out.print(YELLOW + "\n📅 Enter the manufacturing year (e.g., 2020): " + DEF);
            year = scanner.nextInt();
            
            if (year > 1885 && year <= 2025) {
                System.out.println(BG_GREEN + "✔ Valid Year Entered!" + DEF);
                break;
            } else {
                System.out.println(BG_RED + "❌ Invalid year! Please enter a year between 1886 and 2025." + DEF);
            }
        }
        
        scanner.nextLine();

        String licensePlate;
        while (true) {
            System.out.println();
            System.out.print(PURPLE + "🚙 Enter License Plate: " + DEF);
            licensePlate = scanner.nextLine();
            if (Car.isValidLicensePlate(licensePlate)) {
                break;
            } else {
                System.out.println(RED + "❌ Invalid license plate format. Use format 'TS12AB1234' or 'AP34CD5678'." + DEF);
            }
        }

        System.out.print(SKYBLUE + "🔄 Enter Kilometer Reading: " + DEF);
        int kmReading = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Get Google Maps Location
        String location = "";
        while (true) {
            System.out.println(BLUE + "╔════════════════════════════════════════════╗");
            System.out.println("║         Enter Car's Current Location       ║");
            System.out.println("╚════════════════════════════════════════════╝" + DEF);
            System.out.println(YELLOW + "Please share your Google Maps location link" + DEF);
            System.out.println(SKYBLUE + "Format: google/maps/place/..." + DEF);
            
            location = scanner.nextLine().trim();

            if (location.startsWith("google/map/place")) {
                System.out.println(GREEN + "✔ Location successfully recorded!" + DEF);
                break;
            } else {
                System.out.println(RED + "═══════════════════ ERROR ═══════════════════");
                System.out.println("❌ Invalid Google Maps link!");
                System.out.println("Link must start with 'google/maps/place/...'");
                System.out.println("═════════════════════════════════════════════" + DEF);
                System.out.println();
            }
        }

        Car car = new Car(carId, make, model, year, licensePlate, kmReading); // Update Car constructor to include location
        user.addCar(car);
        carIds.add(car.getCarId());
        System.out.println(BG_GREEN + "✔ Car added successfully!" + DEF);
    }

    private void updateCarDetails(User user) {
        System.out.print(BLUE + "🔍 Enter Car ID to update: " + DEF);
        String carId = scanner.nextLine();
        
        for (Car car : user.getCars()) {
            if (car.getCarId().equals(carId)) {
                System.out.println(YELLOW + "\n📝 Updating Car Details" + DEF);
                
                System.out.print(GREEN + "Enter new Make: " + DEF);
                String newMake = scanner.nextLine();
                
                System.out.print(GREEN + "Enter new Model: " + DEF);
                String newModel = scanner.nextLine();
                
                System.out.print(GREEN + "Enter new Year: " + DEF);
                int newYear = scanner.nextInt();
                scanner.nextLine();
                
                String newLicensePlate;
                do {
                    System.out.print(PURPLE + "Enter new License Plate: " + DEF);
                    newLicensePlate = scanner.nextLine();
                    if (!Car.isValidLicensePlate(newLicensePlate)) {
                        System.out.println(RED + "❌ Invalid license plate format. Use format 'TS12AB1234' or 'AP34CD5678'." + DEF);
                    }
                } while (!Car.isValidLicensePlate(newLicensePlate));

                System.out.print(SKYBLUE + "Enter new Kilometer Reading: " + DEF);
                int newKmReading = scanner.nextInt();
                scanner.nextLine();

                car.setMake(newMake);
                car.setModel(newModel);
                car.setYear(newYear);
                car.setLicensePlate(newLicensePlate);
                car.setKmReading(newKmReading);
                System.out.println(BG_GREEN + "✔ Car details updated successfully!" + DEF);
                return;
            }
        }
        System.out.println(BG_RED + "❌ Car not found!" + DEF);
    }

    private void removeCar(User user) {
        System.out.print(BLUE + "🔍 Enter Car ID to remove: " + DEF);
        String carId = scanner.nextLine();
        for (Car car : user.getCars()) {
            if (car.getCarId().equals(carId)) {
                user.getCars().remove(car);
                System.out.println(BG_GREEN + "✔ Car removed successfully!" + DEF);
                return;
            }
        }
        System.out.println(BG_RED + "❌ Car not found!" + DEF);
    }
}