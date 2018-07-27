package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;

public interface IServicePresenter {
    void onClickMore(ServiceGroup serviceGroup);
    void loadData();

    void clickItem(ServicePrice servicePrice);

    void filter(SearchView searchView);
}
