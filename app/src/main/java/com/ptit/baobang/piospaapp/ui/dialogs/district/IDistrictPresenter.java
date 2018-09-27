package com.ptit.baobang.piospaapp.ui.dialogs.district;

import android.content.Intent;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;

public interface IDistrictPresenter {
    void loadData(Province province);

    void clickItem(District district);

    Province getProvince(Intent intent);

    District getDistrict(Intent intent);

    void filter(SearchView searchView);
}
