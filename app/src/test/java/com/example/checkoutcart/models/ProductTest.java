package com.example.checkoutcart.models;
import com.example.checkoutcart.models.Product;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
    Product product = new Product("12345", "Laptop", 1000, "", "Electronics");
    @Test
    public void testGetCategory() {
        String category = product.getCategory();
        assertEquals("Electronics", category);
    }

    @Test
    public void testSetCategory() {
        product.setCategory("NOTELECTRONICS");
        String category = product.getCategory();
        assertEquals("NOTELECTRONICS", category);

    }

    @Test
    public void testGetUnique_identifier() {
        String UUID = product.getUnique_identifier();
        assertEquals("12345", UUID);
    }

    @Test
    public void testSetUnique_identifier() {
        product.setUnique_identifier("54321");
        String UUID = product.getUnique_identifier();
        assertEquals("54321", UUID);

    }

    @Test
    public void testGetName() {
        String name = product.getName();
        assertEquals("Laptop", name);
    }

    @Test
    public void testSetName() {
        product.setName("NOTLAPTOP");
        String name = product.getName();
        assertEquals("NOTLAPTOP", name);

    }

    @Test
    public void testGetPrice() {
        Double price = product.getPrice();
        assertEquals(1000, price, 0);

    }

    @Test
    public void testSetPrice() {
        product.setPrice(500);
        Double price = product.getPrice();
        assertEquals(500, price, 0);
    }

    @Test
    public void testGetImageURL() {
        String url = product.getImageURL();
        assertEquals("", url);
    }

    @Test
    public void testSetImageURL() {
        product.setImageURL("url");
        String url = product.getImageURL();
        assertEquals("url", url);
    }
}
