// UserDao.java
package tn.esprit.sensors.database;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

@TypeConverters(BitmapTypeConverter.class)

@Dao
public interface UserDao {
    @Insert
    void addUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE user_id = :user_id")
    User getUser(int user_id);

    @Query("SELECT * FROM User WHERE name = :username AND pass = :password")
    User getUser(String username, String password);

    @Query("SELECT user_image FROM User WHERE user_id = :user_id")
    Bitmap getUserImage(int user_id);

    @Query("UPDATE User SET user_image = :image WHERE user_id = :user_id")
    void updateUserImage(int user_id, Bitmap image);
}
