package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;

public interface IServicePresenter {
    void onClickMore(ServiceGroup serviceGroup);
    void loadData();

    void clickItem(ServicePrice servicePrice);
}
