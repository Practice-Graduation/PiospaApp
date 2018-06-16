package com.ptit.baobang.piospaapp.ui.fragments.fragment_order.not_payment;

import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IListOrderView extends BaseView {
    void updateRecycleView(List<Order> orders);

    void openOrderDetail(Order order);

}
