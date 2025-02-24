import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class User {
    // Color codes
    protected static final String DEF = "\u001b[0;1m";
    protected static final String BLINK = "\u001b[5m";
    protected static final String RED = "\u001b[31;1m";
    protected static final String GREEN = "\u001b[32;1m";
    protected static final String YELLOW = "\u001b[33;1m";
    protected static final String BLUE = "\u001b[34;1m";
    protected static final String PURPLE = "\u001b[35;1m";
    protected static final String SKYBLUE = "\u001b[36;1m";
    protected static final String BG_RED = "\u001b[101;1m";
    protected static final String BG_GREEN = "\u001b[102;1m";
    protected static final String BG_YELLOW = "\u001b[103;1m";
    protected static final String BG_BLUE = "\u001b[104;1m";
    protected static final String BG_PURPLE = "\u001b[105;1m";
    protected static final String BG_SKYBLUE = "\u001b[106;1m";

    protected static Scanner scanner = new Scanner(System.in);
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private List<Car> cars;
    private String userId;
    private boolean isAdmin;

    public User(String firstName, String lastName, String mobileNumber, String email, String password, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.cars = new ArrayList<>();
        this.userId = email;
        this.isAdmin = isAdmin;
    }

    // Getters remain the same
    public String getEmail() { return email; }
    public String getMobileNumber() { return mobileNumber; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public List<Car> getCars() { return cars; }
    public String getUserId() { return userId; }
    public boolean isAdmin() { return isAdmin; }

    public void addCar(Car car) {
        cars.add(car);
        System.out.println(GREEN + "\n✓ Car added successfully!" + DEF);
    }

    public void displayUserCarInfo() {
        System.out.println(BG_BLUE + "\n╔════════ YOUR CARS ════════╗" + DEF);
        if (cars.isEmpty()) {
            System.out.println(YELLOW + "No cars registered yet." + DEF);
            System.out.println(SKYBLUE + "Add a car from the Car Management menu." + DEF);
        } else {
            for (Car car : cars) {
                System.out.println(BLUE + "Car ID: " + YELLOW + car.getCarId() + DEF);
                System.out.println(BLUE + "Make: " + YELLOW + car.getMake() + DEF);
                System.out.println(BLUE + "Model: " + YELLOW + car.getModel() + DEF);
                System.out.println(SKYBLUE + "------------------------" + DEF);
            }
        }
        System.out.println(BG_BLUE + "╚══════════════════════════╝" + DEF);
    }

    public boolean hasCar(String carId) {
        for (Car car : cars) {
            if (car.getCarId().equals(carId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.matches("[6-9]\\d{9}");
    }
        // Static method to register a new user
    // public static void registerUser(ArrayList<User> users) {
    //    // User newUser = new User("akash", "balerao", "7680060358", "akash@gmail.com", "1234", false);
    //     //users.add(newUser);

    //     System.out.println(BG_BLUE + "\n╔════════ USER REGISTRATION ════════╗" + DEF);
        
    //     System.out.print(SKYBLUE + "Enter First Name: " + DEF);
    //     String firstName = scanner.nextLine();
        
    //     System.out.print(SKYBLUE + "Enter Last Name: " + DEF);
    //     String lastName = scanner.nextLine();


    //     String mobileNumber;
    //     while (true) {
    //         System.out.print(SKYBLUE + "Enter Mobile Number: " + DEF);
    //         mobileNumber = scanner.nextLine();
    //         if (isValidMobileNumber(mobileNumber)) {
    //             break;
    //         } else {
    //             System.out.println(RED + "\n❌ Invalid mobile number!" + DEF);
    //             System.out.println(YELLOW + "• Number should be 10 digits" + DEF);
    //             System.out.println(YELLOW + "• Should start with 6-9" + DEF);
    //             System.out.println(YELLOW + "Please try again." + DEF);
    //         }
    //     }

    //     System.out.print(SKYBLUE + "Enter Email: " + DEF);
    //     String email = scanner.nextLine();
        
    //     System.out.print(SKYBLUE + "Enter Password: " + DEF);
    //     String password = scanner.nextLine();
      
    //     // Default isAdmin to false for regular users
    //     boolean isAdmin = false;

    //     // OTP Generation and Verification
    //     System.out.println(BG_YELLOW + "\n═══════ OTP VERIFICATION ═══════" + DEF);
    //     int otp = generateOTP();
    //     System.out.println(YELLOW + "Your OTP is: " + BLINK + GREEN + otp + DEF);
        
    //     while (true) {
    //         System.out.print(SKYBLUE + "Enter the OTP: " + DEF);
    //         try {
    //             int enteredOtp = scanner.nextInt();
    //             scanner.nextLine(); // Consume newline

    //             if (otp == enteredOtp) {
    //                 User newUser = new User(firstName, lastName, mobileNumber, email, password, isAdmin);
    //                 users.add(newUser);
                    
    //                 System.out.println(BG_GREEN + "\n✓ Registration Successful!" + DEF);
    //                 System.out.println(GREEN + "Welcome to Car Service Application, " + firstName + "!" + DEF);
                    
    //                 break;
    //             } else {
    //                 System.out.println(RED + "\n❌ Incorrect OTP. Please try again." + DEF);
    //             }
    //         } catch (Exception e) {
    //             System.out.println(RED + "\n❌ Invalid input! Please enter a valid OTP." + DEF);
    //             scanner.nextLine(); // Clear the invalid input
    //         }
    //     }
    //     System.out.println(BG_BLUE + "╚══════════════════════════════╝" + DEF);
    // }




    public static void registerUser(ArrayList<User> users) {
        System.out.println(BG_BLUE + "\n╔═════════════ USER REGISTRATION ═════════════╗" + DEF);
    
        System.out.print(SKYBLUE + "Enter First Name: " + DEF);
        String firstName = scanner.nextLine();
    
        System.out.print(SKYBLUE + "Enter Last Name: " + DEF);
        String lastName = scanner.nextLine();
    
        String mobileNumber;
        while (true) {
            System.out.print(SKYBLUE + "Enter Mobile Number: " + DEF);
            mobileNumber = scanner.nextLine();
            if (isValidMobileNumber(mobileNumber)) {
                break;
            } else {
                System.out.println(RED + "\n❌ Invalid mobile number!" + DEF);
                System.out.println(YELLOW + "• Number should be 10 digits" + DEF);
                System.out.println(YELLOW + "• Should start with 6-9" + DEF);
                System.out.println(YELLOW + "Please try again." + DEF);
            }
        }
    
        String email;
        while (true) {
            System.out.print(SKYBLUE + "Enter Email: " + DEF);
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println(RED + "\n❌ Invalid email format!" + DEF);
                System.out.println(YELLOW + "• Example: example@domain.com" + DEF);
                System.out.println(YELLOW + "Please try again." + DEF);
            }
        }
    
        System.out.print(SKYBLUE + "Enter Password: " + DEF);
        String password = scanner.nextLine();
    
        boolean isAdmin = false;
    
        System.out.println(BG_YELLOW + "\n═══════ OTP VERIFICATION ═══════" + DEF);
        int otp = generateOTP();
        System.out.println(YELLOW + "Your OTP is: " + BLINK + GREEN + otp + DEF);
    
        while (true) {
            System.out.print(SKYBLUE + "Enter the OTP: " + DEF);
            try {
                int enteredOtp = scanner.nextInt();
                scanner.nextLine(); 
    
                if (otp == enteredOtp) {
                    User newUser = new User(firstName, lastName, mobileNumber, email, password, isAdmin);
                    users.add(newUser);
                    System.out.println(BG_GREEN + "\n✓ Registration Successful!" + DEF);
                    System.out.println(GREEN + "Welcome to Car Service Application, " + firstName + "!" + DEF);
                    break;
                } else {
                    System.out.println(RED + "\n❌ Incorrect OTP. Please try again." + DEF);
                }
            } catch (Exception e) {
                System.out.println(RED + "\n❌ Invalid input! Please enter a valid OTP." + DEF);
                scanner.nextLine();
            }
        }
        System.out.println(BG_BLUE + "╚═════════════════════════════════════════════╝" + DEF);
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
    
    

    // Static method to log in a user
    public static User loginUser(ArrayList<User> users) {
        while (true) {
            System.out.println(BG_BLUE + "\n╔════════ USER LOGIN ════════╗" + DEF);
            
            System.out.print(SKYBLUE + "Enter Email or Mobile Number: " + DEF);
            String emailOrMobile = scanner.nextLine();

            System.out.print(SKYBLUE + "Enter Password: " + DEF);
            String password = scanner.nextLine();

            for (User user : users) {
                if ((user.getEmail().equals(emailOrMobile) || user.getMobileNumber().equals(emailOrMobile))
                        && user.getPassword().equals(password)) {
                    System.out.println(BG_GREEN + "\n✓ Login Successful!" + DEF);
                    System.out.println(GREEN + "Welcome back, " + user.getFirstName() + "! 👋" + DEF);
                    return user;
                }
            }

            System.out.println(BG_RED + "\n❌ Login Failed!" + DEF);
            System.out.println(RED + "Incorrect email/mobile number or password." + DEF);
            
            System.out.print(YELLOW + "Do you want to try again? (yes/no): " + DEF);
            if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                return null;
            }
        }
    }

    // Static method to log in an admin user
    public static User loginAdmin(ArrayList<User> users) {
        while (true) {
            System.out.println(BG_PURPLE + "\n╔════════ ADMIN LOGIN ════════╗" + DEF);
            
            System.out.print(SKYBLUE + "Enter Admin Email: " + DEF);
            String email = scanner.nextLine();

            System.out.print(SKYBLUE + "Enter Password: " + DEF);
            String password = scanner.nextLine();

            for (User user : users) {
                if (user.isAdmin() && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    System.out.println(BG_GREEN + "\n✓ Admin Login Successful!" + DEF);
                    System.out.println(GREEN + "Welcome back, Admin " + user.getFirstName() + "! 👋" + DEF);
                    return user;
                }
            }

            System.out.println(BG_RED + "\n❌ Admin Login Failed!" + DEF);
            System.out.println(RED + "Incorrect email or password." + DEF);
            
            System.out.print(YELLOW + "Do you want to try again? (yes/no): " + DEF);
            if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                return null;
            }
        }
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    // Static method to generate a 4-digit OTP
    private static int generateOTP() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }

    // Method to simulate processing
    private static void simulateProcessing(String message) {
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