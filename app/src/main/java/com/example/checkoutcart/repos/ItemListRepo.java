package com.example.checkoutcart.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.checkoutcart.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemListRepo {

    private MutableLiveData<List<Product>> mutableProductList;
    private List<Product> tempStorage;
    public LiveData<List<Product>> getProducts(){
        if (mutableProductList == null){
            mutableProductList = new MutableLiveData<>();
            loadProducts();
        }
        return mutableProductList;
    }

    private void loadProducts(){
        List<Product> productList = new ArrayList<>();
        Log.d("running2", "running2");
        productList.add(new Product(UUID.randomUUID().toString(), "TV", 500.30, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/TV.jpg?alt=media&token=07f6d342-2592-457c-9aeb-731cb5fa224a", "Electronics"));
        productList.add(new Product(UUID.randomUUID().toString(), "PS5", 629.00, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/PS5.jpeg?alt=media&token=7e5709b3-62ae-4b68-9aa4-8e33f6007e21", "Electronics"));
        productList.add(new Product(UUID.randomUUID().toString(), "Laptop", 345.60, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/Laptop.jpg?alt=media&token=a5d8544c-cffd-4ea9-bc72-457965e7192a", "Electronics"));
        productList.add(new Product(UUID.randomUUID().toString(), "Iphone", 1200.00, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/Iphone.jpg?alt=media&token=8544cbb1-a24e-46d3-9f3a-756aaf564a00", "Electronics"));
        productList.add(new Product(UUID.randomUUID().toString(), "Monitor", 600.00, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/monitor.jpg?alt=media&token=8430816b-df76-41c9-bc03-d1fa0756cef0", "Electronics"));
        productList.add(new Product(UUID.randomUUID().toString(), "Jeans", 15.30, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/jeans.jpeg?alt=media&token=fd78e512-7ffb-4d74-8e06-ef5e3669a18b", "Clothing"));
        productList.add(new Product(UUID.randomUUID().toString(), "T-Shirt", 10.00, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/Tshirt.jpeg?alt=media&token=6fc6c3ff-9079-46ba-be98-88b18faeec8e", "Clothing"));
        productList.add(new Product(UUID.randomUUID().toString(), "Jacket", 45.90, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/Jacket.jpg?alt=media&token=4289b00c-fe8a-4a65-a1f8-9f2aff8d0651", "Clothing"));
        productList.add(new Product(UUID.randomUUID().toString(), "Hoodie", 35.60, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/Hoodie.jpg?alt=media&token=192105e7-7437-4260-a5cf-4d961fdb21c8", "Clothing"));
        productList.add(new Product(UUID.randomUUID().toString(), "Cap", 5.50, "https://firebasestorage.googleapis.com/v0/b/csc309-a1.appspot.com/o/Cap.jpg?alt=media&token=173d5de6-8be4-4b11-9af8-c00c816ac88d", "Clothing"));
        mutableProductList.setValue(productList);
        tempStorage = new ArrayList<>(mutableProductList.getValue());

    }

    public void filterList(String category){
        if (mutableProductList == null){
            return;
        }
        List<Product> productList = new ArrayList<>();
        if (category == "All"){
            mutableProductList.setValue(tempStorage);
            return;
        }
        for (int i = 0; i < tempStorage.size(); i++){
            if (tempStorage.get(i).getCategory().equals(category)){
                productList.add(tempStorage.get(i));
            }
        }
        mutableProductList.setValue(productList);
    }
}
