package no.nilsjarh.ntnu.fantj2.ui.item;

import android.graphics.Bitmap;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;

import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;

import no.nilsjarh.ntnu.fantj2.ItemRepository;
import no.nilsjarh.ntnu.fantj2.LoginRepository;
import no.nilsjarh.ntnu.fantj2.Result;
import no.nilsjarh.ntnu.fantj2.model.Attachment;
import no.nilsjarh.ntnu.fantj2.model.Item;
import no.nilsjarh.ntnu.fantj2.model.User;

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

    /**
     *  Retrieves a single item element from the server and stores it in the ViewModel
     * @param id Id for the Item to retrieve
     */
    public void loadActiveItem(Long id) {
            itemRepo.getSingleItem(id, (Item receivedItem)-> {
                if (receivedItem != null) {
                    setActiveItem(receivedItem);
                }
            });
    }

    public void setActiveItem(Item item) {
        activeItemLiveData.setValue(item);
        Log.d("ITEMMODEL-INFO","Item was overwritten and flagged");
        Log.d("ITEMMODEL-INFO", "New item " + activeItemLiveData.getValue().getId());
    }

    /**
     *  Send a request to the server to create a new item. Only works if user is logged in
     *  The calling object might implement error handeling. Result<Item> is returned of
     *  result.Success if OK (encapuslates the item inside). A result.Error is returned with NULL
     *  if server rejected or an Exception if an error occured on the client side.
     *  IOException is returned if any problems with network.
     * @param title title of the item to create
     * @param priceNok price of the item to create.
     * @param createdItem The callback of the result (see responses above)
     */
    public void createNewItem(String title, BigDecimal priceNok, Consumer<Result<Item>> createdItem) {
        if (loginRepo.isLoggedIn()) {
            itemRepo.createItem(loginRepo.getToken(), title, priceNok, createdItemResult -> {
                if(createdItemResult instanceof Result.Success) {
                    Item returnedItem = (Item) ((Result.Success) createdItemResult).getData();
                    this.setActiveItem(returnedItem);
                }
                createdItem.accept(createdItemResult);
            });
        } else {
            createdItem.accept(new Result.Error(new UserNotAuthenticatedException()));
        }
    }

    public void setDescriptionExistingItem(Long id, String description, Consumer <Item> updatedItem) {
        if (loginRepo.isLoggedIn()) {
            itemRepo.updateItem(id,loginRepo.getToken(),null,description,null,updatedItemResult ->{
                if (updatedItemResult instanceof Result.Success) {
                    Item i = (Item) ((Result.Success) updatedItemResult).getData();
                    this.setActiveItem(i);
                    updatedItem.accept(i);
                } else {
                    updatedItem.accept(null);
                }
            });
        } else {
            updatedItem.accept(null);
        }
    }

    /**
     * Sends a request to the server to purchase the item present in the view model.
     * Returns via callback an exit code for status. 0 = OK, 1 = ABORT, 2 = SERVER REJECT
     * @param purchaseCallbackResult
     */
    public void purchaseActiveItem(Consumer<Integer> purchaseCallbackResult) {
        Item currentItem = this.activeItemLiveData.getValue();
        if (!(verifyOwnerOfItem(currentItem, loginRepo.getUserId()))) {
            setActiveItem(currentItem);
            if (currentItem.getItemPurchase() == null) {
                Log.d("ITEMMODEL-INFO","Item purchase for item " + currentItem.getId());
                itemRepo.purchaseItem(currentItem.getId(), loginRepo.getToken(), purchase -> {
                    if (purchase != null) {
                        loadActiveItem(currentItem.getId());
                        // Exit 0: Server executed and accepted purchase
                        purchaseCallbackResult.accept(0);
                    } else {
                        // Exit 1: Server rejected purchase
                        purchaseCallbackResult.accept(1);
                    }
                });
            } else {
                Log.d("ITEMMODEL-WARN","Item purchase abort, no item id present");
                // Exit 2: Aborted purchase, due to no defined item in UI
                purchaseCallbackResult.accept(2);
            }
        } else {
            Log.d("ITEMMODEL-WARN","Item purchase abort,seller tryin to get rich on own item");
            purchaseCallbackResult.accept(10);

        }
    }

    public LiveData<Item> getActiveItemLiveData() {
        Log.d("ITEMMODEL-INFO","Returned observable LiveData");
        return activeItemLiveData;
    }

    public Boolean getLoggedInState() {
        return loginRepo.isLoggedIn();
    }

    private boolean verifyOwnerOfItem(Item i, String userId) {
        return i.getItemSeller().getUserId().equals(userId) ? true : false;
    }

}
