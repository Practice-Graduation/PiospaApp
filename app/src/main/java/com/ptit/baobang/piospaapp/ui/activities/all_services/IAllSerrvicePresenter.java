package com.ptit.baobang.piospaapp.ui.activities.all_services;

import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;

public interface IAllSerrvicePresenter {
    void loadData(int serviceGroup);
    void onSelectedProduct(ServicePrice servicePrice);

    void filter(SearchView searchView);
}
