package com.ptit.baobang.piospaapp.ui.activities.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderPresenter extends BasePresenter implements IOrderPresenter{

    private IOrderView mView;

    OrderPresenter(IOrderView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(Context baseContext) {
        mView.showLoading("Tải dữ liệu");
        getCompositeDisposable().add(
                mApiService.getAllOrderStatuses()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handlerResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handlerResponse(EndPoint<List<OrderStatus>> listEndPoint) {
        List<OrderStatus> orderStatuses = listEndPoint.getData();
        mView.addTabLayout(orderStatuses);
        mView.hideLoading();
    }

    public int getSelectedTab(Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle == null) return  0;
        return bundle.getInt(AppConstants.ORDER_FRAGMENT_INDEX);
    }
}
