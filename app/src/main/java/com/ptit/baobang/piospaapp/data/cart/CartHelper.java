package com.ptit.baobang.piospaapp.data.cart;

public class CartHelper {
    private static Cart cart = new Cart();

    public static Cart getCart() {
        if(cart == null){
            cart = new Cart();
        }
        return cart;
    }
}
