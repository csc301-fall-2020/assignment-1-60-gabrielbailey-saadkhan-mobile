package com.example.checkoutcart.repos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.checkoutcart.models.Product;
import com.example.checkoutcart.models.ProductInCart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FinalCheckoutRepo {
    String NO_IMAGE_AVAILABLE = "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/No-image-available.png?alt=media&token=582117fe-3563-4c29-9058-de866b20a23a";
    protected MutableLiveData<List<ProductInCart>> mutableCart = new MutableLiveData<>();
    protected MutableLiveData<Double> mutableTotalPrice = new MutableLiveData<>();

    public LiveData<List<ProductInCart>> getCart(){
        if (mutableCart.getValue() == null){
            initCart();
        }
        return mutableCart;
    }

    public void initCart(){
        mutableCart.setValue(new ArrayList<ProductInCart>());
        calculateCartTotal();

    }

    public boolean addToCart(Product product, int quantity){
        if (mutableCart.getValue() == null){
            initCart();
        }
        List<ProductInCart> productsInCarts = new ArrayList<>(mutableCart.getValue());
        for (int i = 0; i < productsInCarts.size(); i++){
            if (productsInCarts.get(i).getProduct().equals(product)){
                productsInCarts.get(i).setQuantity(productsInCarts.get(i).getQuantity() + quantity);
                mutableCart.setValue(productsInCarts);
                return true;
            }
        }
        ProductInCart productInCart = new ProductInCart(product, quantity, 0);
        productsInCarts.add(productInCart);
        mutableCart.setValue(productsInCarts);
        calculateCartTotal();
        return true;
    }

    public boolean addCustomItem(String productName, double productPrice, int quantity){
        Product prod = new Product(UUID.randomUUID().toString(), productName, productPrice, NO_IMAGE_AVAILABLE, "None");
        if (mutableCart.getValue() == null){
            initCart();
        }
        List<ProductInCart> productsInCarts = new ArrayList<>(mutableCart.getValue());
        ProductInCart productInCart = new ProductInCart(prod, quantity, 0);
        productsInCarts.add(productInCart);
        mutableCart.setValue(productsInCarts);
        calculateCartTotal();
        return true;

    }

    public void removeItemFromCart(ProductInCart prod){
        if (mutableCart == null){
            return;
        }
        List<ProductInCart> productsInCarts = new ArrayList<>(mutableCart.getValue());
        productsInCarts.remove(prod);
        mutableCart.setValue(productsInCarts);
        calculateCartTotal();
    }

    public void updateQuantity(ProductInCart prod, int quantity){
        if (mutableCart == null){
            return;
        }
        if (quantity == 0){
            removeItemFromCart(prod);
            return;
        }
        List<ProductInCart> productsInCarts = new ArrayList<>(mutableCart.getValue());
        ProductInCart newProduct = new ProductInCart(prod.getProduct(), quantity, prod.getDiscount());
        productsInCarts.set(productsInCarts.indexOf(prod), newProduct);
        calculateCartTotal();
        mutableCart.setValue(productsInCarts);
        calculateCartTotal();

    }

    public void updateDiscount(ProductInCart prod, int discount){
        if (mutableCart == null){
            return;
        }
        List<ProductInCart> productsInCarts = new ArrayList<>(mutableCart.getValue());
        ProductInCart newProduct = new ProductInCart(prod.getProduct(), prod.getQuantity(), discount);
        productsInCarts.set(productsInCarts.indexOf(prod), newProduct);
        mutableCart.setValue(productsInCarts);
        calculateCartTotal();

    }

    private void calculateCartTotal(){
        if (mutableCart == null){
            return;
        }
        double total = 0;
        List<ProductInCart> productsInCarts = new ArrayList<>(mutableCart.getValue());
        for (int i = 0; i < productsInCarts.size(); i++){
            total += (productsInCarts.get(i).getProduct().getPrice() * productsInCarts.get(i).getQuantity() * ((100 - productsInCarts.get(i).getDiscount()) / 100));
        }
        mutableTotalPrice.setValue(total);

    }

    public LiveData<Double> getTotalPrice(){
        if (mutableTotalPrice.getValue() == null){
            mutableTotalPrice.setValue(0.0);
            return mutableTotalPrice;
        }
        return mutableTotalPrice;
    }

    public void emptyList(){
        initCart();
    }
}
