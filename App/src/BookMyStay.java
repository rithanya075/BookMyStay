import java.util.*;

/**
 * UC7: Add-On Service Selection
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
}

// Service
class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Manager
class AddOnServiceManager {
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public void displayServices(String reservationId) {
        System.out.println("\nServices for " + reservationId);

        List<Service> list = serviceMap.get(reservationId);

        if (list != null) {
            for (Service s : list) {
                System.out.println(s.getName() + " - " + s.getPrice());
            }
        } else {
            System.out.println("No services");
        }
    }

    public double getTotalCost(String reservationId) {
        double total = 0;

        List<Service> list = serviceMap.get(reservationId);

        if (list != null) {
            for (Service s : list) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

// MAIN CLASS
public class BookMyStay {

    public static void main(String[] args) {

        Reservation r = new Reservation("Rithanya", "Single", "RES1");

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService("RES1", new Service("Breakfast", 500));
        manager.addService("RES1", new Service("Spa", 1500));

        manager.displayServices("RES1");

        System.out.println("Total Cost: " + manager.getTotalCost("RES1"));
    }
}