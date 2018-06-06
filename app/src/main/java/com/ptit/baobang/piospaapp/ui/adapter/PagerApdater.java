package com.ptit.baobang.piospaapp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerApdater extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private boolean doNotifyDataSetChangedOnce = false;
    public PagerApdater(FragmentManager fm) {
        super(fm);
    }

   public  void addTab(Fragment fragment, String title){
       doNotifyDataSetChangedOnce = true;
        mFragments.add(fragment);
        mTitles.add(title);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }

        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       return mTitles.get(position);
    }
}
