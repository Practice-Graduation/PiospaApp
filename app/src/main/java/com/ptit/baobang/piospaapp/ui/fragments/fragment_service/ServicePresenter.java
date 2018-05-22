package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicePresenter extends BasePresenter implements IServicePresenter{

    private IServiceView mView;

    public ServicePresenter(IServiceView mView) {
        this.mView = mView;
    }

    @Override
    public void onClickMore(ServiceGroup serviceGroup) {
        mView.openAllServiceActivity(serviceGroup.getServiceGroupId());
    }

    @Override
    public void loadData() {
        mApiService.getAllServiceGroup().enqueue(new Callback<EndPoint<List<ServiceGroup>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<ServiceGroup>>> call, Response<EndPoint<List<ServiceGroup>>> response) {
                if(response.isSuccessful()){
                    List<ServiceGroup> groups = response.body().getData();
                    mView.onUpdateRecycleView(groups);
                    mView.stopShirrmentAnimation();
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<ServiceGroup>>> call, Throwable t) {
                mView.showMessage(t.getMessage());
            }
        });
    }
}
