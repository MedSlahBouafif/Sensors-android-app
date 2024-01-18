package tn.esprit.sensors.tinder.repositories;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.esprit.sensors.tinder.database.MatchDao;
import tn.esprit.sensors.tinder.database.MatchDatabase;
import tn.esprit.sensors.tinder.entites.Match;

public class MatchRepository {
    private MatchDao matchDao;

    public MatchRepository(Context context) {
        MatchDatabase database = MatchDatabase.getInstance(context);
        matchDao = database.matchDao();
    }

    public void fetchMatchesFromApi() {
        // Assume you have an API service and Retrofit instance set up

        yourApiService.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    List<Match> matches = response.body();

                    // Save the retrieved matching data to the database
                    matchDao.insertMatches(matches);
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}