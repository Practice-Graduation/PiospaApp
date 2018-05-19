package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import com.ptit.baobang.piospaapp.data.cart.CartItem;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface ICartFragmentView extends BaseView{
    void showCartItemList(List<CartItem> cartItems);
    void updateUI();
}
