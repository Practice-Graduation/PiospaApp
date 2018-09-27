package com.ptit.baobang.piospaapp.ui.activities.order_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.data.local.helper.OrderHelper;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.CancelOrderRequest;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class OrderDetailPresenter extends BasePresenter implements IOrderDetailPresenter {

    private String TAG = "OrderDetailPresenter";

    private IOrderDetailView mView;
    private Context mContext;
    private OrderRealm mOrder;

    public OrderDetailPresenter(Context mContext, IOrderDetailView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public OrderRealm getDate(Intent intent) {
        Bundle bundle = intent.getExtras();
        int orderId = bundle.getInt(KeyBundleConstant.ORDER);
        mOrder = OrderHelper.getOrderById(orderId);
        return mOrder;
    }

    @Override
    public void loadData(OrderRealm order) {
        handlerResponse(order);
    }

    @Override
    public void clickCancelOrder(OrderRealm order) {

        OrderStatus status = new OrderStatus();
        status.setOrderStatusId(AppConstants.ORDER_STATUS_CANCLE);


        mView.showLoading(mContext.getString(R.string.loading_cacel_order));
        CancelOrderRequest requestBody = new CancelOrderRequest(order.getOrderId());
        getCompositeDisposable().add(mApiService.cancelOrder(requestBody)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handlerResponseCancleOrder, this::handlerErorr));

    }

    private void handlerResponseCancleOrder(EndPoint<Order> orderEndPoint) {
        if (orderEndPoint.getStatusCode() == AppConstants.SUCCESS_CODE) {
            mView.hideLoading();
            mOrder.setOrderStatusId(orderEndPoint.getData().getOrderStatus().getOrderStatusId());
            mOrder.setOrderStatusName(orderEndPoint.getData().getOrderStatus().getOrderStatusName());
            OrderHelper.saveOrder(mOrder);
            mView.setView(mOrder.getOrderStatusName());
        } else {
            mView.showLoading(mContext.getString(R.string.loading_cacel_order_failed));
            Log.e(TAG, orderEndPoint.getMessage());
        }
    }

    private void handlerErorr(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handlerResponse(OrderRealm orderRealm) {

        String[] address = orderRealm.getShippingAddress().split(AppConstants.COMMA_SYMBOL);

        mView.setView(orderRealm.getOrderId()+"",
                DateTimeUtils.formatDate(DateTimeUtils.getDateFromString(orderRealm.getCreatedAt(), DateTimeUtils.DATE_PATTERN_DDMMYYTHHMMSSSSSZ), DateTimeUtils.DATE_PATTERN_DDMMYY),
                orderRealm.getOrderStatusName(),
                orderRealm.getCustomerName(), address[0], address[1],
                address[2], address[3], orderRealm.getCustomerPhone(),
                orderRealm.getOrderProductRealms(),
                orderRealm.getDeliveryType(),
                orderRealm.getPaymentType(),
                orderRealm.getPaymentTypeDescription(),
                CommonUtils.formatToCurrency(orderRealm.getTotal()),
                CommonUtils.formatToCurrency(orderRealm.getShip()),
                CommonUtils.formatToCurrency(orderRealm.getPayment()));
        mView.setTax(orderRealm.getTaxName(), orderRealm.getTaxValue(), orderRealm.getTaxUnit());
    }
}
