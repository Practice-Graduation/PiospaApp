package com.ptit.baobang.piospaapp.ui.activities.all_services;

import android.annotation.SuppressLint;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AllServicePresenter extends BasePresenter implements IAllSerrvicePresenter {

    private IAllServiceView mView;

    AllServicePresenter(IAllServiceView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(int serviceGroup) {
        mView.showLoading("Tải dữ liệu");
        getCompositeDisposable().add(
                mApiService.getServicePriceByGroupId(serviceGroup)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError)
        );
//        mApiService.getProductByGroupId(productGroupId).enqueue(new Callback<EndPoint<List<Product>>>() {
//            @Override
//            public void onResponse(Call<EndPoint<List<Product>>> call, Response<EndPoint<List<Product>>> response) {
//                handleResponse(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<EndPoint<List<Product>>> call, Throwable t) {
//                    handleError(t);
//            }
//        });

    }

    private void handleError(Throwable throwable) {
        mView.hideLoading( throwable.getMessage(), false);

    }

    private void handleResponse(EndPoint<List<ServicePrice>> listEndPoint) {
        mView.hideLoading();
        List<ServicePrice> products = listEndPoint.getData();
        mView.onUpdateRecyleView(products);
    }

    @Override
    public void onSelectedProduct(ServicePrice servicePrice) {
       mView.openProductDetailActivity(servicePrice);
    }

    @SuppressLint("CheckResult")
    @Override
    public void filter(SearchView searchView) {
        RxSearchObservable.fromView(searchView)
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handFilterResponse);
    }

    private void handFilterResponse(String s) {
        mView.getServerAdapter().getFilter().filter(s);
    }
}
