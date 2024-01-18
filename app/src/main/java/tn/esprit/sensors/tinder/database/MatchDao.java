package tn.esprit.sensors.tinder.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import tn.esprit.sensors.tinder.entites.Match;

@Dao
public interface MatchDao {
    @Query("SELECT * FROM matches")
    List<Match> getAllMatches();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMatches(List<Match> matches);

}
