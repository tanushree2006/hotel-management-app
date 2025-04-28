package owner.controllers;



import owner.models.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportController {
    private RoomController roomController;
    private BookingController bookingController;
    private StaffController staffController;
    private LostFoundController lostFoundController;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ReportController(RoomController roomController, BookingController bookingController,
                            StaffController staffController, LostFoundController lostFoundController) {
        this.roomController = roomController;
        this.bookingController = bookingController;
        this.staffController = staffController;
        this.lostFoundController = lostFoundController;
    }

    // Revenue Reports

    public double getTotalRevenue() {
        return bookingController.getTotalRevenue();
    }

    public Map<String, Double> getRevenueByRoomType() {
        Map<String, Double> revenueByType = new HashMap<>();

        // Initialize with all room types
        for (Room room : roomController.getAllRooms()) {
            String type = room.getType();
            if (!revenueByType.containsKey(type)) {
                revenueByType.put(type, 0.0);
            }
        }

        // Calculate revenue for each type
        for (Booking booking : bookingController.getAllBookings()) {
            if (booking.getStatus().equals("Active") || booking.getStatus().equals("Completed")) {
                Room room = roomController.getRoomByNumber(booking.getRoomNumber());
                if (room != null) {
                    String type = room.getType();
                    double currentRevenue = revenueByType.get(type);

                    // Extract amount from booking
                    String amountStr = booking.getTotalAmount().replace("$", "");
                    try {
                        double amount = Double.parseDouble(amountStr);
                        revenueByType.put(type, currentRevenue + amount);
                    } catch (NumberFormatException e) {
                        // Skip if not a valid number
                    }
                }
            }
        }

        return revenueByType;
    }

    public Map<String, Double> getRevenueByDateRange(String startDateStr, String endDateStr) {
        Map<String, Double> revenueByDate = new HashMap<>();

        try {
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            // Calculate revenue for each booking in the date range
            for (Booking booking : bookingController.getAllBookings()) {
                if (booking.getStatus().equals("Active") || booking.getStatus().equals("Completed")) {
                    try {
                        Date checkInDate = dateFormat.parse(booking.getCheckIn());
                        Date checkOutDate = dateFormat.parse(booking.getCheckOut());

                        // Check if booking falls within the date range
                        if ((checkInDate.after(startDate) || checkInDate.equals(startDate)) &&
                                (checkOutDate.before(endDate) || checkOutDate.equals(endDate))) {

                            // Extract amount from booking
                            String amountStr = booking.getTotalAmount().replace("$", "");
                            try {
                                double amount = Double.parseDouble(amountStr);

                                // Add to the appropriate date
                                String dateKey = booking.getCheckIn();
                                if (revenueByDate.containsKey(dateKey)) {
                                    revenueByDate.put(dateKey, revenueByDate.get(dateKey) + amount);
                                } else {
                                    revenueByDate.put(dateKey, amount);
                                }
                            } catch (NumberFormatException e) {
                                // Skip if not a valid number
                            }
                        }
                    } catch (ParseException e) {
                        // Skip if dates can't be parsed
                    }
                }
            }
        } catch (ParseException e) {
            // Return empty map if date range is invalid
        }

        return revenueByDate;
    }

    // Occupancy Reports

    public double getOccupancyRate() {
        return roomController.getOccupancyRate();
    }

    public Map<String, Double> getOccupancyByRoomType() {
        Map<String, Double> occupancyByType = new HashMap<>();
        Map<String, Integer> totalByType = new HashMap<>();
        Map<String, Integer> occupiedByType = new HashMap<>();

        // Count total rooms and occupied rooms by type
        for (Room room : roomController.getAllRooms()) {
            String type = room.getType();

            // Update total count
            if (totalByType.containsKey(type)) {
                totalByType.put(type, totalByType.get(type) + 1);
            } else {
                totalByType.put(type, 1);
                occupiedByType.put(type, 0);
            }

            // Update occupied count
            if (room.getStatus().equals("Occupied")) {
                occupiedByType.put(type, occupiedByType.get(type) + 1);
            }
        }

        // Calculate occupancy rate for each type
        for (String type : totalByType.keySet()) {
            int total = totalByType.get(type);
            int occupied = occupiedByType.get(type);
            double rate = (double) occupied / total * 100;
            occupancyByType.put(type, rate);
        }

        return occupancyByType;
    }

    public Map<String, Integer> getRoomStatusCounts() {
        Map<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("Available", roomController.getAvailableRoomsCount());
        statusCounts.put("Occupied", roomController.getOccupiedRoomsCount());
        statusCounts.put("Maintenance", roomController.getMaintenanceRoomsCount());
        return statusCounts;
    }

    // Staff Reports

    public Map<String, Integer> getStaffStatusCounts() {
        Map<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("On Duty", staffController.getOnDutyStaffCount());
        statusCounts.put("Off Duty", staffController.getOffDutyStaffCount());
        statusCounts.put("On Leave", staffController.getOnLeaveStaffCount());
        return statusCounts;
    }

    public Map<String, Integer> getStaffByDepartmentCounts() {
        Map<String, Integer> departmentCounts = new HashMap<>();
        String[] departments = {"Front Desk", "Housekeeping", "Food & Beverage", "Maintenance", "Security"};

        for (String department : departments) {
            int count = staffController.getStaffByDepartment(department).size();
            departmentCounts.put(department, count);
        }

        return departmentCounts;
    }

    // Lost & Found Reports

    public Map<String, Integer> getLostItemStatusCounts() {
        Map<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("Unclaimed", lostFoundController.getUnclaimedItemsCount());
        statusCounts.put("Claimed", lostFoundController.getClaimedItemsCount());
        statusCounts.put("Pending", lostFoundController.getPendingClaimsCount());
        return statusCounts;
    }

    public double getClaimRate() {
        int totalItems = lostFoundController.getAllLostItems().size();
        int claimedItems = lostFoundController.getClaimedItemsCount() + lostFoundController.getPendingClaimsCount();

        if (totalItems > 0) {
            return (double) claimedItems / totalItems * 100;
        } else {
            return 0;
        }
    }

    // Generate Reports

    public String generateRevenueReport(String startDate, String endDate) {
        StringBuilder report = new StringBuilder();
        report.append("REVENUE REPORT\n");
        report.append("Period: ").append(startDate).append(" to ").append(endDate).append("\n\n");

        report.append("Total Revenue: $").append(String.format("%.2f", getTotalRevenue())).append("\n\n");

        report.append("Revenue by Room Type:\n");
        Map<String, Double> revenueByType = getRevenueByRoomType();
        for (Map.Entry<String, Double> entry : revenueByType.entrySet()) {
            report.append(entry.getKey()).append(": $").append(String.format("%.2f", entry.getValue())).append("\n");
        }

        report.append("\nRevenue by Date:\n");
        Map<String, Double> revenueByDate = getRevenueByDateRange(startDate, endDate);
        for (Map.Entry<String, Double> entry : revenueByDate.entrySet()) {
            report.append(entry.getKey()).append(": $").append(String.format("%.2f", entry.getValue())).append("\n");
        }

        return report.toString();
    }

    public String generateOccupancyReport() {
        StringBuilder report = new StringBuilder();
        report.append("OCCUPANCY REPORT\n\n");

        report.append("Overall Occupancy Rate: ").append(String.format("%.2f", getOccupancyRate())).append("%\n\n");

        report.append("Occupancy by Room Type:\n");
        Map<String, Double> occupancyByType = getOccupancyByRoomType();
        for (Map.Entry<String, Double> entry : occupancyByType.entrySet()) {
            report.append(entry.getKey()).append(": ").append(String.format("%.2f", entry.getValue())).append("%\n");
        }

        report.append("\nRoom Status Counts:\n");
        Map<String, Integer> statusCounts = getRoomStatusCounts();
        for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
            report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return report.toString();
    }

    public String generateStaffReport() {
        StringBuilder report = new StringBuilder();
        report.append("STAFF REPORT\n\n");

        report.append("Total Staff: ").append(staffController.getAllStaff().size()).append("\n\n");

        report.append("Staff by Status:\n");
        Map<String, Integer> statusCounts = getStaffStatusCounts();
        for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
            report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        report.append("\nStaff by Department:\n");
        Map<String, Integer> departmentCounts = getStaffByDepartmentCounts();
        for (Map.Entry<String, Integer> entry : departmentCounts.entrySet()) {
            report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return report.toString();
    }

    public String generateLostFoundReport() {
        StringBuilder report = new StringBuilder();
        report.append("LOST & FOUND REPORT\n\n");

        report.append("Total Items: ").append(lostFoundController.getAllLostItems().size()).append("\n");
        report.append("Claim Rate: ").append(String.format("%.2f", getClaimRate())).append("%\n\n");

        report.append("Items by Status:\n");
        Map<String, Integer> statusCounts = getLostItemStatusCounts();
        for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
            report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        report.append("\nRecent Claims:\n");
        List<Claim> allClaims = lostFoundController.getAllClaims();
        int count = 0;
        for (Claim claim : allClaims) {
            if (count < 5) {  // Show only 5 most recent claims
                report.append("ID: ").append(claim.getId())
                        .append(", Item: ").append(claim.getItemId())
                        .append(", Traveler: ").append(claim.getTravelerName())
                        .append(", Status: ").append(claim.getStatus())
                        .append("\n");
                count++;
            }
        }

        return report.toString();
    }
}
