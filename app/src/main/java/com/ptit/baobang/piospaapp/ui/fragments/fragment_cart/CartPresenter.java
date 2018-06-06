package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

public class CartPresenter extends BasePresenter implements ICartPresenter {
    private ICartView mView;

    public CartPresenter(ICartView mView) {
        this.mView = mView;
    }

    @Override
    public void clickPayment() {
        Cart cart = CartHelper.getCart();
        if(cart.getTotalQuantity() == 0){
            mView.showMessage("Giỏ hàng hiện rỗng, vui lòng mua hàng trước khi thanh toán");
            return;
        }
        mView.openPaymentActivity();
    }
}
