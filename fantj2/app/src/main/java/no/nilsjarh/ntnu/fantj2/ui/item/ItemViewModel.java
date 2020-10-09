package no.nilsjarh.ntnu.fantj2.ui.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.math.BigDecimal;

import no.nilsjarh.ntnu.fantj2.ItemRepository;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class ItemViewModel extends ViewModel {
    private LiveData<Item> activeItem;
    private ItemRepository itemRepo;

    public ItemViewModel() {
        this.activeItem = new LiveData<Item>() {
        };

        if(activeItem == null) {
            itemRepo = ItemRepository.getInstance();
            activeItem = itemRepo.getSingleItemLiveData();
        }
    }

    public void getItem(Long id) {
        itemRepo.getSingleItem(id);
    }

    public LiveData<Item> getActiveItem() {
        return activeItem;
    }
}
