import java.util.Scanner;
import java.util.ArrayList; // Add this import
import java.util.List;

public class CheckStatus {
    // Color codes as class constants
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

    private final Scanner scanner;
    private final CarService carService;
    private SlotExchangeRequest slotExchangeRequest;
    private final Payment payment;
    private User currentUser; // Added field for currentUser
    public static CarManagement carManagement=new CarManagement();

    private List<SlotExchangeRequest> exchangeRequests = new ArrayList<>(); // Initialize the variable

    public CheckStatus(CarService carService, SlotExchangeRequest slotExchangeRequest, Payment payment, User currentUser) {
        this.exchangeRequests = slotExchangeRequest.getAllRequests(); // Populate the exchangeRequests list
        this.scanner = new Scanner(System.in);
        this.carService = carService;
        this.slotExchangeRequest = slotExchangeRequest;
        this.payment = payment;
        this.currentUser = currentUser; // Initialize currentUser
    }

    public void displayCheckStatusMenu() {
        while (true) {
            System.out.println(BG_BLUE + "\n╔════════ CHECK STATUS MENU ════════╗" + DEF);
            System.out.println("1. " + SKYBLUE + "Check Car Service Status" + DEF);
            System.out.println("2. " + SKYBLUE + "Check Slot Exchange Request Status" + DEF);
            System.out.println("3. " + SKYBLUE + "Check Payment Status" + DEF);
            System.out.println("4. " + RED + "Back to Main Menu" + DEF);
            
            System.out.print(YELLOW + "\nEnter your choice (1-4): " + DEF);
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print(SKYBLUE + "Enter Car ID: " + DEF);
                        String carId = scanner.nextLine().trim();
                        String status = carService.checkServiceStatus(carId);

                        System.out.println(status);
                        //String =
                        break;
                    case 2:
                        checkSlotExchangeRequestStatus();
                        break;
                    case 3:
                        checkPaymentStatus();
                        break;
                    case 4:
                        System.out.println(GREEN + "\nReturning to Main Menu..." + DEF);
                        return;
                    default:
                        System.out.println(RED + "Invalid choice! Please try again." + DEF);
                }
            } catch (Exception e) {
                System.out.println(RED + "Invalid input! Please enter a number." + DEF);
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

private void checkSlotExchangeRequestStatus() {
    try {
        System.out.println(BG_BLUE + "\n╔════════ SLOT EXCHANGE STATUS ════════╗" + DEF);
        
        System.out.print(SKYBLUE + "Enter your CAR ID: " + DEF);
        String carId = scanner.nextLine().trim();

        simulateProcessing("Checking slot exchange status");

        // Get current status from SlotExchangeRequest
        String currentStatus = slotExchangeRequest.getStatus();
        int requestStatus = SlotExchangeRequest.getRequestStatus();

        //System.out.println(currentStatus);
        //System.out.println(requestStatus);


        // Display status based on the current state
        switch (currentStatus) {
            case "unknown" -> { // Initial state
                System.out.println(BG_YELLOW + "\n📝 NO SLOT EXCHANGE REQUESTS FOUND" + DEF);
                System.out.println(YELLOW + "\nTo request a slot exchange:" + DEF);
                System.out.println(SKYBLUE + "1. First book a slot" + DEF);
                System.out.println(SKYBLUE + "2. Then request for slot exchange" + DEF);
            }
            case "no requests found" -> { // Slot booked
                System.out.println(BG_SKYBLUE + "\n═══════ CURRENT STATUS ═══════" + DEF);
                System.out.println(SKYBLUE + "Car ID: " + YELLOW + carId + DEF);
                System.out.println(GREEN + "• Slot is booked" + DEF);
                System.out.println(YELLOW + "• You can now request for slot exchange" + DEF);
            }
            default -> { // Exchange requested
                boolean requestFound = false;
                for (SlotExchangeRequest request : exchangeRequests) {
                    if (carManagement.getCarId(carId)==true) {
                        requestFound = true;
                        displayRequestDetails(request, carId);
                        break;
                    }
                }

                
                if (!requestFound) {
                    System.out.println(BG_RED + "\n❌ No active request found for this car" + DEF);
                }
            }
        }

        displayOptions(requestStatus);

    } catch (Exception e) {
        handleError(e);
    }
}

// Helper method to display request details
private void displayRequestDetails(SlotExchangeRequest request, String carId) {
    System.out.println(BG_SKYBLUE + "\n═══════ REQUEST DETAILS ═══════" + DEF);
    System.out.println(SKYBLUE + "Car ID: " + YELLOW + carId + DEF);
    System.out.println(SKYBLUE + "Current Slot: " + YELLOW + request.getSlotId() + DEF);
    System.out.println(SKYBLUE + "Requested Slot: " + YELLOW + request.getRequestedSlot() + DEF);
    
    String status = request.getStatus();
    if (status != null) {
        switch (status.toLowerCase()) {
            case "pending" -> displayPendingStatus();
            case "approved" -> displayApprovedStatus();
            case "denied" -> displayDeniedStatus();
            default -> displayProcessingStatus();
        }
    }
}

// Helper methods for status display
private void displayPendingStatus() {
    System.out.println(BG_YELLOW + "\n⌛ REQUEST STATUS: PENDING" + DEF);
    System.out.println(YELLOW + "• Your request is being reviewed by admin" + DEF);
    System.out.println(YELLOW + "• Expected processing time: 24-48 hours" + DEF);
    System.out.println(YELLOW + "• You will be notified once processed" + DEF);

}

private void displayApprovedStatus() {
    System.out.println(BG_GREEN + "\n✓ REQUEST STATUS: APPROVED" + DEF);
    System.out.println(GREEN + "• Your slot exchange has been approved!" + DEF);
    System.out.println(GREEN + "• New slot has been assigned" + DEF);
    System.out.println(GREEN + "• Check your email for confirmation" + DEF);
}

private void displayDeniedStatus() {
    System.out.println(BG_RED + "\n✗ REQUEST STATUS: DENIED" + DEF);
    System.out.println(RED + "• Your request was not approved" + DEF);
    System.out.println(RED + "• You can submit a new request" + DEF);
    System.out.println(RED + "• Contact support for more information" + DEF);
}

private void displayProcessingStatus() {
    System.out.println(BG_YELLOW + "\n⌛ REQUEST STATUS: PROCESSING" + DEF);
    System.out.println(YELLOW + "• Your request is being processed" + DEF);
    System.out.println(YELLOW + "• Please check back later" + DEF);
}

private void displayOptions(int requestStatus) {
    System.out.println(BG_PURPLE + "\n═══════ OPTIONS ═══════" + DEF);
    if (requestStatus == 1) {
        System.out.println(PURPLE + "1. Request slot exchange" + DEF);
        System.out.println(PURPLE + "2. Return to status menu" + DEF);
    } else {
        System.out.println(PURPLE + "1. Return to main menu" + DEF);
        System.out.println(PURPLE + "2. Check status again" + DEF);
    }
    
    System.out.print(BLUE + "\nEnter your choice (1-2): " + DEF);
    try {
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1 && requestStatus == 1) {
            System.out.println(BLINK + YELLOW + "\nRedirecting to slot exchange request..." + DEF);
            // Add redirect logic here
        }
    } catch (Exception e) {
        System.out.println(RED + "Invalid choice. Returning to status menu." + DEF);
    }
}

private void handleError(Exception e) {
    System.out.println(BG_RED + "\n❌ ERROR CHECKING STATUS" + DEF);
    System.out.println(RED + "• " + e.getMessage() + DEF);
    System.out.println(RED + "• Please try again or contact support" + DEF);
    System.out.println(BG_BLUE + "\n╚══════════════════════════════════════════╝" + DEF);
}

    private void checkPaymentStatus() {
        try {
            System.out.println(BG_BLUE + "\n╔════════ PAYMENT STATUS ════════╗" + DEF);
            System.out.print(SKYBLUE + "Enter Car ID: " + DEF);
            String carId = scanner.nextLine().trim();

            simulateProcessing("Retrieving payment status");

            double totalBill = carService.getTotalBill();
            
            // Pass the currentUser from the class field
            payment.servicePayment(carId, currentUser);

        } catch (Exception e) {
            System.out.println(BG_RED + "\n❌ Error checking payment status: " + e.getMessage() + DEF);
            System.out.println(RED + "Please try again or contact support." + DEF);
        }
        System.out.println(BG_BLUE + "\n╚══════════════════════════════════════════╝" + DEF);
    }

    private void simulateProcessing(String message) {
        System.out.print(SKYBLUE + message);
        String[] dots = {".  ", ".. ", "..."}; 
        try {
            for (int i = 0; i < 3; i++) {
                for (String dot : dots) {
                    System.out.print("\r" + message + dot);
                    Thread.sleep(200);
                }
            }
            System.out.println(DEF);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String getColoredStatus(String status) {
        if (status == null) return BG_RED + "UNKNOWN ?" + DEF;
        
        return switch (status.toLowerCase()) {
            case "pending" -> BG_YELLOW + "PENDING " + YELLOW + "⌛" + DEF;
            case "approved" -> BG_GREEN + "APPROVED " + GREEN + "✓" + DEF;
            case "denied" -> BG_RED + "DENIED " + RED + "✗" + DEF;
            case "completed" -> BG_GREEN + "COMPLETED " + GREEN + "✓" + DEF;
            case "in progress" -> BG_YELLOW + "IN PROGRESS " + YELLOW + "⌛" + DEF;
            case "cancelled" -> BG_RED + "CANCELLED " + RED + "✗" + DEF;
            case "scheduled" -> BG_BLUE + "SCHEDULED " + BLUE + "►" + DEF;
            case "processing" -> BG_PURPLE + "PROCESSING " + PURPLE + "⚙" + DEF;
            case "on hold" -> BG_SKYBLUE + "ON HOLD " + SKYBLUE + "⏸" + DEF;
            default -> BG_RED + "UNKNOWN " + RED + "?" + DEF;
        };
    }
}
