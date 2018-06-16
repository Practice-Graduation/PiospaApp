package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailActivity extends BaseActivity<ProductDetailPresenter> implements IProductDetailView{

    private Product product;

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.img)
    ImageView img;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        addControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startShirrmentAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopShirrmentAnimation();
    }

    private void addControls() {
        mPresenter = new ProductDetailPresenter(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Sản Phẩm");
        product = mPresenter.getProductFromBundle(getIntent());
        mPresenter.loadData(product);

    }

    @OnClick({R.id.btnAddCart, R.id.fbGoToCart})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btnAddCart:
                mPresenter.onClickAddCart(product);
                break;
            case R.id.fbGoToCart:
                mPresenter.onClickGoToCart();
                break;
        }
    }

    @Override
    public void showProductDetail(Product product) {
        txtName.setText(product.getProductName());
        txtPrice.setText(new StringBuilder(product.getPrice() + ""));
        txtInfo.setText(product.getDescription());
        RequestOptions options = new RequestOptions().placeholder(R.drawable.paceholder).error(R.drawable.error);
        Glide.with(this).load(product.getImage()).apply(options).into(img);
    }

    @Override
    public void openFramentCart() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppConstants.FRAGMENT, AppConstants.CART_INDEX);
        startActivity(intent);
    }

    @Override
    public void startShirrmentAnimation() {
        if(shimmerFrameLayout != null)
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void stopShirrmentAnimation() {
        if(shimmerFrameLayout != null){
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
