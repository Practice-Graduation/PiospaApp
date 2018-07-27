package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.ui.activities.payment.PaymentActivity;
import com.ptit.baobang.piospaapp.ui.adapter.CartTabPagerApdater;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CartFragment extends BaseFragment<CartPresenter> implements ICartView {

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.txtQuanlity)
    TextView txtQuanlity;
    @BindView(R.id.txtTotal)
    TextView txtTotal;

    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.content)
    LinearLayout layoutContent;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new CartPresenter(getContext(),this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.txtPayment)
    void onClick(View view) {
        mPresenter.clickPayment();
    }

    private void addControls() {
        updateUI();


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CartTabPagerApdater adapter = new CartTabPagerApdater(fragmentManager, this);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);

    }


    public TextView getTxtAmount() {
        return txtQuanlity;
    }

    public void setTxtAmount(TextView txtAmount) {
        this.txtQuanlity = txtAmount;
    }

    public TextView getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(TextView txtTotal) {
        this.txtTotal = txtTotal;
    }

    @Override
    public void updateUI() {
        Cart cart = CartHelper.getCart();
        txtQuanlity.setText(new StringBuilder(cart.getTotalQuantity() + ""));
        txtTotal.setText(CommonUtils.formatToCurrency(cart.getTotalPrice()));
        showEmptyCart();
    }

    @Override
    public void openPaymentActivity() {
        Intent intent = new Intent(getContext(), PaymentActivity.class);
        startActivity(intent);
    }

    @Override
    public void showEmptyCart() {
        Cart cart = CartHelper.getCart();
        if (cart.getTotalQuantity() == 0) {
            layoutEmpty.setVisibility(View.VISIBLE);
            layoutContent.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            layoutContent.setVisibility(View.VISIBLE);
        }
    }

}
