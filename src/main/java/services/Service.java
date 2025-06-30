package services;

import entities.Room;
import entities.User;
import entities.Booking;
import entities.RoomType;
import java.util.*;

public class Service {
    private final List<Room> rooms = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        for (int i = 0; i < rooms.size(); i++) {
            Room r = rooms.get(i);
            if (r.roomNumber() == roomNumber) {
                rooms.set(i, new Room(roomNumber, roomType, roomPricePerNight));
                return;
            }
        }
        rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
    }

    public void setUser(int userId, int balance) {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.id() == userId) {
                users.set(i, new User(userId, balance));
                return;
            }
        }
        users.add(new User(userId, balance));
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        User user = users.stream().filter(u -> u.id() == userId).findFirst().orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        Room room = rooms.stream().filter(r -> r.roomNumber() == roomNumber).findFirst().orElse(null);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }
        for (Booking b : bookings) {
            if (b.roomNumber() == roomNumber &&
                    !(checkOut.compareTo(b.checkIn()) <= 0 || checkIn.compareTo(b.checkOut()) >= 0)) {
                System.out.println("Room not available for the selected period.");
                return;
            }
        }
        long diff = checkOut.getTime() - checkIn.getTime();
        int nights = (int) ((diff / (1000 * 60 * 60 * 24)));
        if (nights <= 0) {
            System.out.println("Invalid booking period.");
            return;
        }
        int totalPrice = nights * room.pricePerNight();
        if (user.balance() < totalPrice) {
            System.out.println("Insufficient balance.");
            return;
        }
        // Update user balance
        setUser(userId, user.balance() - totalPrice);
        bookings.add(new Booking(userId, roomNumber, checkIn, checkOut));
        System.out.println("Booking successful.");
    }

    public void printAll() {
        ListIterator<Room> roomIt = rooms.listIterator(rooms.size());
        while (roomIt.hasPrevious()) {
            Room r = roomIt.previous();
            System.out.println("Room " + r.roomNumber() + " (" + r.type() + "), Price: " + r.pricePerNight());
            for (Booking b : bookings) {
                if (b.roomNumber() == r.roomNumber()) {
                    System.out.println("  Booking: User " + b.userId() + ", " + b.checkIn() + " to " + b.checkOut());
                }
            }
        }
    }

    public void printAllUsers() {
        ListIterator<User> userIt = users.listIterator(users.size());
        while (userIt.hasPrevious()) {
            User u = userIt.previous();
            System.out.println("User " + u.id() + ", Balance: " + u.balance());
        }
    }

    // For testing
    public List<Booking> getBookings() { return bookings; }
    public List<User> getUsers() { return users; }
    public List<Room> getRooms() { return rooms; }
}