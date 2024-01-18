package tn.esprit.sensors.tinder.entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import tn.esprit.sensors.database.User;

@Entity(tableName = "matches")
public class Match {
    @PrimaryKey
    private int id;
    private String name;
    private int age;

    // ... getters and setters
}