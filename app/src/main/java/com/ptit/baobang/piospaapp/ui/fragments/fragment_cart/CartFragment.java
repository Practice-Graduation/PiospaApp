package com.ptit.baobang.piospaapp.ui.fragments.fragment_cart;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.ui.adapter.CartPagerApdater;
import com.ptit.baobang.piospaapp.ui.base.BaseFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product.CartProductFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_service.CartServiceFragment;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartFragment extends BaseFragment implements ICartView{

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.txtQuanlity)
    TextView txtQuanlity;
    @BindView(R.id.txtTotal)
    TextView txtTotal;

    private List<Fragment> mFragments;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        addControls(view);
        return view;
    }

    private void addControls(View view) {
        updateUI();
        mFragments = new ArrayList<>();
        CartProductFragment cartProductFragment = CartProductFragment.newInstance();
        cartProductFragment.setCartFragment(this);
        CartServiceFragment cartServiceFragment = CartServiceFragment.newInstance();
        cartServiceFragment.setCartFragment(this);
        mFragments.add(cartProductFragment);
        mFragments.add(cartServiceFragment);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CartPagerApdater adapter = new CartPagerApdater(fragmentManager, getContext(), mFragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setTabsFromPagerAdapter(adapter);
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
        txtQuanlity.setText(cart.getTotalQuantity() + "");
        txtTotal.setText(CommonUtils.formatToCurrency(cart.getTotalPrice()));
    }
}
