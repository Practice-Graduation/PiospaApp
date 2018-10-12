package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.Product;

/**
 * ProductDetailPresenter interface
 *
 * @author BangNB
 */
public interface IProductDetailPresenter {

    void onClickAddCart(Product product, String amount);

    void onClickGoToCart();

    Product getProductFromBundle(Intent intent);

    void onClickPositiveButton(Product product, String strAmount);

    void onClickNegativeButton(String amount);
}
