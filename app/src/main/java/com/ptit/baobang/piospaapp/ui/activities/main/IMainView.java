package com.ptit.baobang.piospaapp.ui.activities.main;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

public interface IMainView extends BaseView{
    void openOrderActivity();
    void logOut();
    void openProfileActivity();
    void openChangePasswordActivity();
    void loadUserInfo(String image, String username, String email);

    void openScanBarcodeActivity();

    void openProductDetail(Product data);
}
