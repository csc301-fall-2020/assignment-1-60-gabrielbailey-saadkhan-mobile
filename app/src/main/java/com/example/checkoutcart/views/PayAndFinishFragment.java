package com.example.checkoutcart.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.checkoutcart.R;
import com.example.checkoutcart.databinding.FragmentItemsListBindingImpl;
import com.example.checkoutcart.databinding.FragmentPayAndFinishBinding;


public class PayAndFinishFragment extends Fragment {
    NavController navController;
    FragmentPayAndFinishBinding fragmentPayAndFinishBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPayAndFinishBinding = FragmentPayAndFinishBinding.inflate(inflater, container, false);
        return fragmentPayAndFinishBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        fragmentPayAndFinishBinding.continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.itemsListFragment);
            }
        });

    }
}