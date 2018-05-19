package com.ptit.baobang.piospaapp.ui.fragments.fragment_product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.ui.activities.all_product.AllProductActivity;
import com.ptit.baobang.piospaapp.ui.activities.product_detail.ProductDetailActivity;
import com.ptit.baobang.piospaapp.ui.adapter.ProductGroupAdapter;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductFragment extends BaseFragment implements IProductFragmentView{

    private static final String TAG = "ProductFragment";
    private ProductFragmentPresenter mPresenter;

    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerLayout;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.rvProductGroups)
    RecyclerView rvProductGroups;
    private List<ProductGroup> mProductGroups;
    private ProductGroupAdapter mAdapter;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        mAdapter.setOnItemClickListerner(position -> mPresenter.onClickMore(mProductGroups.get(position)));
    }

    private void addControls(View view) {

        mPresenter = new ProductFragmentPresenter(this);

        mUnBinder = ButterKnife.bind(this, view);
//        toolbar.setTitle(R.string.title_product);

        mProductGroups =  new ArrayList<>();
        mAdapter = new ProductGroupAdapter(getContext(), mProductGroups, mPresenter.getmApiService());
        mAdapter.setmIProductFragmentView(this);
        rvProductGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProductGroups.setAdapter(mAdapter);
        mPresenter.loadData();
    }

    @Override
    public void openAllProductActivity(int productGroupId) {
        Intent intent = new Intent(getContext(), AllProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PRODUCT_GROUP_ID, productGroupId);
        intent.putExtra(AppConstants.PRODUCT_GROUP_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void openProductDetailActivity(int productId) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PRODUCT_ID, productId);
        intent.putExtra(AppConstants.PRODUCT_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void onUpdateRecycleView(List<ProductGroup> productGroups) {
        mProductGroups.clear();
        mProductGroups.addAll(productGroups);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startShimmerAnimation() {
        shimmerLayout.startShimmerAnimation();
    }

    @Override
    public void stopShimmerAnimation() {
        shimmerLayout.stopShimmerAnimation();
    }

}
