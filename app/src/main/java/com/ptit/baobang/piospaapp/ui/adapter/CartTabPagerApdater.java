package com.ptit.baobang.piospaapp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart.CartFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product.CartProductFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_service.CartServiceFragment;

public class CartTabPagerApdater extends FragmentStatePagerAdapter {
    private CartFragment mCartFragment;
    public CartTabPagerApdater(FragmentManager fm, CartFragment cartFragment) {
        super(fm);
        this.mCartFragment = cartFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CartProductFragment fragment = CartProductFragment.newInstance();
                fragment.setCartFragment(mCartFragment);
                return fragment;
            case 1:
                CartServiceFragment serviceFragment = CartServiceFragment.newInstance();
                serviceFragment.setCartFragment(mCartFragment);
                return serviceFragment;
            default:
                CartProductFragment fragment2 = CartProductFragment.newInstance();
                fragment2.setCartFragment(mCartFragment);
                return fragment2;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mCartFragment.getContext().getString(R.string.product);
            case 1:
                return mCartFragment.getContext().getString(R.string.service);
            default:
                return mCartFragment.getContext().getString(R.string.product);
        }
    }
}
