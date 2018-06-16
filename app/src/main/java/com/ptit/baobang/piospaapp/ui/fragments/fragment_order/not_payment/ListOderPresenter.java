package com.ptit.baobang.piospaapp.ui.fragments.fragment_order.not_payment;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderCustomerBody;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListOderPresenter extends BasePresenter implements IListOrdePresenter {

    private IListOrderView mView;

    ListOderPresenter(IListOrderView mView) {
        this.mView = mView;
    }

    @Override
    public void loadOrder(Context baseContext, OrderStatus orderStatus) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);
        OrderCustomerBody orderCustomerBody = new OrderCustomerBody(orderStatus.getOrderStatusId(), customer.getCustomerId());

        getCompositeDisposable().add(
                mApiService.getOrderByStatus(orderCustomerBody)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mView.showMessage("Thông báo", throwable.getMessage(), SweetAlertDialog.ERROR_TYPE);
    }

    private void handleResponse(EndPoint<List<Order>> listEndPoint) {
        List<Order> orders = listEndPoint.getData();
        mView.updateRecycleView(orders);
    }

    @Override
    public void selectedItem(Order order) {
        mView.openOrderDetail(order);
    }
}
