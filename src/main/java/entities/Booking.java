package entities;

import java.util.Date;

public record Booking(int userId, int roomNumber, Date checkIn, Date checkOut) {}