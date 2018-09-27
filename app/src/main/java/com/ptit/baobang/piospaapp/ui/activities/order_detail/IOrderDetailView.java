package com.ptit.baobang.piospaapp.ui.activities.order_detail;

import com.ptit.baobang.piospaapp.data.local.db_realm.OrderProductRealm;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IOrderDetailView extends BaseView {
    void setView(String code, String createdAt, String orderStatusName, String fullName, String address,
                 String ward, String district, String province,
                 String phone, List<OrderProductRealm> productItems, String orderDeliveryTypeName,
                 String orderPaymentTypeName, String orderPaymentTypeDescription,
                 String total, String ship, String subtotal);

    void updateRecycleProducts(List<OrderProductRealm> productItems);

    void setView(String orderStatusName);

    void setTax(String taxName, int taxValue, String taxUnit);
}
