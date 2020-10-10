package no.nilsjarh.ntnu.fantj2.ui.item;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import no.nilsjarh.ntnu.fantj2.MarketplaceApi;
import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.RestService;
import no.nilsjarh.ntnu.fantj2.model.Attachment;
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
        final ImageView itemPoster = root.findViewById(R.id.item_image);
        final ConstraintLayout purchaseContainer = root.findViewById(R.id.purchase_container);
        final Button purchaseButton = root.findViewById(R.id.button_purchase);
        final int itemPosterWidth = 480;

        purchaseContainer.setVisibility(View.GONE);


        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemViewModel.purchaseActiveItem();
            }
        });

        itemViewModel.getActiveItemLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(@Nullable Item i) {
                Log.d("VIEW-INFO", "Updating UI text elements for item " + i.getId());
                i = itemViewModel.getActiveItemLiveData().getValue();
                Log.d("ITEM-INFO", "Item "+ i.getId() +" " + i.getItemTitle() + "changed.");
                itemTitle.setText(i.getItemTitle());
                itemDescr.setText(i.getItemDescription());
                itemPrice.setText(i.getItemPrice().toString());

                User seller = i.getItemSeller();
                String SellerNameTxt = seller.getFullName().equals("") ? getString(R.string.seller_name_placeholder) : seller.getFullName();
                sellerName.setText(SellerNameTxt);
                // FIXME: sellerMail.setText(seller.getUserEmail());
                sellerMail.setText(seller.getUserEmail());

                if (i.getAttachmentList() != null) {
                    if (i.getAttachmentList().size() > 0) {
                        Attachment attachment = i.getAttachmentList().get(0);
                        Picasso.get().load(
                                RestService.DOMAIN + MarketplaceApi.PREFIX + "image/"
                                        + attachment.getId()+"?"+ itemPosterWidth).into(itemPoster);
                    }
                }

                if (i.getItemPurchase() == null) {
                    if (itemViewModel.getLoggedInState()) {
                        purchaseContainer.setVisibility(View.VISIBLE);
                    } else {
                        purchaseContainer.setVisibility(View.GONE);
                    }
                 } else {
                    itemPrice.setText(getString(R.string.sold_text));
                    sellerMail.setVisibility(View.GONE);
                }
            }
        });

        return root;
    }

}
