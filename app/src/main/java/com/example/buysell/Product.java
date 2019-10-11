package com.example.buysell;

public class Product {
    private String sellerName;
    private String productPrice;
    private String productTitle;
    private String productDescription;
    private String imageUrl;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Product(String productTitle, String productDescription, String productPrice, String image, String sellerName) {
        this.sellerName = sellerName;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        //this.imageUrl = imageUrl;
        this.image = image;
    }
    public Product(String productTitle, String productPrice) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
    }
    public Product(){

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
