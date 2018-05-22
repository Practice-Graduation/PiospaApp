package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_service;

import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface ICartServiceFragmentView extends BaseView{
    void showCartItemList(List<CartServicePriceItem> cartItems);
}
