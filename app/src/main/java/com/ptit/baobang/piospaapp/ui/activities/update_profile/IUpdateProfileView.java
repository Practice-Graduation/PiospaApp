package com.ptit.baobang.piospaapp.ui.activities.update_profile;

import android.graphics.Bitmap;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

public interface IUpdateProfileView extends BaseView{
    void loadData(String customerAvatar, String fullname,
                  String phone, String email,
                  String birthday, String gender,
                  Province province, District district,
                  Ward ward, String address);

    void showOrderActivity();

    void showOrderActivity(int i);

    void logOut();

    void onClickUpdate(Customer customer);

    void onClickProvince(Province mProvince);

    void onClickDistrict(District mDistrict);

    void onClickAddress(String s);

    void onClickFullName(String s);

    void onClickEmail(String s);

    void onClickWard(Ward mWard);

    void onClickPhone(String s);

    void onClickGender(String s);

    void onClickBirthday(String s);

    void updateUIAvatar(Bitmap avatar);

    void setNullAvatar();
}
