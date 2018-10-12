package com.ptit.baobang.piospaapp.ui.activities.login;

import com.ptit.baobang.piospaapp.ui.base.BaseView;

public interface ILoginView extends BaseView {
    void openMainActivity();

    void openRegisterActivity();

    String getUserName();

    String getPassword();

}
