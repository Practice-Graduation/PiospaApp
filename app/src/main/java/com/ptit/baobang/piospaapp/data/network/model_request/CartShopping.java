package com.ptit.baobang.piospaapp.data.network.model_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartShopping {
    @SerializedName("cartItemProducts")
    @Expose
    private List<CartItemProduct> cartItemProducts = null;
    @SerializedName("cartItemServices")
    @Expose
    private List<CartItemService> cartItemServices = null;

    public List<CartItemProduct> getCartItemProducts() {
        return cartItemProducts;
    }

    public void setCartItemProducts(List<CartItemProduct> cartItemProducts) {
        this.cartItemProducts = cartItemProducts;
    }

    public List<CartItemService> getCartItemServices() {
        return cartItemServices;
    }

    public void setCartItemServices(List<CartItemService> cartItemServices) {
        this.cartItemServices = cartItemServices;
    }

}
