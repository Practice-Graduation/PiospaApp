package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product;

import com.ptit.baobang.piospaapp.data.cart.CartProductItem;

public interface ICartProductFragmentPresenter {
    void loadData();
    void addCartItem(CartProductItem cartItem);
    void removeCartItem(CartProductItem cartItem);
}
