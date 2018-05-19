package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartItem;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;

public interface ICartFragmentPresenter{
    void loadData();
    void addCartItem(CartItem cartItem);
    void removeCartItem(CartItem cartItem);
}
