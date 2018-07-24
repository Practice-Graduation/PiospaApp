package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.Product;

public interface IProductDetailPresenter {
    void loadData(Product product);
    void onClickAddCart(Product product, String amount);
    void onClickGoToCart();

    Product getProductFromBundle(Intent intent);

    void onClickAddButton(String s);

    void onClickRemoveButton(String s);
}
