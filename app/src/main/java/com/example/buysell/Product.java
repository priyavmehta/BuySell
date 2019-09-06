package com.example.buysell;

public class Product {
    private String sellerName;
    private String productPrice;
    private String productTitle;
    private String productDescription;
    private String imageUrl;
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Product(String sellerName, String productPrice, String productTitle, String productDescription, String imageUrl, int image) {
        this.sellerName = sellerName;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.imageUrl = imageUrl;
        this.image = image;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
