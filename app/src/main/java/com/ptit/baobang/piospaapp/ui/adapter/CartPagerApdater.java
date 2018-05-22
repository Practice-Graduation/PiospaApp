package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class CartPagerApdater extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> mFragments;

    public CartPagerApdater(FragmentManager fm, Context context, List<Fragment> mFragments) {
        super(fm);
        mContext = context;
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return mFragments.get(0);
        if (position == 1)
            return mFragments.get(1);
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sản Phẩm";
            case 1:
                return "Dịch vụ";
            default:
                return null;
        }
    }
}
