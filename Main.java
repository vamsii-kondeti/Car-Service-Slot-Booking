import java.util.ArrayList;
import java.util.Scanner;

class Main {
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

    static CarService carService = new CarService();
    static ArrayList<SlotExchangeRequest> slotRequests = new ArrayList<>();
    static ArrayList<Feedback> feedbackList = new ArrayList<>();
    static ArrayList<ServiceUpdate> serviceUpdates = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    static Scanner scanner = new Scanner(System.in);
   


    public static void main(String[] args) {
        System.out.println(BLUE + "\n✨ Welcome to Car Service Application ✨" + DEF);
        mainMenu();
    }

    static void mainMenu() {
        while (true) {
            System.out.println(BG_BLUE + "\n╔════════ MAIN MENU ════════╗" + DEF);
            System.out.println(SKYBLUE + "1. " + DEF + "Register");
            System.out.println(SKYBLUE + "2. " + DEF + "Login");
            System.out.println(SKYBLUE + "3. " + DEF + "Admin Login");
            System.out.println(RED + "4. " + DEF + "Exit");
            System.out.print(YELLOW + "\nChoose an option (1-4): " + DEF);

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.println(BG_GREEN + "\n═══════ USER REGISTRATION ═══════" + DEF);
                        User.registerUser(users);
                    }
                    case 2 -> {
                        if (users.isEmpty()) {
                            System.out.println(BG_RED + "\n❌ No users registered!" + DEF);
                            System.out.println(YELLOW + "Please register first." + DEF);
                        } else {
                            System.out.println(BG_BLUE + "\n═══════ USER LOGIN ═══════" + DEF);
                            currentUser = User.loginUser(users);
                            if (currentUser != null) {
                                System.out.println(GREEN + "\n✓ Login successful!" + DEF);
                                System.out.println(YELLOW + "Welcome, " + currentUser.getFirstName() + "!" + DEF);
                                // Menu menu = new Menu(currentUser);
                                ArrayList<SlotExchangeRequest> slots = new ArrayList<>(SlotExchangeRequest.getAllRequests());
                                ArrayList<SlotExchangeRequest> userExchangeRequests = new ArrayList<>();
                                for(SlotExchangeRequest e: slots)
                                {
                                    if(e.getUserId().equals(currentUser.getUserId()))
                                    {
                                        userExchangeRequests.add(e);
                                    }
                                }
                                Menu.displayMenu(currentUser, userExchangeRequests);

                            }
                        }
                    }
                    case 3 -> {
                        System.out.println(BG_PURPLE + "\n═══════ ADMIN LOGIN ═══════" + DEF);
                        if (adminLogin()) {
                            currentUser = new Admin("Admin", "User", "1234567890", 
                                                  "admin@example.com", "adminpassword");
                            System.out.println(GREEN + "\n✓ Admin logged in successfully!" + DEF);
                            ArrayList<Feedback.FeedbackEntry> feedbackEntries = 
                                new ArrayList<>(Feedback.getAllFeedbackEntries());
                                ArrayList<SlotExchangeRequest> slotRequests1 = new ArrayList<>(SlotExchangeRequest.getAllRequests());
                            ((Admin) currentUser).adminMenu(slotRequests1, feedbackEntries, serviceUpdates);
                        } else {
                            System.out.println(RED + "\n❌ Admin login failed!" + DEF);
                        }
                    }
                    case 4 -> {
                        System.out.println(YELLOW + "\nThank you for using Car Service Application!" + DEF);
                        System.out.println(GREEN + "Have a great day! 👋" + DEF);
                        System.exit(0);
                    }
                    default -> System.out.println(RED + "\n❌ Invalid choice! Please try again." + DEF);
                }
            } catch (Exception e) {
                System.out.println(RED + "\n❌ Invalid input! Please enter a number." + DEF);
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private static boolean adminLogin() {
        System.out.print(SKYBLUE + "Enter admin username: " + DEF);
        String username = scanner.nextLine();
        System.out.print(SKYBLUE + "Enter admin password: " + DEF);
        String password = scanner.nextLine();
        return "admin".equals(username) && "adminpassword".equals(password);
    }

    private static void showAboutCompany() {
        System.out.println(BG_BLUE + "\n╔════════ ABOUT COMPANY ════════╗" + DEF);
        System.out.println(SKYBLUE + "Welcome to the Car Service Application!" + DEF);
        System.out.println(YELLOW + "• We provide the best car maintenance services" + DEF);
        System.out.println(YELLOW + "• Our goal is to offer convenient, quick, and reliable services" + DEF);
        System.out.println(YELLOW + "• Trusted by thousands of car owners" + DEF);
        System.out.println(GREEN + "\nThank you for choosing our service! 🚗" + DEF);
        System.out.println(BG_BLUE + "╚══════════════════════════════╝" + DEF);
    }

    private static void collectFeedback() {
        System.out.println(BG_BLUE + "\n╔════════ FEEDBACK ════════╗" + DEF);
        if (currentUser == null) {
            System.out.println(RED + "\n❌ Error: No user is logged in!" + DEF);
            System.out.println(YELLOW + "Please login to provide feedback." + DEF);
            return;
        }

        System.out.println(SKYBLUE + "Please share your experience with us:" + DEF);
        String feedback = scanner.nextLine();
        
        Feedback.storeFeedback(feedback, currentUser);
        System.out.println(GREEN + "\n✓ Thank you for your valuable feedback! 🙂" + DEF);
        System.out.println(BG_BLUE + "╚════════════════════════╝" + DEF);
    }
}