package tn.esprit.sensors.reservations.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservations")
public class ReservationEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "day")
    private int day;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "hour")
    private int hour;

    @ColumnInfo(name = "minute")
    private int minute;

    @ColumnInfo(name = "number_of_campers")
    private int numberOfCampers;

    @ColumnInfo(name = "details")
    private String details;





    public int getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getNumberOfCampers() {
        return numberOfCampers;
    }

    // Setter methods for each field

    public void setId(int id) {
        this.id = id;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setNumberOfCampers(int numberOfCampers) {
        this.numberOfCampers = numberOfCampers;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

}
