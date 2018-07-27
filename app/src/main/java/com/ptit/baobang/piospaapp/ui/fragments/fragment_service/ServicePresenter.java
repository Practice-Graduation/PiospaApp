package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import android.content.Context;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServicePresenter extends BasePresenter implements IServicePresenter{

    private IServiceView mView;
    private Context mContext;

    ServicePresenter(Context mContext, IServiceView mView) {

        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void onClickMore(ServiceGroup serviceGroup) {
        mView.openAllServiceActivity(serviceGroup.getServiceGroupId(), serviceGroup.getServiceGroupName());
    }

    @Override
    public void loadData() {
        mView.showLoading(mContext.getString(R.string.loading));
        getCompositeDisposable().add(
                mApiService.getAllServiceGroup()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleRespone, this::handleError));
    }

    @Override
    public void clickItem(ServicePrice servicePrice) {
        mView.openServiceDetailActivity(servicePrice);
    }

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
        mView.getServiceGroupAdapter().filter(s);
    }
    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);

    }

    private void handleRespone(EndPoint<List<ServiceGroup>> listEndPoint) {
        List<ServiceGroup> groups = listEndPoint.getData();
        mView.onUpdateRecycleView(groups);
    }
}
