package owner.models;

public class Claim {
    private String id;
    private String itemId;
    private String travelerName;
    private String travelerDescription;
    private String claimDate;
    private String status;

    public Claim(String id, String itemId, String travelerName, String travelerDescription, String claimDate, String status) {
        this.id = id;
        this.itemId = itemId;
        this.travelerName = travelerName;
        this.travelerDescription = travelerDescription;
        this.claimDate = claimDate;
        this.status = status;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public String getTravelerName() { return travelerName; }
    public void setTravelerName(String travelerName) { this.travelerName = travelerName; }

    public String getTravelerDescription() { return travelerDescription; }
    public void setTravelerDescription(String travelerDescription) { this.travelerDescription = travelerDescription; }

    public String getClaimDate() { return claimDate; }
    public void setClaimDate(String claimDate) { this.claimDate = claimDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}