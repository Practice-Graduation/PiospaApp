package com.ptit.baobang.piospaapp.ui.activities.all_services;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;

public interface IAllSerrvicePresenter {
    void loadData(int serviceGroup);
    void onSelectedProduct(ServicePrice servicePrice);
}
