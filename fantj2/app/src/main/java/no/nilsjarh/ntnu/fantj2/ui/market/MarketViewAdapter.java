package no.nilsjarh.ntnu.fantj2.ui.market;

import android.view.LayoutInflater;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.model.Item;

public class MarketViewAdapter extends RecyclerView.Adapter<MarketViewAdapter.ViewHolder> {

    private List<Item> itemList = new ArrayList<>();

    @NonNull
    @Override
    public MarketViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowElement = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent,false);
        return new ViewHolder(rowElement);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewAdapter.ViewHolder holder, int position) {
        Item i = itemList.get(position);
        holder.title.setText(i.getTitle());
        holder.price.setText(i.getPrice().toString() + " kr");
    }
    public void setItems(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            price = itemView.findViewById(R.id.seller_text);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Clicked element" + getLayoutPosition() + " - " + this.title.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
