package com.ptit.baobang.piospaapp.ui.activities.service_detail;

import android.content.Intent;
import android.os.Bundle;

import com.ptit.baobang.piospaapp.data.model.Service;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServiceDetailPresenter extends BasePresenter implements ISeriviceDetailPresenter{

    IServiceDetailView mView;



    public ServiceDetailPresenter(IServiceDetailView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(ServicePrice servicePrice) {
        mView.showLoading("Tải dữ liệu");
        getCompositeDisposable().add(
                mApiService.getServicePriceById(servicePrice.getServicePriceId()
                        )
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handleResponse(EndPoint<ServicePrice> servicePriceEndPoint) {
        mView.hideLoading();
        ServicePrice servicePrice = servicePriceEndPoint.getData();
        mView.showServiceDetail(servicePrice);
    }

    @Override
    public void onClickAddBooking(ServicePrice servicePrice) {
        mView.openBookingActivity(servicePrice);
    }

    @Override
    public void loadServicePackageInfo(int packageId) {

        getCompositeDisposable().add(
                mApiService.getServiceByPackageId(packageId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleServicePackageInfoResponse, this::handleError)
        );
    }

    private void handleServicePackageInfoResponse(EndPoint<List<Service>> listEndPoint) {
        List<Service>services = listEndPoint.getData();
        int index = 1;
        int time = 0;
        StringBuilder s = new StringBuilder();
        for(Service service : services){
            time += Integer.parseInt( service.getServiceTime().getTime());
            s.append(index++).append(". ").append(service.getServiceName()).append(" - ").append(service.getServiceTime().getTime()).append(" phút\n");

        }
        mView.addServicePackageTime(time+"");
        mView.setPackageInfo(s.toString());
    }

    @Override
    public ServicePrice getDataFromBundle(Intent intent) {
        Bundle bundle = intent.getExtras();
        return (ServicePrice) (bundle != null ? bundle.getSerializable(AppConstants.SERVICE_PRICE_ID) : null);
    }
}
