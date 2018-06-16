package com.ptit.baobang.piospaapp.ui.dialogs.ward;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WardPresenter extends BasePresenter implements IWardPresenter {

    private IWardView mView;

    WardPresenter(IWardView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(District district) {


        getCompositeDisposable().add(
                mApiService.getWardtByDistrictId(district.getDistrictid())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleRespone, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {

    }

    private void handleRespone(EndPoint<List<Ward>> listEndPoint) {
        List<Ward> wards = listEndPoint.getData();
        mView.updateRecyleView(wards);
        mView.stopShimmerAnimation();
    }

    @Override
    public void clickItem(Ward ward) {
        mView.backToPaymentActivity(ward);
    }

    @Override
    public District getDistrict(Intent intent) {
        return (District) intent.getSerializableExtra(AppConstants.DISTRICT);

    }

    @Override
    public Ward getWard(Intent intent) {
        return (Ward) intent.getSerializableExtra(AppConstants.WARD);
    }
}
