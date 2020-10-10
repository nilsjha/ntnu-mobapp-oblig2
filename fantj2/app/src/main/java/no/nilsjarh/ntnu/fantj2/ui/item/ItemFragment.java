package no.nilsjarh.ntnu.fantj2.ui.item;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.model.Item;
import no.nilsjarh.ntnu.fantj2.model.User;

public class ItemFragment extends Fragment {

    private ItemViewModel itemViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemViewModel =
                new ViewModelProvider(requireActivity(), new ItemViewModelFactory()).get(ItemViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item, container, false);
        final TextView itemTitle = root.findViewById(R.id.item_title);
        final TextView itemDescr = root.findViewById(R.id.item_description);
        final TextView itemPrice = root.findViewById(R.id.item_price);
        final TextView sellerName = root.findViewById(R.id.seller_name);
        final TextView sellerMail = root.findViewById(R.id.seller_mail);


        itemViewModel.getActiveItemLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(@Nullable Item i) {
                i = itemViewModel.getActiveItemLiveData().getValue();
                Log.d("VIEW-INFO", "Updating UI text elements for item " + i.getId());
                i = itemViewModel.getActiveItemLiveData().getValue();
                Log.d("ITEM-INFO", "Item "+ i.getId() +" " + i.getItemTitle() + "changed.");
                itemTitle.setText(i.getItemTitle());
                itemPrice.setText(i.getItemPrice().toString() + " " + getString(R.string.currency_suffix));
                itemDescr.setText(i.getItemDescription());

                User seller = i.getItemSeller();
                String SellerNameTxt = seller.getFullName().isEmpty() ? "Private seller" : seller.getFullName();
                sellerName.setText(SellerNameTxt);
                sellerMail.setText(seller.getUserEmail());
            }
        });

        return root;
    }

}
