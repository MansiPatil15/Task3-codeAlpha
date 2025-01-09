
import java.util.*;

// Room class to represent a hotel room
class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;
    private double pricePerNight;

    public Room(int roomNumber, String category, boolean isAvailable, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = isAvailable;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", category='" + category + '\'' +
                ", isAvailable=" + isAvailable +
                ", pricePerNight=" + pricePerNight +
                '}';
    }
}

// Reservation class to represent a booking
class Reservation {
    private int reservationId;
    private String guestName;
    private Room room;
    private int nights;
    private double totalAmount;

    public Reservation(int reservationId, String guestName, Room room, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
        this.totalAmount = room.getPricePerNight() * nights;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", guestName='" + guestName + '\'' +
                ", room=" + room.getRoomNumber() +
                ", nights=" + nights +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

// HotelReservationSystem class to manage reservations
public class HotelReservationSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int nextReservationId;

    public HotelReservationSystem() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.nextReservationId = 1;
    }

    // Add a room to the system
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Search for available rooms by category
    public List<Room> searchAvailableRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Make a reservation
    public Reservation makeReservation(String guestName, int roomNumber, int nights) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.setAvailable(false);
                Reservation reservation = new Reservation(nextReservationId++, guestName, room, nights);
                reservations.add(reservation);
                return reservation;
            }
        }
        return null; // Room not available
    }

    // View all reservations
    public List<Reservation> viewReservations() {
        return reservations;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelReservationSystem system = new HotelReservationSystem();

        // Adding some sample rooms
        system.addRoom(new Room(101, "Single", true, 100.0));
        system.addRoom(new Room(102, "Double", true, 150.0));
        system.addRoom(new Room(103, "Suite", true, 300.0));

        System.out.println("Welcome to the Hotel Reservation System!");
        while (true) {
            System.out.println("\n1. Search for Available Rooms\n2. Make a Reservation\n3. View Reservations\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter room category (Single/Double/Suite): ");
                    String category = scanner.nextLine();
                    List<Room> availableRooms = system.searchAvailableRooms(category);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No rooms available in this category.");
                    } else {
                        System.out.println("Available rooms:");
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                }
                case 2 -> {
                    System.out.print("Enter your name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter number of nights: ");
                    int nights = scanner.nextInt();
                    Reservation reservation = system.makeReservation(guestName, roomNumber, nights);
                    if (reservation != null) {
                        System.out.println("Reservation successful! Details: " + reservation);
                    } else {
                        System.out.println("Room not available or invalid room number.");
                    }
                }
                case 3 -> {
                    List<Reservation> reservations = system.viewReservations();
                    if (reservations.isEmpty()) {
                        System.out.println("No reservations found.");
                    } else {
                        System.out.println("Reservations:");
                        for (Reservation reservation : reservations) {
                            System.out.println(reservation);
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
