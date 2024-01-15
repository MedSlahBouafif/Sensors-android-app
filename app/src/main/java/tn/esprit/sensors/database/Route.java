package tn.esprit.sensors.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "routes")
public class Route {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "start_time")
    String startTime;

    @ColumnInfo(name = "end_time")
    String endTime;

    @ColumnInfo(name = "coordinates")
    List<String> coordinates;

    @Ignore
    public Route() {
        // Provide default values or leave fields uninitialized
        this.coordinates = new ArrayList<>();
    }

    // Constructor with three String arguments
    public Route(String name, String startTime, String endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.coordinates = new ArrayList<>();
    }

    // Getter methods
    public String getStartTime() {
        return startTime;
    }

    public String getName() {
        return name;
    }

    public String getEndTime() {
        return endTime;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
