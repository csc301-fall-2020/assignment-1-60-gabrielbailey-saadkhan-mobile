package com.example.checkoutcart.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkoutcart.R;
import com.example.checkoutcart.databinding.OneItemCartBinding;
import com.example.checkoutcart.models.ProductInCart;
import com.example.checkoutcart.views.FinalCartFragment;

public class CartListAdapter extends ListAdapter<ProductInCart, CartListAdapter.cartVH> {

    private CartInterface cartInterface;
    public CartListAdapter(CartInterface cartInterface) {
        super(FinalCartFragment.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public cartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OneItemCartBinding oneItemCartBinding = OneItemCartBinding.inflate(layoutInflater, parent, false);
        return new cartVH(oneItemCartBinding);
    }

    public void onBindViewHolder(@NonNull cartVH holder, int position) {
        holder.oneItemCartBinding.setCartItem(getItem(position));
        holder.oneItemCartBinding.executePendingBindings();
    }

    class cartVH extends RecyclerView.ViewHolder{
        OneItemCartBinding oneItemCartBinding;
        public cartVH(final OneItemCartBinding oneItemCartBinding){
            super(oneItemCartBinding.getRoot());
            this.oneItemCartBinding = oneItemCartBinding;
            this.oneItemCartBinding.deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteItem(getItem(getAdapterPosition()));
                }
            });
            this.oneItemCartBinding.updateQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String quantity = oneItemCartBinding.quantityView.getText().toString();
                    if (!quantity.equals("")){
                        cartInterface.updateQuantity(getItem(getAdapterPosition()), Integer.parseInt(quantity));

                    }
                }
            });
            this.oneItemCartBinding.discount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.updateDiscount(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface CartInterface{
        void deleteItem(ProductInCart prod);
        void updateQuantity(ProductInCart prod, int quantity);
        void updateDiscount(ProductInCart prod);
    }


}
