package com.ptit.baobang.piospaapp.ui.activities.all_product;

public interface IAllProductPresenter {
    void loadData(int productGroupId);
    void onSelectedProduct(int productId);
}
