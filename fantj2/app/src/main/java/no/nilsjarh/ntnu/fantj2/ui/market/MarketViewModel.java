package no.nilsjarh.ntnu.fantj2.ui.market;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.ItemRepository;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class MarketViewModel extends ViewModel {

    private MutableLiveData<String> text;
    private LiveData<List<Item>> itemListLiveData;
    private ItemRepository itemRepo;

    public MarketViewModel(ItemRepository instance) {
        text = new MutableLiveData<String>();
        text.setValue("This app got parallax effects!");

        itemRepo = ItemRepository.getInstance();
        if(itemListLiveData == null) {
            itemListLiveData = itemRepo.getItemsLiveData();
        }
    }


    public void loadItemList() {
        itemRepo.getItemList();
    }

    public LiveData<List<Item>> getItemListLiveData() {
        return itemListLiveData;
    }

    public LiveData<String> getText() {
        return text;
    }
}