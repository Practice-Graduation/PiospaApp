package com.ptit.baobang.piospaapp.ui.activities.profile;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

public interface IProfileView extends BaseView{
    void loadData(String customerAvatar, String fullname,
                  String phone, String email,
                  String birthday, String gender,
                  Province province, District district,
                  Ward ward, String address);

}
