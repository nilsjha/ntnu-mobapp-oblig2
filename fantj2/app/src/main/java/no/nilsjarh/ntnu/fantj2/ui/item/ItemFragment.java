package no.nilsjarh.ntnu.fantj2.ui.item;

import android.graphics.Color;
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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
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



        /**
         *  Listen for clicks on purchase button, then do purchase and display result in a snackbar
         */
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar status = Snackbar.make(view,R.string.message_purchase_wait, Snackbar.LENGTH_INDEFINITE);
                Snackbar.make(view, R.string.message_purchase_wait, Snackbar.LENGTH_INDEFINITE).setAction("Purchase", null).show();;
                itemViewModel.purchaseActiveItem(success -> {
                    status.setDuration(BaseTransientBottomBar.LENGTH_LONG).show();
                    if (success.equals(0)) {
                        purchaseButton.setEnabled(false);
                        status.setText(R.string.message_purchase_success).setTextColor(Color.GREEN);
                    } else if (success.equals(1)){
                        status.setText(R.string.message_purchase_failed_invaliditem).setTextColor(Color.RED);
                    } else if (success.equals(2)) {
                        status.setText(R.string.message_purchase_failed_purchasedbefore).setTextColor(Color.RED);
                    }
                });
            }
        });

        itemViewModel.getActiveItemLiveData().observe(getViewLifecycleOwner(), new Observer<Item>() {
            @Override
            public void onChanged(@Nullable Item i) {
                Log.d("VIEW-INFO", "Updating UI text elements for item " + i.getId());
                i = itemViewModel.getActiveItemLiveData().getValue();
                Log.d("ITEM-INFO", "Item " + i.getId() + " " + i.getItemTitle() + "changed.");
                itemTitle.setText(i.getItemTitle());
                itemDescr.setText(i.getItemDescription());
                itemPrice.setText(i.getItemPrice().toString());
                purchaseContainer.setVisibility(View.GONE);

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
                                        + attachment.getId() + "?" + itemPosterWidth).into(itemPoster);
                    }
                }

                if (itemViewModel.getLoggedInState()) {
                    if (i.getItemPurchase() == null) {
                        purchaseContainer.setVisibility(View.VISIBLE);
                    }
                } else {
                    sellerMail.setText(R.string.seller_mail_hidden_placeholder);
                  }
                if (i.getItemPurchase() != null) {
                itemPrice.setText(getString(R.string.sold_text));
                }
            }
        });

        return root;
    }

}
