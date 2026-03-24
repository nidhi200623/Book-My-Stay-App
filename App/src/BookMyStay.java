import java.util.*;

// Simple Reservation class (needed to run the program)
class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName +
                ", Room: " + roomType +
                ", Nights: " + nights;
    }
}

// BookingHistory class
class BookingHistory {

    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

// BookingReportService class
class BookingReportService {

    public void generateReport(BookingHistory history) {
        List<Reservation> reservations = history.getConfirmedReservations();

        System.out.println("\n===== BOOKING REPORT =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        int totalBookings = reservations.size();
        int totalNights = 0;

        for (Reservation r : reservations) {
            System.out.println(r);
            totalNights += r.getNights();
        }

        System.out.println("\n----- Summary -----");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Nights Booked: " + totalNights);
    }
}

// Main class
public class BookMyStay {

    public static void main(String[] args) {

        // Create booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("Alice", "Single", 3);
        Reservation r2 = new Reservation("Bob", "Double", 2);
        Reservation r3 = new Reservation("Charlie", "Suite", 5);

        // Add to booking history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}