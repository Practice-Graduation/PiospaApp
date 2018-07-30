package com.ptit.baobang.piospaapp.ui.activities.order_detail;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;

public interface IOrderDetailPresenter {
    OrderRealm getDate(Intent intent);

    void loadData(OrderRealm order);

    void clickCancelOrder(OrderRealm order);
}
