
import java.util.HashMap;
import java.util.Map;

class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

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

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

class SearchService {
    private RoomInventory inventory;
    private HashMap<String, Room> roomData;

    public SearchService(RoomInventory inventory, HashMap<String, Room> roomData) {
        this.inventory = inventory;
        this.roomData = roomData;
    }

    public void searchAvailableRooms() {
        Map<String, Integer> availability = inventory.getAllAvailability();

        System.out.println("Available Rooms:");

        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();

            if (count > 0 && roomData.containsKey(type)) {
                Room room = roomData.get(type);
                System.out.println(
                        room.getType() + " | Price: " + room.getPrice() +
                                " | Amenities: " + room.getAmenities() +
                                " | Available: " + count
                );
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 0);
        inventory.addRoomType("Suite", 2);

        HashMap<String, Room> roomData = new HashMap<>();

        roomData.put("Single", new Room("Single", 1000, "AC, WiFi"));
        roomData.put("Double", new Room("Double", 2000, "AC, WiFi, TV"));
        roomData.put("Suite", new Room("Suite", 5000, "AC, WiFi, TV, Mini Bar"));

        SearchService searchService = new SearchService(inventory, roomData);

        searchService.searchAvailableRooms();
    }
}

