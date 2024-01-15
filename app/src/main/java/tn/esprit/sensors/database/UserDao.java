package tn.esprit.sensors.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void addUser(User user);
    @Update
    void updateUser(User user);
    @Delete
    void deleteUser(User user);
    @Query("select * from user")
    List<User> getAllUsers();
    @Query("select * from user where user_id==:user_id")
    User getUser(int user_id);
    @Query("select * from user where name = :username AND pass = :password")
    User getUser(String username, String password);
}
