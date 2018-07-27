package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import android.content.Context;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CartPresenter extends BasePresenter implements ICartPresenter {
    private ICartView mView;
    private Context mContext;
    public CartPresenter(Context mContext, ICartView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void clickPayment() {
        Cart cart = CartHelper.getCart();
        if(cart.getTotalQuantity() == 0){
            mView.showMessage(mContext.getString(R.string.message), mContext.getString(R.string.cart_empty_message), SweetAlertDialog.WARNING_TYPE);
            return;
        }
        mView.openPaymentActivity();
    }
}
