package com.ptit.baobang.piospaapp.ui.dialogs.ward;

import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.adapter.WardAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IWardView extends BaseView{
    void updateRecyleView(List<Ward> wards);

    void backToPaymentActivity(Ward ward);

    WardAdapter getWardAdapter();
}
