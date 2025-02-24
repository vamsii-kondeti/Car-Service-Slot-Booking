import java.util.List;

public class FeedbackEntry {
    private String userId;
    private String message;

    public FeedbackEntry(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
