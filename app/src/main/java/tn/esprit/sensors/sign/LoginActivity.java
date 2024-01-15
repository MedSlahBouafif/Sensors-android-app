package tn.esprit.sensors.sign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.android.material.button.MaterialButton;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tn.esprit.sensors.R;
import tn.esprit.sensors.database.User;
import tn.esprit.sensors.database.UserDatabase;
import tn.esprit.sensors.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    MaterialButton loginbtn;
    MaterialButton signupbtn;

    UserDatabase userDatabase;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.signupbtn);

        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "UserDatabase")
                .fallbackToDestructiveMigration()
                .build();
        executorService = Executors.newSingleThreadExecutor();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (!user.isEmpty() && !pass.isEmpty()) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            User userObj = userDatabase.getUserDao().getUser(user, pass);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isBiometricPromptAvailable()) {
                                        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                                                .setTitle("VERIFY")
                                                .setDescription("REQUIRED")
                                                .setNegativeButtonText("Cancel")
                                                .build();

                                        BiometricPrompt biometricPrompt = getPrompt();
                                        biometricPrompt.authenticate(promptInfo);
                                    } else {
                                        notifyUser("Authentication not available");
                                    }
                                }
                            });
                        }
                    });
                } else {
                    notifyUser("Please enter both username and password");
                }
            }
        });


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });
    }

    private BiometricPrompt getPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("BiometricPrompt", "Authentication error: " + errorCode + " - " + errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("Auth Succ");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyUser("Auth Fail");
            }
        };

        return new BiometricPrompt(this, executor, callback);
    }

    private void handleAuthenticationResult(User userObj) {
        if (userObj != null) {
            // Successfully logged in
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            // You can navigate to the next screen or perform any other actions here
        } else {
            // Invalid login credentials
            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void notifyUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isBiometricPromptAvailable() {
        return BiometricManager.from(this).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS;
    }

    @Override
    protected void onDestroy() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        super.onDestroy();
    }
}
