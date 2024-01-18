package tn.esprit.sensors.tinder.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tn.esprit.sensors.tinder.entites.ApiConstants;
import tn.esprit.sensors.tinder.entites.Message;
public interface MessagingApiService {
    @POST(ApiConstants.MESSAGES_ENDPOINT)
    Call<Message> sendMessage(@Body Message message);
}