package com.ptit.baobang.piospaapp.ui.activities.profile;

import android.content.Context;

public interface IProfilePresenter {
    void loadData(Context baseContext);

    void clickShowOrder();

    void clickShowOrder(int i);

    void logOut();

    void clickUpdate();


    void clickChangePassword();

}
