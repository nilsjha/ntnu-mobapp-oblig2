package no.nilsjarh.ntnu.fantj2.ui.market;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.function.Consumer;

import no.nilsjarh.ntnu.fantj2.ItemRepository;
import no.nilsjarh.ntnu.fantj2.LoginRepository;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class MarketViewModel extends ViewModel {

    private MutableLiveData<String> text;
    private LiveData<List<Item>> itemListLiveData;
    private ItemRepository itemRepo;
    private LoginRepository loginRepo;

    public MarketViewModel(ItemRepository itemRepo, LoginRepository loginRepo) {
        text = new MutableLiveData<String>();
        text.setValue("This app got parallax effects!");
        this.itemRepo = itemRepo;
        this.loginRepo = loginRepo;
    }


    /**
     * Tries to load items from server and populate the local data store
     * @param loadResultCallback Returns a callback boolean which is true when everything went OK
     */
    public void loadItemList(Consumer<Integer> loadResultCallback) {
        itemRepo.getItemList(result->{
            loadResultCallback.accept(result);
        });
    }

    public LiveData<List<Item>> getItemListLiveData() {
        return itemRepo.getItemsLiveData();
    }

    public LiveData<String> getText() {
        return text;
    }

    public Boolean isAuthenticated() {
        return loginRepo.isLoggedIn();
    }
}