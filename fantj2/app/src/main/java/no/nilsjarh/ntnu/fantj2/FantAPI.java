package no.nilsjarh.ntnu.fantj2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FantAPI {
    public static String BASE_DOMAIN = "https://nilsjarh-1.uials.no/api/";
    private static Retrofit retrofit;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
