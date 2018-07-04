package com.ptit.baobang.piospaapp.ui.activities.all_services;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.adapter.ServiceAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IAllServiceView extends BaseView{
    void onUpdateRecyleView(List<ServicePrice> servicePrices);
    void openProductDetailActivity(ServicePrice servicePrice);

    ServiceAdapter getServerAdapter();
}
