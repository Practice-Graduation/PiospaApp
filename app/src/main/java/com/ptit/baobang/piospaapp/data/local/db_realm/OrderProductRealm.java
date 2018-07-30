package com.ptit.baobang.piospaapp.data.local.db_realm;

import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.model.BookingDetailObject;
import com.ptit.baobang.piospaapp.data.model.OrderProductObject;

import io.realm.RealmObject;
import io.realm.annotations.RealmField;

public class OrderProductRealm extends RealmObject {


    @RealmField(name = "product_id")
    private int productId;

    @RealmField(name = "product_name")
    private String productName;

    @RealmField(name = "product_image")
    private String productImage;

    @RealmField(name = "price")
    private int price;

    @RealmField(name = "amount")
    private int amount;

    public OrderProductRealm() {
    }

    public OrderProductRealm(CartProductItem item) {
        productId = item.getProduct().getProductId();
        productName = item.getProduct().getProductName();
        price = item.getProduct().getPrice();
        amount = item.getQuanlity();
        productImage = item.getProduct().getImage();
    }

    public OrderProductRealm(OrderProductObject productObject) {
        productId = productObject.getProductId();
        productName = productObject.getProductName();
        price = productObject.getPrice();
        amount = productObject.getAmount();
        productImage = productObject.getProductImage();
    }


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
