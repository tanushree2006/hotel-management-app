import java.util.Date;

public class ItemClaim {
    private int id;
    private int itemId;
    private String username;
    private String description;
    private String additionalInfo;
    private Date dateClaimed;
    private String status; // Pending, Approved, Rejected

    public ItemClaim(int id, int itemId, String username, String description,
                     String additionalInfo, Date dateClaimed, String status) {
        this.id = id;
        this.itemId = itemId;
        this.username = username;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.dateClaimed = dateClaimed;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public int getItemId() { return itemId; }
    public String getUsername() { return username; }
    public String getDescription() { return description; }
    public String getAdditionalInfo() { return additionalInfo; }
    public Date getDateClaimed() { return dateClaimed; }
    public String getStatus() { return status; }

    // Setters
    public void setStatus(String status) { this.status = status; }
}