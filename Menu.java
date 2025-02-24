import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
    // Color codes
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
    static CarService carService = CarService.getInstance();
    static SlotExchangeRequest slotExchangeRequest = new SlotExchangeRequest("userId", "slotId", "requestDate");
    static Payment payment = new Payment(carService);
    public static CheckStatus checkStatus;
    User user;


    public static void displayMenu(User user, ArrayList<SlotExchangeRequest> k) {
        for(SlotExchangeRequest i : k) 
        {
            if(i.getUserId().equals(user.getUserId()))
            {
                slotExchangeRequest = i;
                break;
            }
        }
        System.out.println(slotExchangeRequest.getUserId());
        System.out.println(slotExchangeRequest.getStatus());
        checkStatus = new CheckStatus(CarService.getInstance(), slotExchangeRequest, payment, user);
        //clearConsole();
        //checkStatus.displayCheckStatusMenu();
        CarManagement carManagement = new CarManagement();


       // checkStatus.displayCheckStatusMenu();
        SlotBooking slotBooking = new SlotBooking();
       // checkStatus.displayCheckStatusMenu();

        while (true) {
            try {
                System.out.println(BG_BLUE + "\n╔════════ CAR SERVICE APPLICATION ════════╗" + DEF);
                System.out.println(YELLOW + "Welcome, " + GREEN + user.getFirstName() + "!" + DEF);
                System.out.println(BG_SKYBLUE + "\n═══════ MAIN MENU ═══════" + DEF);
                System.out.println(SKYBLUE + "1. " + DEF + "About Company" + YELLOW + " ℹ️" + DEF);
                System.out.println(SKYBLUE + "2. " + DEF + "Car Management" + YELLOW + " 🚗" + DEF);
                System.out.println(SKYBLUE + "3. " + DEF + "Car Service" + YELLOW + " 🔧" + DEF);
                System.out.println(SKYBLUE + "4. " + DEF + "Slot Booking" + YELLOW + " 📅" + DEF);
                System.out.println(SKYBLUE + "5. " + DEF + "Feedback" + YELLOW + " 📝" + DEF);
                System.out.println(SKYBLUE + "6. " + DEF + "Check Status" + YELLOW + " 🔍" + DEF);
                System.out.println(RED + "7. " + DEF + "Logout" + RED + " ←" + DEF);
                
                System.out.print(YELLOW + "\nChoose an option (1-7): " + DEF);
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        //clearConsole();
                        aboutCompany();
                    }
                    case 2 -> {
                        //clearConsole();
                        System.out.println(BG_SKYBLUE + "\n═══════ CAR MANAGEMENT ═══════" + DEF);
                        carManagement.manageCars(user);
                    }
                    case 3 -> {
                        //clearConsole();
                        if(slotBooking.getcarService()==carService)
                        {
                            carServiceMenu(user, carService);
                        }else{
                            System.out.println(RED + "\n�� Book a slot first" + DEF);
                        }
                    }
                    case 4 -> {
                        //clearConsole();
                        slotBooking = new SlotBooking(carService);
                        slotBooking.showSlotBookingMenu(user);
                    }
                    case 5 -> {
                        //clearConsole();
                        System.out.println(BG_SKYBLUE + "\n═══════ FEEDBACK SYSTEM ═══════" + DEF);
                        Feedback.displayFeedback();
                    }
                    case 6 -> {
                        //clearConsole();
                        CheckStatus r = new CheckStatus(CarService.getInstance(), slotExchangeRequest, payment, user);
                        for(SlotExchangeRequest i : SlotExchangeRequest.getAllRequests()) {
                            if(i.getUserId().equals(user.getUserId())) {
                                slotExchangeRequest = i;
                                r = new CheckStatus(CarService.getInstance(), 
                                                           slotExchangeRequest, payment, user);
                                break;
                            }
                        }
                        // checkStatus.displayCheckStatusMenu(slotExchangeRequest);
                        r.displayCheckStatusMenu();
                    }
                    case 7 -> {
                        System.out.println(YELLOW + "\nLogging out..." + DEF);
                        Thread.sleep(1000);
                        System.out.println(GREEN + "Successfully logged out. Goodbye, " + 
                                         user.getFirstName() + "! 👋" + DEF);
                        return;
                    }
                    default -> System.out.println(RED + "\n❌ Invalid choice! Please try again." + DEF);
                }
            } catch (InputMismatchException e) {
                System.out.println(RED + "\n❌ Invalid input! Please enter a number." + DEF);
                scanner.nextLine(); // Clear invalid input
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    

    private static void carServiceMenu(User user, CarService carService) {
        while (true) {
            try {
                System.out.println(BG_BLUE + "\n╔════════ CAR SERVICE MENU ════════╗" + DEF);
                System.out.println(SKYBLUE + "1. " + DEF + "View Available Services" + YELLOW + " 📋" + DEF);
                System.out.println(SKYBLUE + "2. " + DEF + "Select Services" + YELLOW + " ✓" + DEF);
                System.out.println(SKYBLUE + "3. " + DEF + "View Selected Services" + YELLOW + " 👁️" + DEF);
                System.out.println(SKYBLUE + "4. " + DEF + "Remove Services" + YELLOW + " ❌" + DEF);
                System.out.println(RED + "5. " + DEF + "Back to Main Menu" + RED + " ←" + DEF);
                
                System.out.print(YELLOW + "\nChoose an option (1-5): " + DEF);
                
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 5) {
                    return;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.println(BG_SKYBLUE + "\n═══════ AVAILABLE SERVICES ═══════" + DEF);
                        carService.viewAvailableServices();
                    }
                    case 2 -> {
                        System.out.print(SKYBLUE + "Enter Car ID to select services: " + DEF);
                        String carId = scanner.nextLine();
                        if(!SlotBooking.bookedCars.contains(carId))
                        {
                            System.out.println(RED + "\n❌ Car is not in slot booking list!" + DEF);
                        }else{
                            carService.selectService(carId, user); 
                        }
                    }
                    case 3 -> {
                        System.out.print(SKYBLUE + "Enter Car ID to view selected services: " + DEF);
                        String carId = scanner.nextLine();
                        carService.viewSelectedServices(carId);
                    }
                    case 4 -> {
                        System.out.print(SKYBLUE + "Enter Car ID to remove services: " + DEF);
                        String carId = scanner.nextLine();
                        carService.removeService(carId);
                    }
                    default -> System.out.println(RED + "\n❌ Invalid choice! Please try again." + DEF);
                }
            } catch (InputMismatchException e) {
                System.out.println(RED + "\n❌ Invalid input! Please enter a number." + DEF);
                scanner.nextLine();
            }
        }
    }

    private static void aboutCompany() {
        System.out.println(BG_BLUE + "\n╔════════ ABOUT COMPANY ════════╗" + DEF);
        System.out.println(SKYBLUE + "\n🏢 Welcome to Premium Car Services!" + DEF);
        System.out.println(YELLOW + "\nOur Services:" + DEF);
        System.out.println(GREEN + "• " + DEF + "Complete Car Maintenance");
        System.out.println(GREEN + "• " + DEF + "Professional Repairs");
        System.out.println(GREEN + "• " + DEF + "Custom Modifications");
        System.out.println(GREEN + "• " + DEF + "24/7 Support Services");
        
        System.out.println(YELLOW + "\nWhy Choose Us:" + DEF);
        System.out.println(SKYBLUE + "✓ " + DEF + "Expert Technicians");
        System.out.println(SKYBLUE + "✓ " + DEF + "Quality Service");
        System.out.println(SKYBLUE + "✓ " + DEF + "Competitive Pricing");
        System.out.println(SKYBLUE + "✓ " + DEF + "Customer Satisfaction");
        
        System.out.println(PURPLE + "\n📞 Contact Us:" + DEF);
        System.out.println("Phone: +91-1234567890");
        System.out.println("Email: support@carservice.com");
        System.out.println(BG_BLUE + "\n╚══════════════════════════════╝" + DEF);
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // If console clearing fails, print some newlines
            System.out.println("\n".repeat(50));
        }
    }
}
