package no.nilsjarh.ntnu.fantj2.ui.item;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import no.nilsjarh.ntnu.fantj2.ItemRepository;
import no.nilsjarh.ntnu.fantj2.LoginRepository;
import no.nilsjarh.ntnu.fantj2.model.Attachment;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<Item> activeItemLiveData;
    private ItemRepository itemRepo;
    private LoginRepository loginRepo;

    public ItemViewModel(ItemRepository itemRepo, LoginRepository loginRepo) {
        this.itemRepo = itemRepo;
        this.loginRepo = loginRepo;
        if (activeItemLiveData == null) {
            Log.d("ITEMMODEL-WARN","No active item in model. Overwriting blank");
            activeItemLiveData = new MutableLiveData<>();
        }
    }

    public void loadActiveItem(Long id) {
            itemRepo.getSingleItem(id, (Item receivedItem)-> {
                if (receivedItem != null) {
                    setActiveItem(receivedItem);
                }
            });
    }

    public void setActiveItem(Item item) {
        this.activeItemLiveData.setValue(item);
        Log.d("ITEMMODEL-INFO","Item was overwritten and flagged");
        Log.d("ITEMMODEL-INFO", "New item " + activeItemLiveData.getValue().getId());
    }

    public boolean purchaseActiveItem() {
        boolean executed;
        Item currentItem = this.activeItemLiveData.getValue();
        setActiveItem(currentItem);
        if (currentItem.getItemPurchase() == null) {
            executed = true;
            Log.d("ITEMMODEL-INFO","Item purchase for item " + currentItem.getId());
            itemRepo.purchaseItem(currentItem.getId(), loginRepo.getToken(), purchase -> {
                if (purchase != null) {
                    loadActiveItem(currentItem.getId());
                }
            });
        } else {
            Log.d("ITEMMODEL-WARN","Item purchase abort, no item id present");
            executed  = false;
        }
        return executed;
    }

    public LiveData<Item> getActiveItemLiveData() {
        Log.d("ITEMMODEL-INFO","Returned observable LiveData");
        return activeItemLiveData;
    }

    public Boolean getLoggedInState() {
        return loginRepo.isLoggedIn();
    }

}
