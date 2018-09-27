package com.ptit.baobang.piospaapp.ui.dialogs.province;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProvincePresenter extends BasePresenter implements IProvincePresenter {

    private IProvinceView mView;
    private Context mContext;
    ProvincePresenter(Context mContext, IProvinceView mView) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void loadData() {
        mView.showLoading(mContext.getString(R.string.loading));
        getCompositeDisposable().add(
                mApiService.getAllProvince()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {

        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handleResponse(EndPoint<List<Province>> listEndPoint) {
        List<Province> provinces = listEndPoint.getData();
        mView.updateRecyleView(provinces);
        mView.hideLoading();
    }

    @Override
    public void clickItem(Province province) {
        mView.backToPaymentActivity(province);
    }

    @Override
    public Province getData(Intent intent) {
        return (Province) intent.getSerializableExtra(KeyBundleConstant.PROVINCE);
    }

    @Override
    public void filter(SearchView searchView) {
        RxSearchObservable.fromView(searchView)
                .debounce(RxSearchObservable.TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handFilterResponse);
    }

    private void handFilterResponse(String s) {
        mView.getProvinceAdapter().getFilter().filter(s);
    }
}
