package com.ptit.baobang.piospaapp.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.error.Error;
import com.ptit.baobang.piospaapp.ui.listener.CallBackConfirmDialog;
import com.ptit.baobang.piospaapp.ui.listener.CallBackDialog;
import com.ptit.baobang.piospaapp.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.fabric.sdk.android.Fabric;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected Unbinder mUnbind;
    private SweetAlertDialog mSweetAlertDialog;
    protected T mPresenter;
    private String COLOR = "#A5DC86";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mUnbind = ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading(String message) {
        if (!isFinishing()) {
            if (mSweetAlertDialog == null || !mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor(COLOR));
                mSweetAlertDialog.setTitleText(message);
                mSweetAlertDialog.setCancelable(false);
                mSweetAlertDialog.show();
            } else {
                mSweetAlertDialog.setTitleText(message);
            }
        }
    }

    @Override
    public void hideLoading(String message, boolean isSuccess) {
        if (!isFinishing()) {
            if (mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.setCanceledOnTouchOutside(true);
                mSweetAlertDialog.setTitleText(message);
                mSweetAlertDialog.setConfirmText(getString(R.string.ok));
                if (isSuccess) {
                    mSweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                } else {
                    mSweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            }
        }
    }

    @Override
    public void hideLoading(Error error, boolean isSuccess) {
        if (!isFinishing()) {
            if (mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.setCanceledOnTouchOutside(true);
                mSweetAlertDialog.setTitleText(error.toString());
                mSweetAlertDialog.setConfirmText(getString(R.string.ok));
                if (isSuccess) {
                    mSweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                } else {
                    mSweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            }
        }
    }

    @Override
    public void hideLoading() {
        if (!isFinishing()) {
            if (mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.dismissWithAnimation();
            }
        }
    }

    @Override
    public void showMessage(String title, int message, int messageType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, messageType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(getString(message));
        sweetAlertDialog.setConfirmText(getString(R.string.ok));
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.show();
    }

    @Override
    public void showMessage(String title, Error error, int messageType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, messageType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(error.toString());
        sweetAlertDialog.setConfirmText(getString(R.string.ok));
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.show();
    }

    @Override
    public void showMessage(String title, String message, int messageType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, messageType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(message);
        sweetAlertDialog.setConfirmText(getString(R.string.ok));
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.show();
    }

    public void showEnterTextDialog(String title,
                                    String message,
                                    String text_pos,
                                    String text_neg,
                                    CallBackDialog callback) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.enter_text_layout, null);

        final EditText edt = dialogView.findViewById(R.id.txtText);
        TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        Button btnOk = dialogView.findViewById(R.id.btnOk);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        txtTitle.setText(title);

        edt.setText(message);

        btnOk.setText(text_pos);

        btnCancel.setText(text_neg);

        dialogBuilder.setView(dialogView);
        AlertDialog b = dialogBuilder.create();

        btnOk.setOnClickListener(v -> callback.diaglogPositive(b, edt.getText().toString()));

        btnCancel.setOnClickListener(v -> {
            b.dismiss();
            callback.diaglogNegative();
        });

        b.setCanceledOnTouchOutside(false);
        b.show();
    }

    public void centerToolbarTitle(Toolbar toolbar, int paddingRight) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER);
            titleView.setPadding(0, 0, paddingRight, 0);
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
            //also you can use titleView for changing font: titleView.setTypeface(Typeface);
        }

    }

    @Override
    public void showConfirm(String title,
                            String message,
                            String text_pos,
                            String text_neg,
                            int msgType,
                            CallBackConfirmDialog callback) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, msgType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(message);
        sweetAlertDialog.setConfirmText(text_pos);
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                callback.DiaglogPositive();
            }
        });
        sweetAlertDialog.setCancelText(text_neg);
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                callback.DiaglogNegative();
            }
        });
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void hideKeyboardOutside(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) view.setOnTouchListener(
                (v, event) -> {
                    hideKeyboard();
                    return false;
                });
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardOutside(innerView);
            }
        }
    }

    @Override
    protected void onDestroy() {

        if (mUnbind != null) {
            mUnbind.unbind();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.unSubscribeRequests();
        }
    }
}
