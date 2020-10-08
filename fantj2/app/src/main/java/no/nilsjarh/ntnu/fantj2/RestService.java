package no.nilsjarh.ntnu.fantj2;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {
    public static String DOMAIN = "https://nilsjarh-1.uials.no";

    private static Retrofit retrofit;

    private MarketplaceApi marketplaceService;
    private AuthApi authService;

    public RestService() {



        marketplaceService = retrofit.create(MarketplaceApi.class);
        authService = retrofit.create(AuthApi.class);
    }

    public static Retrofit getRetrofitClient(boolean forceNew) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        if (retrofit == null || forceNew) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(DOMAIN)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public MarketplaceApi getMarketplaceSrv() {
        return marketplaceService;
    }

    public Retrofit getInstance() {
        return retrofit;
    }

    public AuthApi getAuthService() {
        return authService;
    }

}
