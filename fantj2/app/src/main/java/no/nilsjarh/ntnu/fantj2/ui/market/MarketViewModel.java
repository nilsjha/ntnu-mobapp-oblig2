package no.nilsjarh.ntnu.fantj2.ui.market;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;

public class MarketViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Item>> mItemlist;

    public MarketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the market fragment");
    }


    public LiveData<List<Item>> getItems() {
        if(mItemlist == null) {
            mItemlist = new MutableLiveData<>();
        }
        return mItemlist;
    }

    public LiveData<String> getText() {
        return mText;
    }
}