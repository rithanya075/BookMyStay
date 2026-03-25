import java.util.*;

/**
 import java.util.*;

 /**
 * UC9: Error Handling & Validation
 */

// 🔥 Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
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
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 0);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// 🔥 Validator
class BookingValidator {

    public static void validate(Reservation r, RoomInventory inventory) throws InvalidBookingException {

        // Check room type exists
        if (inventory.getAvailability(r.getRoomType()) == -1) {
            throw new InvalidBookingException("Invalid Room Type!");
        }

        // Check availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room Not Available!");
        }
    }
}

// MAIN
public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Test cases
        Reservation r1 = new Reservation("Rithanya", "Single");  // valid
        Reservation r2 = new Reservation("Arun", "Suite");       // no availability
        Reservation r3 = new Reservation("Priya", "Deluxe");     // invalid type

        processBooking(r1, inventory);
        processBooking(r2, inventory);
        processBooking(r3, inventory);
    }

    // 🔥 Safe Booking Method
    public static void processBooking(Reservation r, RoomInventory inventory) {

        try {
            // Validate first (Fail Fast)
            BookingValidator.validate(r, inventory);

            // If valid → proceed
            inventory.reduceRoom(r.getRoomType());

            System.out.println("Booking Successful for " + r.getGuestName());

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed for " + r.getGuestName() + " : " + e.getMessage());
        }
    }
}