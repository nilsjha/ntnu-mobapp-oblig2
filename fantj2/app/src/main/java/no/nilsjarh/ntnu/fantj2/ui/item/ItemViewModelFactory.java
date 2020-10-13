package no.nilsjarh.ntnu.fantj2.ui.item;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import no.nilsjarh.ntnu.fantj2.ItemRepository;
import no.nilsjarh.ntnu.fantj2.LoginDataSource;
import no.nilsjarh.ntnu.fantj2.LoginRepository;
import no.nilsjarh.ntnu.fantj2.ui.item.ItemViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ItemViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ItemViewModel.class)) {
            return (T) new ItemViewModel(
                    ItemRepository.getInstance(),
                    LoginRepository.getInstance(new LoginDataSource())
            );
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}