/* 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CarService {

    // Map to store selected services for each car ID
    private Map<String, List<Service>> carServicesMap = new HashMap<>();

    private static Map<String, Integer> serviceStatus = new HashMap<>();
    // Static variable to track the total service bill
    private static double totalBill = 0.0;

    // Instance of CarService to ensure it's initialized before use
    private static CarService carService = new CarService();

    public static String CarId;

    // private static int serviceStatus;

    // Method to get available services
    private List<Service> getAvailableServices() {
        List<Service> availableServices = new ArrayList<>();
        availableServices.add(new Service("Oil Change", 1000.0)); // ₹1000
        availableServices.add(new Service("Tire Rotation", 600.0)); // ₹600
        availableServices.add(new Service("Brake Inspection", 800.0)); // ₹800
        availableServices.add(new Service("Battery Check", 500.0)); // ₹500
        availableServices.add(new Service("Engine Tune-Up", 2500.0)); // ₹2500
        availableServices.add(new Service("Transmission Service", 3500.0)); // ₹3500
        availableServices.add(new Service("AC Service", 1500.0)); // ₹1500  
        availableServices.add(new Service("Exhaust System Repair", 1800.0)); 
        availableServices.add(new Service("Interior Cleaning", 750.0)); // ₹750
        availableServices.add(new Service("Air Filter Replacement", 1000.0)); // ₹1000
        availableServices.add(new Service("Wheel Alignment", 1200.0)); // ₹1200
        
        return availableServices;
    }

    // Method to view available services
    public void viewAvailableServices() {
        List<Service> availableServices = getAvailableServices();
        System.out.println("Available Car Services:");
        for (int i = 0; i < availableServices.size(); i++) {
            Service service = availableServices.get(i);
            System.out.println((i + 1) + ". " + service.getServiceName() + " - ₹" + service.getServiceCost());
        }
    }

    // Method to select services for a specific car
    public void selectService(String carId, User user) {
        // Validate if the user has the car
        if (!user.hasCar(carId)) {
            System.out.println("No car found with ID: " + carId + ". Please enter a valid car ID.");
            return; // Exit the method if the car ID is invalid
        }
        
        Scanner scanner = new Scanner(System.in);
        viewAvailableServices();
        System.out.print("Enter the number of the service you want to select: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Service> availableServices = getAvailableServices();
        if (choice > 0 && choice <= availableServices.size()) {
            Service selectedService = availableServices.get(choice - 1);
            carServicesMap.computeIfAbsent(carId, k -> new ArrayList<>()).add(selectedService);
            serviceStatus.put(carId, -1);
            totalBill += selectedService.getServiceCost(); // Update total bill
            System.out.println("Service '" + selectedService.getServiceName() + "' selected successfully for Car ID: " + carId);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        // After selecting a service, return to the main menu
        goToMainMenu();
    }

    // New method to get total service cost for a specific car ID
    public double getTotalServiceCost(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        double totalCost = 0.0;

        if (selectedServices != null) {
            for (Service service : selectedServices) {
                totalCost += service.getServiceCost();
            }
        }
        return totalCost;
    }

    // Method to view selected services for a specific car
    public void viewSelectedServices(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        if (selectedServices == null || selectedServices.isEmpty()) {
            System.out.println("No services selected for Car ID: " + carId);
        } else {
            System.out.println("Selected Services for Car ID: " + carId);
            for (Service service : selectedServices) {
                System.out.println("- " + service.getServiceName() + " - ₹" + service.getServiceCost());
            }
        }

        // After viewing selected services, return to the main menu
        goToMainMenu();
    }

    // Method to remove services for a specific car
    public void removeService(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        if (selectedServices == null || selectedServices.isEmpty()) {
            System.out.println("No services to remove for Car ID: " + carId);
            goToMainMenu();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Selected Services for Car ID: " + carId);
        for (int i = 0; i < selectedServices.size(); i++) {
            Service service = selectedServices.get(i);
            System.out.println((i + 1) + ". " + service.getServiceName() + " - ₹" + service.getServiceCost());
        }
        System.out.print("Enter the number of the service you want to remove: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= selectedServices.size()) {
            Service removedService = selectedServices.remove(choice - 1);
            totalBill -= removedService.getServiceCost(); // Update total bill
            System.out.println("Service '" + removedService.getServiceName() + "' removed successfully for Car ID: " + carId);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        // After removing a service, return to the main menu
        goToMainMenu();
    }

    // Method to get the total bill
    public static double getTotalBill() {
        return totalBill;
    }

    public static void setBill()
    {
        totalBill = 0.0;
    }

    public static void setServiceStatus(String carId, int p)
    {
        serviceStatus.put(carId, p);
    }
 
    // Method to get detailed bill for a specific car
    public String getDetailedBill(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        if (selectedServices == null || selectedServices.isEmpty()) {
            return "No services selected for Car ID: " + carId;
        }

        StringBuilder billDetails = new StringBuilder();
        billDetails.append("Detailed Bill for Car ID: ").append(carId).append("\n");
        double total = 0.0;

        for (Service service : selectedServices) {
            billDetails.append(service.getServiceName()).append(" - ₹").append(service.getServiceCost()).append("\n");
            total += service.getServiceCost();
        }

        billDetails.append("Total Service Cost: ₹").append(total).append("\n");
        return billDetails.toString();
    }

    // Method to check the service status for a specific car
    public String checkServiceStatus(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        if (selectedServices == null || selectedServices.isEmpty()) {
            return "No services selected for Car ID: " + carId;
        }

        StringBuilder statusDetails = new StringBuilder();
        statusDetails.append("Services selected for Car ID: ").append(carId).append("\n");
        for (Service service : selectedServices) {
            statusDetails.append("- ").append(service.getServiceName()).append(" - ₹").append(service.getServiceCost()).append(getTestWithColors(serviceStatus.get(carId))).append("\n");
        }
        return statusDetails.toString();
    }

    public String getTestWithColors(int p)
    {
        String ans = "";

        if(p==-1)
        {
            ans += " 😭 Not yet started";
        }else if(p==0)
        {
            ans += " 🙂 Pending";
        }else{
            ans += " 😁 Completed";
        }
        return ans;
    }



    public String getServiceStatus(String carId) {
        return checkServiceStatus(carId); // Reuse existing method
    }

    public List<Service> getSelectedServices(String carId) {
        return carServicesMap.get(carId); // Return the list of selected services
    }

    // Method to return to the main menu
    private void goToMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nReturning to Main Menu...");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    // Method to access the initialized instance of CarService
    public static CarService getInstance() {
        return carService;
    }
}
*/


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CarService {

    // Map to store selected services for each car ID
    private Map<String, List<Service>> carServicesMap = new HashMap<>();

    private static Map<String, Integer> serviceStatus = new HashMap<>();
    // Static variable to track the total service bill
    private static double totalBill = 0.0;

    // Instance of CarService to ensure it's initialized before use
    private static CarService carService = new CarService();

    public static String CarId;

    // private static int serviceStatus;

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

    // Method to get available services
    private List<Service> getAvailableServices() {
        List<Service> availableServices = new ArrayList<>();
        availableServices.add(new Service("Oil Change", 1000.0)); // ₹1000
        availableServices.add(new Service("Tire Rotation", 600.0)); // ₹600
        availableServices.add(new Service("Brake Inspection", 800.0)); // ₹800
        availableServices.add(new Service("Battery Check", 500.0)); // ₹500
        availableServices.add(new Service("Engine Tune-Up", 2500.0)); // ₹2500
        availableServices.add(new Service("Transmission Service", 3500.0)); // ₹3500
        availableServices.add(new Service("AC Service", 1500.0)); // ₹1500  
        availableServices.add(new Service("Exhaust System Repair", 1800.0)); 
        availableServices.add(new Service("Interior Cleaning", 750.0)); // ₹750
        availableServices.add(new Service("Air Filter Replacement", 1000.0)); // ₹1000
        availableServices.add(new Service("Wheel Alignment", 1200.0)); // ₹1200
        
        return availableServices;
    }

    // Method to view available services
    public void viewAvailableServices() {
        List<Service> availableServices = getAvailableServices();
        System.out.println("Available Car Services:");
        for (int i = 0; i < availableServices.size(); i++) {
            Service service = availableServices.get(i);
            System.out.println((i + 1) + ". " + service.getServiceName() + " - ₹" + service.getServiceCost());
        }
    }

    // Method to select services for a specific car
    public void selectService(String carId, User user) {
        // Validate if the user has the car
        if (!user.hasCar(carId)) {
            System.out.println("No car found with ID: " + carId + ". Please enter a valid car ID.");
            return; // Exit the method if the car ID is invalid
        }
        
        Scanner scanner = new Scanner(System.in);
        viewAvailableServices();
        System.out.print("Enter the number of the service you want to select: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Service> availableServices = getAvailableServices();
        if (choice > 0 && choice <= availableServices.size()) {
            Service selectedService = availableServices.get(choice - 1);
            carServicesMap.computeIfAbsent(carId, k -> new ArrayList<>()).add(selectedService);
            serviceStatus.put(carId, -1);
            totalBill += selectedService.getServiceCost(); // Update total bill
            System.out.println("Service '" + selectedService.getServiceName() + "' selected successfully for Car ID: " + carId);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        // After selecting a service, return to the main menu
        goToMainMenu();
    }

    // New method to get total service cost for a specific car ID
    public double getTotalServiceCost(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        double totalCost = 0.0;

        if (selectedServices != null) {
            for (Service service : selectedServices) {
                totalCost += service.getServiceCost();
            }
        }
        return totalCost;
    }

    // Method to view selected services for a specific car
    public void viewSelectedServices(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        if (selectedServices == null || selectedServices.isEmpty()) {
            System.out.println("No services selected for Car ID: " + carId);
        } else {
            System.out.println("Selected Services for Car ID: " + carId);
            for (Service service : selectedServices) {
                System.out.println("- " + service.getServiceName() + " - ₹" + service.getServiceCost());
            }
        }

        // After viewing selected services, return to the main menu
        goToMainMenu();
    }

    // Method to remove services for a specific car
    public void removeService(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        if (selectedServices == null || selectedServices.isEmpty()) {
            System.out.println("No services to remove for Car ID: " + carId);
            goToMainMenu();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Selected Services for Car ID: " + carId);
        for (int i = 0; i < selectedServices.size(); i++) {
            Service service = selectedServices.get(i);
            System.out.println((i + 1) + ". " + service.getServiceName() + " - ₹" + service.getServiceCost());
        }
        System.out.print("Enter the number of the service you want to remove: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= selectedServices.size()) {
            Service removedService = selectedServices.remove(choice - 1);
            totalBill -= removedService.getServiceCost(); // Update total bill
            System.out.println("Service '" + removedService.getServiceName() + "' removed successfully for Car ID: " + carId);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        // After removing a service, return to the main menu
        goToMainMenu();
    }

    // Method to get the total bill
    public static double getTotalBill() {
        return totalBill;
    }

    public static void setBill()
    {
        totalBill = 0.0;
    }

    public static void setServiceStatus(String carId, int p)
    {
        serviceStatus.put(carId, p);
    }
 
    // Method to get detailed bill for a specific car
    public String getDetailedBill(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        if (selectedServices == null || selectedServices.isEmpty()) {
            return "No services selected for Car ID: " + carId;
        }

        StringBuilder billDetails = new StringBuilder();
        billDetails.append("Detailed Bill for Car ID: ").append(carId).append("\n");
        double total = 0.0;

        for (Service service : selectedServices) {
            billDetails.append(service.getServiceName()).append(" - ₹").append(service.getServiceCost()).append("\n");
            total += service.getServiceCost();
        }

        billDetails.append("Total Service Cost: ₹").append(total).append("\n");
        return billDetails.toString();
    }

    // Method to check the service status for a specific car
    public String checkServiceStatus(String carId) {
        List<Service> selectedServices = carServicesMap.get(carId);
        if (selectedServices == null || selectedServices.isEmpty()) {
            return "No services selected for Car ID: " + carId;
        }

        StringBuilder statusDetails = new StringBuilder();
        statusDetails.append("Services selected for Car ID: ").append(carId).append("\n");
        for (Service service : selectedServices) {
            statusDetails.append("- ").append(service.getServiceName()).append(" - ₹").append(service.getServiceCost()).append(getTestWithColors(serviceStatus.get(carId))).append("\n");
        }
        return statusDetails.toString();
    }

    public String getTestWithColors(int p)
    {
        String ans = "";

        if(p==-1)
        {
            ans += RED + " 😭 Not yet started" + DEF;
        }else if(p==0)
        {
            ans += YELLOW + " 🙂 Pending" + DEF;
        }else{
            ans += GREEN + " 😁 Completed" + DEF;
        }
        return ans;
    }

    public String getServiceStatus(String carId) {
        return checkServiceStatus(carId); // Reuse existing method
    }

    public List<Service> getSelectedServices(String carId) {
        return carServicesMap.get(carId); // Return the list of selected services
    }

    // Method to return to the main menu
    private void goToMainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nReturning to Main Menu...");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    // Method to access the initialized instance of CarService
    public static CarService getInstance() {
        return carService;
    }
}