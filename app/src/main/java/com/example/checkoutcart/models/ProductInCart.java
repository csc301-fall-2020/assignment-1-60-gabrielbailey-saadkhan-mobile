package com.example.checkoutcart.models;

public class ProductInCart {

    private Product product;
    private int quantity;
    private float discount;

    public ProductInCart(Product product, int quantity, float discount) {
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInCart that = (ProductInCart) o;
        return getQuantity() == that.getQuantity() &&
                Double.compare(that.getDiscount(), getDiscount()) == 0 &&
                getProduct().equals(that.getProduct());
    }

    @Override
    public String toString() {
        return "ProductInCart{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", discount=" + discount +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
