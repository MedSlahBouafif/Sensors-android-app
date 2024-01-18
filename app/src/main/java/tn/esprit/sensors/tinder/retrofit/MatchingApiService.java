package tn.esprit.sensors.tinder.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tn.esprit.sensors.tinder.entites.ApiConstants;
import tn.esprit.sensors.tinder.entites.Match;

public interface MatchingApiService {
    @GET(ApiConstants.MATCHES_ENDPOINT)
    Call<List<Match>> getMatchingData();
}