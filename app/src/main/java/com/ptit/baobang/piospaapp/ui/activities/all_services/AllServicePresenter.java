package com.ptit.baobang.piospaapp.ui.activities.all_services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllServicePresenter extends BasePresenter implements IAllSerrvicePresenter {

    private IAllServiceView mView;
    private Context mContext;

    AllServicePresenter(Context mContext, IAllServiceView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void loadData(int serviceGroup) {
        mView.showLoading(mContext.getString(R.string.loading));
//        getCompositeDisposable().add(
//                mApiService.getServicePriceByGroupId(serviceGroup)
//                        .subscribeOn(Schedulers.computation())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .unsubscribeOn(Schedulers.io())
//                        .subscribe(this::handleResponse, this::handleError)
//        );
        mApiService.getServicePriceByGroupId(serviceGroup).enqueue(new Callback<EndPoint<List<ServicePrice>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<ServicePrice>>> call, Response<EndPoint<List<ServicePrice>>> response) {
                handleResponse(response.body());
            }

            @Override
            public void onFailure(Call<EndPoint<List<ServicePrice>>> call, Throwable t) {
                handleError(t);
            }
        });

    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);

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
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handFilterResponse);
    }

    private void handFilterResponse(String s) {
        mView.getServerAdapter().getFilter().filter(s);
    }
}
