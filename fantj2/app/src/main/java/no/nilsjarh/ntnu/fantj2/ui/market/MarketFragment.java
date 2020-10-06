package no.nilsjarh.ntnu.fantj2.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class MarketFragment extends Fragment {

    private MarketViewModel marketViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        marketViewModel =
                new ViewModelProvider(this).get(MarketViewModel.class);
        View root = inflater.inflate(R.layout.fragment_market, container, false);
        final TextView textView = root.findViewById(R.id.text_market);
        marketViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview_market);
        marketViewModel.getItems().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {

            }
        });

        return root;
    }
}