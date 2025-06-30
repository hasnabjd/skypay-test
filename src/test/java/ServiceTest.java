
import entities.RoomType;
import entities.Booking;
import org.junit.jupiter.api.Test;
import services.Service;

import java.text.SimpleDateFormat;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    @Test
    public void testBookingEquals() throws Exception {
        Service service = new Service();
        service.setUser(1, 5000);
        service.setRoom(1, RoomType.STANDARD, 1000);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        service.bookRoom(1, 1, sdf.parse("01/07/2026"), sdf.parse("03/07/2026"));

        Booking expected = new Booking(1, 1, sdf.parse("01/07/2026"), sdf.parse("03/07/2026"));
        assertTrue(service.getBookings().contains(expected));
    }

    @Test
    public void testBookingInsufficientBalance() throws Exception {
        Service service = new Service();
        service.setUser(1, 100);
        service.setRoom(1, RoomType.STANDARD, 1000);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        service.bookRoom(1, 1, sdf.parse("01/07/2026"), sdf.parse("03/07/2026"));

        assertEquals(0, service.getBookings().size());
    }

    @Test
    public void testRoomNotAvailable() throws Exception {
        Service service = new Service();
        service.setUser(1, 10000);
        service.setUser(2, 10000);
        service.setRoom(1, RoomType.STANDARD, 1000);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        service.bookRoom(1, 1, sdf.parse("01/07/2026"), sdf.parse("03/07/2026"));
        service.bookRoom(2, 1, sdf.parse("02/07/2026"), sdf.parse("04/07/2026"));

        assertEquals(1, service.getBookings().size());
    }
}