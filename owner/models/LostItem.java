package owner.models;


import java.util.ArrayList;
import java.util.List;

public class LostItem {
    private String id;
    private String description;
    private String roomNumber;
    private String imageFileName;
    private String foundDate;
    private String status;
    private List<Claim> claims;

    public LostItem(String id, String description, String roomNumber, String imageFileName, String foundDate, String status) {
        this.id = id;
        this.description = description;
        this.roomNumber = roomNumber;
        this.imageFileName = imageFileName;
        this.foundDate = foundDate;
        this.status = status;
        this.claims = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getImageFileName() { return imageFileName; }
    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }

    public String getFoundDate() { return foundDate; }
    public void setFoundDate(String foundDate) { this.foundDate = foundDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Claim> getClaims() { return claims; }
    public void setClaims(List<Claim> claims) { this.claims = claims; }

    public void addClaim(Claim claim) {
        this.claims.add(claim);
    }
}
