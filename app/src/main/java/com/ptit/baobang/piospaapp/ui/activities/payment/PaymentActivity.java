package com.ptit.baobang.piospaapp.ui.activities.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.dto.CustomerInfoDTO;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryType;
import com.ptit.baobang.piospaapp.data.model.OrderPaymentType;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Tax;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.activities.order.OrderActivity;
import com.ptit.baobang.piospaapp.ui.adapter.DeliveryTypeAdapter;
import com.ptit.baobang.piospaapp.ui.adapter.PaymentTypeAdapter;
import com.ptit.baobang.piospaapp.ui.adapter.ProductCartConfirmAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.district.DistrictActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.province.ProvinceActivity;
import com.ptit.baobang.piospaapp.ui.dialogs.ward.WardActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DefaultValue;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;
import com.ptit.baobang.piospaapp.utils.RequestCodeConstant;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity<PaymentPresenter> implements IPaymentView {

    public static final int STEP_1_PAYMENT_TYPE = 0;
    public static final int STEP_2_ADDRESS = 1;
    public static final int STEP_3_DELIVERY_TYPE = 2;
    public static final int STEP_4_CONFIRM = 3;

    @BindView(R.id.root)
    LinearLayout root;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.step_view)
    StepView stepView;

    @BindView(R.id.delivery_info)
    NestedScrollView nsvDeliveryInfo;

    @BindView(R.id.payment_type)
    NestedScrollView nsvPaymentType;

    @BindView(R.id.delivery_type)
    NestedScrollView nsvDeliveryType;

    @BindView(R.id.confirm)
    NestedScrollView nsvConfirm;


    @BindView(R.id.txt_name)
    EditText txtName;

    @BindView(R.id.txt_phone)
    EditText txtPhone;

    @BindView(R.id.txt_address)
    EditText txtAddress;

    @BindView(R.id.txt_province)
    TextView txtProvince;

    @BindView(R.id.txt_district)
    TextView txtDistrict;

    @BindView(R.id.txt_ward)
    TextView txtWard;

    @BindView(R.id.rv_payment_type)
    RecyclerView rvPaymentType;

    @BindView(R.id.rv_delivery_type)
    RecyclerView rvDeliveryType;

    @BindView(R.id.btn_next)
    Button btnNext;

    // Confirm
    @BindView(R.id.title_product)
    TextView titleProduct;

    @BindView(R.id.title_services)
    TextView titleServices;

    @BindView(R.id.txt_full_name)
    TextView txtFullName;

    @BindView(R.id.txt_address_confirm)
    TextView txtAddressConfirm;

    @BindView(R.id.txt_ward_confirm)
    TextView txtWardConfirm;

    @BindView(R.id.txt_district_confirm)
    TextView txtDistrictConfirm;

    @BindView(R.id.txt_province_confirm)
    TextView txtProvinceConfirm;

    @BindView(R.id.txt_delivery_type)
    TextView txtDeliveryType;

    @BindView(R.id.txt_payment_type)
    TextView txtPaymentType;

    @BindView(R.id.txt_payment_type_description)
    TextView txtPaymentTypeDescription;

    @BindView(R.id.txt_total)
    TextView txtTotal;

    @BindView(R.id.txt_ship)
    TextView txtShip;

    @BindView(R.id.txt_payment)
    TextView txtPayment;

    @BindView(R.id.lb_tax)
    TextView lbTax;

    @BindView(R.id.txt_tax)
    TextView txtTax;

    @BindView(R.id.rv_products)
    RecyclerView rvProducts;

    @BindView(R.id.rv_services)
    RecyclerView rvServices;

    @BindView(R.id.cv_payment_type_confirm)
    CardView cvPaymentTypeConfirm;

    @BindView(R.id.cv_delivery_type_confirm)
    CardView cvDeliverTypeConfirm;

    @BindView(R.id.lb_ship)
    TextView lbShip;


    List<CartProductItem> mCartProductItems;
    ProductCartConfirmAdapter mProductCartConfirmAdapter;

    List<OrderPaymentType> mOrderPaymentTypes;
    PaymentTypeAdapter mPaymentTypeAdapter;

    List<OrderDeliveryType> mOrderDeliveryTypes;
    DeliveryTypeAdapter mDeliveryTypeAdapter;

    private CustomerInfoDTO mCustomerInfoDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_delivery_info);
        hideKeyboardOutside(root);
        addControls();
        addEvents();
    }

    private void addEvents() {
        mPaymentTypeAdapter.setListener(position -> {
            mCustomerInfoDTO.setPaymentType(mOrderPaymentTypes.get(position));
        });

        mDeliveryTypeAdapter.setmListener(position -> {
            mCustomerInfoDTO.setDeliveryType(mOrderDeliveryTypes.get(position));
        });
    }

    @OnClick({R.id.btn_next, R.id.txt_province,
            R.id.txt_district, R.id.txt_ward})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:

                mCustomerInfoDTO.setName(txtName.getText().toString());
                mCustomerInfoDTO.setPhone(txtPhone.getText().toString());
                mCustomerInfoDTO.setAddress(txtAddress.getText().toString());

                mPresenter.clickButtonNext(stepView, mCustomerInfoDTO);
                break;
            case R.id.txt_province:
                mPresenter.clickTextViewProvince();
                break;
            case R.id.txt_district:
                mPresenter.clickTextViewDistrict(mCustomerInfoDTO.getProvince());
                break;
            case R.id.txt_ward:
                mPresenter.clickTextViewWard(mCustomerInfoDTO.getDistrict());
                break;
        }
    }

    private void addControls() {

        setSupportToolbar();

        mPresenter = new PaymentPresenter(this, this);

        nsvPaymentType.setVisibility(View.VISIBLE);
        nsvDeliveryInfo.setVisibility(View.GONE);
        nsvDeliveryType.setVisibility(View.GONE);
        nsvConfirm.setVisibility(View.GONE);

        stepView.getState()
                .steps(Arrays.asList(getResources().getStringArray(R.array.arr_step)))
                .stepsNumber(getResources().getStringArray(R.array.arr_step).length)
                .commit();

        txtDistrict.setClickable(false);
        txtWard.setClickable(false);

        addRecycleViewDeliveryType();

        addRecycleViewPaymentType();

        addRecycleViewProduct();

        mPresenter.attachDataForInput(getBaseContext());
        mPresenter.loadDeliveryType();
        mPresenter.loadPaymentType();
        mPresenter.loadTax();
    }

    private void addRecycleViewProduct() {
        mCartProductItems = new ArrayList<>();
        mProductCartConfirmAdapter = new ProductCartConfirmAdapter(this, mCartProductItems);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(mProductCartConfirmAdapter);
    }

    private void addRecycleViewPaymentType() {
        mOrderPaymentTypes = new ArrayList<>();
        mPaymentTypeAdapter = new PaymentTypeAdapter(this, mOrderPaymentTypes);
        rvPaymentType.setLayoutManager(new LinearLayoutManager(this));
        rvPaymentType.setAdapter(mPaymentTypeAdapter);
    }

    private void addRecycleViewDeliveryType() {
        mOrderDeliveryTypes = new ArrayList<>();
        mDeliveryTypeAdapter = new DeliveryTypeAdapter(this, mOrderDeliveryTypes);
        rvDeliveryType.setLayoutManager(new LinearLayoutManager(this));
        rvDeliveryType.setAdapter(mDeliveryTypeAdapter);
    }

    private void setSupportToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getString(R.string.check_out));
        centerToolbarTitle(toolbar, DefaultValue.INT);
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

        if (stepView.getCurrentStep() > STEP_1_PAYMENT_TYPE) {
            if (mCustomerInfoDTO.getPaymentType().getOrderPaymentTypeId() == AppConstants.PAYMENT_TYPE_GET) {
                int newStep = stepView.getCurrentStep() - STEP_4_CONFIRM;
                nextStep(newStep);
            } else {
                int newStep = stepView.getCurrentStep();
                newStep--;
                nextStep(newStep);
            }
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(KeyBundleConstant.FRAGMENT, AppConstants.CART_INDEX);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        bacToCart();
    }

    @Override
    public void nextStep(int currentStep) {
        switch (currentStep) {
            case STEP_1_PAYMENT_TYPE:
                nsvPaymentType.setVisibility(View.VISIBLE);
                nsvDeliveryInfo.setVisibility(View.GONE);
                nsvDeliveryType.setVisibility(View.GONE);
                nsvConfirm.setVisibility(View.GONE);
                break;
            case STEP_2_ADDRESS:
                nsvPaymentType.setVisibility(View.GONE);
                nsvDeliveryInfo.setVisibility(View.VISIBLE);
                nsvDeliveryType.setVisibility(View.GONE);
                nsvConfirm.setVisibility(View.GONE);
                break;
            case STEP_3_DELIVERY_TYPE:
                nsvPaymentType.setVisibility(View.GONE);
                nsvDeliveryInfo.setVisibility(View.GONE);
                nsvDeliveryType.setVisibility(View.VISIBLE);
                nsvConfirm.setVisibility(View.GONE);
                break;
            case STEP_4_CONFIRM:
                showData(mCustomerInfoDTO);
                if (mCustomerInfoDTO.getDeliveryType() == null) {
                    cvDeliverTypeConfirm.setVisibility(View.GONE);
                    lbShip.setVisibility(View.GONE);
                    txtShip.setVisibility(View.GONE);
                }
                if (mCustomerInfoDTO.getPaymentType() == null) {
                    cvPaymentTypeConfirm.setVisibility(View.GONE);
                }
                mPresenter.loadCartItem();
                btnNext.setText(R.string.comfirm);
                nsvPaymentType.setVisibility(View.GONE);
                nsvDeliveryInfo.setVisibility(View.GONE);
                nsvDeliveryType.setVisibility(View.GONE);
                nsvConfirm.setVisibility(View.VISIBLE);
                break;
        }
        stepView.go(currentStep, true);
    }

    @Override
    public void openDialogProvince() {
        Intent intent = new Intent(this, ProvinceActivity.class);
        intent.putExtra(KeyBundleConstant.PROVINCE, mCustomerInfoDTO.getProvince());
        startActivityForResult(intent, RequestCodeConstant.REQUEST_CODE_PROVINCE);
    }

    @Override
    public void openDialogDistrict(Province province) {
        Intent intent = new Intent(this, DistrictActivity.class);
        intent.putExtra(KeyBundleConstant.PROVINCE, mCustomerInfoDTO.getProvince());
        intent.putExtra(KeyBundleConstant.DISTRICT, mCustomerInfoDTO.getDistrict());
        startActivityForResult(intent, RequestCodeConstant.REQUEST_CODE_DISTRICT);
    }

    @Override
    public void openDialogWard(District district) {
        Intent intent = new Intent(this, WardActivity.class);
        intent.putExtra(KeyBundleConstant.DISTRICT, mCustomerInfoDTO.getDistrict());
        intent.putExtra(KeyBundleConstant.WARD, mCustomerInfoDTO.getWard());
        startActivityForResult(intent, RequestCodeConstant.REQUEST_CODE_WARD);
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
    public void showData(CustomerInfoDTO customerInfoDTO) {
        String nameAndPhone = customerInfoDTO.getName() + AppConstants.TAB_SYMBOL + customerInfoDTO.getPhone();
        txtFullName.setText(nameAndPhone);
        txtAddressConfirm.setText(customerInfoDTO.getAddress());
        setWardConfirm(customerInfoDTO.getWard());
        setDistrictConfirm(customerInfoDTO.getDistrict());
        setProvinceConfirm(customerInfoDTO.getProvince());
        setDeliveryType(customerInfoDTO.getDeliveryType());
        setPaymentType(customerInfoDTO.getPaymentType());
    }

    private void setPaymentType(OrderPaymentType paymentType) {
        String paymentName = paymentType == null ? DefaultValue.STRING : paymentType.getOrderPaymentTypeName();
        txtPaymentType.setText(paymentName);
    }

    private void setDeliveryType(OrderDeliveryType deliveryType) {
        String deliveryTypeName = deliveryType == null ? DefaultValue.STRING : deliveryType.getOrderDeliveryTypeName();
        txtDeliveryType.setText(deliveryTypeName);
    }

    private void setProvinceConfirm(Province province) {
        String provinceName = province == null ? DefaultValue.STRING : province.toString();
        txtProvinceConfirm.setText(provinceName);
    }

    private void setDistrictConfirm(District district) {
        String districtName = district == null ? DefaultValue.STRING : district.toString();
        txtDistrictConfirm.setText(districtName);
    }

    private void setWardConfirm(Ward ward) {
        String wardName = ward == null ? DefaultValue.STRING : ward.toString();
        txtWardConfirm.setText(wardName);
    }

    @Override
    public void updateRVProduct(List<CartProductItem> mItems) {

        mCartProductItems.clear();
        mCartProductItems.addAll(mItems);
        mProductCartConfirmAdapter.notifyDataSetChanged();

        if (mItems.size() == AppConstants.LIST_EMPTY_SIZE) {
            rvProducts.setVisibility(View.GONE);
            titleProduct.setVisibility(View.GONE);
        } else {
            rvProducts.setVisibility(View.VISIBLE);
            titleProduct.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDataForInput(Customer customer) {

        setDataToDTO(customer);

        setTextToView();

        enableClickView();
    }

    private void enableClickView() {
        if (mCustomerInfoDTO.getProvince() == null) {
            txtProvince.setClickable(true);
            txtDistrict.setClickable(false);
            txtWard.setClickable(false);
        } else {
            txtProvince.setClickable(true);
            txtDistrict.setClickable(true);
            if (mCustomerInfoDTO.getDistrict() == null) {
                txtWard.setClickable(false);
            } else {
                txtWard.setClickable(true);
            }
        }
    }

    private void setTextToView() {
        txtName.setText(mCustomerInfoDTO.getName());

        txtPhone.setText(mCustomerInfoDTO.getPhone());

        if (mCustomerInfoDTO.getProvince() != null) {
            txtProvince.setText(mCustomerInfoDTO.getProvince().getName());
        }
        if (mCustomerInfoDTO.getDistrict() != null) {
            txtDistrict.setText(mCustomerInfoDTO.getDistrict().getName());
        }
        if (mCustomerInfoDTO.getWard() != null) {
            txtWard.setText(mCustomerInfoDTO.getWard().getName());
        }
        if (mCustomerInfoDTO.getAddress() != null) {
            txtAddress.setText(mCustomerInfoDTO.getAddress());
        }
    }

    private void setDataToDTO(Customer customer) {
        mCustomerInfoDTO = new CustomerInfoDTO();
        mCustomerInfoDTO.setName(customer.getFullName());
        mCustomerInfoDTO.setPhone(customer.getPhone());
        Ward ward = customer.getWard();
        District district = ward.getDistrict();
        Province province = district.getProvince();
        mCustomerInfoDTO.setProvince(province);
        mCustomerInfoDTO.setDistrict(district);
        mCustomerInfoDTO.setWard(ward);
        mCustomerInfoDTO.setAddress(customer.getAddress());
    }

    @Override
    public void doneStep() {
        stepView.done(true);
    }

    @Override
    public void openOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(KeyBundleConstant.ORDER_FRAGMENT_INDEX, AppConstants.ORDER_STATUS_NOT_PAYMENT_INDEX);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateUIPaymentInfo(String totalPrice, String ship, String payment) {
        txtTotal.setText(totalPrice);
        txtShip.setText(ship);
        txtPayment.setText(payment);
    }

    @Override
    public void setTax(Tax data) {
        mCustomerInfoDTO.setTax(data);
        lbTax.setText(data.getName());
        if (data.getType().equals(AppConstants.PECENT)) {
            txtTax.setText(new StringBuilder(data.getValue() + AppConstants.PERCENT_SYMBOL));
        } else if (data.getType().equals(AppConstants.MONEY)) {
            txtTax.setText(CommonUtils.formatToCurrency(data.getValue()));
        }
        mPresenter.computeTaxAndShip(mCustomerInfoDTO.getDeliveryType(), data);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodeConstant.REQUEST_CODE_PROVINCE:
                    Province province = (Province) data.getSerializableExtra(KeyBundleConstant.PROVINCE);
                    mCustomerInfoDTO.setProvince(province);
                    txtProvince.setText(province.getName());
                    txtDistrict.setText(DefaultValue.STRING);
                    txtWard.setText(DefaultValue.STRING);
                    mCustomerInfoDTO.setDistrict(null);
                    mCustomerInfoDTO.setWard(null);
                    txtDistrict.setClickable(true);
                    txtWard.setClickable(false);
                    break;
                case RequestCodeConstant.REQUEST_CODE_DISTRICT:
                    District district = (District) data.getSerializableExtra(KeyBundleConstant.DISTRICT);
                    mCustomerInfoDTO.setDistrict(district);
                    txtDistrict.setText(district.getName());
                    txtWard.setText(DefaultValue.STRING);
                    mCustomerInfoDTO.setWard(null);
                    txtWard.setClickable(true);
                    break;
                case RequestCodeConstant.REQUEST_CODE_WARD:
                    Ward ward = (Ward) data.getSerializableExtra(KeyBundleConstant.WARD);
                    txtWard.setText(ward.getName());
                    mCustomerInfoDTO.setWard(ward);
                    break;
            }
        }
    }
}
