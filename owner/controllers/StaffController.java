package owner.controllers;


import owner.models.Staff;
import java.util.ArrayList;
import java.util.List;

public class StaffController {
    private List<Staff> staffList;

    public StaffController() {
        this.staffList = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample data for testing
        staffList.add(new Staff("S001", "Michael Brown", "Receptionist", "555-0101", "michael.brown@miravelle.com", "On Duty", "2022-01-15", "2023-04-21"));
        staffList.add(new Staff("S002", "Sarah Wilson", "Housekeeper", "555-0102", "sarah.wilson@miravelle.com", "On Duty", "2022-02-10", "2023-04-21"));
        staffList.add(new Staff("S003", "David Lee", "Maintenance", "555-0103", "david.lee@miravelle.com", "Off Duty", "2022-03-05", "2023-04-20"));
        staffList.add(new Staff("S004", "Emily Johnson", "Manager", "555-0104", "emily.johnson@miravelle.com", "On Duty", "2021-11-10", "2023-04-21"));
        staffList.add(new Staff("S005", "James Smith", "Chef", "555-0105", "james.smith@miravelle.com", "On Duty", "2022-01-20", "2023-04-21"));
        staffList.add(new Staff("S006", "Jessica Davis", "Receptionist", "555-0106", "jessica.davis@miravelle.com", "Off Duty", "2022-04-15", "2023-04-19"));
        staffList.add(new Staff("S007", "Robert Miller", "Security", "555-0107", "robert.miller@miravelle.com", "On Duty", "2022-02-28", "2023-04-21"));
        staffList.add(new Staff("S008", "Jennifer Wilson", "Housekeeper", "555-0108", "jennifer.wilson@miravelle.com", "On Leave", "2022-03-10", "2023-04-18"));
    }

    public List<Staff> getAllStaff() {
        return staffList;
    }

    public Staff getStaffById(String id) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id)) {
                return staff;
            }
        }
        return null;
    }

    public List<Staff> getStaffByPosition(String position) {
        List<Staff> result = new ArrayList<>();
        for (Staff staff : staffList) {
            if (staff.getPosition().equals(position)) {
                result.add(staff);
            }
        }
        return result;
    }

    public List<Staff> getStaffByStatus(String status) {
        List<Staff> result = new ArrayList<>();
        for (Staff staff : staffList) {
            if (staff.getStatus().equals(status)) {
                result.add(staff);
            }
        }
        return result;
    }

    public List<Staff> searchStaff(String query) {
        List<Staff> result = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Staff staff : staffList) {
            if (staff.getId().toLowerCase().contains(lowerQuery) ||
                    staff.getName().toLowerCase().contains(lowerQuery) ||
                    staff.getPosition().toLowerCase().contains(lowerQuery) ||
                    staff.getContactNumber().toLowerCase().contains(lowerQuery) ||
                    staff.getEmail().toLowerCase().contains(lowerQuery)) {
                result.add(staff);
            }
        }

        return result;
    }

    public void addStaff(Staff staff) {
        staffList.add(staff);
    }

    public boolean updateStaff(Staff staff) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId().equals(staff.getId())) {
                staffList.set(i, staff);
                return true;
            }
        }
        return false;
    }

    public boolean deleteStaff(String id) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId().equals(id)) {
                staffList.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getOnDutyStaffCount() {
        int count = 0;
        for (Staff staff : staffList) {
            if (staff.getStatus().equals("On Duty")) {
                count++;
            }
        }
        return count;
    }

    public int getOffDutyStaffCount() {
        int count = 0;
        for (Staff staff : staffList) {
            if (staff.getStatus().equals("Off Duty")) {
                count++;
            }
        }
        return count;
    }

    public int getOnLeaveStaffCount() {
        int count = 0;
        for (Staff staff : staffList) {
            if (staff.getStatus().equals("On Leave")) {
                count++;
            }
        }
        return count;
    }

    public List<Staff> getStaffByDepartment(String department) {
        List<Staff> result = new ArrayList<>();

        // Map positions to departments
        for (Staff staff : staffList) {
            String position = staff.getPosition();
            String staffDepartment = "";

            if (position.equals("Receptionist") || position.equals("Manager")) {
                staffDepartment = "Front Desk";
            } else if (position.equals("Housekeeper")) {
                staffDepartment = "Housekeeping";
            } else if (position.equals("Chef")) {
                staffDepartment = "Food & Beverage";
            } else if (position.equals("Maintenance")) {
                staffDepartment = "Maintenance";
            } else if (position.equals("Security")) {
                staffDepartment = "Security";
            }

            if (staffDepartment.equals(department)) {
                result.add(staff);
            }
        }

        return result;
    }
}