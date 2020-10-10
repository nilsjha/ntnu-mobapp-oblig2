package no.nilsjarh.ntnu.fantj2;

import android.util.Log;

import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import no.nilsjarh.ntnu.fantj2.model.Purchase;
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


    public void getSingleItem(Long id, Consumer<Item> itemCallback) {
        serviceApi.getSingleItem(id).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful()) {
                    Log.d("ITEM-INFO:", "Item retrieved OK");
                    itemCallback.accept(response.body());
                }
                ;
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.d("ITEM-WARN:", "Item retrieve failed");
                itemCallback.accept(null);

            }
        });
    }

    public void purchaseItem(Long id, String token, Consumer<Purchase> purchaseCallback) {
        serviceApi.purchaseItem(id, token).enqueue(new Callback<Purchase>() {
            @Override
            public void onResponse(Call<Purchase> call, Response<Purchase> response) {
                Log.d("PURCHASE-INFO:", "OK");
                purchaseCallback.accept(response.body());

            }

            @Override
            public void onFailure(Call<Purchase> call, Throwable t) {
                Log.d("PURCHASE-WARN:", "Failed for item " + id.toString());
                purchaseCallback.accept(null);

            }
        });
    }
}

