package com.ptit.baobang.piospaapp.ui.dialogs.ward;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WardPresenter extends BasePresenter implements IWardPresenter {

    private IWardView mView;
    private Context mContext;
    WardPresenter(Context mContext, IWardView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void loadData(District district) {

        mView.showLoading(mContext.getString(R.string.loading));
        getCompositeDisposable().add(
                mApiService.getWardtByDistrictId(district.getDistrictid())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleRespone, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handleRespone(EndPoint<List<Ward>> listEndPoint) {
        List<Ward> wards = listEndPoint.getData();
        mView.updateRecyleView(wards);
        mView.hideLoading();
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
        mView.getWardAdapter().getFilter().filter(s);
    }
}
