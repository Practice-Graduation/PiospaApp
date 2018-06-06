package com.ptit.baobang.piospaapp.ui.dialogs.province;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.Province;

public interface IProvincePresenter {
    void loadData();

    void clickItem(Province province);

    Province getData(Intent intent);
}
