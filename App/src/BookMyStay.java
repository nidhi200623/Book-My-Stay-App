import java.util.*;

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
    }

    public void restoreInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public int getAvailableRooms(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class CancellationService {
    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (reservationRoomTypeMap.containsKey(reservationId)) {
            String roomType = reservationRoomTypeMap.get(reservationId);

            inventory.restoreInventory(roomType);
            releasedRoomIds.push(reservationId);
            reservationRoomTypeMap.remove(reservationId);

            System.out.println("Booking Cancellation");
            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        } else {
            System.out.println("Error: Reservation ID not found.");
        }
    }

    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No history found.");
        } else {
            for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
                System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        cancellationService.registerBooking("Single-1", "Single");

        cancellationService.cancelBooking("Single-1", inventory);

        cancellationService.showRollbackHistory();

        System.out.println("\nUpdated Single Room Availability: " + inventory.getAvailableRooms("Single"));
    }
}