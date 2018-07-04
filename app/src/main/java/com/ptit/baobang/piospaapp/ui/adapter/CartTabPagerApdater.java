package com.ptit.baobang.piospaapp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_product.CartProductFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart_service.CartServiceFragment;

public class CartTabPagerApdater extends FragmentPagerAdapter {
    public CartTabPagerApdater(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CartProductFragment.newInstance();
            case 1:
                return CartServiceFragment.newInstance();
            default:
                return CartProductFragment.newInstance();
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
                return "Sản phẩm";
            case 1:
                return "Dịch vụ";
            default:
                return "Sản phẩm";
        }
    }
}
