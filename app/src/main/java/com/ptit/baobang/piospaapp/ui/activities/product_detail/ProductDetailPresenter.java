package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductDetailPresenter extends BasePresenter implements IProductDetailPresenter {

    private IProductDetailView mView;
    private Context mContext;
    public ProductDetailPresenter(Context mContext, IProductDetailView mIProductDetailView) {
        this.mContext = mContext;
        this.mView = mIProductDetailView;
    }

    @Override
    public void loadData(Product product) {
        mView.showProductDetail(product);
    }

    @Override
    public void onClickAddCart(Product product, String amount) {
        Cart cart = CartHelper.getCart();
        try {
            cart.add(product, Integer.parseInt(amount));
            mView.showMessage(mContext.getString(R.string.message), mContext.getString(R.string.added) + " " + product.getProductName(), SweetAlertDialog.SUCCESS_TYPE);
        } catch (Exception e) {
            mView.showMessage(mContext.getString(R.string.message), e.getMessage(), SweetAlertDialog.ERROR_TYPE);
        }
    }

    @Override
    public void onClickGoToCart() {
        mView.openFramentCart();
    }

    @Override
    public Product getProductFromBundle(Intent intent) {
        Bundle bundle = intent.getExtras();
        return (Product) (bundle != null ? bundle.getSerializable(AppConstants.PRODUCT_ID) : null);
    }

    @Override
    public void onClickAddButton(String s) {
        int amount = Integer.parseInt(s);
        mView.setAmount(String.valueOf(++amount));
    }

    @Override
    public void onClickRemoveButton(String s) {
        int amount = Integer.parseInt(s);
        if (amount > 1) --amount;
        mView.setAmount(String.valueOf(amount));
    }
}
