package tn.esprit.sensors.reservations.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tn.esprit.sensors.reservations.entities.ReservationEntity;
@Database(entities = {ReservationEntity.class}, version = 4, exportSchema = false)
public abstract class ResDatabase extends RoomDatabase {

    private static ResDatabase instance;

    public abstract ReservationDao reservationDao();

    public static synchronized ResDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ResDatabase.class,
                            "reservation_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
