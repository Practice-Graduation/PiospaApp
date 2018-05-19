package com.ptit.baobang.piospaapp.ui.activities.product_detail;

public interface IProductDetailPresenter {
    void loadData(int productId);
    void onClickAddCart(int productId);
    void onClickGoToCart();

}
