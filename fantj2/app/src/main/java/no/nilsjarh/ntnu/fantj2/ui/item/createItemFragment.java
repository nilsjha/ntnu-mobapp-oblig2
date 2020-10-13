package no.nilsjarh.ntnu.fantj2.ui.item;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.Result;
import no.nilsjarh.ntnu.fantj2.model.Item;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class createItemFragment extends Fragment {

    private NavController navController;
    private ItemViewModel itemViewModel;

    public createItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.navController = NavHostFragment.findNavController(getParentFragment());
        this.itemViewModel =
                new ViewModelProvider(requireActivity(), new ItemViewModelFactory()).get(ItemViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_create_item, container, false);

        final Button cancelBtn = root.findViewById(R.id.item_create_button_cancel);
        final Button acceptBtn = root.findViewById(R.id.item_create_button_ok);
        final EditText title = root.findViewById(R.id.item_create_title);
        final EditText description  = root.findViewById(R.id.item_create_description);
        final EditText price = root.findViewById(R.id.item_create_price);



        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar status = Snackbar.make(root,"",Snackbar.LENGTH_LONG);
                String titleString = title.getText().toString();
                String priceString = price.getText().toString();
                if (validateInput(titleString,priceString)) {
                    // VALID INPUT
                    status.setText("Please hold on while creating the item....").setDuration(Snackbar.LENGTH_INDEFINITE).show();

                    itemViewModel.createNewItem(title.getText().toString(), new BigDecimal(price.getText().toString()), createdItem -> {
                        status.setDuration(BaseTransientBottomBar.LENGTH_LONG);
                        if (createdItem instanceof Result.Success) {
                            // Item created, view model updated with new item
                            status.setDuration(BaseTransientBottomBar.LENGTH_SHORT);
                            status.setTextColor(Color.GREEN).setText("Success").show();
                            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                            navController.navigate(R.id.action_nav_create_to_nav_item);
                        } else {
                            Exception error = ((Result.Error) createdItem).getError();
                            if (error == null) {
                                // RETURNED NULL BECUASE SERVER RJECTED OUR REQUEST
                                status.setTextColor(Color.RED).setText("Ensure that the item got a title and price").show();
                            } else {
                                // CLIENT SIDE ERROR
                                if (error instanceof IOException) {
                                    status.setTextColor(Color.YELLOW).setText("Network error, please chack the conntection").show();
                                } else {
                                    status.setTextColor(Color.RED).setText("Unable to create item, please restart the app").show();
                                }

                            }
                        }
                    });
                } else {
                    //INVALID INPUT
                    status.setTextColor(Color.RED).setText("Title or price is invalid");
                    status.setDuration(BaseTransientBottomBar.LENGTH_LONG).show();
                }

            }
        });

        return root;
    }

    /**
     * Validate data input from user in the app
     * @param title string title, checked if empty
     * @param price string price, checked if empty and valid number
     * @return true if all checks are valid and may be accepted by server
     */
    private Boolean validateInput( String title, String price ) {
        if ((title.isEmpty()) || (price.isEmpty())) {
            return false;
        } else {
            try {
                BigDecimal parseTest = new BigDecimal(price);
                return true;
            } catch (NumberFormatException ne) {
               return false;
            }
        }
    }

}