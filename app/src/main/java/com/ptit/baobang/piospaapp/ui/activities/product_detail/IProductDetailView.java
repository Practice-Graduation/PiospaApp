package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

public interface IProductDetailView extends BaseView{
    void showProductDetail(Product product);

    void openFramentCart();
}
