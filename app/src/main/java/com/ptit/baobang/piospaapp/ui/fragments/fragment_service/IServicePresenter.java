package com.ptit.baobang.piospaapp.ui.fragments.fragment_service;

import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.model.ServiceGroup;

public interface IServicePresenter {
    void onClickMore(ServiceGroup serviceGroup);
    void loadData();
}
