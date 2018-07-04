package com.ptit.baobang.piospaapp.ui.activities.profile;

import android.content.Context;

import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfilePresenter extends BasePresenter implements IProfilePresenter {

    private Context mContext;
    private IProfileView mView;

    public ProfilePresenter(Context mContext,IProfileView mView) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void loadData(Context baseContext) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);

        String gender = customer.getGender();

        gender = gender.equalsIgnoreCase("male") ? "Nam" : "Nữ";
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("vi", "VN"));
        try {
            calendar.setTime(sdf.parse(customer.getBirthday()));
        } catch (ParseException e) {

            e.printStackTrace();

            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", new Locale("vi", "VN"));
            try {
                calendar.setTime(sdf.parse(customer.getBirthday()));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        mView.loadData(customer.getCustomerAvatar(),
                customer.getFullname(),
                customer.getPhone(),
                customer.getEmail(),
                DateTimeUtils.formatDate(calendar.getTime(), DateTimeUtils.DATE_PATTERN_DDMMYY),
                gender,
                customer.getProvince(),
                customer.getDistrict(),
                customer.getWard(),
                customer.getAddress());
    }

    @Override
    public void clickShowOrder() {
        mView.showOrderActivity();
    }

    @Override
    public void clickShowOrder(int i) {
        mView.showOrderActivity(i);
    }

    @Override
    public void logOut() {
        mView.logOut();
    }

    @Override
    public void clickUpdate() {
        Customer customer = SharedPreferenceUtils.getUser(mContext);
        mView.onClickUpdate(customer);
    }

    @Override
    public void clickChangePassword() {
        mView.openChangePasswordActivity();
    }

}
