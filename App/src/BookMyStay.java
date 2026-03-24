import java.util.*;

public class BookMyStay {

    static class RoomInventory {
        private Map<String, Integer> availableCounts = new HashMap<>();

        public void addRoomType(String type, int total) {
            availableCounts.put(type, total);
        }

        public boolean hasStock(String type) {
            return availableCounts.getOrDefault(type, 0) > 0;
        }

        public void decrement(String type) {
            availableCounts.put(type, availableCounts.get(type) - 1);
        }

        public int getStock(String type) {
            return availableCounts.getOrDefault(type, 0);
        }
    }

    static class Reservation {
        private String guestName;
        private String roomType;
        private String assignedRoomId;
        private boolean isConfirmed = false;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() { return guestName; }
        public String getRoomType() { return roomType; }
        public void confirm(String roomId) {
            this.assignedRoomId = roomId;
            this.isConfirmed = true;
        }

        @Override
        public String toString() {
            return String.format("Guest: %-10s | Type: %-8s | Status: %-10s | Room ID: %s",
                    guestName, roomType, (isConfirmed ? "CONFIRMED" : "FAILED"),
                    (assignedRoomId != null ? assignedRoomId : "N/A"));
        }
    }

    static class RoomAllocationService {
        private Set<String> allAllocatedIds = new HashSet<>();
        private Map<String, Set<String>> typeToIdsMap = new HashMap<>();

        public void processBooking(Reservation res, RoomInventory inventory) {
            String type = res.getRoomType();

            if (inventory.hasStock(type)) {
                int nextNum = typeToIdsMap.getOrDefault(type, new HashSet<>()).size() + 1;
                String uniqueId = type + "-" + nextNum;

                while (allAllocatedIds.contains(uniqueId)) {
                    nextNum++;
                    uniqueId = type + "-" + nextNum;
                }

                allAllocatedIds.add(uniqueId);
                typeToIdsMap.computeIfAbsent(type, k -> new HashSet<>()).add(uniqueId);
                inventory.decrement(type);
                res.confirm(uniqueId);
            }
        }
    }

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Deluxe", 2);
        inventory.addRoomType("Suite", 1);

        RoomAllocationService allocationService = new RoomAllocationService();

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Deluxe"));
        bookingQueue.add(new Reservation("Bob", "Deluxe"));
        bookingQueue.add(new Reservation("Charlie", "Suite"));
        bookingQueue.add(new Reservation("David", "Deluxe"));
        bookingQueue.add(new Reservation("Eve", "Suite"));

        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.poll();
            allocationService.processBooking(request, inventory);
            System.out.println(request);
        }
    }
}
