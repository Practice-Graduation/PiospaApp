package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product;

import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface ICartProductFragmentView extends BaseView{
    void showCartItemList(List<CartProductItem> cartItems);
}
