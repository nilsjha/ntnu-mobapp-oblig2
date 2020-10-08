package no.nilsjarh.ntnu.fantj2.ui.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.math.BigDecimal;

import no.nilsjarh.ntnu.fantj2.model.Item;

public class ItemViewModel extends ViewModel {

    private MutableLiveData<Item> mActiveItem;
    private MutableLiveData<String> mItemTitle;
    private MutableLiveData<String> mItemDecription;
    //FIXME: HENT USERSELLER DATA I EIGE REPO + VIEWMODEL

    public ItemViewModel() {
        mActiveItem = new MutableLiveData<>();
        Item i = new Item();
        i.setTitle("Jan sine beste hits");
        i.setPrice(BigDecimal.valueOf(20000));
        mActiveItem.setValue(i);
    }


    public LiveData<Item> getActiveItem() {
        return mActiveItem;
    }
}
