import java.util.*;

/**
 * UC11: Concurrent Booking Simulation
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

// Shared Inventory (CRITICAL RESOURCE)
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 1); // only 1 room → conflict demo
    }

    // 🔥 synchronized → only one thread enters at a time
    public synchronized boolean bookRoom(String type) {

        if (inventory.getOrDefault(type, 0) > 0) {

            System.out.println(Thread.currentThread().getName()
                    + " booking...");

            // simulate delay (race condition)
            try { Thread.sleep(100); } catch (Exception e) {}

            inventory.put(type, inventory.get(type) - 1);

            System.out.println(Thread.currentThread().getName()
                    + " SUCCESS");

            return true;
        }

        System.out.println(Thread.currentThread().getName()
                + " FAILED (No rooms)");

        return false;
    }
}

// Booking Task (Thread)
class BookingTask implements Runnable {

    private Reservation reservation;
    private RoomInventory inventory;

    public BookingTask(Reservation r, RoomInventory inventory) {
        this.reservation = r;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        inventory.bookRoom(reservation.getRoomType());
    }
}

// MAIN
public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // 2 users → 1 room → conflict
        Reservation r1 = new Reservation("Rithanya", "Single");
        Reservation r2 = new Reservation("Arun", "Single");

        Thread t1 = new Thread(new BookingTask(r1, inventory), "User-1");
        Thread t2 = new Thread(new BookingTask(r2, inventory), "User-2");

        t1.start();
        t2.start();
    }
}