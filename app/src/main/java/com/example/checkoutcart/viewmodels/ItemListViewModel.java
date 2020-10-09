package com.example.checkoutcart.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.checkoutcart.models.Product;
import com.example.checkoutcart.models.ProductInCart;
import com.example.checkoutcart.repos.FinalCheckoutRepo;
import com.example.checkoutcart.repos.ItemListRepo;

import java.util.List;

public class ItemListViewModel extends ViewModel {

    ItemListRepo itemListRepo = new ItemListRepo();
    FinalCheckoutRepo finalCheckoutRepo = new FinalCheckoutRepo();

    public LiveData<List<Product>> getProducts() {
        return itemListRepo.getProducts();
    }

    public LiveData<List<ProductInCart>> getCart(){return finalCheckoutRepo.getCart();}

    public boolean addToCart(Product product, int quantity){
        return finalCheckoutRepo.addToCart(product, quantity);
    }

    public boolean addCustomItem(String productName, double productPrice, int quantity){
        return finalCheckoutRepo.addCustomItem(productName, productPrice, quantity);

    }

    public void removeItemFromCart(ProductInCart prod){
        finalCheckoutRepo.removeItemFromCart(prod);
    }

    public void updateQuantity(ProductInCart prod, int quantity){
        finalCheckoutRepo.updateQuantity(prod, quantity);
    }

    public void updateDiscount(ProductInCart prod, int discount){
        finalCheckoutRepo.updateDiscount(prod, discount);
    }

    public LiveData<Double> getTotalPrice(){
        return finalCheckoutRepo.getTotalPrice();
    }

    public void filterList(String category){
        itemListRepo.filterList(category);
    }

    public void emptyList(){
        finalCheckoutRepo.emptyList();
    }
}
