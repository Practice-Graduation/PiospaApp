package com.ptit.baobang.piospaapp.ui.activities.all_product;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.adapter.ProductAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IAllProductView extends BaseView{
    void onUpdateRecyleView(List<Product> products);
    void openProductDetailActivity(Product product);

    ProductAdapter getProductAdapter();
}
