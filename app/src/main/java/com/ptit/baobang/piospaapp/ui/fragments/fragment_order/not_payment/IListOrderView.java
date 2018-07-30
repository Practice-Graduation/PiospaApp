package com.ptit.baobang.piospaapp.ui.fragments.fragment_order.not_payment;

import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IListOrderView extends BaseView {
    void updateRecycleView(List<OrderRealm> orders);

    void openOrderDetail(OrderRealm order);

}
