package com.ptit.baobang.piospaapp.ui.activities.login;

public interface ILoginPresenter<V extends ILoginView>{
    void onClickLogin(String username, String password);
    void onClickRegister();
}
