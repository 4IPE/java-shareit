package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.Booking;

import java.util.List;
//DATEADD('SECOND', 1, current_timestamp)

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select bok from Booking as bok where bok.status = ?1 order by bok.start desc ")
    List<Booking> getAllBookingWithState(String state);

    @Query(value = "select * from bookings as b where b.end_date < NOW() ", nativeQuery = true)
    List<Booking> getPast();
    @Query(value = "select * from bookings as b where b.end_date > NOW() ", nativeQuery = true)
    List<Booking> getFuture();
    @Query(value = "select * from bookings as b where b.end_date < NOW() and start_date > NOW() ", nativeQuery = true)
    List<Booking> getCurrent();

    List<Booking> findByBookerAndStatus(Integer booker, String status);
}
