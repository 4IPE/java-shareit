package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.enumarated.StatusBooking;

import java.time.LocalDateTime;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByBooker_idAndItem_id(Integer idUser, Integer idItem);

    List<Booking> findByItem_idAndEndIsAfterOrderByEnd(int id, LocalDateTime now);

    List<Booking> findByItem_idAndEndIsBeforeOrderByEndDesc(int id, LocalDateTime now);

    List<Booking> findByStatusAndBooker_idOrderByStartDesc(StatusBooking status, Integer booker);

    List<Booking> findByBooker_idOrderByStartDesc(Integer booker);


    List<Booking> findByBooker_idAndStartIsAfterOrderByStartDesc(Integer booker, LocalDateTime now);

    @Query(value = "SELECT *\n" +
            "FROM BOOKINGS b \n" +
            "WHERE b.BOOKER_ID  =?1 AND b.START_DATE <= CURRENT_TIME() AND b.END_DATE >= CURRENT_TIME()", nativeQuery = true)
    List<Booking> getCurrentBooker(Integer id, LocalDateTime now);

    @Query(value = "SELECT *\n" +
            "FROM BOOKINGS b \n" +
            "JOIN ITEMS i ON b.ITEM_ID = i.ID \n" +
            "WHERE i.OWNER_ID =?1 AND b.START_DATE <= CURRENT_TIME() AND b.END_DATE >= CURRENT_TIME()", nativeQuery = true)
    List<Booking> getCurrentOwner(Integer id);

    @Query(value = "SELECT *\n" +
            "FROM BOOKINGS b \n" +
            "JOIN ITEMS i ON b.ITEM_ID = i.ID \n" +
            "WHERE i.OWNER_ID =?1 AND b.START_DATE <= CURRENT_TIME() AND b.END_DATE <= CURRENT_TIME() " +
            "ORDER BY END_DATE DESC", nativeQuery = true)
    List<Booking> getPastOwner(Integer id);

    @Query(value = "SELECT *\n" +
            "FROM BOOKINGS b \n" +
            "WHERE b.BOOKER_ID =?1 AND b.START_DATE <= CURRENT_TIME() AND b.END_DATE <= CURRENT_TIME() " +
            "ORDER BY END_DATE DESC", nativeQuery = true)
    List<Booking> getPastBooker(Integer id);

    List<Booking> findByItem_ownerIdAndStatusOrderByStartDesc(Integer id, StatusBooking status);

    List<Booking> findByItem_ownerIdAndStartIsAfterOrderByStartDesc(Integer id, LocalDateTime now);

    List<Booking> findByItem_ownerIdOrderByStartDesc(Integer id);


}
