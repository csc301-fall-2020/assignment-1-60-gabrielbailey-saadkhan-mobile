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
import androidx.recyclerview.widget.DividerItemDecoration;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.checkoutcart.R;
import com.example.checkoutcart.adapters.ItemsListAdapter;
import com.example.checkoutcart.databinding.FragmentItemsListBinding;
import com.example.checkoutcart.databinding.OneItemBinding;
import com.example.checkoutcart.databinding.OneItemBindingImpl;
import com.example.checkoutcart.models.Product;
import com.example.checkoutcart.viewmodels.ItemListViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;


public class ItemsListFragment extends Fragment implements ItemsListAdapter.ItemsInterface {
    String [] categories = {"All", "Clothing", "Electronics"};
    List<String> cats = new ArrayList<>();
    FragmentItemsListBinding fragmentItemsListBinding;
    private ItemsListAdapter itemsListAdapter;
    private ItemListViewModel itemListViewModel;
    private NavController navController;
    public ItemsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentItemsListBinding = FragmentItemsListBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return fragmentItemsListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsListAdapter = new ItemsListAdapter(this);
        fragmentItemsListBinding.itemsListRecyclerView.setAdapter(itemsListAdapter);
        fragmentItemsListBinding.itemsListRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentItemsListBinding.itemsListRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        itemListViewModel = new ViewModelProvider(requireActivity()).get(ItemListViewModel.class);
        itemListViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                itemsListAdapter.submitList(products);
            }
        });
        Spinner spin = (Spinner) fragmentItemsListBinding.categoriesList;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemListViewModel.filterList((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        navController = Navigation.findNavController(view);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final String[] m_Text = {"", "", ""};
        if (item.getItemId() == R.id.custom_product){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            builder.setTitle("Title");

// Set up the input
            final EditText productName = new EditText(getContext());
            final EditText price = new EditText(getContext());
            final EditText quantity = new EditText(getContext());
            productName.setHint("Name of Product");
            price.setHint("Price of Product");
            quantity.setHint("Quantity of Product");
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            productName.setInputType(InputType.TYPE_CLASS_TEXT);
            price.setInputType(InputType.TYPE_CLASS_NUMBER);
            quantity.setInputType(InputType.TYPE_CLASS_NUMBER);
            layout.addView(productName);
            layout.addView(price);
            layout.addView(quantity);
            builder.setView(layout);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text[0] = productName.getText().toString();
                    m_Text[1] = price.getText().toString();
                    m_Text[2] = quantity.getText().toString();
                    itemListViewModel.addCustomItem(m_Text[0], Double.parseDouble(m_Text[1]), Integer.parseInt(m_Text[2]));
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addItem(Product product, int quantity) {
        Log.d("Product", product.toString());
        boolean isAdded = itemListViewModel.addToCart(product, quantity);
        if (isAdded) {
            Snackbar.make(requireView(), product.getName() + " added to cart.", Snackbar.LENGTH_LONG)
                    .setAction("Checkout", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.finalCartFragment);
                        }
                    })
                    .show();
        }
    }
}