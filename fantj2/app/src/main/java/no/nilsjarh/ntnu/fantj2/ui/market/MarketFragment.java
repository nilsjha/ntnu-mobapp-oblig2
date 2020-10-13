package no.nilsjarh.ntnu.fantj2.ui.market;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.model.Item;
import no.nilsjarh.ntnu.fantj2.ui.item.ItemViewModel;
import no.nilsjarh.ntnu.fantj2.ui.item.ItemViewModelFactory;

public class MarketFragment extends Fragment {

    private MarketViewModel marketViewModel;
    private MarketViewAdapter marketViewAdapter;
    private ItemViewModel elementInViewModel;
    private List<Item> itemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        elementInViewModel = new ViewModelProvider(requireActivity(), new ItemViewModelFactory()).get(ItemViewModel.class);
        NavController navController = NavHostFragment.findNavController(getParentFragment());

        marketViewModel = new ViewModelProvider(this, new MarketViewModelFactory()).get(MarketViewModel.class);
        marketViewAdapter = new MarketViewAdapter(new ArrayList<Item>(), new MarketViewAdapter.RecyclerViewClickListener() {


            @Override
            public void onClickItem(Item i) {
                //Toast.makeText(getContext(), "Loading item " + i.getItemTitle(), Toast.LENGTH_SHORT).show();
                elementInViewModel.setActiveItem(i);
                //elementInViewModel.downloadItemById(i.getId());
                navController.navigate(R.id.action_nav_market_to_nav_item);


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

        final FloatingActionButton createItemfab = root.findViewById(R.id.fab_create_item);
        if (marketViewModel.isAuthenticated()) {
            createItemfab.setVisibility(View.VISIBLE);
            createItemfab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.action_nav_market_to_createItemFragment);
                }
            });
        } else {
            createItemfab.setVisibility(View.GONE);
        }

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview_market);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(marketViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        marketViewModel.loadItemList(listResultOk->{
            Log.d("MARKET", "Result of load list = " + listResultOk.toString());
            Snackbar listResultMsg = Snackbar.make(root,"Unable to load items", Snackbar.LENGTH_LONG);
            listResultMsg.setTextColor(Color.YELLOW);
            switch (listResultOk) {
                case 1: listResultMsg.show();break;
                case 2: listResultMsg.setText("Network error, please check your connection!").show();break;
                case 3: listResultMsg.setTextColor(Color.RED).setText("Application error. contact publisher").show();break;
                default: listResultMsg.dismiss(); break;
            }
        });

        marketViewModel.getItemListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> itemList) {
                marketViewAdapter.setItems(itemList);
            }
        });

        return root;
    }
}