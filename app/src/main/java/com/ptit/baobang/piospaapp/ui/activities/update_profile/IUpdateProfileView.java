package com.ptit.baobang.piospaapp.ui.activities.update_profile;

import android.graphics.Bitmap;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

public interface IUpdateProfileView extends BaseView{
    void loadData(Customer customer);

    void onClickProvince(Province mProvince);

    void onClickDistrict(District mDistrict);

    void onClickWard(Ward mWard);

    void onClickGender(String s);

    void onClickBirthday(String s);

    void updateUIAvatar(Bitmap avatar);

    void setNullAvatar();

    void loadAvatar(String customerAvatar);

    void cameraIntent();

    void galleryIntent();

    boolean checkCamPermission();

    boolean checkGalleryPermission();

}
