package no.nilsjarh.ntnu.fantj2.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class MarketFragment extends Fragment {

    private MarketViewModel marketViewModel;
    private MarketViewAdapter marketViewAdapter;
    private List<Item> itemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        marketViewModel =
                new ViewModelProvider(this).get(MarketViewModel.class);
        marketViewAdapter = new MarketViewAdapter(new ArrayList<Item>(), new MarketViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClickItem(Item i) {
                Toast.makeText(getContext(), "Clicked element" + i.getItemTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        View root = inflater.inflate(R.layout.fragment_market, container, false);
        final TextView textView = root.findViewById(R.id.text_market);
        marketViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {


            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview_market);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(marketViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        marketViewModel.getItems();

        marketViewModel.getItemListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> itemList) {
                marketViewAdapter.setItems(itemList);
            }
        });

        return root;
    }
}