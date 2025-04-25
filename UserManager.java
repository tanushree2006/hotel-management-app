import java.util.HashMap;
import java.util.Map;

/**
 * Manages user registration and authentication in the hotel management system.
 * Provides methods to register users and verify login credentials.
 */
public class UserManager {
    // Singleton instance
    private static UserManager instance;
    
    // Map to store registered users (username -> User)
    private Map<String, User> users;
    
    private UserManager() {
        users = new HashMap<>();
        
        // Add a default owner account for testing
        addUser(new User("admin", "admin123", "System Administrator", 
                "admin@hotel.com", "123-456-7890", true));
    }
    
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }
    
    /**
     * Adds a new user to the system.
     * 
     * @param user The user to add
     * @return true if the user was added successfully, false if the username already exists
     */
    public boolean addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        
        users.put(user.getUsername(), user);
        return true;
    }
    
    /**
     * Checks if a user with the given username exists.
     * 
     * @param username The username to check
     * @return true if the user exists, false otherwise
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
    
    /**
     * Authenticates a user with the given username and password.
     * 
     * @param username The username
     * @param password The password
     * @return The User object if authentication is successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        
        return null;
    }
    
    /**
     * Gets a user by username.
     * 
     * @param username The username
     * @return The User object if found, null otherwise
     */
    public User getUser(String username) {
        return users.get(username);
    }
}
