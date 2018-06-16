package com.ptit.baobang.piospaapp.ui.dialogs.district;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictPresenter extends BasePresenter implements IDistrictPresenter {

    private IDistrictView mView;

    DistrictPresenter(IDistrictView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(Province province) {

        getCompositeDisposable().add(
                mApiService.getDistrictByProvinceId(province.getProvinceid())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {

    }

    private void handleResponse(EndPoint<List<District>> listEndPoint) {
        List<District> districts = listEndPoint.getData();
        mView.updateRecyleView(districts);
        mView.stopShimmerAnimation();
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
}
