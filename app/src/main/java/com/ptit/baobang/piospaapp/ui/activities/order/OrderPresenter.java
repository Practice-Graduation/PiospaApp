package com.ptit.baobang.piospaapp.ui.activities.order;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPresenter extends BasePresenter implements IOrderPresenter{

    IOrderView mView;

    public OrderPresenter(IOrderView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(Context baseContext) {
        mApiService.getAllOrderStatuses().enqueue(new Callback<EndPoint<List<OrderStatus>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<OrderStatus>>> call, Response<EndPoint<List<OrderStatus>>> response) {
                if(response.isSuccessful()){
                    List<OrderStatus> orderStatuses = response.body().getData();
                    mView.addTabLayout(orderStatuses);
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<OrderStatus>>> call, Throwable t) {

            }
        });
    }
}
