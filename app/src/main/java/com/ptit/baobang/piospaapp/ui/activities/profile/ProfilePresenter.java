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
    private IProfileView mView;

    public ProfilePresenter(IProfileView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(Context baseContext) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);

        String gender = customer.getGender();

        gender = gender.equalsIgnoreCase("male") ? "Nam" : "Nữ";
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("vi", "VN"));
        try {
            calendar.setTime(sdf.parse(customer.getBirthday()));
        } catch (ParseException e) {
            e.printStackTrace();
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
}
