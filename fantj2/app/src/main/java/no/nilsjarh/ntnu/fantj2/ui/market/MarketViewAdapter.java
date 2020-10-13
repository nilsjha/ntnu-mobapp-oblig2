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

    private List<Item> itemList;
    private final RecyclerViewClickListener listener;

    public MarketViewAdapter(List<Item> items, RecyclerViewClickListener listener) {
        this.itemList = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MarketViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowElement = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent,false);
        return new ViewHolder(rowElement);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewAdapter.ViewHolder holder, int position) {
        Item i = itemList.get(position);
        holder.title.setText(i.getItemTitle());
        holder.price.setText(i.getItemPrice().toString() + " kr");
        holder.bind(i, listener);

        if (i.getItemPurchase() != null) {
            holder.price.setText(R.string.sold_text);
        }
    }


    public void setItems(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (itemList != null) {
           return itemList.size();
        } else {
            return 0;
        }
    }

    public interface RecyclerViewClickListener {
        void onClickItem(Item i);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            price = itemView.findViewById(R.id.item_price);
        }

        public void bind(final Item item, final RecyclerViewClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickItem(item);

                }
            });

        }
    }
}
