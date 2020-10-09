package com.example.checkoutcart.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.checkoutcart.R;
import com.example.checkoutcart.adapters.CartListAdapter;
import com.example.checkoutcart.databinding.FragmentFinalCartBinding;
import com.example.checkoutcart.models.ProductInCart;
import com.example.checkoutcart.viewmodels.ItemListViewModel;

import java.util.List;


public class FinalCartFragment extends Fragment implements CartListAdapter.CartInterface {
    ItemListViewModel itemListViewModel;
    FragmentFinalCartBinding finalCartBinding;
    NavController navController;
    public FinalCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        finalCartBinding = FragmentFinalCartBinding.inflate(inflater, container, false);
        return finalCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final CartListAdapter cartListAdapter = new CartListAdapter(this);
        navController = Navigation.findNavController(view);
        finalCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        finalCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        itemListViewModel = new ViewModelProvider(requireActivity()).get(ItemListViewModel.class);
        itemListViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<ProductInCart>>() {
            @Override
            public void onChanged(List<ProductInCart> productInCarts) {
                cartListAdapter.submitList(productInCarts);
                finalCartBinding.placeOrderButton.setEnabled(productInCarts.size() > 0);

            }
        });
        itemListViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                finalCartBinding.orderTotalTextView.setText("Total:  $ " + aDouble.toString());
            }
        });
        finalCartBinding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.payAndFinishFragment);
            }
        });
    }

    public static DiffUtil.ItemCallback<ProductInCart> itemCallback = new DiffUtil.ItemCallback<ProductInCart>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductInCart oldItem, @NonNull ProductInCart newItem) {
            return oldItem.getProduct().equals(newItem.getProduct());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductInCart oldItem, @NonNull ProductInCart newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public void deleteItem(ProductInCart prod) {
        itemListViewModel.removeItemFromCart(prod);
    }

    @Override
    public void updateQuantity(ProductInCart prod, int quantity) {
        itemListViewModel.updateQuantity(prod, quantity);
    }

    @Override
    public void updateDiscount(final ProductInCart prod){
        final String[] m_Text = {""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Title");

// Set up the input
        final EditText discount = new EditText(getContext());
        discount.setHint("Discount to apply in percentage");
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        discount.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(discount);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text[0] = discount.getText().toString();
                itemListViewModel.updateDiscount(prod, Integer.parseInt(m_Text[0]));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}