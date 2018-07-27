package com.ptit.baobang.piospaapp.ui.dialogs.district;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.ui.adapter.DistrictAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IDistrictView extends BaseView{

    void updateRecyleView(List<District> districts);

    void backToPaymentActivity(District district);

    DistrictAdapter getDistrictAdapter();
}
