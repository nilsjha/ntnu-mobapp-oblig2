package no.nilsjarh.ntnu.fantj2;

import no.nilsjarh.ntnu.fantj2.model.LoggedInUser;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {
    public static String PREFIX = "/api/auth/";

    @GET(PREFIX + "currentuser")
    public Call<LoggedInUser> getCurrentUser(
            @Header("Authorization") String auth
    );

    @FormUrlEncoded
    @POST(PREFIX + "login")
    public Call<Void> doLogin(
            @Field("email") String email,
            @Field("pwd") String password);
}
