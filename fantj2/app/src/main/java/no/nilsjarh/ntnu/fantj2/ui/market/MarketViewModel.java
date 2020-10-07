package no.nilsjarh.ntnu.fantj2.ui.market;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.centralRepository;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class MarketViewModel extends AndroidViewModel {

    private MutableLiveData<String> text;
    private LiveData<List<Item>> itemListLiveData;
    private centralRepository itemRepo;

    public MarketViewModel(@NonNull Application app) {
        super(app);
        text = new MutableLiveData<String>();
        text.setValue("This app got parallax effects!");

        if(itemListLiveData == null) {
            itemRepo = centralRepository.getInstance();
            itemListLiveData = itemRepo.getItemsLiveData();
        }
    }


    public void getItems() {
        itemRepo.getItems();
    }


    public LiveData<List<Item>> getItemListLiveData() {
        return itemListLiveData;
    }

    public LiveData<String> getText() {
        return text;
    }
}