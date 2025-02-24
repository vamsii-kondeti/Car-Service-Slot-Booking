import java.util.Scanner;   
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {
    // ANSI color codes
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

    private static double balance = 51540.0;
    private static final String PIN = "0009";
    private Scanner scanner = new Scanner(System.in);
    private CarService carService = new CarService();
    static Feedback Feedback = new Feedback();

    public Payment(CarService carService) {
        this.carService = carService;
    }

    public Payment()
    {

    }

    private void displayBill(String paymentType, double baseAmount, double totalBill) {
        System.out.println(YELLOW + "\n╔════════ FINAL BILL ════════╗" + DEF);
        System.out.println(SKYBLUE + "Payment Type: " + DEF + paymentType);
        System.out.println(SKYBLUE + "Base Amount: " + DEF + "₹" + String.format("%.2f", baseAmount));
        System.out.println(SKYBLUE + "Basic Services Included:" + DEF);
        System.out.println(SKYBLUE + "  • Car Wash: " + DEF + "Included for Free");
        System.out.println(SKYBLUE + "  • Interior Cleaning: " + DEF + "Included for Free");
        System.out.println(SKYBLUE + "  • Doorstep Delivery: " + DEF + "Included for Free");
        System.out.println(GREEN + "Total Amount: ₹" + String.format("%.2f", totalBill) + DEF);
        System.out.println(YELLOW + "╚═══════════════════════════╝\n" + DEF);
    }

    public boolean slotBookPayment(User user) {
        return processPayment("Slot Booking", 1200.0, null, user);
    }

    public boolean servicePayment(String carId, User user) {
        double serviceTotal = carService.getTotalBill();
        if(serviceTotal<=0)
        {
            System.out.println(YELLOW + "\nNo service charges to be made." + DEF);
            return true;
        }
        System.out.println(YELLOW + "\n╔════════ BILL BREAKDOWN ════════╗" + DEF);
        System.out.println(carService.getDetailedBill(carId));
        System.out.println(YELLOW + "╚═══════════════════════════════╝\n" + DEF);
        return processPayment("Service Payment", serviceTotal, carService, user);
    }

    private boolean processPayment(String paymentType, double baseAmount, CarService carService, User user) {
        try {
            double bill = baseAmount;
            System.out.println(SKYBLUE + "Current Bill Amount: ₹" + String.format("%.2f", bill) + DEF);

            displayBill(paymentType, baseAmount, bill);

            if (balance < bill) {
                System.out.println(RED + "\n❌ Insufficient balance! Transaction cancelled." + DEF);
                return false;
            }

            if (!processPaymentMethod()) {
                return false;
            }

            if (!verifyPin()) {
                System.out.println(RED + "\n⚠️ Account temporarily blocked for 24 hours due to security concerns." + DEF);
                return false;
            }

            balance -= bill;
            carService.setBill();
            System.out.println(GREEN + "\n✓ Payment Successful!" + DEF);
            System.out.println(BLUE + "Remaining Balance: ₹" + String.format("%.2f", balance) + DEF);

            collectFeedback(user);
            return true;

        } catch (Exception e) {
            System.out.println(RED + "\n❌ Error processing payment: " + e.getMessage() + DEF);
            return false;
        }
    }

    private boolean processPaymentMethod() {
        try {
            System.out.println(YELLOW + "\n═══════ Payment Methods ═══════" + DEF);
            System.out.println("1. " + GREEN + "Google Pay" + DEF);
            System.out.println("2. " + BLUE + "PhonePe" + DEF);
            System.out.println("3. " + PURPLE + "Paytm" + DEF);
            System.out.print("\nSelect payment method (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            String paymentMethod = "";
            String color = "";
            switch (choice) {
                case 1:
                    paymentMethod = "Google Pay";
                    color = GREEN;
                    break;
                case 2:
                    paymentMethod = "PhonePe";
                    color = BLUE;
                    break;
                case 3:
                    paymentMethod = "Paytm";
                    color = PURPLE;
                    break;
                default:
                    System.out.println(RED + "\n❌ Invalid payment method selected!" + DEF);
                    return false;
            }

            simulatePaymentProcessing(paymentMethod, color);
            return true;

        } catch (Exception e) {
            System.out.println(RED + "\n❌ Error in payment method selection!" + DEF);
            return false;
        }
    }

    private void simulatePaymentProcessing(String method, String color) throws InterruptedException {
        String[] steps = {
            "Initializing " + method + " secure gateway",
            "Establishing encrypted connection",
            "Verifying merchant details",
            "Connecting to banking network",
            "Preparing transaction"
        };

        System.out.println("\n" + color + "╔════════ " + method + " Payment Gateway ════════╗" + DEF);
        for (String step : steps) {
            System.out.print(color + "► " + step);
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.print(".");
            }
            System.out.println(GREEN + " ✓" + DEF);
            Thread.sleep(300);
        }
        System.out.println(color + "╚═══════════════════════════════════════╝\n" + DEF);
    }

    private boolean verifyPin() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print(YELLOW + "\nEnter UPI PIN: " + DEF);
            String enteredPin = scanner.nextLine();
            
            if (PIN.equals(enteredPin)) {
                System.out.println(GREEN + "\n✓ PIN verified successfully!" + DEF);
                return true;
            } else {
                attempts--;
                System.out.println(RED + "\n❌ Incorrect PIN! Attempts remaining: " + attempts + DEF);
            }
        }
        return false;
    }

    private void collectFeedback(User user) {
        System.out.println(YELLOW + "\n═══════ Feedback ═══════" + DEF);
        System.out.println(SKYBLUE + "We value your feedback! Please share your experience:" + DEF);
        String feedback = scanner.nextLine();
        Feedback.storeFeedback(feedback, user);
        System.out.println(GREEN + "\n✓ Thank you for your valuable feedback! 🙂" + DEF);
    }

    public double getTotalBill() {
        return balance;
    }

    public void checkPaymentStatus(String carId, User user) {
        System.out.println(YELLOW + "\n═══════ Payment Status ═══════" + DEF);
        System.out.println(SKYBLUE + "Total Bill: " + DEF + "₹" + String.format("%.2f", getTotalBill()));
        System.out.print("\nProceed with payment? (yes/no): ");
        
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            if (servicePayment(carId, user)) {
                System.out.println(GREEN + "\n✓ Payment completed successfully!" + DEF);
            } else {
                System.out.println(RED + "\n❌ Payment failed! Please try again." + DEF);
            }
        } else {
            System.out.println(YELLOW + "\nPayment cancelled by user." + DEF);
        }
    }

    private void displayReceipt(String carId, double amount, String paymentMethod, String transactionId) {
        System.out.println(YELLOW + "\n╔════════ PAYMENT RECEIPT ════════╗" + DEF);
        System.out.println(BLUE + "Date: " + DEF + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        System.out.println(BLUE + "Car ID: " + DEF + carId);
        System.out.println(BLUE + "Transaction ID: " + DEF + transactionId);
        System.out.println(BLUE + "Payment Method: " + DEF + paymentMethod);
        System.out.println(GREEN + "\nTotal Amount Paid: ₹" + String.format("%.2f", amount) + DEF);
        System.out.println(YELLOW + "╚══════════════════════════════╝" + DEF);
    }

    private String getColoredStatus(String status) {
        if (status == null) return BG_RED + "UNKNOWN " + RED + "?" + DEF;
        
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