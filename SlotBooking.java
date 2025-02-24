import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SlotBooking {

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

    // Fields
    public static final Map<String, List<String>> slots = new HashMap<>();
    public static final Map<String, String> bookedSlots = new HashMap<>();
    private static final List<SlotExchangeRequest> exchangeRequests = new ArrayList<>();
    public static final List<String> bookedCars = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Payment payment = new Payment();
    private CarService cs=new CarService();

    // Constructor
    public SlotBooking(CarService carService) {
        this.cs = carService;
        this.scanner = new Scanner(System.in);
        this.payment = new Payment(carService);
        initializeSlots();
        // SlotExchangeRequest.updateRequestStatus(0);
    }

    public SlotBooking()
    {

    }

    CarService getcarService()
    {
        return cs;
    }
        // Initialization and Display Methods
    private void initializeSlots() {
        LocalDate currentDate = LocalDate.now();
        String[] timeSlots = {"09:00 AM", "10:00 AM", "11:00 AM", "02:00 PM", "03:00 PM", "04:00 PM"};
        
        for (int i = 1; i <= 7; i++) {
            LocalDate date = currentDate.plusDays(i);
            String dateStr = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            slots.put(dateStr, new ArrayList<>(Arrays.asList(timeSlots)));
        }
    }

    public void showAvailableSlots() {
        System.out.println(BG_BLUE + "\n═══════ Available Slots ═══════" + DEF);
        if (slots.isEmpty()) {
            System.out.println(RED + "No slots available." + DEF);
            return;
        }

        slots.forEach((date, timeSlots) -> {
            if (!timeSlots.isEmpty()) {
                System.out.println(BLUE + "\nDate: " + DEF + date);
                System.out.println(SKYBLUE + "Available Times:" + DEF);
                for (int i = 0; i < timeSlots.size(); i++) {
                    System.out.println(SKYBLUE + (i + 1) + ". " + DEF + timeSlots.get(i));
                }
            }
        });
        System.out.println(BG_BLUE + "════════════════════════════" + DEF);
    }

    private String generateSlotId(String date, String selectedTime) {
        return date + "_" + selectedTime;
    }

    // Booking Methods
    public void bookSlot(User user) {
        
        try {

            System.out.println(BG_BLUE + "\n╔════════ SLOT BOOKING ════════╗" + DEF);
            System.out.println("enter your car id here to book  slot " );
            String carid = scanner.nextLine();
            // Validate if the user has the car
        if (!user.hasCar(carid)) {
            System.out.println("No car found with ID: " + carid + ". Please enter a valid car ID.");
            return; // Exit the method if the car ID is invalid
        }

            showAvailableSlots();
            
            if (slots.isEmpty()) {
                System.out.println(BG_RED + "\n❌ No slots available for booking." + DEF);
                return;
            }

            String date = getValidDateInput();
            if (date == null) return;

            List<String> timeSlots = slots.get(date);
            if (timeSlots.isEmpty()) { 
                System.out.println(BG_RED + "\n❌ No time slots available for selected date." + DEF);
                return;
            }

            String selectedTime = getValidTimeSlotInput(timeSlots);
            if (selectedTime == null) return;

            String slotId = generateSlotId(date, selectedTime);

            if (payment.slotBookPayment(user)) {
                timeSlots.remove(selectedTime);
                bookedSlots.put(slotId, user.getUserId());
                SlotExchangeRequest.updateRequestStatus(1); // Set to "slot booked"
                
                System.out.println(BG_GREEN + "\n✓ Slot booked successfully!" + DEF);
                System.out.println(SKYBLUE + "Date: " + YELLOW + date + DEF);
                System.out.println(SKYBLUE + "Time: " + YELLOW + selectedTime + DEF);
                System.out.println(SKYBLUE + "Slot ID: " + YELLOW + slotId + DEF);
                System.out.println(BLUE + "\nNote: You can request slot exchange if needed." + DEF);
                bookedCars.add(carid);
            } else {
                System.out.println(BG_RED + "\n❌ Slot booking failed due to payment issue." + DEF);
            }

        } catch (Exception e) {
            System.out.println(BG_RED + "\n❌ Error in slot booking: " + e.getMessage() + DEF);
            scanner.nextLine();
        }
        System.out.println(BG_BLUE + "╚══════════════════════════════╝" + DEF);
    }

    // Validation Methods
    private String getValidDateInput() {
        System.out.print(SKYBLUE + "Enter the date (dd-MM-yyyy): " + DEF);
        String date = scanner.nextLine();
        if (!slots.containsKey(date)) {
            System.out.println(RED + "Invalid date or no slots available for this date." + DEF);
            return null;
        }
        return date;
    }

    private String getValidTimeSlotInput(List<String> timeSlots) {
        System.out.println(SKYBLUE + "\nAvailable time slots:" + DEF);
        for (int i = 0; i < timeSlots.size(); i++) {
            System.out.println(SKYBLUE + (i + 1) + ". " + DEF + timeSlots.get(i));
        }
        System.out.print(BLUE + "Select time slot number: " + DEF);
        
        try {
            int slotChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (slotChoice < 1 || slotChoice > timeSlots.size()) {
                System.out.println(RED + "Invalid slot selection." + DEF);
                return null;
            }
            return timeSlots.get(slotChoice - 1);
        } catch (InputMismatchException e) {
            System.out.println(RED + "Please enter a valid number." + DEF);
            scanner.nextLine();
            return null;
        }
    }
        // Slot Exchange and Cancellation Methods
    public void cancelSlot(User user) {
        System.out.println(BG_BLUE + "\n╔════════ SLOT CANCELLATION ════════╗" + DEF);
        List<String> userSlots = getUserBookedSlots(user);

        if (userSlots.isEmpty()) {
            System.out.println(BG_RED + "\n⚠️ You have no booked slots." + DEF);
            return;
        }

        System.out.println(BLUE + "\nYour Booked Slots:" + DEF);
        userSlots.forEach(slot -> System.out.println(SKYBLUE + "• " + slot + DEF));

        System.out.println(BG_RED + "\nWARNING: Cancellation Policy" + DEF);
        System.out.println(YELLOW + "• No refund will be provided for cancellations" + DEF);
        System.out.print(BLUE + "\nWould you like to cancel or exchange? (Type 'cancel' or 'exchange'): " + DEF);
        String userChoice = scanner.nextLine().toLowerCase();
        
        switch (userChoice) {
            case "cancel" -> processCancellation(user);
            case "exchange" -> requestSlotExchange(user);
            default -> System.out.println(RED + "Invalid choice. Please enter 'cancel' or 'exchange'." + DEF);
        }
        System.out.println(BG_BLUE + "╚══════════════════════════════════╝" + DEF);
    }

    private void processCancellation(User user) {
        System.out.print(SKYBLUE + "\nEnter the slot ID to cancel: " + DEF);
        String slotId = scanner.nextLine();

        if (bookedSlots.containsKey(slotId) && bookedSlots.get(slotId).equals(user.getUserId())) {
            try {
                String[] parts = slotId.split("_");
                String date = parts[0];
                String time = parts[1];

                slots.computeIfAbsent(date, k -> new ArrayList<>()).add(time);
                bookedSlots.remove(slotId);
                SlotExchangeRequest.updateRequestStatus(0);
                System.out.println(BG_GREEN + "\n✓ Slot cancelled successfully!" + DEF);
            } catch (Exception e) {
                System.out.println(BG_RED + "\n❌ Error processing cancellation: " + e.getMessage() + DEF);
            }
        } else {
            System.out.println(BG_RED + "\n❌ Invalid slot ID or insufficient permissions." + DEF);
        }
    }

    public void requestSlotExchange(User user) {
        System.out.println(BG_BLUE + "\n╔════════ SLOT EXCHANGE REQUEST ════════╗" + DEF);
        
        List<String> userSlots = getUserBookedSlots(user);

        if (userSlots.isEmpty()) {
            System.out.println(BG_RED + "\n⚠️ You have no booked slots to exchange." + DEF);
            System.out.println(SKYBLUE + "Please book a slot first from the main menu." + DEF);
            return;
        }

        System.out.println(BLUE + "\nYour Current Bookings:" + DEF);
        userSlots.forEach(slot -> System.out.println(SKYBLUE + "• " + slot + DEF));

        System.out.print(SKYBLUE + "\nEnter your current slot ID to exchange: " + DEF);
        String currentSlotId = scanner.nextLine().trim();

        if (!bookedSlots.containsKey(currentSlotId) || !bookedSlots.get(currentSlotId).equals(user.getUserId())) {
            System.out.println(BG_RED + "\n❌ Invalid slot ID or insufficient permissions." + DEF);
            return;
        }

        showAvailableSlots();
        System.out.print(SKYBLUE + "\nEnter desired date for exchange (dd-MM-yyyy): " + DEF);
        String newDate = scanner.nextLine().trim();

        if (!slots.containsKey(newDate)) {
            System.out.println(BG_RED + "\n❌ Invalid date or no slots available." + DEF);
            return;
        }

        List<String> timeSlots = slots.get(newDate);
        if (timeSlots.isEmpty()) {
            System.out.println(BG_RED + "\n❌ No time slots available for selected date." + DEF);
            return;
        }

        String selectedTime = getValidTimeSlotInput(timeSlots);
        if (selectedTime == null) return;

        // Create and submit the request
        SlotExchangeRequest request = new SlotExchangeRequest(user.getUserId(), currentSlotId, newDate + "_" + selectedTime);
        SlotExchangeRequest.updateRequestStatus(2); // Update status to "exchange requested"
        request.submitRequest();
        exchangeRequests.add(request);
        
        System.out.println(BG_GREEN + "\n✓ Slot exchange request submitted successfully!" + DEF);
        System.out.println(SKYBLUE + "• Current Slot: " + YELLOW + currentSlotId + DEF);
        System.out.println(SKYBLUE + "• Requested Date: " + YELLOW + newDate + DEF);
        System.out.println(SKYBLUE + "• Requested Time: " + YELLOW + selectedTime + DEF);
        System.out.println(SKYBLUE + "• Status: " + YELLOW + "PENDING" + DEF);
        System.out.println(BLUE + "• Check status in 'Check Status' menu" + DEF);
        
        System.out.println(BG_BLUE + "╚══════════════════════════════════╝" + DEF);
    }

    // Utility Methods
    private List<String> getUserBookedSlots(User user) {
        return bookedSlots.entrySet().stream()
                .filter(entry -> entry.getValue().equals(user.getUserId()))
                .map(Map.Entry::getKey)
                .toList();
    }

    public static List<SlotExchangeRequest> getExchangeRequests() {
        return new ArrayList<>(exchangeRequests);
    }
        // Menu System
    public void showSlotBookingMenu(User user) {
        while (true) {
            try {
                System.out.println(BG_BLUE + "\n╔════════ SLOT BOOKING MENU ════════╗" + DEF);
                System.out.println(SKYBLUE + "1. " + DEF + "Show Available Slots" + YELLOW + " 📅" + DEF);
                System.out.println(SKYBLUE + "2. " + DEF + "Book a Slot" + YELLOW + " ✓" + DEF);
                System.out.println(SKYBLUE + "3. " + DEF + "Cancel a Slot" + YELLOW + " ❌" + DEF);
                System.out.println(SKYBLUE + "4. " + DEF + "Request Slot Exchange" + YELLOW + " 🔄" + DEF);
                System.out.println(RED + "5. " + DEF + "Back to Main Menu" + RED + " ←" + DEF);
                
                System.out.print(BLUE + "\nChoose an option (1-5): " + DEF);
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        showAvailableSlots();
                        promptEnterToContinue();
                    }
                    case 2 -> {
                        bookSlot(user);
                        promptEnterToContinue();
                    }
                    case 3 -> {
                        cancelSlot(user);
                        promptEnterToContinue();
                    }
                    case 4 -> {
                        requestSlotExchange(user);
                        promptEnterToContinue();
                    }
                    case 5 -> {
                        System.out.println(GREEN + "\nReturning to Main Menu..." + DEF);
                        return;
                    }
                    default -> {
                        System.out.println(BG_RED + "\n❌ Invalid choice! Please try again." + DEF);
                        promptEnterToContinue();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(BG_RED + "\n❌ Please enter a valid number." + DEF);
                scanner.nextLine(); // Clear scanner buffer
                promptEnterToContinue();
            } catch (Exception e) {
                System.out.println(BG_RED + "\n❌ An error occurred: " + e.getMessage() + DEF);
                scanner.nextLine(); // Clear scanner buffer
                promptEnterToContinue();
            }
        }
    }

    // Utility Methods
    private void promptEnterToContinue() {
        System.out.print(YELLOW + "\nPress Enter to continue..." + DEF);
        scanner.nextLine();
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

    // Feedback Methods
    public static void viewFeedback() {
        List<Feedback.FeedbackEntry> feedbackList = Feedback.getAllFeedbackEntries();
        if (feedbackList.isEmpty()) {
            System.out.println(BG_BLUE + "\n═══════ Feedback ═══════" + DEF);
            System.out.println(BLUE + "No feedback available." + DEF);
        } else {
            System.out.println(BG_BLUE + "\n═══════ User Feedback ═══════" + DEF);
            for (Feedback.FeedbackEntry feedback : feedbackList) {
                System.out.println(SKYBLUE + "User: " + DEF + feedback.getUserId() + 
                                 SKYBLUE + " - Feedback: " + DEF + feedback.getMessage());
            }
        }
        System.out.println(BG_BLUE + "════════════════════════" + DEF);
    }

    // Validation Methods
    public boolean slotExists(String slotId) {
        return bookedSlots.containsKey(slotId);
    }

    public String getSlotOwner(String slotId) {
        return bookedSlots.get(slotId);
    }

    private boolean isValidSlotFormat(String slotId) {
        return slotId != null && slotId.matches("^[A-Za-z]{3}\\d{2}[A-Z]$");
    }

    // Helper method for displaying error messages
    private void displayError(String message) {
        System.out.println(BG_RED + "\n❌ Error: " + message + DEF);
    }

    // Helper method for displaying success messages
    private void displaySuccess(String message) {
        System.out.println(BG_GREEN + "\n✓ " + message + DEF);
    }

    // Helper method for displaying information messages
    private void displayInfo(String message) {
        System.out.println(SKYBLUE + message + DEF);
    }
}

/*
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SlotBooking {
    // Constants and Fields
    private static final Map<String, List<String>> slots = new HashMap<>();
    private static final Map<String, String> bookedSlots = new HashMap<>();
    private static final List<SlotExchangeRequest> exchangeRequests = new ArrayList<>(); 
    private final Scanner scanner;
    private final Payment payment;

    // Color Constants
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    // Constructor
    public SlotBooking(CarService carService) {
        this.scanner = new Scanner(System.in);
        this.payment = new Payment(carService);
        initializeSlots();
        SlotExchangeRequest.updateRequestStatus(0); // Initialize with no request
    }

    // Initialization Methods
    private void initializeSlots() {
        LocalDate currentDate = LocalDate.now();
        String[] timeSlots = {"09:00 AM", "10:00 AM", "11:00 AM", "02:00 PM", "03:00 PM", "04:00 PM"};
        
        for (int i = 1; i <= 7; i++) {
            LocalDate date = currentDate.plusDays(i);
            String dateStr = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            slots.put(dateStr, new ArrayList<>(Arrays.asList(timeSlots)));
        }
    }

    public void showAvailableSlots() {
        System.out.println(YELLOW + "\n═══════ Available Slots ═══════" + RESET);
        if (slots.isEmpty()) {
            System.out.println(RED + "No slots available." + RESET);
            return;
        }

        slots.forEach((date, timeSlots) -> {
            if (!timeSlots.isEmpty()) {
                System.out.println(BLUE + "\nDate: " + RESET + date);
                System.out.println(CYAN + "Available Times:" + RESET);
                for (int i = 0; i < timeSlots.size(); i++) {
                    System.out.println(CYAN + (i + 1) + ". " + RESET + timeSlots.get(i));
                }
            }
        });
        System.out.println(YELLOW + "════════════════════════════" + RESET);
    }

    private String generateSlotId() {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String day = days[new Random().nextInt(days.length)];
        String numberPart = String.format("%02d", new Random().nextInt(100));
        char randomChar = (char) ('A' + new Random().nextInt(26));
        return day + numberPart + randomChar;
    }
        // Booking Methods
    public void bookSlot(User user) {
        try {
            System.out.println(YELLOW + "\n╔════════ SLOT BOOKING ════════╗" + RESET);
            showAvailableSlots();
            
            if (slots.isEmpty()) {
                System.out.println(RED + "\n❌ No slots available for booking." + RESET);
                return;
            }

            String date = getValidDateInput();
            if (date == null) return;

            List<String> timeSlots = slots.get(date);
            if (timeSlots.isEmpty()) {
                System.out.println(RED + "\n❌ No time slots available for selected date." + RESET);
                return;
            }

            String selectedTime = getValidTimeSlotInput(timeSlots);
            if (selectedTime == null) return;

            String slotId = generateSlotId();

            if (payment.slotBookPayment(user)) {
                timeSlots.remove(selectedTime);
                bookedSlots.put(slotId, user.getUserId());
                SlotExchangeRequest.updateRequestStatus(1); // Update status to "slot booked"
                
                System.out.println(GREEN + "\n✓ Slot booked successfully!" + RESET);
                System.out.println(CYAN + "Date: " + YELLOW + date + RESET);
                System.out.println(CYAN + "Time: " + YELLOW + selectedTime + RESET);
                System.out.println(CYAN + "Slot ID: " + YELLOW + slotId + RESET);
                System.out.println(BLUE + "\nNote: You can request slot exchange if needed." + RESET);
            } else {
                System.out.println(RED + "\n❌ Slot booking failed due to payment issue." + RESET);
            }

        } catch (Exception e) {
            System.out.println(RED + "\n❌ Error in slot booking: " + e.getMessage() + RESET);
            scanner.nextLine(); // Clear scanner buffer
        }
        System.out.println(YELLOW + "╚══════════════════════════════╝" + RESET);
    }

    // Validation Methods
    private String getValidDateInput() {
        System.out.print(BLUE + "Enter the date (dd-MM-yyyy): " + RESET);
        String date = scanner.nextLine();
        if (!slots.containsKey(date)) {
            System.out.println(RED + "Invalid date or no slots available for this date." + RESET);
            return null;
        }
        return date;
    }

    private String getValidTimeSlotInput(List<String> timeSlots) {
        System.out.println(CYAN + "\nAvailable time slots:" + RESET);
        for (int i = 0; i < timeSlots.size(); i++) {
            System.out.println(BLUE + (i + 1) + ". " + RESET + timeSlots.get(i));
        }
        System.out.print(BLUE + "Select time slot number: " + RESET);
        
        try {
            int slotChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (slotChoice < 1 || slotChoice > timeSlots.size()) {
                System.out.println(RED + "Invalid slot selection." + RESET);
                return null;
            }
            return timeSlots.get(slotChoice - 1);
        } catch (InputMismatchException e) {
            System.out.println(RED + "Please enter a valid number." + RESET);
            scanner.nextLine(); // Clear invalid input
            return null;
        }
    }

    public static void viewFeedback() {
        List<Feedback.FeedbackEntry> feedbackList = Feedback.getAllFeedbackEntries();
        if (feedbackList.isEmpty()) {
            System.out.println(YELLOW + "\n═══════ Feedback ═══════" + RESET);
            System.out.println(BLUE + "No feedback available." + RESET);
        } else {
            System.out.println(YELLOW + "\n═══════ User Feedback ═══════" + RESET);
            for (Feedback.FeedbackEntry feedback : feedbackList) {
                System.out.println(CYAN + "User: " + RESET + feedback.getUserId() + 
                                 CYAN + " - Feedback: " + RESET + feedback.getMessage());
            }
        }
        System.out.println(YELLOW + "════════════════════════" + RESET);
    }
        // Slot Exchange and Cancellation Methods
    public void cancelSlot(User user) {
        System.out.println(YELLOW + "\n╔════════ SLOT CANCELLATION ════════╗" + RESET);
        List<String> userSlots = getUserBookedSlots(user);

        if (userSlots.isEmpty()) {
            System.out.println(RED + "\n⚠️ You have no booked slots." + RESET);
            return;
        }

        System.out.println(BLUE + "\nYour Booked Slots:" + RESET);
        userSlots.forEach(slot -> System.out.println(CYAN + "• " + slot + RESET));

        System.out.println(RED + "\nWARNING: Cancellation policy" + RESET);
        System.out.println(YELLOW + "• No refund will be provided for cancellations" + RESET);
        System.out.print(BLUE + "\nWould you like to cancel or exchange? (Type 'cancel' or 'exchange'): " + RESET);
        String userChoice = scanner.nextLine().toLowerCase();
        
        switch (userChoice) {
            case "cancel" -> processCancellation(user);
            case "exchange" -> requestSlotExchange(user);
            default -> System.out.println(RED + "Invalid choice. Please enter 'cancel' or 'exchange'." + RESET);
        }
        System.out.println(YELLOW + "╚══════════════════════════════════╝" + RESET);
    }

    private void processCancellation(User user) {
        System.out.print(BLUE + "\nEnter the slot ID to cancel: " + RESET);
        String slotId = scanner.nextLine();

        if (bookedSlots.containsKey(slotId) && bookedSlots.get(slotId).equals(user.getUserId())) {
            try {
                String[] parts = slotId.split("_");
                String date = parts[0];
                String time = parts[1];

                slots.computeIfAbsent(date, k -> new ArrayList<>()).add(time);
                bookedSlots.remove(slotId);
                SlotExchangeRequest.updateRequestStatus(0); // Reset status
                System.out.println(GREEN + "\n✓ Slot cancelled successfully!" + RESET);
            } catch (Exception e) {
                System.out.println(RED + "\n❌ Error processing cancellation: " + e.getMessage() + RESET);
            }
        } else {
            System.out.println(RED + "\n❌ Invalid slot ID or insufficient permissions." + RESET);
        }
    }

    public void requestSlotExchange(User user) {
        System.out.println(YELLOW + "\n╔════════ SLOT EXCHANGE REQUEST ════════╗" + RESET);
        
        List<String> userSlots = getUserBookedSlots(user);

        if (userSlots.isEmpty()) {
            System.out.println(RED + "\n⚠️ You have no booked slots to exchange." + RESET);
            System.out.println(CYAN + "Please book a slot first from the main menu." + RESET);
            return;
        }

        System.out.println(BLUE + "\nYour Current Bookings:" + RESET);
        userSlots.forEach(slot -> System.out.println(CYAN + "• " + slot + RESET));

        System.out.print(YELLOW + "\nEnter your current slot ID to exchange: " + RESET);
        String currentSlotId = scanner.nextLine().trim();

        if (!bookedSlots.containsKey(currentSlotId) || !bookedSlots.get(currentSlotId).equals(user.getUserId())) {
            System.out.println(RED + "\n❌ Invalid slot ID or insufficient permissions." + RESET);
            return;
        }

        showAvailableSlots();
        System.out.print(YELLOW + "\nEnter desired date for exchange (dd-MM-yyyy): " + RESET);
        String newDate = scanner.nextLine().trim();

        if (!slots.containsKey(newDate)) {
            System.out.println(RED + "\n❌ Invalid date or no slots available." + RESET);
            return;
        }

        List<String> timeSlots = slots.get(newDate);
        if (timeSlots.isEmpty()) {
            System.out.println(RED + "\n❌ No time slots available for selected date." + RESET);
            return;
        }

        String selectedTime = getValidTimeSlotInput(timeSlots);
        if (selectedTime == null) return;

        // Create and submit the request
        SlotExchangeRequest request = new SlotExchangeRequest(user.getUserId(), currentSlotId, newDate + "_" + selectedTime);
        SlotExchangeRequest.updateRequestStatus(2); // Update status to "exchange requested"
        request.submitRequest();
        exchangeRequests.add(request);
        
        System.out.println(GREEN + "\n✓ Slot exchange request submitted successfully!" + RESET);
        System.out.println(CYAN + "• Current Slot: " + YELLOW + currentSlotId + RESET);
        System.out.println(CYAN + "• Requested Date: " + YELLOW + newDate + RESET);
        System.out.println(CYAN + "• Requested Time: " + YELLOW + selectedTime + RESET);
        System.out.println(CYAN + "• Status: " + YELLOW + "PENDING" + RESET);
        System.out.println(BLUE + "• Check status in 'Check Status' menu" + RESET);
        
        System.out.println(YELLOW + "╚══════════════════════════════════╝" + RESET);
    }

        // Utility Methods
    private List<String> getUserBookedSlots(User user) {
        return bookedSlots.entrySet().stream()
                .filter(entry -> entry.getValue().equals(user.getUserId()))
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<SlotExchangeRequest> getExchangeRequests() {
        return new ArrayList<>(exchangeRequests);
    }

    // Menu System
    public void showSlotBookingMenu(User user) {
        while (true) {
            try {
                System.out.println(YELLOW + "\n╔════════ SLOT BOOKING MENU ════════╗" + RESET);
                System.out.println(CYAN + "1. " + RESET + "Show Available Slots");
                System.out.println(CYAN + "2. " + RESET + "Book a Slot");
                System.out.println(CYAN + "3. " + RESET + "Cancel a Slot");
                System.out.println(CYAN + "4. " + RESET + "Request Slot Exchange");
                System.out.println(RED + "5. " + RESET + "Back to Main Menu");
                
                System.out.print(BLUE + "\nChoose an option (1-5): " + RESET);
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        showAvailableSlots();
                        promptEnterToContinue();
                    }
                    case 2 -> {
                        bookSlot(user);
                        promptEnterToContinue();
                    }
                    case 3 -> {
                        cancelSlot(user);
                        promptEnterToContinue();
                    }
                    case 4 -> {
                        requestSlotExchange(user);
                        promptEnterToContinue();
                    }
                    case 5 -> {
                        System.out.println(GREEN + "\nReturning to Main Menu..." + RESET);
                        return;
                    }
                    default -> {
                        System.out.println(RED + "\n❌ Invalid choice! Please try again." + RESET);
                        promptEnterToContinue();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(RED + "\n❌ Please enter a valid number." + RESET);
                scanner.nextLine(); // Clear scanner buffer
                promptEnterToContinue();
            } catch (Exception e) {
                System.out.println(RED + "\n❌ An error occurred: " + e.getMessage() + RESET);
                scanner.nextLine(); // Clear scanner buffer
                promptEnterToContinue();
            }
        }
    }

    private void promptEnterToContinue() {
        System.out.print(YELLOW + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    // Helper method to simulate processing
    private void simulateProcessing(String message) {
        System.out.print(CYAN + message);
        String[] dots = {".  ", ".. ", "..."};
        try {
            for (int i = 0; i < 3; i++) {
                for (String dot : dots) {
                    System.out.print("\r" + message + dot);
                    Thread.sleep(200);
                }
            }
            System.out.println(RESET);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Method to check if a slot exists
    public boolean slotExists(String slotId) {
        return bookedSlots.containsKey(slotId);
    }

    // Method to get slot owner
    public String getSlotOwner(String slotId) {
        return bookedSlots.get(slotId);
    }

    // Method to validate slot format
    private boolean isValidSlotFormat(String slotId) {
        return slotId != null && slotId.matches("^[A-Za-z]{3}\\d{2}[A-Z]$");
    }
}
*/
