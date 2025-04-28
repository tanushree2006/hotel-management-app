package owner.models;

public class Room {
    private String id;
    private String roomNumber;
    private String type;
    private String status;
    private String price;
    private String lastCleaned;

    public Room(String id, String roomNumber, String type, String status, String price, String lastCleaned) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.status = status;
        this.price = price;
        this.lastCleaned = lastCleaned;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getLastCleaned() { return lastCleaned; }
    public void setLastCleaned(String lastCleaned) { this.lastCleaned = lastCleaned; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ")";
    }

}