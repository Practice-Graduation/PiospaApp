package com.ptit.baobang.piospaapp.ui.activities.register;

public interface IRegisterPresenter{
    void clickBackLogin();
    void clickRegister(String fullName, String email, String password, String retypePassword);
}
