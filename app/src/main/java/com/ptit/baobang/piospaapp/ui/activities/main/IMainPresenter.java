package com.ptit.baobang.piospaapp.ui.activities.main;

import android.content.Context;

public interface IMainPresenter {
    void clickNavProfile();

    void clickNavOrder();

    void clickNavChangePassword();

    void clickNavLogout();

    void loadUserInfo(Context context);
}
