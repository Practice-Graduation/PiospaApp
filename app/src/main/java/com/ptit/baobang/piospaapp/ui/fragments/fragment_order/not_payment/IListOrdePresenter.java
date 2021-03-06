package com.ptit.baobang.piospaapp.ui.fragments.fragment_order.not_payment;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;

public interface IListOrdePresenter {
    void loadOrder(Context baseContext, OrderStatus orderStatus);

    void selectedItem(Order order);
}
