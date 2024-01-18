package tn.esprit.sensors.sign.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.sensors.R;

public class ForgotPassword extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);

        emailEditText = findViewById(R.id.emailEditText);
        resetButton = findViewById(R.id.resetButton);
        progressBar = findViewById(R.id.progressBar);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (!email.isEmpty()) {
                    // Call the API to request password reset
                    sendPasswordResetRequest(email);
                } else {
                    Toast.makeText(ForgotPassword.this, "Enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendPasswordResetRequest(String email) {
        showProgressBar(true);

        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiInterface.forgotPassword(email);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                showProgressBar(false);

                if (response.isSuccessful()) {
                    // Password reset request successful
                    // Display a message or navigate to a success screen
                    Toast.makeText(ForgotPassword.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after a successful password reset request
                } else {
                    // Handle the error, e.g., display an error message
                    Toast.makeText(ForgotPassword.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showProgressBar(false);

                // Handle network errors or other failures
                Toast.makeText(ForgotPassword.this, "Network error. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.VISIBLE);
        }
    }
}
