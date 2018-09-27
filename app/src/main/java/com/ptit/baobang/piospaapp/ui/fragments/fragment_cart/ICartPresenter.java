package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import com.ptit.baobang.piospaapp.data.cart.CartProductItem;

public interface ICartPresenter {
    void clickPayment();

    void addCartItem(CartProductItem cartProductItem);

    void removeCartItem(CartProductItem cartProductItem);

    void loadData();
}
