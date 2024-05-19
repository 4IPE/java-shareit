package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.enumarated.StatusBooking;

import java.time.LocalDateTime;
import java.util.List;
//DATEADD('SECOND', 1, current_timestamp)

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findByBooker_idAndItem_id(Integer idUser,Integer idItem);
    List<Booking>findByItem_idAndEndIsAfterOrderByEnd(int id,LocalDateTime now);
    List<Booking>findByItem_idAndEndIsBeforeOrderByEnd(int id,LocalDateTime now);

    List<Booking> findByStatusAndBooker_idOrderByStartDesc(StatusBooking status, Integer booker);

    List<Booking> findByBooker_idOrderByStartDesc(Integer booker);

    List<Booking> findByBooker_idAndStartIsBeforeOrderByStartDesc(Integer booker, LocalDateTime now);

    List<Booking> findByBooker_idAndStartIsAfterOrderByStartDesc(Integer booker, LocalDateTime now);

    @Query("select b from Booking as b where booker.id = ?1 and b.start between ?2 AND b.end")
    List<Booking> getCurrentBooker(Integer id, LocalDateTime now);

    List<Booking> findByItem_ownerIdAndStatusOrderByStartDesc(Integer id,StatusBooking status);
    List<Booking> findByItem_ownerIdAndStartIsBeforeOrderByStartDesc(Integer id,LocalDateTime now);
    List<Booking> findByItem_ownerIdAndStartIsAfterOrderByStartDesc(Integer id,LocalDateTime now);
    List<Booking> findByItem_ownerIdOrderByStartDesc(Integer id);
    @Query("select b from Booking as b where item.ownerId = ?1 and b.start between ?2 AND b.end")
    List<Booking> getCurrentOwner(Integer id, LocalDateTime now);
}
