package com.ptit.baobang.piospaapp.data.cart;

import com.ptit.baobang.piospaapp.data.model.Product;

import java.math.BigDecimal;

public class CartProductItem {
    private Product product;
    private int quanlity;

    public CartProductItem() {
    }

    public CartProductItem(Product product, int quanlity) {
        this.product = product;
        this.quanlity = quanlity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public BigDecimal getTotalItem(){
        return BigDecimal.valueOf(quanlity * product.getPrice());
    }
}
