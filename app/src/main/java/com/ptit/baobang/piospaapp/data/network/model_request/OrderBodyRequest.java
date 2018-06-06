package com.ptit.baobang.piospaapp.data.network.model_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptit.baobang.piospaapp.data.model.Order;

public class OrderBodyRequest {
    @SerializedName("cartShopping")
    @Expose
    private CartShopping cartShopping;
    @SerializedName("order")
    @Expose
    private Order order;

    public CartShopping getCartShopping() {
        return cartShopping;
    }

    public void setCartShopping(CartShopping cartShopping) {
        this.cartShopping = cartShopping;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
