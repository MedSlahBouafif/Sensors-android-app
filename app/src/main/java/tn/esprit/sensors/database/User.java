package tn.esprit.sensors.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    String name ;
    @ColumnInfo(name = "mail")
    String mail ;
    @ColumnInfo(name = "pass")
    String pass ;


    @Ignore
    public User(){

    }


    public User(String name, String pass, String mail ) {
        this.name = name;
        this.pass = pass;
        this.mail = mail;
        this.id = 0;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
