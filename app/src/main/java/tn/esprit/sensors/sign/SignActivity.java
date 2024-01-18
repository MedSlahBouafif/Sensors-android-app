package tn.esprit.sensors.sign;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tn.esprit.sensors.R;
import tn.esprit.sensors.database.User;
import tn.esprit.sensors.database.UserDatabase;

public class SignActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText email;
    EditText age;
    EditText aboutMe;
    MaterialButton signupbtn;
    MaterialButton loginbtn;
    ImageButton addPictureBtn;
    ImageView selectedImageView;
    String name, pass, mail, userAge, userAboutMe;
    byte[] byteArray;
    UserDatabase userDatabase; // Declare userDatabase at the class level

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        age = findViewById(R.id.age);
        aboutMe = findViewById(R.id.aboutMe);

        addPictureBtn = findViewById(R.id.addPictureBtn);
        selectedImageView = findViewById(R.id.selectedImageView);

        signupbtn = findViewById(R.id.signupbtn);
        loginbtn = findViewById(R.id.loginbtn);

        addPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
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

        // Initialize userDatabase
        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class,
                "UserDatabase").addCallback(myCallBack).build();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = username.getText().toString();
                pass = password.getText().toString();
                mail = email.getText().toString();
                userAge = age.getText().toString();
                userAboutMe = aboutMe.getText().toString();

                // Add the new fields to the User object along with the image data
                User u1 = new User(name, pass, mail, Integer.parseInt(userAge), userAboutMe, byteArray);

                // Add the user to the database
                addUserInBackground(u1);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            handleImageCaptureResult(data);
        }
    }

    private void handleImageCaptureResult(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        // Set the captured image to the ImageView
        selectedImageView.setImageBitmap(imageBitmap);

        // Convert the bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();

        Toast.makeText(this, "Photo added", Toast.LENGTH_SHORT).show();
    }

    public void addUserInBackground(User user) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Background task
                userDatabase.getUserDao().addUser(user);

                // On finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignActivity.this, "Added to Database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
