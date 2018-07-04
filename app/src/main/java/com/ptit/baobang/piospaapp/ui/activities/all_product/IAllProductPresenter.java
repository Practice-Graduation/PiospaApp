package com.ptit.baobang.piospaapp.ui.activities.all_product;

import com.ptit.baobang.piospaapp.data.model.Product;

public interface IAllProductPresenter {
    void loadData(int productGroupId);
    void onSelectedProduct(Product product);
}
