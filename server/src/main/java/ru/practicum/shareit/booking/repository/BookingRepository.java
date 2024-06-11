package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Booking> findByStatusAndBooker_id(StatusBooking status, Integer booker, Pageable pageable);

    Page<Booking> findByBooker_id(Integer booker, Pageable pageable);

    List<Booking> findByBooker_id(Integer booker);


    Page<Booking> findByBooker_idAndStartIsAfter(Integer booker, LocalDateTime now, Pageable pageable);

    @Query(value = "SELECT *\n" +
            "FROM BOOKINGS b \n" +
            "WHERE b.BOOKER_ID  =?1 AND b.START_DATE <= CURRENT_TIME() AND b.END_DATE >= CURRENT_TIME()", nativeQuery = true)
    Page<Booking> getCurrentBooker(Integer id, Pageable pageable);

    @Query(value = "SELECT *\n" +
            "FROM BOOKINGS b \n" +
            "JOIN ITEMS i ON b.ITEM_ID = i.ID \n" +
            "WHERE i.OWNER_ID =?1 AND b.START_DATE <= CURRENT_TIME() AND b.END_DATE >= CURRENT_TIME()", nativeQuery = true)
    Page<Booking> getCurrentOwner(Integer id, Pageable pageable);

    @Query(value = "SELECT *\n" +
            "FROM BOOKINGS b \n" +
            "JOIN ITEMS i ON b.ITEM_ID = i.ID \n" +
            "WHERE i.OWNER_ID =?1 AND b.START_DATE <= CURRENT_TIME() AND b.END_DATE <= CURRENT_TIME() " +
            "ORDER BY END_DATE DESC", nativeQuery = true)
    Page<Booking> getPastOwner(Integer id, Pageable pageable);

    @Query(value = "SELECT *\n" +
            "FROM BOOKINGS b \n" +
            "WHERE b.BOOKER_ID =?1 AND b.START_DATE <= CURRENT_TIME() AND b.END_DATE <= CURRENT_TIME() " +
            "ORDER BY END_DATE DESC", nativeQuery = true)
    Page<Booking> getPastBooker(Integer id, Pageable pageable);

    Page<Booking> findByItem_ownerId_idAndStatus(Integer id, StatusBooking status, Pageable pageable);

    Page<Booking> findByItem_ownerId_idAndStartIsAfter(Integer id, LocalDateTime now, Pageable pageable);

    List<Booking> findByItem_idOrderByStart(Integer id);

    Page<Booking> findByItem_ownerId_id(Integer id, Pageable pageable);

}
