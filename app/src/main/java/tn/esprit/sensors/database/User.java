package tn.esprit.sensors.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@TypeConverters(BitmapTypeConverter.class)
@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "mail")
    private String mail;

    @ColumnInfo(name = "pass")
    private String pass;

    @ColumnInfo(name = "user_age")
    private int userAge;

    @ColumnInfo(name = "user_about_me")
    private String userAboutMe;

    @ColumnInfo(name = "user_image")
    private byte[] userImage; // Change type to byte[]

    @Ignore
    public User() {
    }

    public User(String name, String pass, String mail, int userAge, String userAboutMe, byte[] userImage) {
        this.name = name;
        this.pass = pass;
        this.mail = mail;
        this.userAge = userAge;
        this.userAboutMe = userAboutMe;
        this.userImage = userImage;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserAboutMe() {
        return userAboutMe;
    }

    public void setUserAboutMe(String userAboutMe) {
        this.userAboutMe = userAboutMe;
    }

    public Bitmap getUserImageBitmap() {
        if (userImage != null) {
            return BitmapFactory.decodeByteArray(userImage, 0, userImage.length);
        } else {
            return null;
        }
    }
}
