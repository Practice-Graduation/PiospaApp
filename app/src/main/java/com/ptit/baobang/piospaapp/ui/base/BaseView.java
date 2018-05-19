package com.ptit.baobang.piospaapp.ui.base;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;

public interface BaseView {
    void hideLoading();
    void showLoading();
    void onError(String message);
    void onError(@StringRes int message);
    void showMessage(String message);
    void showMessage(@StringRes int message);
    boolean isNetworkConnected();
    void hideKeyboard();
    void hideKeyboardOutside(View view);
    Context getBaseContext();
}
