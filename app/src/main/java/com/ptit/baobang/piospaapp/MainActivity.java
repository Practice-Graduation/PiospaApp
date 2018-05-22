package com.ptit.baobang.piospaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ptit.baobang.piospaapp.ui.activities.login.LoginActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart.CartFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_product.ProductFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_service.ServiceFragment;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String SELECTED_ITEM = "arg_selected_item";
    private int mSelectedItem = 0;
    private int mSelectedFragment = 0;
    private static final int MAX_TAB = 3;
    private static final int TAB_PRODUCT = 0;
    private static final int TAB_SERVICE = 1;
    private static final int TAB_CART = 2;

   @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mToggle;

    Fragment fragments[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        mUnbinder = ButterKnife.bind(this);
        addControls();
        addEvents();
        addSelectedFragment(savedInstanceState);
//            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mNavigation.getLayoutParams();
//           layoutParams.setBehavior(new BottomNavigationBehavior());
    }

    private void addEvents() {
        mNavigation.setOnNavigationItemSelectedListener(item -> {
            selectedFragment(item);
            return true;
        });
    }

    private void addControls( ) {
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mNavigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        CommonUtils.disableShiftMode(mNavigation);
        fragments = new Fragment[MAX_TAB];
        addToolBar();
        addDrawerLayout();
        // attaching bottom sheet behaviour - hide / show on scroll

    }

    private void addDrawerLayout() {
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    private void addToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void addSelectedFragment(Bundle savedInstanceState) {
        MenuItem selectedItem;
//        if (savedInstanceState != null) {
//            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
//            selectedItem = mNavigation.getMenu().findItem(mSelectedItem);
//        } else {
//            selectedItem = mNavigation.getMenu().getItem(TAB_PRODUCT);
//        }
        Intent intent = getIntent();
        mSelectedFragment =  intent.getIntExtra(AppConstants.FRAGMENT, 0);
        selectedItem = mNavigation.getMenu().getItem(mSelectedFragment);
        selectedFragment(selectedItem);
    }

    private void selectedFragment(MenuItem selectedItem) {
        Fragment fragment = null;
        switch (selectedItem.getItemId()){
            case R.id.navigation_product:
                if(fragments[TAB_PRODUCT] == null){
                    fragments[TAB_PRODUCT] = ProductFragment.newInstance();
                }
                fragment = fragments[TAB_PRODUCT];
                break;
            case R.id.navigation_service:
                if(fragments[TAB_SERVICE] == null){
                    fragments[TAB_SERVICE] =  ServiceFragment.newInstance();
                }
                fragment = fragments[TAB_SERVICE];
                break;
            case R.id.navigation_cart:
                if(fragments[TAB_CART] == null){
                    fragments[TAB_CART] = new CartFragment().newInstance();
                }
                fragment = fragments[TAB_CART];
                break;
        }
        // update selected item
        mSelectedItem = selectedItem.getItemId();
        mNavigation.getMenu().getItem(mSelectedFragment).setChecked(true);
        if(fragment != null){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
            return;
        }
//        MenuItem productItem = mNavigation.getMenu().getItem(TAB_PRODUCT);
//        if(mSelectedItem != productItem.getItemId()){
//            selectedFragment(productItem);
//            return;
//        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferenceUtils.saveUser(this, null);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }
}
