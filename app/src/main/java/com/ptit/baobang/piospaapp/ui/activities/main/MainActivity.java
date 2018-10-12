package com.ptit.baobang.piospaapp.ui.activities.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.services.FCMUtils;
import com.ptit.baobang.piospaapp.ui.activities.change_password.ChangePasswordActivity;
import com.ptit.baobang.piospaapp.ui.activities.login.LoginActivity;
import com.ptit.baobang.piospaapp.ui.activities.order.OrderActivity;
import com.ptit.baobang.piospaapp.ui.activities.product_detail.ProductDetailActivity;
import com.ptit.baobang.piospaapp.ui.activities.profile.ProfileActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart.CartFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_product.ProductFragment;
import com.ptit.baobang.piospaapp.ui.listener.CallBackConfirmDialog;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DefaultValue;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, NavigationView.OnNavigationItemSelectedListener {
    private static final String SELECTED_ITEM = "arg_selected_item";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(R.id.nav_view)
    NavigationView mLeftNavigation;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mToggle;

    ImageView imgAvatar;

    TextView txtUsername;

    TextView txtEmai;

    private Fragment mCurrentFragment;
    private FragmentTransaction mFragmentTran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        CommonUtils.setUpStackMainScreen(this);

        addControls();
        addEvents();
        addSelectedFragment();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.loadUserInfo(this);
            mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
            if (mCurrentFragment instanceof CartFragment) {
                showCartFragment();
            }
        }
    }

    private void addEvents() {
        mNavigation.setOnNavigationItemSelectedListener(item -> {
            selectedFragment(item);
            return true;
        });
        mLeftNavigation.setNavigationItemSelectedListener(this);
    }

    private void addControls() {
        mPresenter = new MainPresenter(this, this);
        CommonUtils.disableShiftMode(mNavigation);
        addToolBar();
        addDrawerLayout();
    }

    private void addDrawerLayout() {
        mToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        View view = mLeftNavigation.getHeaderView(0);
        imgAvatar = view.findViewById(R.id.imageView);
        txtEmai = view.findViewById(R.id.textView);
        txtUsername = view.findViewById(R.id.txtUsername);
        mPresenter.loadUserInfo(this);

    }

    private void addToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void addSelectedFragment() {
        MenuItem selectedItem;
        Intent intent = getIntent();
        int selectedFragment = intent.getIntExtra(KeyBundleConstant.FRAGMENT, DefaultValue.INT);
        selectedItem = mNavigation.getMenu().getItem(selectedFragment);
        selectedFragment(selectedItem);
    }

    private void selectedFragment(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.navigation_product:
                showProductFragment();
                break;
            case R.id.navigation_cart:
                showCartFragment();
                break;
        }

        centerToolbarTitle(mToolbar, AppConstants.PADDING_TOOLBAR);
    }


    private void showCartFragment() {
        mToolbar.setTitle(getString(R.string.title_cart) + "      ");
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.content, CartFragment.newInstance());
        mFragmentTran.commit();
        mNavigation.getMenu().getItem(AppConstants.CART_INDEX).setChecked(true);
    }

    private void showProductFragment() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (!(mCurrentFragment instanceof ProductFragment)) {
            mToolbar.setTitle(getString(R.string.title_product));
            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.content, ProductFragment.newInstance());
            mFragmentTran.commit();
            mNavigation.getMenu().getItem(AppConstants.PRODUCT_INDEX).setChecked(true);
        }
    }
    @Override
    public void onBackPressed() {


        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
            return;
        }

        int count = SharedPreferenceUtils.getCount(this);
        if (count > 1) {
            SharedPreferenceUtils.saveCount(this, count - 1);
            finish();
        } else {
            showConfirm(getString(R.string.message), getString(R.string.message_quit_app), getString(R.string.ok), getString(R.string.cancel), SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                @Override
                public void DiaglogPositive() {
                    SharedPreferenceUtils.saveCount(MainActivity.this, 0);
                    finish();
                }

                @Override
                public void DiaglogNegative() {
                    return;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_order:
                mPresenter.clickNavOrder();
                break;
            case R.id.nav_profile:
                mPresenter.clickNavProfile();
                break;
//            case R.id.nav_scancode:
//                mPresenter.clickScanBarcode();
//                break;
            case R.id.nav_change_password:
                mPresenter.clickNavChangePassword();
                break;
            case R.id.nav_logout:
                mPresenter.clickNavLogout();
                break;

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putInt(SELECTED_ITEM, mSelectedItem);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void openOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void logOut() {
        Customer customer = SharedPreferenceUtils.getUser(this);
        FCMUtils.unsubscribeTopicFCM(this, customer.getAccount());
        SharedPreferenceUtils.clearAll(this);
        SharedPreferenceUtils.saveFirstInit(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void openChangePasswordActivity() {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadUserInfo(String image, String username, String email) {
        txtUsername.setText(username);
        txtEmai.setText(email);

        Bitmap error = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), error);
        circularBitmapDrawable.setCircular(true);

        Glide.with(this).load(image)
                .apply(RequestOptions.centerCropTransform().circleCrop().error(circularBitmapDrawable))
                .into(imgAvatar);
    }

    @Override
    public void openProductDetail(Product data) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KeyBundleConstant.PRODUCT_ID, data);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
