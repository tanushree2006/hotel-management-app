package owner.utils;


import java.awt.Color;

public class Constants {

    // Application constants
    public static final String APP_NAME = "MiraVelle Hotel Management System";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_BUILD = "2023.04.22.1";

    // Color scheme
    public static final Color PRIMARY_COLOR = new Color(59, 130, 246);
    public static final Color SECONDARY_COLOR = new Color(107, 114, 128);
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    public static final Color WARNING_COLOR = new Color(234, 179, 8);
    public static final Color DANGER_COLOR = new Color(239, 68, 68);
    public static final Color LIGHT_GRAY = new Color(229, 231, 235);
    public static final Color BACKGROUND_COLOR = Color.WHITE;

    // Status constants
    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_INACTIVE = "Inactive";
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String STATUS_UPCOMING = "Upcoming";

    // Room status constants
    public static final String ROOM_AVAILABLE = "Available";
    public static final String ROOM_OCCUPIED = "Occupied";
    public static final String ROOM_MAINTENANCE = "Maintenance";
    public static final String ROOM_CLEANING = "Cleaning";

    // Staff status constants
    public static final String STAFF_ON_DUTY = "On Duty";
    public static final String STAFF_OFF_DUTY = "Off Duty";
    public static final String STAFF_ON_LEAVE = "On Leave";

    // Lost & Found status constants
    public static final String ITEM_UNCLAIMED = "Unclaimed";
    public static final String ITEM_CLAIMED_PENDING = "Claimed (Pending)";
    public static final String ITEM_CLAIMED = "Claimed";

    // Claim status constants
    public static final String CLAIM_PENDING = "Pending";
    public static final String CLAIM_APPROVED = "Approved";
    public static final String CLAIM_REJECTED = "Rejected";

    // Date formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";

    // File paths
    public static final String IMAGE_PATH = "resources/images/";
    public static final String REPORT_PATH = "resources/reports/";
    public static final String BACKUP_PATH = "resources/backups/";

    // Default values
    public static final String DEFAULT_CURRENCY = "USD";
    public static final double DEFAULT_TAX_RATE = 8.5;

    // Table column names
    public static final String[] ROOM_COLUMNS = {"Room No.", "Type", "Status", "Price", "Last Cleaned", "Actions"};
    public static final String[] BOOKING_COLUMNS = {"Booking ID", "Customer", "Room", "Check-in", "Check-out", "Status", "Amount", "Actions"};
    public static final String[] STAFF_COLUMNS = {"ID", "Name", "Position", "Contact", "Status", "Last Shift", "Actions"};
    public static final String[] LOST_ITEM_COLUMNS = {"ID", "Description", "Room", "Found Date", "Status", "Claims", "Actions"};
}