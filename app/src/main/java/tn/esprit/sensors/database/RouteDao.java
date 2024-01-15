package tn.esprit.sensors.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// RouteDao.java
@Dao
public interface RouteDao {

    @Insert
    long insertRoute(Route route);

    @Update
    void updateRoute(Route route);

    @Delete
    void deleteRoute(Route route);

    @Query("SELECT * FROM routes")
    List<Route> getAllRoutes();
}
