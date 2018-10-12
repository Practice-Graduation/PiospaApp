package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

/**
 * ProductDetailView interface
 *
 * @author BangNB
 */
public interface IProductDetailView extends BaseView {
    void showProductDetail(Product product);

    void openFragmentCart();

    void setAmount(String amount);
}
