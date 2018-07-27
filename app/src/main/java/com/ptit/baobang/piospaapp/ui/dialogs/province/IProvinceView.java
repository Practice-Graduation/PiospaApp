package com.ptit.baobang.piospaapp.ui.dialogs.province;

import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.ui.adapter.ProvinceAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IProvinceView extends BaseView{
    void updateRecyleView(List<Province> provinces);

    void backToPaymentActivity(Province province);

    ProvinceAdapter getProvinceAdapter();
}
