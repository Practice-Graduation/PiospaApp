package com.ptit.baobang.piospaapp.ui.activities.profile;

import android.content.Context;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfilePresenter extends BasePresenter implements IProfilePresenter {

    private Context mContext;
    private IProfileView mView;

    public ProfilePresenter(Context mContext, IProfileView mView) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void loadData(Context baseContext) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);

        String gender = customer.getGender();
        String birday = (customer.getBirthday() == null || customer.getBirthday().trim().length() == 0) ? "" : customer.getBirthday();
        if (gender == null || gender.trim().length() == 0) {
            gender = "";
        } else {
            gender = gender.equalsIgnoreCase(mContext.getString(R.string.male)) ? mContext.getString(R.string.text_male) : mContext.getString(R.string.text_female);
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtils.DATE_PATTERN_DDMMYYTHHMMSSSSSZ, DateTimeUtils.getLocale());
        try {
            calendar.setTime(sdf.parse(birday));
            birday = DateTimeUtils.formatDate(calendar.getTime(), DateTimeUtils.DATE_PATTERN_DDMMYY);
        } catch (ParseException e) {
            e.printStackTrace();
            birday = "";
        }

        mView.loadData(
                (customer.getCustomerAvatar() == null || customer.getCustomerAvatar().trim().length() == 0) ? "" : customer.getCustomerAvatar(),
                customer.getFullname(),
                (customer.getPhone() == null || customer.getPhone().trim().length() == 0) ? "" : customer.getPhone(),
                (customer.getEmail() == null || customer.getEmail().trim().length() == 0) ? "" : customer.getEmail(),
                birday,
                gender,
                customer.getProvince(),
                customer.getDistrict(),
                customer.getWard(),
                (customer.getAddress() == null || customer.getAddress().trim().length() == 0) ? "" : customer.getAddress());
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
