package com.ptit.baobang.piospaapp.ui.activities.service_detail;

import android.support.v7.widget.Toolbar;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailPresenter extends BasePresenter implements ISeriviceDetailPresenter{

    IServiceDetailView mView;



    public ServiceDetailPresenter(IServiceDetailView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(int servicePriceId) {
        mApiService.getServicePriceById(servicePriceId).enqueue(new Callback<EndPoint<ServicePrice>>() {
            @Override
            public void onResponse(Call<EndPoint<ServicePrice>> call, Response<EndPoint<ServicePrice>> response) {
                if(response.isSuccessful()){
                    ServicePrice servicePrice = response.body().getData();
                    mView.showServiceDetail(servicePrice);
                }
            }

            @Override
            public void onFailure(Call<EndPoint<ServicePrice>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickAddBooking(int servicePriceId) {
        mView.openBookingActivity(servicePriceId);
    }
}
