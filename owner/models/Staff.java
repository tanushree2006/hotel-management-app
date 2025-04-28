package owner.models;

public class Staff {
    private String id;
    private String name;
    private String position;
    private String contactNumber;
    private String email;
    private String status;
    private String joinDate;
    private String lastShift;

    public Staff(String id, String name, String position, String contactNumber, String email, String status, String joinDate, String lastShift) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.contactNumber = contactNumber;
        this.email = email;
        this.status = status;
        this.joinDate = joinDate;
        this.lastShift = lastShift;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }

    public String getLastShift() { return lastShift; }
    public void setLastShift(String lastShift) { this.lastShift = lastShift; }

    @Override
    public String toString() {
        return name + " (" + position + ")";
    }
}