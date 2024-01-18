package tn.esprit.sensors.reservations.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tn.esprit.sensors.reservations.entities.ReservationEntity;

@Dao
public interface ReservationDao {

    @Insert
    void insert(ReservationEntity reservation);
    @Delete
    void delete(ReservationEntity reservation);

    @Update
    void update(ReservationEntity reservation);

    @Query("SELECT * FROM reservations WHERE id = :reservationId")
    ReservationEntity getReservationById(long reservationId);

    @Query("SELECT * FROM reservations")

    List<ReservationEntity> getAllReservations();
}
