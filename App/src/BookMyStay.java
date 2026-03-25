import java.util.*;

/**
 * UC10: Booking Cancellation & Inventory Rollback
 */

// Reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void increase(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }
}

// Booking Service (stores confirmed bookings)
class BookingService {
    Map<String, Reservation> confirmedBookings = new HashMap<>();
    Map<String, String> roomAllocations = new HashMap<>(); // reservationId → roomId

    public void confirmBooking(Reservation r, String roomId, RoomInventory inventory) {
        confirmedBookings.put(r.getReservationId(), r);
        roomAllocations.put(r.getReservationId(), roomId);
        inventory.decrease(r.getRoomType());

        System.out.println("Booking Confirmed: " + r.getReservationId());
    }
}

// 🔥 Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingService bookingService,
                              RoomInventory inventory) {

        // Validate existence
        if (!bookingService.confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid Reservation ID");
            return;
        }

        Reservation r = bookingService.confirmedBookings.get(reservationId);
        String roomId = bookingService.roomAllocations.get(reservationId);

        // Push to rollback stack (LIFO)
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increase(r.getRoomType());

        // Remove booking
        bookingService.confirmedBookings.remove(reservationId);
        bookingService.roomAllocations.remove(reservationId);

        System.out.println("Booking Cancelled: " + reservationId);
        System.out.println("Rolled back Room ID: " + rollbackStack.peek());
    }
}

// MAIN
public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService();
        CancellationService cancelService = new CancellationService();

        // Confirm bookings
        Reservation r1 = new Reservation("RES1", "Rithanya", "Single");
        bookingService.confirmBooking(r1, "S101", inventory);

        // Cancel booking
        cancelService.cancelBooking("RES1", bookingService, inventory);

        // Invalid cancellation
        cancelService.cancelBooking("RES999", bookingService, inventory);
    }
}