package no.nilsjarh.ntnu.fantj2.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import no.nilsjarh.ntnu.fantj2.MainActivity;
import no.nilsjarh.ntnu.fantj2.R;
import no.nilsjarh.ntnu.fantj2.ui.item.ItemViewModel;
import no.nilsjarh.ntnu.fantj2.ui.item.ItemViewModelFactory;
import no.nilsjarh.ntnu.fantj2.ui.login.LoginViewModel;
import no.nilsjarh.ntnu.fantj2.ui.login.LoginViewModelFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LoginViewModel loginViewModel;
    private ItemViewModel itemViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        loginViewModel = new ViewModelProvider(requireActivity(), new LoginViewModelFactory())
                .get(LoginViewModel.class);
        itemViewModel = new ViewModelProvider(requireActivity(), new ItemViewModelFactory()).get(ItemViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final Button create_item = root.findViewById(R.id.home_button_create_item);
        final Button login_user = root.findViewById(R.id.home_button_login_user);
        final Button goto_market = root.findViewById(R.id.home_button_goto_market);

        NavController navController = NavHostFragment.findNavController(getParentFragment());

        //FIXME: Implement create user

        Boolean loggedOn = itemViewModel.getLoggedInState();

        if (loggedOn) {
            login_user.setText(R.string.main_button_logout);
        } else {
            create_item.setVisibility(View.GONE);
        }


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedOn) {
                    loginViewModel.logout();

                    Activity main = (MainActivity)getActivity();
                    TextView navMainText = main.findViewById(R.id.nav_user_name);
                    TextView navUnderText = main.findViewById(R.id.nav_user_mail);
                    navMainText.setText(R.string.nav_header_title);
                    navUnderText.setText(R.string.nav_header_subtitle);
                    navController.navigate(R.id.nav_home);

                } else {
                    navController.navigate(R.id.action_nav_home_to_nav_login);
                }
            }
        });

        goto_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_home_to_nav_market);
            }
        });

        create_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_home_to_nav_create);
            }
        });
        return root;
    }
}