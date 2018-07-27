package com.ptit.baobang.piospaapp.ui.dialogs.district;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DistrictPresenter extends BasePresenter implements IDistrictPresenter {

    private IDistrictView mView;
    private Context mContext;
    DistrictPresenter(Context mContext, IDistrictView mView) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void loadData(Province province) {
        mView.showLoading(mContext.getString(R.string.loading));
        getCompositeDisposable().add(
                mApiService.getDistrictByProvinceId(province.getProvinceid())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
            mView.hideLoading(throwable.getMessage(), false);
    }

    private void handleResponse(EndPoint<List<District>> listEndPoint) {
        List<District> districts = listEndPoint.getData();
        mView.updateRecyleView(districts);
        mView.hideLoading();
    }

    @Override
    public void clickItem(District district) {
        mView.backToPaymentActivity(district);
    }

    @Override
    public Province getProvince(Intent intent) {
        return (Province) intent.getSerializableExtra(AppConstants.PROVINCE);
    }

    @Override
    public District getDistrict(Intent intent) {
        return (District) intent.getSerializableExtra(AppConstants.DISTRICT);
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
        mView.getDistrictAdapter().getFilter().filter(s);
    }
}
