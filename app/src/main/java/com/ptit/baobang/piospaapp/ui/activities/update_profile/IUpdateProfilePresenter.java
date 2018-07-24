package com.ptit.baobang.piospaapp.ui.activities.update_profile;

import android.content.Context;
import android.graphics.Bitmap;

import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;

public interface IUpdateProfilePresenter {
    void loadData(Context baseContext);

    void logOut();

    void clickProvine(Province mProvince);

    void clickDistrict(Province mProvince, District mDistrict);

    void clickAddress(String s);

    void clickFullName(String s);

    void clickEmil(String s);

    void clickWard(District mDistrict, Ward mWard);

    void clickPhone(String s);

    void clickBirthday(String s);

    void clickGender(String s);

    void clickDone(Bitmap avatar, String fullName, String phone, String email, String birthday, String gender, Province mProvince, District mDistrict, Ward mWard, String address);

    void clickUpdateAvatar(UpdateProfileActivity updateProfileActivity);
}
