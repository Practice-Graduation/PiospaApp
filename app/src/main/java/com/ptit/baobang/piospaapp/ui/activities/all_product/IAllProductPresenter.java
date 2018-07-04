package com.ptit.baobang.piospaapp.ui.activities.all_product;

import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.Product;

public interface IAllProductPresenter {
    void loadData(int productGroupId);
    void onSelectedProduct(Product product);

    void filter(SearchView searchView);
}
