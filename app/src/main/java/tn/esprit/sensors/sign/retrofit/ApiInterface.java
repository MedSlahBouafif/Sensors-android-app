package tn.esprit.sensors.sign.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("forgot-password")
    Call<Void> forgotPassword(@Field("email") String email);
}
