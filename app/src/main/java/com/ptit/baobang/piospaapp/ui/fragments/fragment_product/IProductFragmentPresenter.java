package com.ptit.baobang.piospaapp.ui.fragments.fragment_product;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;

public interface IProductFragmentPresenter {
    void onClickMore(ProductGroup productGroup);
    void loadData();
}
