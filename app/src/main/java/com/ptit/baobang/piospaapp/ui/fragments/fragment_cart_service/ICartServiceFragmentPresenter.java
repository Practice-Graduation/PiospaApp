package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_service;

import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;

public interface ICartServiceFragmentPresenter {
    void loadData();
    void addCartItem(CartServicePriceItem cartItem);
    void removeCartItem(CartServicePriceItem cartItem);
}
