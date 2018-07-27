package com.ptit.baobang.piospaapp.ui.dialogs.province;

import android.content.Intent;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.Province;

public interface IProvincePresenter {
    void loadData();

    void clickItem(Province province);

    Province getData(Intent intent);

    void filter(SearchView searchView);
}
