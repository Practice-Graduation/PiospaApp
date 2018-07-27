package com.ptit.baobang.piospaapp.ui.dialogs.ward;

import android.content.Intent;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Ward;

public interface IWardPresenter {
    void loadData(District district);

    void clickItem(Ward ward);

    District getDistrict(Intent intent);

    Ward getWard(Intent intent);

    void filter(SearchView searchView);
}
