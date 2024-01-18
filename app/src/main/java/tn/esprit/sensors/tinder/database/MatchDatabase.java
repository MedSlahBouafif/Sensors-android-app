/*package tn.esprit.sensors.tinder.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tn.esprit.sensors.tinder.entites.Match;

@Database(entities = {Match.class}, version = 1)
public abstract class MatchDatabase extends RoomDatabase {
    public abstract MatchDao matchDao();

    private static volatile MatchDatabase instance;

    public static MatchDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (MatchDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    MatchDatabase.class, "match_database")
                            .build();
                }
            }
        }
        return instance;
    }
}*/