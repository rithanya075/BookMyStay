import java.util.*;

/**
 * UC5: Booking Request Queue using FIFO
 *
 * Demonstrates how booking requests are stored
 * and processed in arrival order without modifying inventory.
 *
 * @author Rithanya
 * @version 1.0
 */

// Abstract Room
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Concrete Rooms
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// Reservation class (NEW 🔥)
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

    public void display() {
        System.out.println("Guest: " + guestName + " requested " + roomType);
    }
}

// Booking Queue (NEW 🔥)
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added to queue");
    }

    // Display queue
    public void displayQueue() {
        System.out.println("\n=== Booking Requests Queue ===");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        // Guests submit requests
        bookingQueue.addRequest(new Reservation("Rithanya", "Single Room"));
        bookingQueue.addRequest(new Reservation("Arun", "Double Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite Room"));

        // Show queue (FIFO order)
        bookingQueue.displayQueue();
    }
}