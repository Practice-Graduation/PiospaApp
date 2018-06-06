package com.ptit.baobang.piospaapp.ui.dialogs.province;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.List;

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
        mApiService.getAllProvince().enqueue(new Callback<EndPoint<List<Province>>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<List<Province>>> call, @NonNull Response<EndPoint<List<Province>>> response) {
                if(response.isSuccessful()){
                    List<Province> provinces = response.body().getData();
                    mView.updateRecyleView(provinces);
                    mView.stopShimmerAnimation();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EndPoint<List<Province>>> call, @NonNull Throwable t) {

            }
        });
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
