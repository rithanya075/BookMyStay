import java.util.*;

/**
 * UC6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates safe allocation of rooms using Queue, HashMap, and Set.
 * Ensures no double booking and maintains inventory consistency.
 *
 * @author Rithanya
 * @version 1.0
 */

// Reservation
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
}

// Booking Queue
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// 🔥 Booking Service (CORE LOGIC)
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").toUpperCase() + "_" + UUID.randomUUID().toString().substring(0, 5);
    }

    public void processBookings(BookingQueue queue, RoomInventory inventory) {

        System.out.println("\n=== Processing Bookings ===\n");

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                String roomId;

                // Ensure uniqueness
                do {
                    roomId = generateRoomId(type);
                } while (allocatedRoomIds.contains(roomId));

                allocatedRoomIds.add(roomId);

                // Map room type → assigned IDs
                roomAllocations.putIfAbsent(type, new HashSet<>());
                roomAllocations.get(type).add(roomId);

                // Update inventory immediately
                inventory.reduceRoom(type);

                System.out.println("Booking Confirmed for " + r.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId + "\n");

            } else {
                System.out.println("Booking Failed for " + r.getGuestName() + " (No availability)\n");
            }
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Add requests (FIFO)
        queue.addRequest(new Reservation("Rithanya", "Single Room"));
        queue.addRequest(new Reservation("Arun", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Single Room")); // should fail
        queue.addRequest(new Reservation("Kiran", "Suite Room"));

        // Process bookings
        service.processBookings(queue, inventory);
    }
}