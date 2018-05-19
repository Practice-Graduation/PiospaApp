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
import com.ptit.baobang.piospaapp.ui.activities.booking.BookingActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceDetailActivity extends BaseActivity implements IServiceDetailView {

    ServiceDetailPresenter mPresenter;

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
    private int servicePriceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        getData();
        addControls();
    }

    @OnClick(R.id.btnBooking)
    void onClick(View view) {
        mPresenter.onClickAddBooking(servicePriceId);
    }

    private void addControls() {
        mPresenter = new ServiceDetailPresenter(this);
        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mPresenter.loadData(servicePriceId);
    }

    @Override
    public void showServiceDetail(ServicePrice servicePrice) {

        String image = "", name = "", price = "";

        if(servicePrice.getService() != null){
            Service service = servicePrice.getService();
            image = service.getImage();
            name = service.getServiceName();
            price = CommonUtils.formatToCurrency(servicePrice.getRetailPrice());
        }else if(servicePrice.getServicePackage() != null){
            ServicePackage servicePackage = servicePrice.getServicePackage();
            image = servicePackage.getImage();
            name = servicePackage.getServicePackageName();
            price = CommonUtils.formatToCurrency(servicePrice.getAllPrice());
        }


        txtName.setText(name);
        txtPrice.setText(price);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.paceholder).error(R.drawable.error);
        Glide.with(this).load(image)
                .apply(options).into(img);
    }

    @Override
    public void openBookingActivity(int servicePriceId) {
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra(AppConstants.SERVICE_PRICE_ID, servicePriceId);
        startActivity(intent);
    }

    public void getData() {
        Bundle bundle = getIntent().getBundleExtra(AppConstants.SERVICE_PRICE_BUNDLE);
        servicePriceId = bundle.getInt(AppConstants.SERVICE_PRICE_ID);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
