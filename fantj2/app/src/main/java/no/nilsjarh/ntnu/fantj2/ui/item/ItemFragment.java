package no.nilsjarh.ntnu.fantj2.ui.item;

import android.os.Bundle;
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

public class ItemFragment extends Fragment {

    private ItemViewModel itemViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemViewModel =
                new ViewModelProvider(this).get(ItemViewModel.class);
        View root = inflater.inflate(R.layout.fragment_item, container, false);
        final TextView textView = root.findViewById(R.id.item_title);

        itemViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

}
