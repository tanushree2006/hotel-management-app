/**
 * Represents a user in the hotel management system.
 * Stores basic user information and authentication details.
 */
public class User {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private boolean isOwner;
    
    public User(String username, String password, String fullName, String email, String phone, boolean isOwner) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.isOwner = isOwner;
    }
    
    // Getters
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public boolean isOwner() {
        return isOwner;
    }
}
