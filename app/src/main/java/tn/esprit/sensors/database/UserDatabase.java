package tn.esprit.sensors.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    public abstract UserDao getUserDao();

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "UserDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
