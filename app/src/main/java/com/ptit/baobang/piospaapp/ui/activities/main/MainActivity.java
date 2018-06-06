package com.ptit.baobang.piospaapp.ui.activities.main;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.activities.login.LoginActivity;
import com.ptit.baobang.piospaapp.ui.activities.order.OrderActivity;
import com.ptit.baobang.piospaapp.ui.activities.profile.ProdfileActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_cart.CartFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_product.ProductFragment;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_service.ServiceFragment;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainView, NavigationView.OnNavigationItemSelectedListener {
    private static final String SELECTED_ITEM = "arg_selected_item";
    private static int mSelectedItem = 0;
    private static int mSelectedFragment = 0;

    private MainPresenter mPresenter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        addControls();
        addEvents();
        addSelectedFragment();
    }

    private void addEvents() {
        mNavigation.setOnNavigationItemSelectedListener(item -> {
            selectedFragment(item);
            return true;
        });
        mLeftNavigation.setNavigationItemSelectedListener(this);
    }

    private void addControls() {
        mUnbinder = ButterKnife.bind(this);
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
        getSupportActionBar().setDisplayShowTitleEnabled(true);
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
        Fragment fragment = null;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        switch (selectedItem.getItemId()) {
            case R.id.navigation_product:

                if(!(currentFragment instanceof ProductFragment)){
                    fragment = ProductFragment.newInstance();
                }
                break;
            case R.id.navigation_service:

                if(!(currentFragment instanceof ServiceFragment)){
                    fragment = ServiceFragment.newInstance();
                }
                break;
            case R.id.navigation_cart:
                if(!(currentFragment instanceof CartFragment)){
                    Log.e("NEW CAR", "NEW CART");
                    fragment = CartFragment.newInstance();
                }
                break;
        }
        // update selected item
        mSelectedItem = selectedItem.getItemId();
        mNavigation.getMenu().getItem(mSelectedFragment).setChecked(true);
        if (fragment != null) {
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
        return super.onOptionsItemSelected(item);
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

    }

    @Override
    public void loadUserInfo(String image, String username, String email) {
        txtUsername.setText(username);
        txtEmai.setText(email);
        Glide.with(this).load(image)
                .apply(RequestOptions.centerCropTransform().circleCrop())
                .into(imgAvatar);
    }
}
