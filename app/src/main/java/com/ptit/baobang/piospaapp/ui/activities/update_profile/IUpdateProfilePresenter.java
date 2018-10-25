package com.ptit.baobang.piospaapp.ui.activities.update_profile;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.dto.CustomerProfileDTO;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;

public interface IUpdateProfilePresenter {
    void loadData(Context baseContext);

    void clickProvince(Province mProvince);

    void clickDistrict(Province mProvince, District mDistrict);

    void clickWard(District mDistrict, Ward mWard);

    void clickBirthday(String s);

    void clickGender(String s);

    void clickDone(CustomerProfileDTO customerProfileDTO);

    void clickUpdateAvatar(UpdateProfileActivity updateProfileActivity);
}
