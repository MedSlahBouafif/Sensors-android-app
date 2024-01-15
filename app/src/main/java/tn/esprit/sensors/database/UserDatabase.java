package tn.esprit.sensors.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {User.class},version = 5,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
    private static UserDatabase instance;
    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "UserDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
