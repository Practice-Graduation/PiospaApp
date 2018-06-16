package com.ptit.baobang.piospaapp.ui.activities.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryType;
import com.ptit.baobang.piospaapp.data.model.OrderPaymentType;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.adapter.DeliveryTypeAdapter;
import com.ptit.baobang.piospaapp.ui.adapter.PaymentTypeAdapter;
import com.ptit.baobang.piospaapp.ui.adapter.ProductCartComfirmAdapter;
import com.ptit.baobang.piospaapp.ui.adapter.ServiceCartComfirmAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.district.DistrictActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.province.ProvinceActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.ward.WardActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity<PaymentPresenter> implements IPaymentView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.stepView)
    StepView stepView;

    @BindView(R.id.delivery_info)
    NestedScrollView nsvDeliveryInfo;

    @BindView(R.id.payment_type)
    NestedScrollView nsvPaymentType;

    @BindView(R.id.comfirm)
    NestedScrollView nsvComfirm;

    @BindView(R.id.txtName)
    EditText txtName;

    @BindView(R.id.txtPhone)
    EditText txtPhone;

    @BindView(R.id.txtAddress)
    EditText txtAddress;

    @BindView(R.id.txtProvince)
    TextView txtProvince;

    @BindView(R.id.txtDistrict)
    TextView txtDistrict;

    @BindView(R.id.txtWard)
    TextView txtWard;

    @BindView(R.id.rvPaymentType)
    RecyclerView rvPaymentType;

    @BindView(R.id.rvDeliveryType)
    RecyclerView rvDeliveryType;

    @BindView(R.id.btnNext)
    Button btnNext;

    // Comfirm
    @BindView(R.id.titleProduct)
    TextView titleProduct;

    @BindView(R.id.titleServices)
    TextView titleServices;

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

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;

    @BindView(R.id.rvServices)
    RecyclerView rvServices;

    List<CartProductItem> mCartProductItems;
    ProductCartComfirmAdapter mProductCartComfirmAdapter;

    List<CartServicePriceItem> mCartServicePriceItems;
    ServiceCartComfirmAdapter mServiceCartComfirmAdapter;

    List<OrderPaymentType> mOrderPaymentTypes;
    PaymentTypeAdapter mPaymentTypeAdapter;

    List<OrderDeliveryType> mOrderDeliveryTypes;
    DeliveryTypeAdapter mDeliveryTypeAdapter;

    private Province mProvince;
    private District mDistrict;
    private Ward mWard;
    private OrderDeliveryType mDeliveryType;
    private OrderPaymentType mPaymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_delivery_info);
        addControls();
        addEvents();
    }

    private void addEvents() {
        mPaymentTypeAdapter.setListener(position -> mPaymentType = mOrderPaymentTypes.get(position));

        mDeliveryTypeAdapter.setmListener(position -> mDeliveryType = mOrderDeliveryTypes.get(position));
    }

    @OnClick({R.id.btnNext, R.id.txtProvince, R.id.txtDistrict, R.id.txtWard})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                mPresenter.clickButtonNext(stepView.getCurrentStep(),
                        txtName.getText().toString(),
                        txtPhone.getText().toString(),
                        mProvince,
                        mDistrict,
                        mWard,
                        txtAddress.getText().toString(),
                        mDeliveryType,
                        mPaymentType);
                break;
            case R.id.txtProvince:
                mPresenter.clickTextViewProvince();
                break;
            case R.id.txtDistrict:
                mPresenter.clickTextViewDistrict(mProvince);
                break;
            case R.id.txtWard:
                mPresenter.clickTextViewWard(mDistrict);
                break;
        }
    }

    private void addControls() {

        setSupportToolbar();

        mPresenter = new PaymentPresenter(this, this);
        nsvPaymentType.setVisibility(View.GONE);
        nsvComfirm.setVisibility(View.GONE);
        stepView.getState()
                .steps(Arrays.asList(getResources().getStringArray(R.array.arr_step)))
                .stepsNumber(getResources().getStringArray(R.array.arr_step).length)
                .commit();

        txtDistrict.setClickable(false);
        txtWard.setClickable(false);

        mOrderDeliveryTypes = new ArrayList<>();
        mDeliveryTypeAdapter = new DeliveryTypeAdapter(this, mOrderDeliveryTypes);
        rvDeliveryType.setLayoutManager(new LinearLayoutManager(this));
        rvDeliveryType.setAdapter(mDeliveryTypeAdapter);

        mOrderPaymentTypes = new ArrayList<>();
        mPaymentTypeAdapter = new PaymentTypeAdapter(this, mOrderPaymentTypes);
        rvPaymentType.setLayoutManager(new LinearLayoutManager(this));
        rvPaymentType.setAdapter(mPaymentTypeAdapter);

        mCartProductItems = new ArrayList<>();
        mProductCartComfirmAdapter = new ProductCartComfirmAdapter(this, mCartProductItems);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(mProductCartComfirmAdapter);

        mCartServicePriceItems = new ArrayList<>();
        mServiceCartComfirmAdapter = new ServiceCartComfirmAdapter(this, mCartServicePriceItems);
        rvServices.setLayoutManager(new LinearLayoutManager(this));
        rvServices.setAdapter(mServiceCartComfirmAdapter);

        mPresenter.attachDataForInput(getBaseContext());
        mPresenter.loadDeliveryType();
        mPresenter.loadPaymentType();
    }

    private void setSupportToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Đặt Hàng");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                bacToCart();
        }
        return (super.onOptionsItemSelected(item));
    }

    private void bacToCart() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppConstants.FRAGMENT, AppConstants.CART_INDEX);
        startActivity(intent);
        finish();
    }

    @Override
    public void nextStep(int currentStep) {
        switch (currentStep) {
            case 0:
                nsvDeliveryInfo.setVisibility(View.VISIBLE);
                nsvPaymentType.setVisibility(View.GONE);
                nsvComfirm.setVisibility(View.GONE);
                break;
            case 1:

                nsvDeliveryInfo.setVisibility(View.GONE);
                nsvPaymentType.setVisibility(View.VISIBLE);
                nsvComfirm.setVisibility(View.GONE);
                break;
            case 2:
                mPresenter.attachData(txtName.getText().toString(),
                        txtPhone.getText().toString(),
                        mProvince,
                        mDistrict,
                        mWard,
                        txtAddress.getText().toString(),
                        mDeliveryType,
                        mPaymentType);
                mPresenter.loadCartItem();
                btnNext.setText(R.string.comfirm);
                nsvDeliveryInfo.setVisibility(View.GONE);
                nsvPaymentType.setVisibility(View.GONE);
                nsvComfirm.setVisibility(View.VISIBLE);
                break;
        }
        stepView.go(currentStep, true);
    }

    @Override
    public void openDialogProvince() {
        Intent intent = new Intent(this, ProvinceActivity.class);
        intent.putExtra(AppConstants.PROVINCE, mProvince);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_PROVINCE);
    }

    @Override
    public void openDialogDistrict(Province province) {
        Intent intent = new Intent(this, DistrictActivity.class);
        intent.putExtra(AppConstants.PROVINCE, mProvince);
        intent.putExtra(AppConstants.DISTRICT, mDistrict);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_DISTRICT);
    }

    @Override
    public void openDialogWard(District district) {
        Intent intent = new Intent(this, WardActivity.class);
        intent.putExtra(AppConstants.DISTRICT, mDistrict);
        intent.putExtra(AppConstants.WARD, mWard);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_WARD);
    }

    @Override
    public void updateRVOrderDeliveryType(List<OrderDeliveryType> deliveryTypes) {
        mOrderDeliveryTypes.clear();
        mOrderDeliveryTypes.addAll(deliveryTypes);
        mDeliveryTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateRVOrderPaymentType(List<OrderPaymentType> paymentTypes) {
        mOrderPaymentTypes.clear();
        mOrderPaymentTypes.addAll(paymentTypes);
        mPaymentTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showData(String name, String phone, String provinceName,
                         String districtName, String wardName, String address,
                         String orderDeliveryTypeName, String orderPaymentTypeName,
                         String orderPaymentTypeDescription) {

        txtFullName.setText(new StringBuilder(name + "\t" + phone));
        txtAddressComfirm.setText(address);
        txtWardComfirm.setText(wardName);
        txtDistrictComfirm.setText(districtName);
        txtProvinceComfirm.setText(provinceName);
        txtDeliveryType.setText(orderDeliveryTypeName);
        txtPaymentType.setText(orderPaymentTypeName);
        txtPaymentTypeDescription.setText(orderPaymentTypeDescription);

    }

    @Override
    public void updateRVProduct(List<CartProductItem> mItems) {
        mCartProductItems.clear();
        mCartProductItems.addAll(mItems);
        mProductCartComfirmAdapter.notifyDataSetChanged();
        if (mItems.size() == 0) {
            rvProducts.setVisibility(View.GONE);
            titleProduct.setVisibility(View.GONE);
        } else {
            rvProducts.setVisibility(View.VISIBLE);
            titleProduct.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateRVService(List<CartServicePriceItem> mItems) {
        mCartServicePriceItems.clear();
        mCartServicePriceItems.addAll(mItems);
        mServiceCartComfirmAdapter.notifyDataSetChanged();
        if (mItems.size() == 0) {
            rvServices.setVisibility(View.GONE);
            titleServices.setVisibility(View.GONE);
        } else {
            rvServices.setVisibility(View.VISIBLE);
            titleServices.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDataForInput(Customer customer) {
        txtName.setText(customer.getFullname());
        txtPhone.setText(customer.getPhone());
        mProvince = customer.getProvince();
        mDistrict = customer.getDistrict();
        mWard = customer.getWard();

        if (mProvince != null){
            txtProvince.setText(mProvince.getName());
            txtProvince.setClickable(true);
        }
        if (mDistrict != null){
            txtDistrict.setText(mDistrict.getName());
            txtDistrict.setClickable(true);
        }
        if (mWard != null){
            txtWard.setText(mWard.getName());
            txtWard.setClickable(true);
        }

        if(customer.getAddress() != null){
            txtAddress.setText(customer.getAddress());

        }
    }

    @Override
    public void doneStep() {
        stepView.done(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_PROVINCE:
                    Province province = (Province) data.getSerializableExtra(AppConstants.PROVINCE);
                    mProvince = province;
                    txtProvince.setText(province.getName());
                    txtDistrict.setText("");
                    txtWard.setText("");
                    mDistrict = null;
                    mWard = null;
                    txtDistrict.setClickable(true);
                    txtWard.setClickable(false);
                    break;
                case AppConstants.REQUEST_CODE_DISTRICT:
                    District district = (District) data.getSerializableExtra(AppConstants.DISTRICT);
                    mDistrict = district;
                    txtDistrict.setText(district.getName());
                    txtWard.setText("");
                    mWard = null;
                    txtWard.setClickable(true);
                    break;
                case AppConstants.REQUEST_CODE_WARD:
                    Ward ward = (Ward) data.getSerializableExtra(AppConstants.WARD);
                    txtWard.setText(ward.getName());
                    mWard = ward;
                    break;
            }
        }
    }
}
