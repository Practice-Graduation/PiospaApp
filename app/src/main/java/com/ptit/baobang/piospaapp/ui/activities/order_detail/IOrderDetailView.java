package com.ptit.baobang.piospaapp.ui.activities.order_detail;

import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IOrderDetailView extends BaseView {
    void setView(String code, String createdAt, String orderStatusName, String fullName, String address,
                 String ward, String district, String province,
                 String phone, List<CartProductItem> productItems, List<CartServicePriceItem> priceItems, String orderDeliveryTypeName,
                 String orderPaymentTypeName, String orderPaymentTypeDescription,
                 String total, String ship, String subtotal);

    void updateRecycleProducts(List<CartProductItem> productItems);

    void updateRecycleServices(List<CartServicePriceItem> priceItems);
}
