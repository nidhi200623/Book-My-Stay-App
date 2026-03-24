import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;

class Reservation {
    private String guestName;
    private String roomType;
    private String allocatedRoomId;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public void setAllocatedRoomId(String id) { this.allocatedRoomId = id; }
    public String getAllocatedRoomId() { return allocatedRoomId; }
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation res) {
        queue.add(res);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public int getCount(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void updateCount(String type, int count) {
        inventory.put(type, count);
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        inventory.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}

class RoomAllocationService {
    public void allocateRoom(Reservation res, RoomInventory inventory) {
        String type = res.getRoomType();
        int available = inventory.getCount(type);
        if (available > 0) {
            inventory.updateCount(type, available - 1);
            res.setAllocatedRoomId(type + "-" + (available));
            System.out.println("Booking confirmed for Guest: " + res.getGuestName() + ", Room ID: " + res.getAllocatedRoomId());
        }
    }
}

class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation;

            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            if (reservation != null) {
                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }

            try { Thread.sleep(10); } catch (InterruptedException e) { break; }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        inventory.displayInventory();
    }
}