import java.io.*;
import java.util.*;

/**
 * UC12: Data Persistence & System Recovery
 */

// Reservation (Serializable)
class Reservation implements Serializable {
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

// Inventory (Serializable)
class RoomInventory implements Serializable {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

// 🔥 Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "data.ser";

    // SAVE
    public void save(RoomInventory inventory, List<Reservation> bookings) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(inventory);
            out.writeObject(bookings);
            System.out.println("Data saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving data!");
        }
    }

    // LOAD
    public Object[] load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory inventory = (RoomInventory) in.readObject();
            List<Reservation> bookings = (List<Reservation>) in.readObject();

            System.out.println("Data loaded successfully!");
            return new Object[]{inventory, bookings};

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

// MAIN
public class BookMyStay {

    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        // Try loading old data
        Object[] data = service.load();

        RoomInventory inventory;
        List<Reservation> bookings;

        if (data != null) {
            inventory = (RoomInventory) data[0];
            bookings = (List<Reservation>) data[1];
        } else {
            inventory = new RoomInventory();
            bookings = new ArrayList<>();
        }

        // Add new booking
        bookings.add(new Reservation("Rithanya", "Single"));

        // Display current state
        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r.getGuestName() + " → " + r.getRoomType());
        }

        // Save before exit
        service.save(inventory, bookings);
    }
}