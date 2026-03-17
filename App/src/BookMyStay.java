import java.util.HashMap;
import java.util.Map;

/**
 * UC4: Room Search & Availability Check (Read-Only)
 *
 * Demonstrates safe read-only access to inventory without modifying state.
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

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: " + price);
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

// Inventory (same as UC3)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // example unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// 🔥 Search Service (NEW for UC4)
class RoomSearchService {

    public void searchRooms(Room[] rooms, RoomInventory inventory) {

        System.out.println("\n=== Available Rooms ===\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check → only show available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        // Room objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Inventory (state holder)
        RoomInventory inventory = new RoomInventory();

        // Search Service (read-only)
        RoomSearchService searchService = new RoomSearchService();

        // Guest searches rooms
        searchService.searchRooms(rooms, inventory);
    }
}