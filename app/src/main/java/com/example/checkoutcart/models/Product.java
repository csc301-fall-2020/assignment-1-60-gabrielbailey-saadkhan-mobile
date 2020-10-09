package com.example.checkoutcart.models;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class Product {
    private String unique_identifier;
    public String name;
    private double price;
    private String imageURL;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Product(String unique_identifier, String name, double price, String imageURL, String category) {
        this.unique_identifier = unique_identifier;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "unique_identifier='" + unique_identifier + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    public String getUnique_identifier() {
        return unique_identifier;
    }

    public void setUnique_identifier(String unique_identifier) {
        this.unique_identifier = unique_identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 &&
                getUnique_identifier().equals(product.getUnique_identifier()) &&
                getName().equals(product.getName()) &&
                getImageURL().equals(product.getImageURL());
    }

    @BindingAdapter("android:productImage")
    public static void loadImage(ImageView imageView, String imageURL){
        Glide.with(imageView).load(imageURL).fitCenter().into(imageView);
        Log.d("rundgd", "efsfs");
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getUnique_identifier().equals(newItem.getUnique_identifier());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };
}
