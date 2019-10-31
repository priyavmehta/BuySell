package com.example.buysell;

public class CartItem {
    private String productTitle;
    private String productPrice;
    private String id;

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CartItem() {
    }

    public CartItem(String productTitle, String productPrice, String id) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.id = id;
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
