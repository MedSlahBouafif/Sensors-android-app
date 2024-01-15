package tn.esprit.sensors.sign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tn.esprit.sensors.R;
import tn.esprit.sensors.database.User;
import tn.esprit.sensors.database.UserDatabase;

public class SignActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    EditText email;
    MaterialButton signupbtn;

    MaterialButton loginbtn;
    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        signupbtn = findViewById(R.id.signupbtn);
        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class,
                "UserDatabase").addCallback(myCallBack).build();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = username.getText().toString();
                String pass = password.getText().toString();
                String mail = email.getText().toString();


                User u1 = new User(name, pass, mail);

                addUserInBackground(u1);

            }
        });

    }


    public void addUserInBackground(User user){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //Background task
                userDatabase.getUserDao().addUser(user);

                //On finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignActivity.this,"Added to Database",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}


