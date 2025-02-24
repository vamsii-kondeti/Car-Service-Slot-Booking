/*import java.util.ArrayList;
import java.util.List;

public class SlotExchangeRequest {
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

    private static List<SlotExchangeRequest> allRequests = new ArrayList<>(); // Store all requests

    // Method to retrieve all requests
    public static List<SlotExchangeRequest> getAllRequests() {
        return allRequests;
    }
    
    // Remove the duplicate submitRequest method
    private String userId;
    private String slotId;
    private String requestDate;
    private String status = null;
    private static boolean requestSubmitted = false;
    private static int requestStatus = 0; // 0: No request, 1: Slot booked, 2: Exchange requested

    // Admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    public SlotExchangeRequest(String userId, String slotId, String requestDate) {
        this.userId = userId;
        this.slotId = slotId;
        this.requestDate = requestDate;
        this.requestStatus=2;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getRequestedSlot() {
        return slotId;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    // Status management methods
    public static void updateRequestStatus(int status) {
        requestStatus = status;
    }

    public static int getRequestStatus() {
        // If a request exists, return 1
        if (requestStatus == 2) {
            return 2;  // Return 1 when slot exchange is requested
        }
        return requestStatus;
    }

    public String getStatus() {
       return status != null ? status : "pending";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Admin login
    public static boolean login(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    // Display methods
    public void displayCurrentSlotId() {
        System.out.println(BG_BLUE + "\n═══════ CURRENT SLOT DETAILS ═══════" + DEF);
        System.out.println(SKYBLUE + "Current Slot ID: " + YELLOW + getSlotId() + DEF);
    }

    public void manageSlotExchanges(ArrayList<SlotExchangeRequest> slotRequests) {
        System.out.println(BG_BLUE + "\n╔════════ MANAGE SLOT EXCHANGES ════════╗" + DEF);
        
        if (slotRequests.isEmpty()) {
            System.out.println(YELLOW + "\nNo pending slot exchange requests." + DEF);
            return;
        }

        for (SlotExchangeRequest request : slotRequests) {
            System.out.println(BG_SKYBLUE + "\n═══════ Request Details ═══════" + DEF);
            System.out.println(SKYBLUE + "User ID: " + YELLOW + request.getUserId() + DEF);
            System.out.println(SKYBLUE + "Requested Slot: " + YELLOW + request.getSlotId() + DEF);
            System.out.println(SKYBLUE + "Request Date: " + YELLOW + request.getRequestDate() + DEF);
            System.out.println(SKYBLUE + "Status: " + getColoredStatus(request.getStatus()) + DEF);
        }
        System.out.println(BG_BLUE + "\n╚══════════════════════════════════════╝" + DEF);
    }

    // Request management methods
    public void submitRequest() {
        requestSubmitted = true;
        status = "pending";
        requestStatus = 2;
        System.out.println(BG_GREEN + "\n✓ Request Submitted Successfully" + DEF);
        System.out.println(SKYBLUE + "• Status: " + YELLOW + "PENDING" + DEF);
        System.out.println(SKYBLUE + "• You can check the status in 'Check Status' menu" + DEF);
    }

    public void processRequest(String decision) {
        if (decision.equalsIgnoreCase("approve")) {
            this.status = "approved";
            requestStatus = 1;  // Keep as 1 after approval
        } else if (decision.equalsIgnoreCase("deny")) {
            this.status = "denied";
            requestStatus = 1;  // Keep as 1 after denial to allow new requests
        }
    }

    private void displayRequestOutcome() {
        System.out.println(BG_SKYBLUE + "\n═══════ Request Details ═══════" + DEF);
        System.out.println(SKYBLUE + "User ID: " + YELLOW + userId + DEF);
        System.out.println(SKYBLUE + "Slot ID: " + YELLOW + slotId + DEF);
        System.out.println(SKYBLUE + "Status: " + getColoredStatus(status) + DEF);
    }

    public void resetRequest() {
        requestSubmitted = false;
        status = null;
        requestStatus = 0;
        System.out.println(YELLOW + "\nRequest has been reset." + DEF);
    }

    private String getColoredStatus(String status) {
        if (status == null) return BG_RED + "UNKNOWN ?" + DEF;
        
        return switch (status.toLowerCase()) {
            case "pending" -> BG_YELLOW + "PENDING " + YELLOW + "⌛" + DEF;
            case "approved" -> BG_GREEN + "APPROVED " + GREEN + "✓" + DEF;
            case "denied" -> BG_RED + "DENIED " + RED + "✗" + DEF;
            case "no requests found" -> BG_BLUE + "NO REQUESTS" + BLUE + " !" + DEF;
            default -> BG_RED + "UNKNOWN " + RED + "?" + DEF;
        };
    }

    // Method to simulate processing
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
}
*/
import java.util.ArrayList;
import java.util.List;

public class SlotExchangeRequest {
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

    // Status Constants
    private static final String STATUS_UNKNOWN = "unknown";
    private static final String STATUS_NO_REQUESTS = "no requests found";
    private static final String STATUS_PENDING = "pending";
    private static final String STATUS_APPROVED = "approved";
    private static final String STATUS_DENIED = "denied";

    // Request Status Constants
    private static final int STATUS_INITIAL = 0;
    private static final int STATUS_BOOKED = 1;
    private static final int STATUS_REQUESTED = 2;
    private static final int STATUS_APPROVED_VALUE = 3;
    private static final int STATUS_DENIED_VALUE = 4;

    // Static fields
    private static List<SlotExchangeRequest> allRequests = new ArrayList<>();
    private static boolean requestSubmitted = false;
    private static int requestStatus = STATUS_INITIAL;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    // Instance fields
    private final String userId;
    private final String slotId;
    private final String requestDate;
    private String status;
        // Constructor
    public SlotExchangeRequest(String userId, String slotId, String requestDate) {
        this.userId = userId;
        this.slotId = slotId;
        this.requestDate = requestDate;
        this.status = STATUS_UNKNOWN;
        if(!userId.equals("userId"))
        {
            allRequests.add(this);
        }
    }

    // Getters
    public String getUserId() { return userId; }
    public String getRequestedSlot() { return slotId; }
    public String getSlotId() { return slotId; }
    public String getRequestDate() { return requestDate; }
    public static List<SlotExchangeRequest> getAllRequests() { return allRequests; }

    // Status Management Methods
    public static void updateRequestStatus(int newStatus) {
        requestStatus = newStatus;
        switch (newStatus) {
            case STATUS_INITIAL -> setGlobalStatus(STATUS_UNKNOWN);
            case STATUS_BOOKED -> setGlobalStatus(STATUS_NO_REQUESTS);
            case STATUS_REQUESTED -> setGlobalStatus(STATUS_PENDING);
            case STATUS_APPROVED_VALUE -> setGlobalStatus(STATUS_APPROVED);
            case STATUS_DENIED_VALUE -> setGlobalStatus(STATUS_DENIED);
        }
    }
    
    private static void setGlobalStatus(String newStatus) {
        for (SlotExchangeRequest request : allRequests) {
            request.setStatus(newStatus);
        }
        // if(newStatus.equalsIgnoreCase("APPROVED"))
        // {
        //     requestStatus = STATUS_APPROVED_VALUE;
        // }else if(newStatus.equalsIgnoreCase("denied"))
        // {
        //     requestStatus = STATUS_DENIED_VALUE;
        // }
    }

    public static int getRequestStatus() {
        return requestStatus;
    }

    public String getStatus() {
        return status != null ? status : STATUS_UNKNOWN;
    }

    public void setStatus(String newStatus) {
        if (isValidStatus(newStatus)) {
            this.status = newStatus;
        }
    }

    private boolean isValidStatus(String status) {
        return status != null && (
            status.equals(STATUS_UNKNOWN) ||
            status.equals(STATUS_NO_REQUESTS) ||
            status.equals(STATUS_PENDING) ||
            status.equals(STATUS_APPROVED) ||
            status.equals(STATUS_DENIED)
        );
    }

    // Admin Authentication
    public static boolean login(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
        // Request Processing Methods
    public void submitRequest() {
        requestSubmitted = true;
        status = STATUS_PENDING;
        requestStatus = STATUS_REQUESTED;

        allRequests.add(this);

        
        System.out.println(BG_GREEN + "\n✓ Request Submitted Successfully" + DEF);
        System.out.println(SKYBLUE + "• Status: " + YELLOW + "PENDING" + DEF);
        System.out.println(SKYBLUE + "• You can check the status in 'Check Status' menu" + DEF);
        
        displayRequestDetails();
    }

    // 
    public void processRequest(String decision) {
        // if (!status.equals(STATUS_PENDING)) {
        //     System.out.println(RED + "Cannot process: Request is not in pending state" + DEF);
        //     return;
        // }

        switch (decision.toLowerCase()) {
            case "approved" -> {
                this.status = STATUS_APPROVED;
                // Update the status for the request in the global list
                for (SlotExchangeRequest request : allRequests) {
                    if (request.getUserId().equals(this.userId) && request.getRequestedSlot().equals(this.slotId)) {
                        request.setStatus(STATUS_APPROVED);  // Ensure status is updated in the list
                        SlotBooking.bookedSlots.remove(request.slotId.split("_")[0]);
                        SlotBooking.bookedSlots.put(request.requestDate.split("_")[1], userId);
                        SlotBooking.slots.computeIfAbsent(request.slotId.split("_")[0], k -> new ArrayList<>()).add(request.slotId.split("_")[1]);
                        SlotBooking.slots.computeIfAbsent(request.requestDate.split("_")[0], k -> new ArrayList<>()).remove(request.requestDate.split("_")[1]);
                    }
                }
                System.out.println(BG_GREEN + "\n✓ Request Approved" + DEF);
                displayRequestOutcome("approved");
            }
            case "deny" -> {
                this.status = STATUS_DENIED;
                System.out.println(BG_RED + "\n✗ Request Denied" + DEF);
                displayRequestOutcome("denied");
            }
            default -> System.out.println(RED + "Invalid decision" + DEF);
        }
    }


    // Display Methods
    private void displayRequestDetails() {
        System.out.println(BG_SKYBLUE + "\n═══════ Request Details ═══════" + DEF);
        System.out.println(SKYBLUE + "User ID: " + YELLOW + userId + DEF);
        System.out.println(SKYBLUE + "Slot ID: " + YELLOW + slotId + DEF);
        System.out.println(SKYBLUE + "Request Date: " + YELLOW + requestDate + DEF);
        System.out.println(SKYBLUE + "Status: " + getColoredStatus(status) + DEF);
        System.out.println(BG_SKYBLUE + "════════════════════════════" + DEF);
    }

    private void displayRequestOutcome(String outcome) {
        System.out.println(BG_SKYBLUE + "\n═══════ Request Outcome ═══════" + DEF);
        System.out.println(SKYBLUE + "User ID: " + YELLOW + userId + DEF);
        System.out.println(SKYBLUE + "Slot ID: " + YELLOW + slotId + DEF);
        
        switch (outcome) {
            case "approved" -> {
                System.out.println(GREEN + "• Your slot exchange request has been approved" + DEF);
                System.out.println(GREEN + "• New slot has been assigned" + DEF);
                System.out.println(GREEN + "• Check your email for confirmation" + DEF);
            }
            case "denied" -> {
                System.out.println(RED + "• Your slot exchange request was denied" + DEF);
                System.out.println(RED + "• You can submit a new request" + DEF);
                System.out.println(RED + "• Contact support for more information" + DEF);
            }
        }
        System.out.println(BG_SKYBLUE + "════════════════════════════" + DEF);
    }

    public void displayCurrentSlotId() {
        System.out.println(BG_BLUE + "\n═══════ Current Slot Details ═══════" + DEF);
        System.out.println(SKYBLUE + "Current Slot ID: " + YELLOW + slotId + DEF);
        System.out.println(BG_BLUE + "══════════════════════════════" + DEF);
    }

    public void manageSlotExchanges(ArrayList<SlotExchangeRequest> slotRequests) {
        System.out.println(BG_BLUE + "\n╔════════ Manage Slot Exchanges ════════╗" + DEF);
        
        if (slotRequests.isEmpty()) {
            System.out.println(YELLOW + "\nNo pending slot exchange requests." + DEF);
            return;
        }

        for (SlotExchangeRequest request : slotRequests) {
            System.out.println(BG_SKYBLUE + "\n═══════ Request Details ═══════" + DEF);
            System.out.println(SKYBLUE + "User ID: " + YELLOW + request.getUserId() + DEF);
            System.out.println(SKYBLUE + "Requested Slot: " + YELLOW + request.getSlotId() + DEF);
            System.out.println(SKYBLUE + "Request Date: " + YELLOW + request.getRequestDate() + DEF);
            System.out.println(SKYBLUE + "Status: " + getColoredStatus(request.getStatus()) + DEF);
        }
        System.out.println(BG_BLUE + "\n╚══════════════════════════════════════╝" + DEF);
    }

    private String getColoredStatus(String status) {
        if (status == null) return BG_RED + "UNKNOWN " + RED + "?" + DEF;
        
        return switch (status.toLowerCase()) {
            case "pending" -> BG_YELLOW + "PENDING " + YELLOW + "⌛" + DEF;
            case "approved" -> BG_GREEN + "APPROVED " + GREEN + "✓" + DEF;
            case "denied" -> BG_RED + "DENIED " + RED + "✗" + DEF;
            case "no requests found" -> BG_BLUE + "NO REQUESTS " + BLUE + "!" + DEF;
            default -> BG_RED + "UNKNOWN " + RED + "?" + DEF;
        };
    }

    public void resetRequest() {
        status = STATUS_UNKNOWN;
        requestSubmitted = false;
        requestStatus = STATUS_INITIAL;
        System.out.println(YELLOW + "\nRequest has been reset." + DEF);
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
}