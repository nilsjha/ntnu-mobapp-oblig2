package no.nilsjarh.ntnu.fantj2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemRepository {
    public static String MARKETPLACE_SERVICE_URL = "https://nilsjarh-1.uials.no";
    private static ItemRepository ItemRepository;

    private MarketplaceApi serviceApi;
    private MutableLiveData<List<Item>> itemsResponseLiveData;
    private MutableLiveData<Item> viewItemResponseLiveData;
    private boolean authenticated;

    public static ItemRepository getInstance() {
        if (ItemRepository == null) {
            ItemRepository = new ItemRepository();
        }
        return ItemRepository;
    }

    // private constructor : singleton access
    private ItemRepository() {
        itemsResponseLiveData = new MutableLiveData<>();

        Retrofit marketplaceService = RestService.getRetrofitClient(false);
        serviceApi = marketplaceService.create(MarketplaceApi.class);
    }

    public void getItems() {
        serviceApi.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
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

    public void viewItem(Long id) {
        serviceApi.getDetails(id).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
               if (response.isSuccessful()) {
                   viewItemResponseLiveData.postValue(response.body());
               }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                itemsResponseLiveData.postValue(null);

            }
        });
    }
}
