package no.nilsjarh.ntnu.fantj2.ui.market;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class MarketViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    List<Item> itemList;

    public MarketViewAdapter(Activity context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View rootview = LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        return new RecyclerViewViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
