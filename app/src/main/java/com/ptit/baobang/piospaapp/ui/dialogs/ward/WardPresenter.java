package com.ptit.baobang.piospaapp.ui.dialogs.ward;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WardPresenter extends BasePresenter implements IWardPresenter {

    private IWardView mView;

    WardPresenter(IWardView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(District district) {
        mApiService.getWardtByDistrictId(district.getDistrictid()).enqueue(new Callback<EndPoint<List<Ward>>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<List<Ward>>> call, Response<EndPoint<List<Ward>>> response) {
                if (response.isSuccessful()) {
                    List<Ward> wards = response.body().getData();
                    mView.updateRecyleView(wards);
                    mView.stopShimmerAnimation();
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<Ward>>> call, Throwable t) {

            }
        });
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
