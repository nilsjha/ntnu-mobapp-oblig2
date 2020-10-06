package no.nilsjarh.ntnu.fantj2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemRepository {
    public static String MARKETPLACE_SERVICE_URL = "https://nilsjarh-1.uials.no";

    private MarketplaceApi serviceApi;
    private MutableLiveData<List<Item>> itemsResponseLiveData;
    private static ItemRepository itemRepository;

    public static ItemRepository getInstance() {
        if (itemRepository == null) {
            itemRepository = new ItemRepository();
        }
        return itemRepository;
    }

    public ItemRepository() {
        itemsResponseLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        serviceApi = new retrofit2.Retrofit.Builder()
                .baseUrl(MARKETPLACE_SERVICE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarketplaceApi.class);
    }

    public void getItems() {
        serviceApi.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response != null) {
                    itemsResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                itemsResponseLiveData.postValue(null);

            }
        });
    }


    public LiveData<List<Item>> getItemsLiveData() {
        return itemsResponseLiveData;
    }
}
