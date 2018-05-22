package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IServiceView extends BaseView {
    void openAllServiceActivity(int serviceGroupId);
    void openServiceDetailActivity(int ServiceId);
    void onUpdateRecycleView(List<ServiceGroup> serviceGroups);
    void startShirrmentAnimation();
    void stopShirrmentAnimation();
}
