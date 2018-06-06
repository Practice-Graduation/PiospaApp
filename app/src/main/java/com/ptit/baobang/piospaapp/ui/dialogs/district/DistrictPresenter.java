package com.ptit.baobang.piospaapp.ui.dialogs.district;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.List;

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
        mApiService.getDistrictByProvinceId(province.getProvinceid()).enqueue(new Callback<EndPoint<List<District>>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<List<District>>> call, @NonNull Response<EndPoint<List<District>>> response) {
                if(response.isSuccessful()){
                    List<District> districts = response.body().getData();
                    mView.updateRecyleView(districts);
                    mView.stopShimmerAnimation();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EndPoint<List<District>>> call, @NonNull Throwable t) {

            }
        });
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
