import java.util.LinkedList;
import java.util.Queue;

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}


class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Added booking request for " + reservation.getGuestName());
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}


public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Booking Request Queue");

        BookingRequestQueue queue = new BookingRequestQueue();

        Reservation r1=new Reservation("Alice", "Deluxe");
        Reservation r2=new Reservation("Bob", "Standard");
        Reservation r3=new Reservation("Charlie", "Suite");

        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        System.out.println("\n=== Processing Booking Requests (FIFO) ===");

        while (queue.hasPendingRequests()) {
            Reservation request = queue.getNextRequest();
            System.out.println("Processing -> " + request);
        }

        System.out.println("\nAll booking requests processed.");
    }
}
