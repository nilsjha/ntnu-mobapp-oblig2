package no.nilsjarh.ntnu.fantj2.ui.market;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import no.nilsjarh.ntnu.fantj2.ItemRepository;
import no.nilsjarh.ntnu.fantj2.LoginDataSource;
import no.nilsjarh.ntnu.fantj2.LoginRepository;
import no.nilsjarh.ntnu.fantj2.ui.market.MarketViewModel;

public class MarketViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MarketViewModel.class)) {
            return (T) new MarketViewModel(ItemRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}