package com.ptit.baobang.piospaapp.data.cart;

import com.ptit.baobang.piospaapp.data.model.Product;

public class CartItem {
    private Product product;
    private int quanlity;

    public CartItem(Product product, int quanlity) {
        this.product = product;
        this.quanlity = quanlity;
    }

    public CartItem() {
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
    public int getTotalItem(){
        return  quanlity * product.getPrice();
    }
}
