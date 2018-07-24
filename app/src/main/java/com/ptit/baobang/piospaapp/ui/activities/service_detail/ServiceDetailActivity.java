package com.ptit.baobang.piospaapp.ui.activities.service_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Service;
import com.ptit.baobang.piospaapp.data.model.ServicePackage;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.activities.booking_info.BookingInfoActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceDetailActivity extends BaseActivity<ServiceDetailPresenter> implements IServiceDetailView {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.txtInfo)
    TextView txtInfo;
    @BindView(R.id.btnBooking)
    Button btnBooking;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ServicePrice mServicePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        addControls();
    }

    @OnClick(R.id.btnBooking)
    void onClick(View view) {
        mPresenter.onClickAddBooking(mServicePrice);
    }

    private void addControls() {
        mPresenter = new ServiceDetailPresenter(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Dịch vụ      ");
        centerToolbarTitle(toolbar, 20);
        mServicePrice = mPresenter.getDataFromBundle(getIntent());
        mPresenter.loadData(mServicePrice);
    }

    @Override
    public void showServiceDetail(ServicePrice servicePrice) {

        String image = "", name = "", price = "", info = "";

        if (servicePrice.getService() != null) {
            Service service = servicePrice.getService();
            image = service.getImage();
            name = service.getServiceName();
            price = CommonUtils.formatToCurrency(servicePrice.getRetailPrice());
            info = service.getDescription();
            txtInfo.setText(info);
        } else if (servicePrice.getServicePackage() != null) {
            ServicePackage servicePackage = servicePrice.getServicePackage();
            image = servicePackage.getImage();
            name = servicePackage.getServicePackageName();
            price = CommonUtils.formatToCurrency(servicePrice.getAllPrice());
            mPresenter.loadServicePackageInfo(servicePackage.getServicePackageId());
        }


        txtName.setText(name);
        txtPrice.setText(new StringBuilder("Giá: " + price));
        RequestOptions options = new RequestOptions().placeholder(R.drawable.paceholder).error(R.drawable.error);
        Glide.with(this).load(image)
                .apply(options).into(img);
    }

    @Override
    public void openBookingActivity(ServicePrice servicePrice) {
        Intent intent = new Intent(this, BookingInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.SERVICE_PRICE_ID, servicePrice);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setPackageInfo(String s) {
        txtInfo.setText(s);
    }

    @Override
    public void addServicePackageTime(String time) {
        txtPrice.setText(txtPrice.getText().toString() + " - " + time + " phút");
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
