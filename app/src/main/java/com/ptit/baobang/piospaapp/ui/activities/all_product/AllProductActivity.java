package com.ptit.baobang.piospaapp.ui.activities.all_product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.activities.product_detail.ProductDetailActivity;
import com.ptit.baobang.piospaapp.ui.adapter.ProductAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AllProductActivity extends BaseActivity<AllProductPresenter> implements IAllProductView{

    private static final String TAG = "AllProductActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    ProductAdapter mProductAdapter;
    List<Product> mProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        addControls();
        addEvents();
    }

    private void addEvents() {
        mProductAdapter.setOnClickListener(position -> {
            mPresenter.onSelectedProduct(mProducts.get(position).getProductId());
        });
    }

    private void addControls() {

        mPresenter = new AllProductPresenter(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Sản phẩm");

        mProducts = new ArrayList<>();
        mProductAdapter = new ProductAdapter(this, mProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(this, AppConstants.SPAN_COUNT));
        rvProducts.setAdapter(mProductAdapter);

        mPresenter.loadData(getGroupIdFromBundle());
    }

    @Override
    public void onUpdateRecyleView(List<Product> products) {
        mProducts.clear();
        mProducts.addAll(products);
        mProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void openProductDetailActivity(int productId) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PRODUCT_ID, productId);
        intent.putExtra(AppConstants.PRODUCT_BUNDLE, bundle);
        startActivity(intent);
    }

    public int getGroupIdFromBundle() {

        Bundle bundle = getIntent().getBundleExtra(AppConstants.PRODUCT_GROUP_BUNDLE);

        return bundle.getInt(AppConstants.PRODUCT_GROUP_ID);
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
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
