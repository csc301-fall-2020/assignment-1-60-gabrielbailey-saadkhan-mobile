package com.example.checkoutcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkoutcart.databinding.OneItemBinding;
import com.example.checkoutcart.models.Product;

public class ItemsListAdapter extends ListAdapter<Product, ItemsListAdapter.ItemsListViewHolder> {

    ItemsInterface itemsInterface;
    public ItemsListAdapter(ItemsInterface itemsInterface) {
        super(Product.itemCallback);
        this.itemsInterface = itemsInterface;
    }

    @NonNull
    @Override
    public ItemsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OneItemBinding oneItemBinding = OneItemBinding.inflate(layoutInflater, parent, false);
        return new ItemsListViewHolder(oneItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsListViewHolder holder, int position) {
        Product product = getItem(position);
        holder.oneItemBinding.setProduct(product);
    }

    class ItemsListViewHolder extends RecyclerView.ViewHolder{

        OneItemBinding oneItemBinding;

        public ItemsListViewHolder(final OneItemBinding oneItemBinding){
            super(oneItemBinding.getRoot());
            this.oneItemBinding = oneItemBinding;
            this.oneItemBinding.addCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String quantity = oneItemBinding.productQuantity.getText().toString();
                    int pro_quan = 0;
                    if (quantity.trim().equals("")){
                        pro_quan = 1;
                    }
                    else{
                        pro_quan = Integer.parseInt(quantity);
                    }
                    itemsInterface.addItem(oneItemBinding.getProduct(), pro_quan);
                }
            });
        }
    }

    public interface ItemsInterface{
        void addItem(Product product, int quantity);
    }
}
