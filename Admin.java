
/*import java.util.ArrayList;
import java.util.Scanner;

class Admin extends User {
    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "adminpassword";
    private Scanner scanner = new Scanner(System.in);
   
    public Admin(String firstName, String lastName, String mobileNumber, String email, String password) {
        super(firstName, lastName, mobileNumber, email, password, true);
    }

    public static boolean login(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    public void manageSlotExchanges(ArrayList<SlotExchangeRequest> slotRequests) {
        if (slotRequests.isEmpty()) {
            System.out.println(RED + "\n⚠️ No pending slot exchange requests!" + RESET);
            return;
        }

        System.out.println(YELLOW + "\n╔════════ MANAGE SLOT EXCHANGES ════════╗" + RESET);

        for (SlotExchangeRequest request : slotRequests) {
            System.out.println(CYAN + "\n► Request Details:" + RESET);
            System.out.println("   User ID: " + BLUE + request.getUserId() + RESET);
            System.out.println("   Requested Slot: " + BLUE + request.getRequestedSlot() + RESET);
            System.out.println("   Request Date: " + BLUE + request.getRequestDate() + RESET);
            System.out.println("   Status: " + getStatusWithColor(request.getStatus()));

            if (request.getStatus().equalsIgnoreCase("pending")) {
                processRequest(request);
            }
        }
        System.out.println(YELLOW + "\n╚════════ END OF REQUESTS ════════╝" + RESET);
    }

    private void processRequest(SlotExchangeRequest request) {
        System.out.println(YELLOW + "\n═══════ Admin Action Required ═══════" + RESET);
        System.out.println("1. " + GREEN + "Approve Request" + RESET);
        System.out.println("2. " + RED + "Deny Request" + RESET);
        System.out.println("3. " + BLUE + "Skip for now" + RESET);
        System.out.print("\nEnter your choice (1-3): ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    approveRequest(request);
                    break;
                case 2:
                    denyRequest(request);
                    break;
                case 3:
                    System.out.println(BLUE + "Request skipped for later review." + RESET);
                    break;
                default:
                    System.out.println(RED + "Invalid choice! Request skipped." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Invalid input! Request skipped." + RESET);
            scanner.nextLine(); // Clear the invalid input
        }
    }

    private void approveRequest(SlotExchangeRequest request) {
    try {
        System.out.println(YELLOW + "\nProcessing approval..." + RESET);
        simulateProcessing();
        
        request.processRequest("approve");
        
        System.out.println(GREEN + "\n✓ Request Approved Successfully!" + RESET);
        System.out.println(CYAN + "Notification sent to user: " + request.getUserId() + RESET);
        
        logAction(request, "APPROVED");
        
    } catch (Exception e) {
        System.out.println(RED + "Error processing approval: " + e.getMessage() + RESET);
    }
}

private void denyRequest(SlotExchangeRequest request) {
    try {
        System.out.print(YELLOW + "\nEnter reason for denial (optional): " + RESET);
        String reason = scanner.nextLine();
        
        System.out.println(YELLOW + "\nProcessing denial..." + RESET);
        simulateProcessing();
        
        request.processRequest("deny");
        
        System.out.println(RED + "\n✓ Request Denied" + RESET);
        System.out.println(CYAN + "Notification sent to user: " + request.getUserId() + RESET);
        
        if (!reason.isEmpty()) {
            System.out.println("Reason: " + reason);
        }
        
        logAction(request, "DENIED: " + reason);
        
    } catch (Exception e) {
        System.out.println(RED + "Error processing denial: " + e.getMessage() + RESET);
    }
}
    private void simulateProcessing() {
        try {
            String[] dots = {".  ", ".. ", "..."};
            for (int i = 0; i < 5; i++) {
                for (String dot : dots) {
                    System.out.print("\rProcessing" + dot);
                    Thread.sleep(200);
                }
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void logAction(SlotExchangeRequest request, String action) {
        String timestamp = java.time.LocalDateTime.now().toString();
        System.out.println(BLUE + "\n[LOG] " + timestamp + RESET);
        System.out.println(BLUE + "User: " + request.getUserId() + RESET);
        System.out.println(BLUE + "Slot: " + request.getRequestedSlot() + RESET);
        System.out.println(BLUE + "Action: " + action + RESET);
    }

    private String getStatusWithColor(String status) {
        switch (status.toLowerCase()) {
            case "pending":
                return YELLOW + status.toUpperCase() + RESET;
            case "approved":
                return GREEN + status.toUpperCase() + RESET;
            case "denied":
                return RED + status.toUpperCase() + RESET;
            default:
                return BLUE + status.toUpperCase() + RESET;
        }
    }

    public void checkServiceStatus() {
        System.out.print(CYAN + "Enter Service ID to check status: " + RESET);
        String serviceId = scanner.nextLine();
        
        // Retrieve the status using SlotExchangeRequest's method
        SlotExchangeRequest request = new SlotExchangeRequest("userId", serviceId, "requestDate"); // Placeholder for actual request creation
        String status = request.getStatus();
        
        if (status != null) {
            System.out.println(BLUE + "Status for Service ID " + serviceId + ": " + getStatusWithColor(status) + RESET);
        } else {
            System.out.println(RED + "No status found for Service ID " + serviceId + RESET);
        }
    }
    public void checkFeedback(ArrayList<Feedback.FeedbackEntry> feedbackEntries) {
        System.out.println(YELLOW + "\n╔════════ USER FEEDBACK ════════╗" + RESET);
        if (feedbackEntries.isEmpty()) {
            System.out.println(BLUE + "No feedback entries available." + RESET);
        } else {
            for (Feedback.FeedbackEntry entry : feedbackEntries) {
                System.out.println(CYAN + "\n► Feedback Entry:" + RESET);
                System.out.println("   User: " + BLUE + entry.getUserId() + RESET);
                System.out.println("   Message: " + entry.getMessage());
            }
        }
        System.out.println(YELLOW + "╚════════════════════════════════╝" + RESET);
    }

    public void viewServiceUpdates(ArrayList<ServiceUpdate> serviceUpdates) {
        System.out.println(YELLOW + "\n╔════════ SERVICE UPDATES ════════╗" + RESET);
        if (serviceUpdates.isEmpty()) {
            System.out.println(BLUE + "No service updates available." + RESET);
        } else {
            for (ServiceUpdate update : serviceUpdates) {
                System.out.println(CYAN + "\n► Service Update:" + RESET);
                System.out.println("   User: " + BLUE + update.getUserId() + RESET);
                System.out.println("   Status: " + getStatusWithColor(update.getUpdateStatus()));
            }
        }
        //System.out.println(YELLOW + "╚════════════════════════════╝" + RESET);
        System.out.println(YELLOW + "╚════════════════════════════════╝" + RESET);
    }

    public void adminUpdateServiceStatus() {
        System.out.println(YELLOW + "\n╔════════ UPDATE SERVICE STATUS ════════╗" + RESET);
        System.out.print(CYAN + "Enter Car ID: " + RESET);
        String carId = scanner.nextLine();
        
        System.out.println(YELLOW + "\nSelect status:" + RESET);
        System.out.println("1. " + RED + "Not Started" + RESET);
        System.out.println("2. " + YELLOW + "Pending" + RESET);
        System.out.println("3. " + GREEN + "Completed" + RESET);
        
        System.out.print(CYAN + "\nEnter choice (1-3): " + RESET);
        String choice = scanner.nextLine();
        String status;
        
        switch (choice) {
            case "1":
                status = "not started";
                break;
            case "2":
                status = "pending";
                break;
            case "3":
                status = "completed";
                break;
            default:
                System.out.println(RED + "Invalid choice!" + RESET);
                return;
        }
        
        simulateProcessing();
        System.out.println(GREEN + "\n✓ Service status updated successfully!" + RESET);
        System.out.println(BLUE + "Car ID: " + carId + RESET);
        System.out.println(BLUE + "New Status: " + getStatusWithColor(status) + RESET);
    }

    public void adminMenu(ArrayList<SlotExchangeRequest> slotRequests, 
                         ArrayList<Feedback.FeedbackEntry> feedbackEntries, 
                         ArrayList<ServiceUpdate> serviceUpdates) {
        while (true) {
            System.out.println(YELLOW + "\n╔════════ ADMIN PANEL ════════╗" + RESET);
            System.out.println("1. " + CYAN + "Manage Slot Exchanges" + RESET);
            System.out.println("2. " + CYAN + "Check User Feedback" + RESET);
            System.out.println("3. " + CYAN + "View Service Updates" + RESET);
            System.out.println("4. " + CYAN + "Update Service Status" + RESET);
            System.out.println("5. " + RED + "Logout" + RESET);
            System.out.print(YELLOW + "\nChoose an option (1-5): " + RESET);

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        manageSlotExchanges(slotRequests);
                        break;
                    case 2:
                        checkFeedback(feedbackEntries);
                        break;
                    case 3:
                        viewServiceUpdates(serviceUpdates);
                        break;
                    case 4:
                        adminUpdateServiceStatus();
                        break;
                    case 5:
                        System.out.println(GREEN + "\n✓ Logged out successfully!" + RESET);
                        return;
                    default:
                        System.out.println(RED + "\n❌ Invalid choice! Please try again." + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + "\n❌ Invalid input! Please enter a number." + RESET);
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}
*/
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Admin extends User {
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

    // Admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "adminpassword";
    
    private final Scanner scanner;
    private static final DateTimeFormatter dateFormatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Constructor
    public Admin(String firstName, String lastName, String mobileNumber, String email, String password) {
        super(firstName, lastName, mobileNumber, email, password, true);
        this.scanner = new Scanner(System.in);
    }

    // Admin authentication
    public static boolean login(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
        // Slot Exchange Management Methods
    public void manageSlotExchanges(ArrayList<SlotExchangeRequest> slotRequests) {
        if (slotRequests.isEmpty()) {
            System.out.println(BG_RED + "\n⚠️ No pending slot exchange requests!" + DEF);
            return;
        }

        System.out.println(BG_BLUE + "\n╔════════ MANAGE SLOT EXCHANGES ════════╗" + DEF);

        for (SlotExchangeRequest request : slotRequests) {
            displayRequestDetails(request);

            if (request.getStatus().equalsIgnoreCase("pending")) {
                processRequest(request);
            }
        }
        System.out.println(BG_BLUE + "\n╚════════ END OF REQUESTS ════════╝" + DEF);
    }

    private void displayRequestDetails(SlotExchangeRequest request) {
        System.out.println(BG_SKYBLUE + "\n═══════ Request Details ═══════" + DEF);
        System.out.println(SKYBLUE + "User ID: " + YELLOW + request.getUserId() + DEF);
        System.out.println(SKYBLUE + "Requested Slot: " + YELLOW + request.getRequestedSlot() + DEF);
        System.out.println(SKYBLUE + "Request Date: " + YELLOW + request.getRequestDate() + DEF);
        System.out.println(SKYBLUE + "Current Status: " + getStatusWithColor(request.getStatus()) + DEF);
    }

    private void processRequest(SlotExchangeRequest request) {
        System.out.println(BG_YELLOW + "\n═══════ Admin Action Required ═══════" + DEF);
        System.out.println("1. " + GREEN + "Approve Request" + DEF);
        System.out.println("2. " + RED + "Deny Request" + DEF);
        System.out.println("3. " + BLUE + "Skip for now" + DEF);
        System.out.print(SKYBLUE + "\nEnter your choice (1-3): " + DEF);

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    approveRequest(request);
                    request.updateRequestStatus(3);
                    
                    System.out.println(request.getStatus());
                }
                case 2 -> {
                    denyRequest(request);
                    request.updateRequestStatus(4);
                }
                case 3 -> System.out.println(BLUE + "\nRequest skipped for later review." + DEF);
                default -> System.out.println(RED + "\n❌ Invalid choice! Request skipped." + DEF);
            }
        } catch (Exception e) {
            System.out.println(BG_RED + "\n❌ Invalid input! Request skipped." + DEF);
            scanner.nextLine();
        }
    }

    private void approveRequest(SlotExchangeRequest request) {
        try {
            System.out.println(YELLOW + "\nProcessing approval..." + DEF);
            simulateProcessing();
            
            request.setStatus("approved");
            request.processRequest("approved");
            
            logAction(request, "APPROVED");

            System.out.println(BG_GREEN + "\n✓ Request Approved Successfully!" + DEF);
            System.out.println(SKYBLUE + "Notification sent to user: " + request.getUserId() + DEF);
            
            
        } catch (Exception e) {
            System.out.println(BG_RED + "\n❌ Error processing approval: " + e.getMessage() + DEF);
        }
    }

    private void denyRequest(SlotExchangeRequest request) {
        try {
            System.out.print(SKYBLUE + "\nEnter reason for denial (optional): " + DEF);
            String reason = scanner.nextLine();
            
            System.out.println(YELLOW + "\nProcessing denial..." + DEF);
            simulateProcessing();
            
            request.setStatus("denied");
            request.processRequest("deny");
            
            System.out.println(BG_RED + "\n✗ Request Denied" + DEF);
            System.out.println(SKYBLUE + "Notification sent to user: " + request.getUserId() + DEF);
            
            if (!reason.isEmpty()) {
                System.out.println(YELLOW + "Reason: " + reason + DEF);
            }
            
            logAction(request, "DENIED: " + reason);
            
        } catch (Exception e) {
            System.out.println(BG_RED + "\n❌ Error processing denial: " + e.getMessage() + DEF);
        }
    }

    private String getStatusWithColor(String status) {
        if (status == null) return BG_BLUE + "UNKNOWN" + DEF;
        
        return switch (status.toLowerCase()) {
            case "pending" -> BG_YELLOW + "PENDING " + YELLOW + "⌛" + DEF;
            case "approved" -> BG_GREEN + "APPROVED " + GREEN + "✓" + DEF;
            case "denied" -> BG_RED + "DENIED " + RED + "✗" + DEF;
            case "no requests found" -> BG_BLUE + "NO REQUESTS" + DEF;
            default -> BG_BLUE + status.toUpperCase() + DEF;
        };
    }

    private String getStatusWithColorForService(String status) {
        if (status == null) return BG_BLUE + "UNKNOWN" + DEF;
        
        return switch (status.toLowerCase()) {
            case "not started" -> BG_YELLOW + "NOT YET STARTED " + YELLOW + "⌛" + DEF;
            case "pending" -> BG_GREEN + "WORK IN PROGRESS " + GREEN + "✓" + DEF;
            case "completed" -> BG_RED + "COMPLETED " + RED + "✗" + DEF;
            default -> BG_BLUE + status.toUpperCase() + DEF;
        };
    }
        // Service Management Methods
    public void checkServiceStatus() {
        System.out.println(BG_BLUE + "\n╔════════ SERVICE STATUS CHECK ════════╗" + DEF);
        System.out.print(SKYBLUE + "Enter Service ID to check status: " + DEF);
        String serviceId = scanner.nextLine();
        
        simulateProcessing();
        
        // Create temporary request to check status
        SlotExchangeRequest request = new SlotExchangeRequest("userId", serviceId, "requestDate");
        String status = request.getStatus();
        
        if (status != null) {
            System.out.println(SKYBLUE + "\nService ID: " + YELLOW + serviceId + DEF);
            System.out.println(SKYBLUE + "Status: " + getStatusWithColor(status) + DEF);
        } else {
            System.out.println(BG_RED + "\n❌ No status found for Service ID " + serviceId + DEF);
        }
        System.out.println(BG_BLUE + "╚══════════════════════════════════════╝" + DEF);
    }

    public void checkFeedback(ArrayList<Feedback.FeedbackEntry> feedbackEntries) {
        System.out.println(BG_BLUE + "\n╔════════ USER FEEDBACK ════════╗" + DEF);
        if (feedbackEntries.isEmpty()) {
            System.out.println(YELLOW + "No feedback entries available." + DEF);
        } else {
            for (Feedback.FeedbackEntry entry : feedbackEntries) {
                System.out.println(BG_SKYBLUE + "\n═══════ Feedback Entry ═══════" + DEF);
                System.out.println(SKYBLUE + "User: " + YELLOW + entry.getUserId() + DEF);
                System.out.println(SKYBLUE + "Message: " + DEF + entry.getMessage());
            }
        }
        System.out.println(BG_BLUE + "╚════════════════════════════╝" + DEF);
    }

    public void viewServiceUpdates(ArrayList<ServiceUpdate> serviceUpdates) {
        System.out.println(BG_BLUE + "\n╔════════ SERVICE UPDATES ════════╗" + DEF);
        if (serviceUpdates.isEmpty()) {
            System.out.println(YELLOW + "No service updates available." + DEF);
        } else {
            for (ServiceUpdate update : serviceUpdates) {
                System.out.println(BG_SKYBLUE + "\n═══════ Service Update ═══════" + DEF);
                System.out.println(SKYBLUE + "User: " + YELLOW + update.getUserId() + DEF);
                System.out.println(SKYBLUE + "Status: " + getStatusWithColor(update.getUpdateStatus()) + DEF);
            }
        }
        System.out.println(BG_BLUE + "╚════════════════════════════════╝" + DEF);
    }

    public void adminUpdateServiceStatus() {
        System.out.println(BG_BLUE + "\n╔════════ UPDATE SERVICE STATUS ════════╗" + DEF);
        System.out.print(SKYBLUE + "Enter Car ID: " + DEF);
        String carId = scanner.nextLine();
        
        System.out.println(BG_YELLOW + "\nSelect status:" + DEF);
        System.out.println("1. " + RED + "Not Started" + DEF);
        System.out.println("2. " + YELLOW + "Pending" + DEF);
        System.out.println("3. " + GREEN + "Completed" + DEF);
        
        System.out.print(SKYBLUE + "\nEnter choice (1-3): " + DEF);
        String choice = scanner.nextLine();
        String status = switch (choice) {
            case "1" -> ServiceNotStart(carId);
            case "2" -> ServicePending(carId);
            case "3" -> ServiceCompleted(carId);
            default -> null;
        };
        
        if (status == null) {
            System.out.println(BG_RED + "\n❌ Invalid choice!" + DEF);
            return;
        }
        
        simulateProcessing();
        System.out.println(BG_GREEN + "\n✓ Service status updated successfully!" + DEF);
        System.out.println(SKYBLUE + "Car ID: " + YELLOW + carId + DEF);
        System.out.println(SKYBLUE + "New Status: " + getStatusWithColorForService(status) + DEF);
        System.out.println(BG_BLUE + "╚══════════════════════════════════════╝" + DEF);
    }

    public String ServiceNotStart(String carId)
    {
        CarService.setServiceStatus(carId, -1);
        return "not started";
    }
    public String ServicePending(String carId)
    {
        CarService.setServiceStatus(carId, 0);
        return "pending";
    }
    public String ServiceCompleted(String carId)
    {
        CarService.setServiceStatus(carId, 1);
        return "completed";
    }

    // Utility Methods
    private void simulateProcessing() {
        try {
            String[] dots = {".  ", ".. ", "..."};
            System.out.print(SKYBLUE);
            for (int i = 0; i < 3; i++) {
                for (String dot : dots) {
                    System.out.print("\rProcessing" + dot);
                    Thread.sleep(200);
                }
            }
            System.out.println(DEF);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void logAction(SlotExchangeRequest request, String action) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(BG_BLUE + "\n═══════ Action Log ═══════" + DEF);
        System.out.println(SKYBLUE + "Timestamp: " + YELLOW + now.format(dateFormatter) + DEF);
        System.out.println(SKYBLUE + "User ID: " + YELLOW + request.getUserId() + DEF);
        System.out.println(SKYBLUE + "Slot ID: " + YELLOW + request.getRequestedSlot() + DEF);
        System.out.println(SKYBLUE + "Action: " + YELLOW + action + DEF);
        System.out.println(BG_BLUE + "════════════════════════" + DEF);
    }
        // Admin Menu and Control Flow
    public void adminMenu(ArrayList<SlotExchangeRequest> slotRequests, 
                         ArrayList<Feedback.FeedbackEntry> feedbackEntries, 
                         ArrayList<ServiceUpdate> serviceUpdates) {
        while (true) {
            displayAdminMenu();
            try {
                int choice = getMenuChoice();
                
                switch (choice) {
                    case 1 -> manageSlotExchanges(slotRequests);
                    case 2 -> checkFeedback(feedbackEntries);
                    case 3 -> viewServiceUpdates(serviceUpdates);
                    case 4 -> adminUpdateServiceStatus();
                    case 5 -> {
                        handleLogout();
                        return;
                    }
                    default -> showInvalidChoice();
                }
                
                promptEnterToContinue();
                
            } catch (Exception e) {
                handleMenuError(e);
            }
        }
    }

    private void displayAdminMenu() {
        System.out.println(BG_BLUE + "\n╔════════ ADMIN PANEL ════════╗" + DEF);
        System.out.println(SKYBLUE + "1. " + DEF + "Manage Slot Exchanges" + YELLOW + " 🔄" + DEF);
        System.out.println(SKYBLUE + "2. " + DEF + "Check User Feedback" + YELLOW + " 📝" + DEF);
        System.out.println(SKYBLUE + "3. " + DEF + "View Service Updates" + YELLOW + " 📊" + DEF);
        System.out.println(SKYBLUE + "4. " + DEF + "Update Service Status" + YELLOW + " ⚙️" + DEF);
        System.out.println(RED + "5. " + DEF + "Logout" + RED + " ←" + DEF);
        System.out.print(YELLOW + "\nChoose an option (1-5): " + DEF);
    }

    private int getMenuChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return choice;
    }

    private void handleLogout() {
        System.out.println(YELLOW + "\nLogging out..." + DEF);
        simulateProcessing();
        System.out.println(BG_GREEN + "\n✓ Logged out successfully!" + DEF);
    }

    private void showInvalidChoice() {
        System.out.println(BG_RED + "\n❌ Invalid choice! Please try again." + DEF);
    }

    private void handleMenuError(Exception e) {
        System.out.println(BG_RED + "\n❌ Invalid input! Please enter a number." + DEF);
        scanner.nextLine(); // Clear invalid input
    }

    private void promptEnterToContinue() {
        System.out.print(YELLOW + "\nPress Enter to continue..." + DEF);
        scanner.nextLine();
    }

    // Additional Utility Methods for Admin Functions
    private void displayActionHeader(String title) {
        System.out.println(BG_BLUE + "\n╔════════ " + title + " ════════╗" + DEF);
    }

    private void displayActionFooter() {
        System.out.println(BG_BLUE + "╚══════════════════════════════════════╝" + DEF);
    }

    private void displaySuccess(String message) {
        System.out.println(BG_GREEN + "\n✓ " + message + DEF);
    }

    private void displayError(String message) {
        System.out.println(BG_RED + "\n❌ " + message + DEF);
    }

    private void displayWarning(String message) {
        System.out.println(BG_YELLOW + "\n⚠️ " + message + DEF);
    }

    private void displayInfo(String message) {
        System.out.println(SKYBLUE + message + DEF);
    }

    // Status Update Notification System
    private void notifyStatusUpdate(String userId, String status, String message) {
        System.out.println(BG_SKYBLUE + "\n═══════ Status Update Notification ═══════" + DEF);
        System.out.println(SKYBLUE + "To User: " + YELLOW + userId + DEF);
        System.out.println(SKYBLUE + "Status: " + getStatusWithColor(status) + DEF);
        System.out.println(SKYBLUE + "Message: " + DEF + message);
        System.out.println(BG_SKYBLUE + "════════════════════════════════════" + DEF);
    }

    // Admin Session Management
    private boolean validateAdminSession() {
        LocalDateTime currentTime = LocalDateTime.now();
        // Add your session validation logic here
        return true; // Placeholder return
    }

    private void refreshAdminSession() {
        if (!validateAdminSession()) {
            displayWarning("Admin session expired. Please login again.");
            handleLogout();
        }
    }

    // Error Logging
    private void logError(String action, Exception e) {
        System.out.println(BG_RED + "\n═══════ Error Log ═══════" + DEF);
        System.out.println(RED + "Action: " + action + DEF);
        System.out.println(RED + "Error: " + e.getMessage() + DEF);
        System.out.println(RED + "Timestamp: " + LocalDateTime.now().format(dateFormatter) + DEF);
        System.out.println(BG_RED + "════════════════════════" + DEF);
    }
}