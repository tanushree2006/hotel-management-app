import java.util.Date;

public class LostItem {
    private int id;
    private String name;
    private String description;
    private String roomNumber;
    private Date dateFound;
    private String imageUrl;
    private String status; // Unclaimed, Claimed, Returned

    public LostItem(int id, String name, String description, String roomNumber, Date dateFound, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.dateFound = dateFound;
        this.imageUrl = imageUrl;
        this.status = "Unclaimed";
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getRoomNumber() { return roomNumber; }
    public Date getDateFound() { return dateFound; }
    public String getImageUrl() { return imageUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}