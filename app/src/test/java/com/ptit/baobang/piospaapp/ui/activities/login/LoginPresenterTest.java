package com.ptit.baobang.piospaapp.ui.activities.login;

import android.content.Context;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.error.Error;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Callback;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private Context context;
    @Mock
    private ILoginView view;
    private LoginPresenter presenter;

    @Captor
    private ArgumentCaptor<Callback<EndPoint<Customer>>> callbackArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(context, view);
    }

    @Test
    public void usernameIsEmpty() {
        when(view.getUserName()).thenReturn("");
        presenter.onClickLogin(view.getUserName(), view.getPassword());
        verify(view).showMessage(context.getString(R.string.message), Error.ERROR_LOGIN_USR_EMPTY, SweetAlertDialog.WARNING_TYPE);
    }

    @Test
    public void passwordIsEmpty() {
        when(view.getUserName()).thenReturn("anyValue");
        when(view.getPassword()).thenReturn("");
        presenter.onClickLogin(view.getUserName(), view.getPassword());
        verify(view).showMessage(context.getString(R.string.message),Error.ERROR_LOGIN_PWD_EMPTY, SweetAlertDialog.WARNING_TYPE);

    }
}