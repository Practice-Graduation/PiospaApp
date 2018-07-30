package com.ptit.baobang.piospaapp.ui.fragments.fragment_order.not_payment;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.local.helper.OrderHelper;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.util.List;

public class ListOderPresenter extends BasePresenter implements IListOrdePresenter {

    private IListOrderView mView;
    private Context mContext;
    ListOderPresenter(Context mContext, IListOrderView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void loadOrder(Context baseContext, OrderStatus orderStatus) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);
        List<OrderRealm> orderRealms = OrderHelper.getOrderByStatus(customer.getCustomerId(), orderStatus.getOrderStatusId());
        handleResponse(orderRealms);
    }


    private void handleResponse(List<OrderRealm> orderRealms) {
        mView.updateRecycleView(orderRealms);
    }

    @Override
    public void selectedItem(OrderRealm order) {
        mView.openOrderDetail(order);
    }
}
