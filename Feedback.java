import java.util.ArrayList;
import java.util.List;

public class Feedback {

    // A list to store feedback entries
    private static List<FeedbackEntry> feedbackList = new ArrayList<>();

    // Method to store feedback without user details
    public static void storeFeedback(String feedback) {
        FeedbackEntry feedbackEntry = new FeedbackEntry("Anonymous", "N/A", feedback);
        feedbackList.add(feedbackEntry);
        System.out.println("Feedback submitted successfully.");
    }

    // Method to store feedback with user details
    public static void storeFeedback(String feedback, User user) {
        FeedbackEntry feedbackEntry = new FeedbackEntry(user.getName(), user.getEmail(), feedback);
        feedbackList.add(feedbackEntry);
        System.out.println("Feedback submitted successfully.");
    }

    // Method to retrieve all feedback entries as strings
    public static List<String> getAllFeedback() {
        List<String> feedbackMessages = new ArrayList<>();
        for (FeedbackEntry entry : feedbackList) {
            feedbackMessages.add(entry.toString());
        }
        return feedbackMessages;
    }

    // Method to display all feedback entries
    public static void displayFeedback() {
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available.");
        } else {
            System.out.println("\n----- Feedback Entries -----");
            for (FeedbackEntry entry : feedbackList) {
                System.out.println(entry);
            }
            System.out.println("----------------------------");
        }
    }

    // Method to get the number of feedback entries
    public static int getFeedbackCount() {
        return feedbackList.size();
    }

    // Method to clear all feedback entries
    public static void clearFeedback() {
        feedbackList.clear();
        System.out.println("All feedback entries have been cleared.");
    }

    // Method to get all feedback entries as FeedbackEntry objects
    public static List<FeedbackEntry> getAllFeedbackEntries() {
        return new ArrayList<>(feedbackList); // Return a copy to prevent external modification
    }

    // FeedbackEntry class to hold the details of a feedback
    public static class FeedbackEntry {
        private String userName;
        private String userEmail;
        private String feedback;

        // Constructor to initialize feedback entry
        public FeedbackEntry(String userName, String userEmail, String feedback) {
            this.userName = userName;
            this.userEmail = userEmail;
            this.feedback = feedback;
        }

        // Getters for FeedbackEntry fields
        public String getUserName() {
            return userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getFeedback() {
            return feedback;
        }

        // Method to get userId (assuming userName is used as userId)
        public String getUserId() {
            return userName;
        }

        // Method to get message (same as getFeedback)
        public String getMessage() {
            return feedback;
        }

        // Override toString to display feedback entry in a readable format
        @Override
        public String toString() {
            return "User: " + userName + " | Email: " + userEmail + " | Feedback: " + feedback;
        }
    }
}
