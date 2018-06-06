package com.ptit.baobang.piospaapp.ui.fragments.fragment_order.not_payment;

import android.content.Context;
import android.util.Log;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderCustomerBody;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOderPresenter extends BasePresenter implements IListOrdePresenter {

    IListOrderView mView;

    public ListOderPresenter(IListOrderView mView) {
        this.mView = mView;
    }

    @Override
    public void loadOrder(Context baseContext, OrderStatus orderStatus) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);
        OrderCustomerBody orderCustomerBody = new OrderCustomerBody(orderStatus.getOrderStatusId(), customer.getCustomerId());
        Log.e("DATA", orderStatus.getOrderStatusId() + "-" + customer.getCustomerId());
        mApiService.getOrderByStatus(orderCustomerBody).enqueue(new Callback<EndPoint<List<Order>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<Order>>> call, Response<EndPoint<List<Order>>> response) {
                if(response.isSuccessful()){
                    Log.e("LIST ORDER", response.body().getMessage());
                    List<Order> orders = response.body().getData();
                    Log.e("LIST  SIZE", response.body().getData().size() + "");
                    mView.updateRecycleView(orders);
                }else{
                    Log.e("LIST ORDER", "NOT SUCCESS");
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<Order>>> call, Throwable t) {

            }
        });
    }
}
