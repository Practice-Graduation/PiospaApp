package com.ptit.baobang.piospaapp.ui.activities.order_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderProductRealm;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.adapter.OrderProductAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DefaultValue;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;
import net.cachapa.expandablelayout.ExpandableLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements IOrderDetailView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cvOrderStatus)
    CardView cvOrderStatus;

    @BindView(R.id.txtOrderId)
    TextView txtOrderId;

    @BindView(R.id.txtCreatedAt)
    TextView txtCreatedAt;

    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @BindView(R.id.txtFullName)
    TextView txtFullName;

    @BindView(R.id.txtAddressComfirm)
    TextView txtAddressComfirm;

    @BindView(R.id.txtWardComfirm)
    TextView txtWardComfirm;

    @BindView(R.id.txtDistrictComfirm)
    TextView txtDistrictComfirm;

    @BindView(R.id.txtProvinceComfirm)
    TextView txtProvinceComfirm;

    @BindView(R.id.txtDeliveryType)
    TextView txtDeliveryType;

    @BindView(R.id.txtPaymentType)
    TextView txtPaymentType;

    @BindView(R.id.txtPaymentTypeDescription)
    TextView txtPaymentTypeDescription;

    @BindView(R.id.txtTotal)
    TextView txtTotal;

    @BindView(R.id.txtShip)
    TextView txtShip;

    @BindView(R.id.txtPayment)
    TextView txtPayment;

    @BindView(R.id.layout_product)
    LinearLayout layoutProduct;

    @BindView(R.id.layout_service)
    LinearLayout layoutService;

    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.lbTax)
    TextView lbTax;
    @BindView(R.id.txtTax)
    TextView txtTax;
    private OrderRealm order;


    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    List<OrderProductRealm> mProduct;
    OrderProductAdapter mProductAdapter;

    @BindView(R.id.cvDeliveyType)
    CardView cvDeliveyType;
    @BindView(R.id.cvPaymentTypeComfirm)
    CardView cvPaymentTypeComfirm;
    @BindView(R.id.lbShip)
    TextView lbShip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setUpStackMainScreen();
        init();
    }
    private void setUpStackMainScreen() {
        int current = android.os.Process.myPid();
        if (current != SharedPreferenceUtils.getProcessID(this)){
            SharedPreferenceUtils.saveCurrentProcessID(this);
            SharedPreferenceUtils.saveCount(this, DefaultValue.INT);
        }
    }
    private void init() {
        mPresenter = new OrderDetailPresenter(this, this);
        setToolbar();


        mProduct = new ArrayList<>();
        mProductAdapter = new OrderProductAdapter(this, mProduct);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(mProductAdapter);

        order = mPresenter.getDate(getIntent());
        mPresenter.loadData(order);
    }

    @OnClick(R.id.btnCancel)
    void onClick(View view){
        switch (view.getId()){
            case R.id.btnCancel:
                mPresenter.clickCancelOrder(order);
                break;
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getString(R.string.order_detail) + "     ");
        centerToolbarTitle(toolbar, DefaultValue.INT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        int count = SharedPreferenceUtils.getCount(this);
        if(count == 0){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else{
            finish();
        }
    }

    @Override
    public void setView(String code, String createdAt, String orderStatusName, String fullName, String address, String ward, String district,
                        String province, String phone, List<OrderProductRealm> productItems, String orderDeliveryTypeName,
                        String orderPaymentTypeName, String orderPaymentTypeDescription,
                        String total, String ship, String subtotal) {

        switch (orderStatusName) {
            case AppConstants.NOT_PAY_MENT:
                cvOrderStatus.setBackgroundResource(R.color.light_blue);
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case AppConstants.PAYMENT:
                cvOrderStatus.setBackgroundResource(R.color.light_green);
                btnCancel.setVisibility(View.GONE);
                break;
            case AppConstants.CANCEL:
                cvOrderStatus.setBackgroundResource(R.color.light_red);
                btnCancel.setVisibility(View.GONE);
                break;
        }

        txtOrderId.setText(getString(R.string.order_id) + code);
        txtCreatedAt.setText(getString(R.string.created_at) + createdAt);
        txtStatus.setText(getString(R.string.status) + orderStatusName);
        txtFullName.setText(new StringBuilder(fullName + AppConstants.TAB_SYMBOL + phone));
        txtAddressComfirm.setText(address);
        txtWardComfirm.setText(ward);
        txtDistrictComfirm.setText(district);
        txtProvinceComfirm.setText(province);

        if(orderDeliveryTypeName.equals("")){
            cvDeliveyType.setVisibility(View.GONE);
            lbShip.setVisibility(View.GONE);
            txtShip.setVisibility(View.GONE);
        }else{
            txtDeliveryType.setText(orderDeliveryTypeName);
        }

        if(orderPaymentTypeName.equals("")){
            cvPaymentTypeComfirm.setVisibility(View.GONE);
        }else{


            txtPaymentType.setText(orderPaymentTypeName);
            txtPaymentTypeDescription.setText(orderPaymentTypeDescription);
        }

        txtTotal.setText(total);
        txtShip.setText(ship);
        txtPayment.setText(subtotal);
        updateRecycleProducts(productItems);
        if (mProduct.size() == 0) {
            layoutProduct.setVisibility(View.GONE);
        } else {
            layoutProduct.setVisibility(View.VISIBLE);
        }

        expandableLayout.expand(true);

    }



    @Override
    public void updateRecycleProducts(List<OrderProductRealm> productItems) {
        mProduct.clear();
        mProduct.addAll(productItems);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void setView(String orderStatusName) {
        switch (orderStatusName) {
            case AppConstants.NOT_PAY_MENT:
                cvOrderStatus.setBackgroundResource(R.color.light_blue);
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case AppConstants.PAYMENT:
                cvOrderStatus.setBackgroundResource(R.color.light_green);
                btnCancel.setVisibility(View.GONE);
                break;
            case AppConstants.CANCEL:
                cvOrderStatus.setBackgroundResource(R.color.light_red);
                btnCancel.setVisibility(View.GONE);
                break;
        }
        txtStatus.setText(getString(R.string.status) + orderStatusName);
    }


    @Override
    public void setTax(String taxName, int taxValue, String taxUnit) {
        lbTax.setText(taxName);
        if(taxUnit.equals(AppConstants.PECENT)){
            txtTax.setText(new StringBuilder(taxValue + AppConstants.PERCENT_SYMBOL));
        }else if(taxUnit.equals(AppConstants.MONEY)){
            txtTax.setText(CommonUtils.formatToCurrency(taxValue));
        }
    }

}
