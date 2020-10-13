package no.nilsjarh.ntnu.fantj2;

import android.util.Log;

import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import no.nilsjarh.ntnu.fantj2.model.Purchase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemRepository {
    public static String MARKETPLACE_SERVICE_URL = "https://nilsjarh-1.uials.no";
    private static ItemRepository ItemRepository;

    private LoginRepository loginRepository;
    private MarketplaceApi serviceApi;
    private MutableLiveData<List<Item>> mItemsResponseLiveData;

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

    /**
     * Retrieve the item list from the server and update data stored in the app.
     * Also returns a callback with exit code. 0 = OK, 1 = Server Failure/reject, 2 = Network
     * 3 = Grave errpr
     * @param successfulCallbackResult
     */
    public void getItemList(Consumer<Integer> successfulCallbackResult) {
        serviceApi.getItemList().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    // Update data and exit 0
                    mItemsResponseLiveData.postValue(response.body());
                    successfulCallbackResult.accept(0);
                } else {
                    // Server did not send 200, exit with reject/failure
                    successfulCallbackResult.accept(1);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                // Failure on client side, reset app data and send exit 2 to UI/model
                mItemsResponseLiveData.postValue(null);
                if (t instanceof IOException) {
                    successfulCallbackResult.accept(2);
                } else {
                    successfulCallbackResult.accept(3);
                }

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



    public void createItem(String token, String title, BigDecimal price, Consumer <Result<Item>> createdItemCallback) {
        serviceApi.createItem(title, token,price).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful()) {
                    Log.d("CREATE-INFO:", "Item" + response.body().getId().toString() + " created with success!");
                    createdItemCallback.accept(new Result.Success<>(response.body()));
                } else {
                    Log.d("CREATE-WARN:", "Server rejected create request");
                    createdItemCallback.accept(new Result.Error(null));
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.d("CREATE-WARN", "Network error");
                } else {
                    Log.d("CREATE-ERROR:", "Unable to create item due to: " + t.getCause().getMessage());
                }
                createdItemCallback.accept(new Result.Error(new Exception(t)));
            }
        });
    }


    public void updateItem(Long id, String token, String title, String description, BigDecimal price, Consumer<Result<Item>> updatedItemCallback) {
        serviceApi.updateItem(id,token,title,price,description).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful()) {
                    Log.d("UPDATE-INFO:", "Item" + id.toString() + "update OK");
                    updatedItemCallback.accept(new Result.Success<Item>(response.body()));


                } else if (response.code() == 304) {
                    Log.d("UPDATE-WARN:", "Item" + id.toString() + "not modified");
                    updatedItemCallback.accept(new Result.Error(null));

                } else {
                    Log.d("UPDATE-ERROR:", "Error on server");
                    updatedItemCallback.accept(new Result.Error(null));

                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.d("UPDATE-WARN:", "Failure with network when updating item " + id.toString());
                } else {
                    Log.d("UPDATE-ERROR:", "Failure when trying to update item " + id.toString());
                }

                updatedItemCallback.accept(new Result.Error(null));
            }
        });
    }
}

