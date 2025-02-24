import java.util.ArrayList;

public class AdminPanel {
    private Admin admin;

    public AdminPanel(Admin admin) {
        this.admin = admin;
    }

    public void adminMenu(ArrayList<SlotExchangeRequest> slotRequests, ArrayList<Feedback.FeedbackEntry> feedbackEntries, ArrayList<ServiceUpdate> serviceUpdates) {
        admin.adminMenu(slotRequests, feedbackEntries, serviceUpdates); // Delegate to the existing Admin class method
    }
}
