package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailActivity extends BaseActivity<ProductDetailPresenter> implements IProductDetailView {

    private Product product;

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.imgSmallImage)
    ImageView imgSmallImage;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtPrice)
    TextView txtPrice;
    @BindView(R.id.txtInfo)
    TextView txtInfo;
    @BindView(R.id.btnAddCart)
    Button btnAddCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.clToolbar)
    CollapsingToolbarLayout clToolbar;
    @BindView(R.id.txtAmount)
    TextView txtAmount;

    @BindView(R.id.txtProductLable)
    TextView txtProductLable;
    @BindView(R.id.txtProductOrigin)
    TextView txtProductOrigin;
    @BindView(R.id.txtProductQuanlity)
    TextView txtProductQuanlity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        addControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void addControls() {
        mPresenter = new ProductDetailPresenter(this, this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        product = mPresenter.getProductFromBundle(getIntent());
        mPresenter.loadData(product);

    }

    @OnClick({R.id.btnAddCart, R.id.fbGoToCart, R.id.btnAdd, R.id.btnRemove})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddCart:
                mPresenter.onClickAddCart(product, txtAmount.getText().toString());
                break;
            case R.id.fbGoToCart:
                mPresenter.onClickGoToCart();
                break;
            case R.id.btnAdd:
                mPresenter.onClickAddButton(txtAmount.getText().toString());
                break;
            case R.id.btnRemove:
                mPresenter.onClickRemoveButton(txtAmount.getText().toString());
                break;
        }
    }

    @Override
    public void showProductDetail(Product product) {
        txtName.setText(product.getProductName());
        txtPrice.setText(new StringBuilder(getString(R.string.price) + ": " + CommonUtils.formatToCurrency(product.getPrice())));
        txtInfo.setText(Html.fromHtml(product.getDescription()));
        txtAmount.setText("1");
        txtProductLable.setText("Thương hiệu: " + product.getProductLabel().getProductLabelName());
        txtProductOrigin.setText("Nguồn gốc: " +  product.getProductOrigin().getProductOriginName());
        txtProductQuanlity.setText("Trọng lượng: " + product.getQuantity() + product.getQuantityValue());
        RequestOptions options = new RequestOptions().placeholder(R.drawable.paceholder).error(R.drawable.error);
        Glide.with(this).load(product.getImage()).apply(options).into(img);
        Glide.with(this).load(product.getImage()).apply(options).into(imgSmallImage);
    }

    @Override
    public void openFramentCart() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppConstants.FRAGMENT, AppConstants.CART_INDEX);
        startActivity(intent);
    }

    @Override
    public void setAmount(String s) {
        txtAmount.setText(s);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(item));
    }
}
