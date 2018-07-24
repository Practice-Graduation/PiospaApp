package com.ptit.baobang.piospaapp.ui.activities.main;

import android.app.ActivityManager;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.activities.change_password.ChangePasswordActivity;
import com.ptit.baobang.piospaapp.ui.activities.login.LoginActivity;
import com.ptit.baobang.piospaapp.ui.activities.order.OrderActivity;
import com.ptit.baobang.piospaapp.ui.activities.profile.ProdfileActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart.CartFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_product.ProductFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_service.ServiceFragment;
import com.ptit.baobang.piospaapp.ui.listener.CallBackConfirmDialog;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, NavigationView.OnNavigationItemSelectedListener {
    private static final String SELECTED_ITEM = "arg_selected_item";
    private static int mSelectedItem = 0;
    private static int mSelectedFragment = 0;

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

        addControls();
        addEvents();
        addSelectedFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.loadUserInfo(this);
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
        mPresenter = new MainPresenter(this);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mNavigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        CommonUtils.disableShiftMode(mNavigation);
        addToolBar();
        addDrawerLayout();
    }

    private void addDrawerLayout() {
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        mSelectedFragment = intent.getIntExtra(AppConstants.FRAGMENT, 0);
        selectedItem = mNavigation.getMenu().getItem(mSelectedFragment);
        selectedFragment(selectedItem);
    }

    private void selectedFragment(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.navigation_product:
                showProductFragment();
                break;
            case R.id.navigation_service:
                showServiceFragment();
                break;
            case R.id.navigation_cart:
                showCartFragment();
                break;
        }

        centerToolbarTitle(mToolbar, AppConstants.PADDING_TOOLBAR);
        // update selected item
//        mSelectedItem = selectedItem.getItemId();
//        mNavigation.getMenu().getItem(mSelectedFragment).setChecked(true);
//        if (fragment != null) {
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.content, fragment).commit();
//        }
    }


    private void showCartFragment() {
//        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
//        if (!(mCurrentFragment instanceof CartFragment)) {
        mToolbar.setTitle(getString(R.string.title_cart) + "      ");
        mFragmentTran = getSupportFragmentManager().beginTransaction();
        mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mFragmentTran.replace(R.id.content, CartFragment.newInstance());
        mFragmentTran.commit();
        mNavigation.getMenu().getItem(2).setChecked(true);
//        }
    }

    private void showServiceFragment() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (!(mCurrentFragment instanceof ServiceFragment)) {
            mToolbar.setTitle(getString(R.string.title_service) + "      ");
            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.content, ServiceFragment.newInstance());
            mFragmentTran.commit();
            mNavigation.getMenu().getItem(1).setChecked(true);
        }
    }

    private void showProductFragment() {
        mCurrentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (!(mCurrentFragment instanceof ProductFragment)) {
            mToolbar.setTitle(
                    getString(R.string.title_product) + "      ");
            mFragmentTran = getSupportFragmentManager().beginTransaction();
            mFragmentTran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            mFragmentTran.replace(R.id.content, ProductFragment.newInstance());
            mFragmentTran.commit();
            mNavigation.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {


        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
            return;
        }

        ActivityManager manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        int sizeStack = manager.getRunningTasks(3).size();
        if (sizeStack == 1)
            showConfirm("Thông báo", "Bạn có muốn thoát ứng dụng", "Đồng ý", "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                @Override
                public void DiaglogPositive() {
                    MainActivity.super.onBackPressed();
                }

                @Override
                public void DiaglogNegative() {
                    return;
                }
            });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show();
        switch (id) {
            case R.id.nav_order:
                mPresenter.clickNavOrder();
                break;
            case R.id.nav_profile:
                mPresenter.clickNavProfile();
                break;
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void openOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void logOut() {
        SharedPreferenceUtils.saveUser(this, null);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void openProfileActivity() {
        Intent intent = new Intent(this, ProdfileActivity.class);
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
        Glide.with(this).load(image)
                .apply(RequestOptions.centerCropTransform().circleCrop().error(R.drawable.user))
                .into(imgAvatar);
    }
}
