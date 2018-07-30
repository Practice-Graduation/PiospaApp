package com.ptit.baobang.piospaapp.ui.activities.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.local.helper.OrderHelper;
import com.ptit.baobang.piospaapp.data.local.listener.RealmCallBack;
import com.ptit.baobang.piospaapp.data.local.db_realm.BookingDetailRealm;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderProductRealm;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.data.model.BookingDetailObject;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.OrderObject;
import com.ptit.baobang.piospaapp.data.model.OrderProductObject;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;

public class OrderPresenter extends BasePresenter implements IOrderPresenter {

    private IOrderView mView;
    private Context mContext;
    private List<OrderStatus> orderStatuses = new ArrayList<>();
    OrderPresenter(Context mContext, IOrderView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void loadData(Context baseContext) {
        orderStatuses = new ArrayList<>();
        OrderStatus orderNotPayment = new OrderStatus();
        orderNotPayment.setOrderStatusId(AppConstants.ORDER_STATUS_NOT_PAYMENT);
        orderNotPayment.setOrderStatusName(mContext.getString(R.string.not_payment));

        OrderStatus orderPayment = new OrderStatus();
        orderPayment.setOrderStatusId(AppConstants.ORDER_STATUS_PAYMENT);
        orderPayment.setOrderStatusName(mContext.getString(R.string.payment));

        OrderStatus orderCancel = new OrderStatus();
        orderCancel.setOrderStatusId(AppConstants.ORDER_STATUS_CANCLE);
        orderCancel.setOrderStatusName(mContext.getString(R.string.cancel));
        orderStatuses.add(orderNotPayment);
        orderStatuses.add(orderPayment);
        orderStatuses.add(orderCancel);

        boolean isUpdateOrder = SharedPreferenceUtils.getIsUpdateOrder(mContext);
        if (isUpdateOrder) {
            mView.addTabLayout(orderStatuses);
        } else {
            mView.showLoading(mContext.getString(R.string.loading));
            Customer customer = SharedPreferenceUtils.getUser(mContext);
            getCompositeDisposable()
                    .add(mApiService.getOrderByCustomerId(customer.getCustomerId())
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .unsubscribeOn(Schedulers.io())
                            .subscribe(this::handleResponeSuccess, this::handleError));
        }

    }

    private void handleError(Throwable throwable) {
        mView.hideLoading("Tải dữ liệu thất bại", false);
    }

    private void handleResponeSuccess(EndPoint<List<OrderObject>> orderEndPoint) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(realm1 -> realm1.delete(OrderRealm.class));
        List<OrderRealm> orderRealms = new ArrayList<>();
        List<OrderObject> orderObjects = orderEndPoint.getData();
        for (OrderObject orderObject : orderObjects) {
            RealmList<BookingDetailRealm> bookingDetails = getBookingDetailRealm(orderObject.getBookingDetails());
            RealmList<OrderProductRealm> orderProductRealms = getOrderProductRealm(orderObject.getOrderProducts());
            orderRealms.add(new OrderRealm(orderObject.getOrder(), bookingDetails, orderProductRealms));
        }
        OrderHelper.saveOrderList(orderRealms, new RealmCallBack() {
            @Override
            public void onSuccess() {
                mView.addTabLayout(orderStatuses);
                SharedPreferenceUtils.saveIsUpdateOrder(mContext, true);
                mView.hideLoading();
            }

            @Override
            public void onFail(String error) {

                SharedPreferenceUtils.saveIsUpdateOrder(mContext, false);
                mView.hideLoading(error, false);
            }
        });
    }

    private RealmList<OrderProductRealm> getOrderProductRealm(List<OrderProductObject> orderProducts) {
        RealmList<OrderProductRealm> orderProductRealms = new RealmList<>();
        for(OrderProductObject productObject : orderProducts){
            orderProductRealms.add(new OrderProductRealm(productObject));
        }
        return orderProductRealms;
    }

    private RealmList<BookingDetailRealm> getBookingDetailRealm(List<BookingDetailObject> bookingDetails) {
        RealmList<BookingDetailRealm> bookingDetailRealms = new RealmList<>();
        for(BookingDetailObject bookingDetailObject : bookingDetails){
            bookingDetailRealms.add(new BookingDetailRealm(bookingDetailObject));
        }
        return bookingDetailRealms;
    }


    public int getSelectedTab(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) return 0;
        return bundle.getInt(AppConstants.ORDER_FRAGMENT_INDEX);
    }
}
