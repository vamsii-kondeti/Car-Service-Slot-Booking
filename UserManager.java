import java.util.Collection;  // Add this import
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static Map<String, User> allUsers = new HashMap<>();  // Declare and initialize allUsers
    private static HashMap<String, User> registeredUsers = new HashMap<>();
    private static final String ADMIN_USERNAME = "admin"; // Static username
    private static final String ADMIN_PASSWORD = "adminpassword"; // Static password

    // Method to log in a user
    public static User loginUser(String email, String password) {
        User user = registeredUsers.get(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful.");
            return user;
        } else if (ADMIN_USERNAME.equals(email) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Admin login successful.");
            return new Admin("Admin", "User", "9999999999", "admin@example.com", "adminpassword");
        } else {
            System.out.println("Invalid credentials.");
            return null;
        }
    }

    public static void addUser(User user) {
        allUsers.put(user.getEmail(), user);
    }

    public static Collection<User> getAllUsers() {
        return allUsers.values();
    }

    public static User getUserByEmail(String email) {
        return allUsers.get(email);
    }

    public static void removeUser(User user) {
        allUsers.remove(user.getEmail());
    }

    // Method to register a new user
    public static boolean registerUser(User user) {        
        // Remove default user creation logic
        if (registeredUsers.containsKey(user.getEmail())) {
            System.out.println("User already registered.");
            return false;
        }
        registeredUsers.put(user.getEmail(), user);
        System.out.println("User registered successfully.");
        return true;
    }

    // Method to check if a user is already registered
    public static boolean isUserRegistered(String email) {
        return registeredUsers.containsKey(email);
    }
}