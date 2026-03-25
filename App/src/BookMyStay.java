import java.util.*;

/**
 * UC8: Booking History & Reporting
 */

// Reservation
class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

    public Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// 🔥 Booking History (stores data)
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// 🔥 Reporting Service (only reads data)
class BookingReportService {

    public void generateReport(List<Reservation> list) {

        System.out.println("\n=== Booking Report ===");

        for (Reservation r : list) {
            System.out.println("ID: " + r.getReservationId()
                    + " | Guest: " + r.getGuestName()
                    + " | Room: " + r.getRoomType());
        }

        System.out.println("\nTotal Bookings: " + list.size());
    }
}

// MAIN
public class BookMyStay {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Assume these are confirmed bookings (from UC6)
        Reservation r1 = new Reservation("Rithanya", "Single", "RES1");
        Reservation r2 = new Reservation("Arun", "Double", "RES2");
        Reservation r3 = new Reservation("Priya", "Suite", "RES3");

        // Store in history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Generate report
        reportService.generateReport(history.getAllReservations());
    }
}