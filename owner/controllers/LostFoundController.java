package owner.controllers;


import owner.models.LostItem;
import owner.models.Claim;
import java.util.ArrayList;
import java.util.List;

public class LostFoundController {
    private List<LostItem> lostItems;

    public LostFoundController() {
        this.lostItems = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample data for testing
        LostItem item1 = new LostItem("L001", "Black leather wallet with initials JD", "102", "wallet.jpg", "2023-04-15", "Claimed (Pending)");
        item1.addClaim(new Claim("C001", "L001", "John Doe", "Lost my wallet during checkout. It has my ID and credit cards.", "2023-04-16", "Pending"));

        LostItem item2 = new LostItem("L002", "Silver watch with brown leather strap", "205", "watch.jpg", "2023-04-17", "Unclaimed");

        LostItem item3 = new LostItem("L003", "Blue iPhone charger", "301", "charger.jpg", "2023-04-18", "Unclaimed");

        LostItem item4 = new LostItem("L004", "Gold earrings with pearl", "402", "earrings.jpg", "2023-04-14", "Claimed");
        item4.addClaim(new Claim("C002", "L004", "Emily Wilson", "I left my earrings in the bathroom. They're gold with pearls.", "2023-04-15", "Approved"));

        LostItem item5 = new LostItem("L005", "Prescription glasses with black frame", "103", "glasses.jpg", "2023-04-19", "Unclaimed");

        lostItems.add(item1);
        lostItems.add(item2);
        lostItems.add(item3);
        lostItems.add(item4);
        lostItems.add(item5);
    }

    public List<LostItem> getAllLostItems() {
        return lostItems;
    }

    public LostItem getLostItemById(String id) {
        for (LostItem item : lostItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<LostItem> getLostItemsByRoom(String roomNumber) {
        List<LostItem> result = new ArrayList<>();
        for (LostItem item : lostItems) {
            if (item.getRoomNumber().equals(roomNumber)) {
                result.add(item);
            }
        }
        return result;
    }

    public List<LostItem> getLostItemsByStatus(String status) {
        List<LostItem> result = new ArrayList<>();
        for (LostItem item : lostItems) {
            if (item.getStatus().equals(status)) {
                result.add(item);
            }
        }
        return result;
    }

    public List<LostItem> searchLostItems(String query) {
        List<LostItem> result = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (LostItem item : lostItems) {
            if (item.getId().toLowerCase().contains(lowerQuery) ||
                    item.getDescription().toLowerCase().contains(lowerQuery) ||
                    item.getRoomNumber().toLowerCase().contains(lowerQuery)) {
                result.add(item);
            }
        }

        return result;
    }

    public void addLostItem(LostItem item) {
        lostItems.add(item);
    }

    public boolean updateLostItem(LostItem item) {
        for (int i = 0; i < lostItems.size(); i++) {
            if (lostItems.get(i).getId().equals(item.getId())) {
                lostItems.set(i, item);
                return true;
            }
        }
        return false;
    }

    public boolean deleteLostItem(String id) {
        for (int i = 0; i < lostItems.size(); i++) {
            if (lostItems.get(i).getId().equals(id)) {
                lostItems.remove(i);
                return true;
            }
        }
        return false;
    }

    public Claim getClaimById(String claimId) {
        for (LostItem item : lostItems) {
            for (Claim claim : item.getClaims()) {
                if (claim.getId().equals(claimId)) {
                    return claim;
                }
            }
        }
        return null;
    }

    public boolean addClaim(String itemId, Claim claim) {
        LostItem item = getLostItemById(itemId);
        if (item != null) {
            item.addClaim(claim);

            // Update item status if it was unclaimed
            if (item.getStatus().equals("Unclaimed")) {
                item.setStatus("Claimed (Pending)");
            }

            return true;
        }
        return false;
    }

    public boolean updateClaimStatus(String claimId, String newStatus) {
        for (LostItem item : lostItems) {
            for (Claim claim : item.getClaims()) {
                if (claim.getId().equals(claimId)) {
                    claim.setStatus(newStatus);

                    // Update item status if claim is approved
                    if (newStatus.equals("Approved")) {
                        item.setStatus("Claimed");
                    }

                    return true;
                }
            }
        }
        return false;
    }

    public int getUnclaimedItemsCount() {
        int count = 0;
        for (LostItem item : lostItems) {
            if (item.getStatus().equals("Unclaimed")) {
                count++;
            }
        }
        return count;
    }

    public int getClaimedItemsCount() {
        int count = 0;
        for (LostItem item : lostItems) {
            if (item.getStatus().equals("Claimed")) {
                count++;
            }
        }
        return count;
    }

    public int getPendingClaimsCount() {
        int count = 0;
        for (LostItem item : lostItems) {
            if (item.getStatus().equals("Claimed (Pending)")) {
                count++;
            }
        }
        return count;
    }

    public List<Claim> getAllClaims() {
        List<Claim> allClaims = new ArrayList<>();
        for (LostItem item : lostItems) {
            allClaims.addAll(item.getClaims());
        }
        return allClaims;
    }
}
