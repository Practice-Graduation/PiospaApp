package com.ptit.baobang.piospaapp.ui.activities.order_detail;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.Order;

public interface IOrderDetailPresenter {
    Order getDate(Intent intent);

    void loadData(Order order);

    void clickCancelOrder(Order order);
}
