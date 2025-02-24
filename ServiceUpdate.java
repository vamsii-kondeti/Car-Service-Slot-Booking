public class ServiceUpdate {
    private String userId; // Added userId field
    private String updateMessage;
    private String updateDate;

    public ServiceUpdate(String userId, String updateMessage, String updateDate) {
        this.userId = userId;
        this.updateMessage = updateMessage;
        this.updateDate = updateDate; // Rs-cost
    }
 
public String getUserId() {
    return userId;
}

public String getUpdateStatus() {
    return updateMessage; // Assuming updateMessage represents the status
}
}
