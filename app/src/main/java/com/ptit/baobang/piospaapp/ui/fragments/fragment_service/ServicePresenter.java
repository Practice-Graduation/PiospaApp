package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServicePresenter extends BasePresenter implements IServicePresenter{

    private IServiceView mView;

    ServicePresenter(IServiceView mView) {
        this.mView = mView;
    }

    @Override
    public void onClickMore(ServiceGroup serviceGroup) {
        mView.openAllServiceActivity(serviceGroup.getServiceGroupId(), serviceGroup.getServiceGroupName());
    }

    @Override
    public void loadData() {
        mView.showLoading("Tải dữ liệu");
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

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);

    }

    private void handleRespone(EndPoint<List<ServiceGroup>> listEndPoint) {
        List<ServiceGroup> groups = listEndPoint.getData();
        mView.onUpdateRecycleView(groups);
        mView.hideLoading();
    }
}
