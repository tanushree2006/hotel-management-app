package owner.controllers;



import owner.models.Booking;
import owner.models.Room;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
    private List<Booking> bookings;
    private RoomController roomController;

    public BookingController() {
        this.bookings = new ArrayList<>();
        this.roomController = roomController;
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample data for testing
        bookings.add(new Booking("B001", "John Smith", "102", "2023-04-18", "2023-04-23", "Active", "$900"));
        bookings.add(new Booking("B002", "Jane Doe", "301", "2023-04-20", "2023-04-25", "Active", "$900"));
        bookings.add(new Booking("B003", "Robert Johnson", "201", "2023-04-15", "2023-04-19", "Completed", "$1000"));
        bookings.add(new Booking("B004", "Emily Wilson", "105", "2023-04-22", "2023-04-24", "Upcoming", "$240"));
        bookings.add(new Booking("B005", "Michael Brown", "302", "2023-04-10", "2023-04-15", "Completed", "$1250"));
        bookings.add(new Booking("B006", "Sarah Johnson", "401", "2023-04-25", "2023-04-30", "Upcoming", "$2500"));
        bookings.add(new Booking("B007", "David Lee", "202", "2023-04-12", "2023-04-14", "Cancelled", "$240"));
        bookings.add(new Booking("B008", "Lisa Anderson", "303", "2023-04-19", "2023-04-22", "Active", "$540"));
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    public Booking getBookingById(String id) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(id)) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getBookingsByCustomer(String customerName) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomerName().equals(customerName)) {
                result.add(booking);
            }
        }
        return result;
    }

    public List<Booking> getBookingsByRoom(String roomNumber) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getRoomNumber().equals(roomNumber)) {
                result.add(booking);
            }
        }
        return result;
    }

    public List<Booking> getBookingsByStatus(String status) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getStatus().equals(status)) {
                result.add(booking);
            }
        }
        return result;
    }

    public List<Booking> searchBookings(String query) {
        List<Booking> result = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Booking booking : bookings) {
            if (booking.getId().toLowerCase().contains(lowerQuery) ||
                    booking.getCustomerName().toLowerCase().contains(lowerQuery) ||
                    booking.getRoomNumber().toLowerCase().contains(lowerQuery) ||
                    booking.getStatus().toLowerCase().contains(lowerQuery)) {
                result.add(booking);
            }
        }

        return result;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);

        // Update room status if booking is active
        if (booking.getStatus().equals("Active")) {
            Room room = roomController.getRoomByNumber(booking.getRoomNumber());
            if (room != null) {
                room.setStatus("Occupied");
                roomController.updateRoom(room);
            }
        }
    }

    public boolean updateBooking(Booking booking) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equals(booking.getId())) {
                // Check if status changed
                String oldStatus = bookings.get(i).getStatus();
                String newStatus = booking.getStatus();

                bookings.set(i, booking);

                // Update room status if booking status changed
                if (!oldStatus.equals(newStatus)) {
                    Room room = roomController.getRoomByNumber(booking.getRoomNumber());
                    if (room != null) {
                        if (newStatus.equals("Active")) {
                            room.setStatus("Occupied");
                        } else if (oldStatus.equals("Active")) {
                            room.setStatus("Available");
                        }
                        roomController.updateRoom(room);
                    }
                }

                return true;
            }
        }
        return false;
    }

    public boolean deleteBooking(String id) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equals(id)) {
                // Update room status if booking was active
                Booking booking = bookings.get(i);
                if (booking.getStatus().equals("Active")) {
                    Room room = roomController.getRoomByNumber(booking.getRoomNumber());
                    if (room != null) {
                        room.setStatus("Available");
                        roomController.updateRoom(room);
                    }
                }

                bookings.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getActiveBookingsCount() {
        int count = 0;
        for (Booking booking : bookings) {
            if (booking.getStatus().equals("Active")) {
                count++;
            }
        }
        return count;
    }

    public int getUpcomingBookingsCount() {
        int count = 0;
        for (Booking booking : bookings) {
            if (booking.getStatus().equals("Upcoming")) {
                count++;
            }
        }
        return count;
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Booking booking : bookings) {
            if (booking.getStatus().equals("Active") || booking.getStatus().equals("Completed")) {
                // Remove $ sign and convert to double
                String amountStr = booking.getTotalAmount().replace("$", "");
                try {
                    double amount = Double.parseDouble(amountStr);
                    total += amount;
                } catch (NumberFormatException e) {
                    // Skip if not a valid number
                }
            }
        }
        return total;
    }
}
