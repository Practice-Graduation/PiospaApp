package com.ptit.baobang.piospaapp.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ptit.baobang.piospaapp.ui.listener.CallBackConfirmDialog;
import com.ptit.baobang.piospaapp.utils.NetworkUtils;

import java.util.Objects;

import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    protected Unbinder mUnBinder;
    private SweetAlertDialog mSweetAlertDialog;
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideKeyboardOutside(view);
    }

    @Override
    public Context getBaseContext() {
        return getContext();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void showLoading(String message) {
        if(!Objects.requireNonNull(getActivity()).isFinishing()) {
            if (mSweetAlertDialog == null || !mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog = new SweetAlertDialog(getBaseContext(), SweetAlertDialog.PROGRESS_TYPE);
                mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
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
        if(!Objects.requireNonNull(getActivity()).isFinishing()) {
            if (mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.setCanceledOnTouchOutside(true);
                mSweetAlertDialog.setTitleText(message);
                mSweetAlertDialog.setConfirmText("OK");
                if (isSuccess) {
                    mSweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                } else {
                    mSweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            }
        }
    }

    @Override
    public void showConfirm(String title, String message, String text_pos, String text_neg, int msgType, CallBackConfirmDialog callback) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getBaseContext(), msgType);
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
    public void hideLoading() {
        if(!Objects.requireNonNull(getActivity()).isFinishing()) {
            if (mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.dismissWithAnimation();
            }
        }
    }

    @Override
    public void showMessage(String title, int message, int messageType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getBaseContext(),messageType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(getString(message));
        sweetAlertDialog.setConfirmText("OK");
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.show();
    }

    @Override
    public void showMessage(String title, String message, int messageType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getBaseContext(),messageType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(message);
        sweetAlertDialog.setConfirmText("OK");
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.show();
    }

    @Override
    public boolean isNetworkConnected() {

        return NetworkUtils.isNetworkConnected(getBaseContext());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mPresenter != null){
            mPresenter.unSubscribeRequests();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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
}
