package com.example.buysell;

public class CartItem {
    private String productTitle;
    private String productPrice;

    public CartItem() {
    }

    public CartItem(String productTitle, String productPrice) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
    }

    public String getTitle() {
        return productTitle;
    }

    public void setTitle(String title) {
        productTitle = title;
    }

    public void setPrice(String price) {
        productPrice = price;
    }

    public String getPrice() {
        return productPrice;
    }
}
