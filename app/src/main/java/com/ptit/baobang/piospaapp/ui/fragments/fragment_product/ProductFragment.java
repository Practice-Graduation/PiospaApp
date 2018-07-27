package com.ptit.baobang.piospaapp.ui.fragments.fragment_product;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
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


public class ProductFragment extends BaseFragment<ProductFragmentPresenter> implements IProductFragmentView {

    private static final String TAG = "ProductFragment";

    @BindView(R.id.rvProductGroups)
    RecyclerView rvProductGroups;
    private List<ProductGroup> mProductGroups;
    private ProductGroupAdapter mAdapter;
    private SearchView searchView;
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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager)
                getBaseContext().getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(false);

        mPresenter.filter(searchView);
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
        mAdapter.setOnSelectMore(position -> mPresenter.onClickMore(mProductGroups.get(position)));

        mAdapter.setItemSelected(product -> {
            mPresenter.clickItem((Product) product);
        });
    }

    private void addControls(View view) {

        mPresenter = new ProductFragmentPresenter(getBaseContext(), this);

        mUnBinder = ButterKnife.bind(this, view);
//        toolbar.setTitle(R.string.title_product);

        mProductGroups = new ArrayList<>();
        mAdapter = new ProductGroupAdapter(getContext(), mProductGroups,
               this, mPresenter.getmApiService());
        rvProductGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProductGroups.setAdapter(mAdapter);
        mPresenter.loadData();
    }

    @Override
    public void openAllProductActivity(int productGroupId, String productGroupName) {
        Intent intent = new Intent(getContext(), AllProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PRODUCT_GROUP_ID, productGroupId);
        bundle.putString(AppConstants.TOOL_BAR_TITLE, productGroupName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void openProductDetailActivity(Product product) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.PRODUCT_ID, product);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onUpdateRecycleView(List<ProductGroup> productGroups) {
//        mProductGroups.clear();
//        mProductGroups.addAll(productGroups);
//        mAdapter.notifyDataSetChanged();

        mProductGroups = new ArrayList<>(productGroups);
        mAdapter = new ProductGroupAdapter(getContext(), mProductGroups,
                this, mPresenter.getmApiService());
        rvProductGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProductGroups.setAdapter(mAdapter);
        addEvents();
    }

    @Override
    public ProductGroupAdapter getProductGroupAdapter() {
        return mAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
