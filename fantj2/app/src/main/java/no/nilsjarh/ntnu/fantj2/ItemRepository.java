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

    private LoginRepository loginRepository;
    private MarketplaceApi serviceApi;
    private MutableLiveData<List<Item>> mItemsResponseLiveData;
    private MutableLiveData<Item> mSingleItemResponseLiveData;

    public static ItemRepository getInstance() {
        if (ItemRepository == null) {
            ItemRepository = new ItemRepository();
        }
        return ItemRepository;
    }

    // private constructor : singleton access
    private ItemRepository() {
        mItemsResponseLiveData = new MutableLiveData<>();

        Retrofit marketplaceService = RestService.getRetrofitClient(false);
        serviceApi = marketplaceService.create(MarketplaceApi.class);
    }

    public LiveData<List<Item>> getItemsLiveData() {
        return mItemsResponseLiveData;
    }

    public LiveData<Item> getSingleItemLiveData() {
        return mSingleItemResponseLiveData;
    }
    public void getItemList() {
        serviceApi.getItemList().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    mItemsResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                mItemsResponseLiveData.postValue(null);

            }
        });
    }


    public void getSingleItem(Long id) {
        serviceApi.getSingleItem(id).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
               if (response.isSuccessful()) {
                   mSingleItemResponseLiveData.postValue(response.body());
                   };
               }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                mSingleItemResponseLiveData.postValue(null);

            }
        });
    }
    public void purchaseItem(Long id, String token) {
        serviceApi.purchaseItem(id, token).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {

            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });
    }

}
