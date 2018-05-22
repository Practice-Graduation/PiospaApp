package com.ptit.baobang.piospaapp.ui.activities.service_detail;

import com.ptit.baobang.piospaapp.data.model.Service;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

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
                    mView.stopShimmerAnimation();
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

    @Override
    public void loadServicePackageInfo(int packageId) {
        mApiService.getServiceByPackageId(packageId).enqueue(new Callback<EndPoint<List<Service>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<Service>>> call, Response<EndPoint<List<Service>>> response) {
                if(response.isSuccessful()){
                    List<Service>services = response.body().getData();
                    int index = 1;
                    int time = 0;
                    String s = "";
                    for(Service service : services){
                        time += Integer.parseInt( service.getServiceTime().getTime());
                        s += index++ + ". " + service.getServiceName()
                                + " - " + service.getServiceTime().getTime() + " phút\n";

                    }
                    mView.addServicePackageTime(time+"");
                    mView.setPackageInfo(s);
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<Service>>> call, Throwable t) {

            }
        });
    }
}
