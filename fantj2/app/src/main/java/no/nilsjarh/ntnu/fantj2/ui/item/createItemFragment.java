package no.nilsjarh.ntnu.fantj2.ui.item;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import no.nilsjarh.ntnu.fantj2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link createItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class createItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final Boolean ARG_AUTHENTICATED = false;

    // TODO: Rename and change types of parameters
    private Boolean mAuthenticated;
    private NavController navController;

    public createItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RecieptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static createItemFragment newInstance(Boolean param1) {
        createItemFragment fragment = new createItemFragment();
        Bundle args = new Bundle();
        args.putBoolean(String.valueOf(ARG_AUTHENTICATED), param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.navController = NavHostFragment.findNavController(getParentFragment());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAuthenticated = getArguments().getBoolean(String.valueOf(ARG_AUTHENTICATED));
        }
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

        return root;
    }
}