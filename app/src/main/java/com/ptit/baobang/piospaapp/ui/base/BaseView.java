package com.ptit.baobang.piospaapp.ui.base;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;

public interface BaseView {
    void hideLoading();
    void hideLoading(String message, boolean isSuccess);
    void showLoading(String message);
    void showMessage(String title, @StringRes int message, int messageType);
    void showMessage(String title, String message, int messageType);
    boolean isNetworkConnected();
    void hideKeyboard();
    void hideKeyboardOutside(View view);
    Context getBaseContext();
}
