package no.nilsjarh.ntnu.fantj2.ui.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;

public class ItemViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ItemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is where the item details will be displayed");
    }


    public LiveData<String> getText() {
        return mText;
    }
}
