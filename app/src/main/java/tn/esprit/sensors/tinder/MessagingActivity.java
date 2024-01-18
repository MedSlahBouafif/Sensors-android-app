package tn.esprit.sensors.tinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.esprit.sensors.R;
import tn.esprit.sensors.tinder.entites.ApiConstants;
import tn.esprit.sensors.tinder.entites.Match;
import tn.esprit.sensors.tinder.retrofit.MatchingApiService;
import tn.esprit.sensors.tinder.retrofit.MessagingApiService;
public class MessagingActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private MessagingApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        // Initialize Retrofit
        initializeRetrofit();

        // Fetch matching data
        fetchMatchingData();
    }

    private void fetchMatchingData() {
        Call<List<Match>> call = apiService.getMatchingData();
        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    List<Match> matches = response.body();
                    // Process and display the matching data
                    // Update the UI or perform any other necessary actions
                } else {
                    // Handle API error
                    String errorMessage = response.message();
                    // Display an error message to the user or handle the error condition
                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                // Handle network or unexpected errors
                // Display an error message to the user or handle the failure condition
            }
        });
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(MessagingApiService.class);
    }

}