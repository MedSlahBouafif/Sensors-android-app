package tn.esprit.sensors.tinder;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.esprit.sensors.R;
import tn.esprit.sensors.tinder.adapters.MatchAdapter;
import tn.esprit.sensors.tinder.database.MatchDao;
import tn.esprit.sensors.tinder.database.MatchDatabase;
import tn.esprit.sensors.tinder.entites.ApiConstants;
import tn.esprit.sensors.tinder.entites.Match;
import tn.esprit.sensors.tinder.retrofit.MatchingApiService;
import tn.esprit.sensors.tinder.retrofit.MessagingApiService;
public class MatchingActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private MatchingApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve the matching data from the database
        MatchDatabase database = MatchDatabase.getInstance(this);
        MatchDao matchDao = database.matchDao();
        List<Match> matches = matchDao.getAllMatches();

        // Create and set the adapter with the retrieved matching data
        MatchAdapter adapter = new MatchAdapter(matches);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(match -> {
            // Handle item click here
            Toast.makeText(MatchingActivity.this, "Clicked on match: " + match.getName(), Toast.LENGTH_SHORT).show();

            match.setAge(match.getAge() + 1);

            MatchDatabase database = MatchDatabase.getInstance(MatchingActivity.this);
            MatchDao matchDao = database.matchDao();
            matchDao.deleteMatch(match);
        });


        // Initialize Retrofit and API service
        initializeRetrofit();
        // Fetch matching data from the API
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

                    // Update the adapter with the new matching data
                    adapter.setMatches(matches);
                    adapter.notifyDataSetChanged();
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

        apiService = retrofit.create(MatchingApiService.class);
    }
}}