package com.ptit.baobang.piospaapp.ui.fragments.fragment_product;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IProductFragmentView extends BaseView{
    void openAllProductActivity(int productGroupId);
    void openProductDetailActivity(Product product);
    void onUpdateRecycleView(List<ProductGroup> productGroups);
}
