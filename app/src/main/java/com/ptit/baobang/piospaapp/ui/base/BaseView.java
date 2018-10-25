package com.ptit.baobang.piospaapp.ui.base;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;

import com.ptit.baobang.piospaapp.ui.listener.CallBackConfirmDialog;
import com.ptit.baobang.piospaapp.error.Error;

public interface BaseView {
    void hideLoading();

    void hideLoading(String message, boolean isSuccess);

    void hideLoading(Error error, boolean isSuccess);

    void showLoading(String message);

    void showMessage(String title, @StringRes int message, int messageType);

    void showMessage(String message);

    void showMessage(@StringRes int message);

    void showMessage(String title, String message, int messageType);

    boolean isNetworkConnected();

    void hideKeyboard();

    void hideKeyboardOutside(View view);

    Context getBaseContext();

    void showConfirm(String title,
                     String message,
                     String text_pos,
                     String text_neg,
                     int msgType,
                     CallBackConfirmDialog callback);

    void showConfirm(String message,
                     CallBackConfirmDialog callback);

    void showMessage(String string, Error error, int warningType);

    void showWarningMessage(Error error);

    void showErrorMessage(Error error);
}
