
import java.util.HashMap;
import java.util.Map;

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateAvailability(String type, int change) {
        if (inventory.containsKey(type)) {
            int current = inventory.get(type);
            int updated = current + change;

            if (updated >= 0) {
                inventory.put(type, updated);
            } else {
                System.out.println("Not enough rooms available for: " + type);
            }
        } else {
            System.out.println("Room type does not exist: " + type);
        }
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 5);
        inventory.addRoomType("Suite", 2);

        inventory.displayInventory();

        System.out.println();

        System.out.println("Available Single Rooms: " + inventory.getAvailability("Single"));

        inventory.updateAvailability("Single", -2);
        inventory.updateAvailability("Double", +1);

        System.out.println();

        inventory.displayInventory();
    }
}

