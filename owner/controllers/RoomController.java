package owner.controllers;


import owner.models.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private List<Room> rooms;

    public RoomController() {
        this.rooms = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample data for testing
        rooms.add(new Room("R101", "101", "Standard", "Available", "$120/night", "2023-04-21"));
        rooms.add(new Room("R102", "102", "Deluxe", "Occupied", "$180/night", "2023-04-20"));
        rooms.add(new Room("R103", "201", "Suite", "Maintenance", "$250/night", "2023-04-19"));
        rooms.add(new Room("R104", "202", "Standard", "Available", "$120/night", "2023-04-21"));
        rooms.add(new Room("R105", "301", "Deluxe", "Occupied", "$180/night", "2023-04-20"));
        rooms.add(new Room("R106", "302", "Suite", "Available", "$250/night", "2023-04-22"));
        rooms.add(new Room("R107", "303", "Standard", "Occupied", "$120/night", "2023-04-20"));
        rooms.add(new Room("R108", "304", "Deluxe", "Maintenance", "$180/night", "2023-04-18"));
        rooms.add(new Room("R109", "401", "Presidential Suite", "Available", "$500/night", "2023-04-22"));
        rooms.add(new Room("R110", "402", "Standard", "Occupied", "$120/night", "2023-04-21"));
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public Room getRoomById(String id) {
        for (Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public Room getRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public List<Room> getRoomsByType(String type) {
        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getType().equals(type)) {
                result.add(room);
            }
        }
        return result;
    }

    public List<Room> getRoomsByStatus(String status) {
        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getStatus().equals(status)) {
                result.add(room);
            }
        }
        return result;
    }

    public List<Room> searchRooms(String query) {
        List<Room> result = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Room room : rooms) {
            if (room.getRoomNumber().toLowerCase().contains(lowerQuery) ||
                    room.getType().toLowerCase().contains(lowerQuery) ||
                    room.getStatus().toLowerCase().contains(lowerQuery) ||
                    room.getPrice().toLowerCase().contains(lowerQuery)) {
                result.add(room);
            }
        }

        return result;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public boolean updateRoom(Room room) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId().equals(room.getId())) {
                rooms.set(i, room);
                return true;
            }
        }
        return false;
    }

    public boolean deleteRoom(String id) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId().equals(id)) {
                rooms.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getAvailableRoomsCount() {
        int count = 0;
        for (Room room : rooms) {
            if (room.getStatus().equals("Available")) {
                count++;
            }
        }
        return count;
    }

    public int getOccupiedRoomsCount() {
        int count = 0;
        for (Room room : rooms) {
            if (room.getStatus().equals("Occupied")) {
                count++;
            }
        }
        return count;
    }

    public int getMaintenanceRoomsCount() {
        int count = 0;
        for (Room room : rooms) {
            if (room.getStatus().equals("Maintenance")) {
                count++;
            }
        }
        return count;
    }

    public double getOccupancyRate() {
        int occupied = getOccupiedRoomsCount();
        int total = rooms.size();
        return (double) occupied / total * 100;
    }
}
