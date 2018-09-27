package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface ICartView extends BaseView {
    void updateUI();

    void openPaymentActivity();

    void showEmptyCart();

    void showCartItemList(List<CartProductItem> cartItems);
}
