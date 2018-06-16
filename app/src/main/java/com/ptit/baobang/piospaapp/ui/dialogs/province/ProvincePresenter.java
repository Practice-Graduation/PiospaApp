package com.ptit.baobang.piospaapp.ui.dialogs.province;

import android.content.Intent;
import android.support.annotation.NonNull;

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

public class ProvincePresenter extends BasePresenter implements IProvincePresenter {

    private IProvinceView mView;

    ProvincePresenter(IProvinceView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData() {

        getCompositeDisposable().add(
                mApiService.getAllProvince()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {

    }

    private void handleResponse(EndPoint<List<Province>> listEndPoint) {
        List<Province> provinces = listEndPoint.getData();
        mView.updateRecyleView(provinces);
        mView.stopShimmerAnimation();
    }

    @Override
    public void clickItem(Province province) {
        mView.backToPaymentActivity(province);
    }

    @Override
    public Province getData(Intent intent) {
        return (Province) intent.getSerializableExtra(AppConstants.PROVINCE);
    }
}
