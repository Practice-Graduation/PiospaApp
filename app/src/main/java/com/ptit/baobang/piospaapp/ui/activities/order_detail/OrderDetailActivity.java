package com.ptit.baobang.piospaapp.ui.activities.order_detail;

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
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.Tax;
import com.ptit.baobang.piospaapp.ui.adapter.ProductCartComfirmAdapter;
import com.ptit.baobang.piospaapp.ui.adapter.ServiceCartComfirmAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

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

    TextView lbTax;
    @BindView(R.id.txtTax)
    TextView txtTax;
    private Order order;


    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    List<CartProductItem> mProduct;
    ProductCartComfirmAdapter mProductAdapter;

    @BindView(R.id.rvServices)
    RecyclerView rvServices;
    List<CartServicePriceItem> mServices;
    ServiceCartComfirmAdapter mServiceAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        init();
    }

    private void init() {
        mPresenter = new OrderDetailPresenter(this);
        setToolbar();
        order = mPresenter.getDate(getIntent());
        mPresenter.loadData(order);

        mProduct = new ArrayList<>();
        mProductAdapter = new ProductCartComfirmAdapter(this, mProduct);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(mProductAdapter);

        mServices = new ArrayList<>();
        mServiceAdapter = new ServiceCartComfirmAdapter(this, mServices);
        rvServices.setLayoutManager(new LinearLayoutManager(this));
        rvServices.setAdapter(mServiceAdapter);
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
        toolbar.setTitle("Chi tiết đơn hàng     ");
        centerToolbarTitle(toolbar, 0);
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
    public void setView(String code, String createdAt, String orderStatusName, String fullName, String address, String ward, String district,
                        String province, String phone, List<CartProductItem> productItems, List<CartServicePriceItem> priceItems, String orderDeliveryTypeName,
                        String orderPaymentTypeName, String orderPaymentTypeDescription,
                        String total, String ship, String subtotal) {

        switch (orderStatusName) {
            case "Chưa thanh toán":
                cvOrderStatus.setBackgroundResource(R.color.light_blue);
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case "Thanh toán":
                cvOrderStatus.setBackgroundResource(R.color.light_green);
                btnCancel.setVisibility(View.GONE);
                break;
            case "Hủy":
                cvOrderStatus.setBackgroundResource(R.color.light_red);
                btnCancel.setVisibility(View.GONE);
                break;
        }

        txtOrderId.setText("Hóa đơn số: " + code);
        txtCreatedAt.setText("Ngày tạo: " + createdAt);
        txtStatus.setText("Trạng thái: " + orderStatusName);
        txtFullName.setText(new StringBuilder(fullName + "\t" + phone));
        txtAddressComfirm.setText(address);
        txtWardComfirm.setText(ward);
        txtDistrictComfirm.setText(district);
        txtProvinceComfirm.setText(province);
        txtDeliveryType.setText(orderDeliveryTypeName);
        txtPaymentType.setText(orderPaymentTypeName);
        txtPaymentTypeDescription.setText(orderPaymentTypeDescription);
        txtTotal.setText(total);
        txtShip.setText(ship);
        txtPayment.setText(subtotal);
        updateRecycleProducts(productItems);
        updateRecycleServices(priceItems);
        if (mProduct.size() == 0) {
            layoutProduct.setVisibility(View.GONE);
        } else {
            layoutProduct.setVisibility(View.VISIBLE);
        }
        if (mServices.size() == 0) {
            layoutService.setVisibility(View.GONE);
        } else {
            layoutService.setVisibility(View.VISIBLE);
        }

        expandableLayout.expand(true);

    }



    @Override
    public void updateRecycleProducts(List<CartProductItem> productItems) {
        mProduct.clear();
        mProduct.addAll(productItems);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateRecycleServices(List<CartServicePriceItem> priceItems) {
        mServices.clear();
        mServices.addAll(priceItems);
        mServiceAdapter.notifyDataSetChanged();
    }

    @Override
    public void setView(String orderStatusName) {
        switch (orderStatusName) {
            case "Chưa thanh toán":
                cvOrderStatus.setBackgroundResource(R.color.light_blue);
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case "Thanh toán":
                cvOrderStatus.setBackgroundResource(R.color.light_green);
                btnCancel.setVisibility(View.GONE);
                break;
            case "Hủy":
                cvOrderStatus.setBackgroundResource(R.color.light_red);
                btnCancel.setVisibility(View.GONE);
                break;
        }
        txtStatus.setText("Trạng thái: " + orderStatusName);
    }

    @Override
    public void setTax(Tax tax) {
        lbTax.setText(tax.getName());
        if(tax.getType().equals("percent")){
            txtTax.setText(new StringBuilder(tax.getValue() + "%"));
        }else if(tax.getType().equals("money")){
            txtTax.setText(CommonUtils.formatToCurrency(tax.getValue()));
        }
    }

}
