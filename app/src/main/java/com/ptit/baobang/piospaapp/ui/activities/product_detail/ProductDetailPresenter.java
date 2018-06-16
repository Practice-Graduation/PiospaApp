package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import android.content.Intent;
import android.os.Bundle;

import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductDetailPresenter extends BasePresenter implements IProductDetailPresenter {

    private IProductDetailView mView;

    public ProductDetailPresenter(IProductDetailView mIProductDetailView) {
        this.mView = mIProductDetailView;
    }

    @Override
    public void loadData(Product product) {
        mView.stopShirrmentAnimation();
        mView.showProductDetail(product);
    }

    @Override
    public void onClickAddCart(Product product) {
        Cart cart = CartHelper.getCart();
        cart.add(product, 1);
        mView.showMessage("Thông báo", "Thêm " + product.getProductName(), SweetAlertDialog.SUCCESS_TYPE);
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
}
