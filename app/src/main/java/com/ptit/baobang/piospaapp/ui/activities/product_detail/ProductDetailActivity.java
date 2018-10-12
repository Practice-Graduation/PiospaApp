package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductLabel;
import com.ptit.baobang.piospaapp.data.model.ProductOrigin;
import com.ptit.baobang.piospaapp.ui.activities.main.MainActivity;
import com.ptit.baobang.piospaapp.ui.base.BaseActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.DefaultValue;
import com.ptit.baobang.piospaapp.utils.FileUtil;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProductDetailActivity activity
 *
 * @author BangNB
 */
public class ProductDetailActivity extends BaseActivity<ProductDetailPresenter>
        implements IProductDetailView {

    private final String TAG = "ProductDetailActivity";
    private final String LOG_ON_CREATE = "onCreate " + TAG;
    private final String LOG_ON_START = "===================== START =========================\r\n" +
            "================ " + TAG + "=================\r\n" +
            "============= " + DateTimeUtils.getCurrentDateTime() + "============\r\n";
    private final String LOG_ON_RESUME = "onResume " + TAG;
    private final String LOG_ON_PAUSE = "onPause " + TAG;
    private final String LOG_ON_STOP = "onStop " + TAG;
    private final String LOG_CLICK_ADD_CART = "Click add cart";
    private final String LOG_CLICK_GO_TO_CART = "Click go to cart";
    private final String LOG_CLICK_POSITIVE = "Click positive";
    private final String LOG_CLICK_NEGATIVE = "Click negative";
    private final String LOG_SHOW_PRODUCT_DETAIL = "Show product detail";
    private final String LOG_OPEN_FRAGMENT_CART = "Open fragment cart";
    private final String LOG_CLICK_HOME = "Clik home";
    private final String LOG_SET_AMOUNT = "Set amount: ";
    private final String LOG_ON_DESTROY = "===================== END ===============================\r\n" +
            "================ " + TAG + "=================\r\n" +
            "===============================================================\r\n";
    private Product product; // store product was got from intent

    @BindView(R.id.img)
    ImageView imgBackGround;

    @BindView(R.id.imgSmallImage)
    ImageView imgSmallImage;

    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.txtPrice)
    TextView txtPrice;

    @BindView(R.id.txtInfo)
    TextView txtInfo;

    @BindView(R.id.btnAddCart)
    Button btnAddCart;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.clToolbar)
    CollapsingToolbarLayout clToolbar; //  layout use to scroll toolbar

    @BindView(R.id.txtAmount)
    TextView txtAmount;

    @BindView(R.id.txtProductLable)
    TextView txtProductLabel;

    @BindView(R.id.txtProductOrigin)
    TextView txtProductOrigin;

    @BindView(R.id.txtProductQuanlity)
    TextView txtProductQuality;

    /**
     * Method was called when start an activity.
     * Set layout to activity
     *
     * @param savedInstanceState
     * @return nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileUtil.writeLog(LOG_ON_START);
        FileUtil.writeLog(LOG_ON_CREATE);

        setContentView(R.layout.activity_product_detail);
        addToolBar();
        addControls();
    }

    /**
     * (1) Hiển thị ban đầu
     *      1. Lấy thông tin sản phẩm được truyền thông qua Intent
     *      2. Thực hiện khởi tạo màn hình ban đầu
     * Get{@link Product} was passed from {@link Intent}
     * Show product infomation on view
     *
     * @return nothing
     */
    private void addControls() {
        mPresenter = new ProductDetailPresenter(this, this);
        product = mPresenter.getProductFromBundle(getIntent());
        showProductDetail(product);

    }

    /**
     * Method add support action bar to activity
     *
     * @return nothing
     */
    private void addToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Do click events
     *
     * @param view the component was clicked
     * @return nothing
     */
    @OnClick({
            R.id.btnAddCart,
            R.id.fbGoToCart,
            R.id.btnPositive,
            R.id.btnNegative})
    void onClick(View view) {
        switch (view.getId()) {
            //(5) Xử lý thêm giỏ hàng
            // click add product to cart event
            case R.id.btnAddCart:
                FileUtil.writeLog(LOG_CLICK_ADD_CART);
                mPresenter.onClickAddCart(product, txtAmount.getText().toString());
                break;

            // (2) Xử lý nhấn button giỏ hàng
            // click Float Button event
            case R.id.fbGoToCart:
                FileUtil.writeLog(LOG_CLICK_GO_TO_CART);
                mPresenter.onClickGoToCart();
                break;
            //(4) Xử lý tăng số lượng sản phẩm
            // Click button positive event
            case R.id.btnPositive:

                FileUtil.writeLog(LOG_CLICK_POSITIVE);
                mPresenter.onClickPositiveButton(product, txtAmount.getText().toString());
                break;
            //(3) Xử lý giảm số lượng sản phẩm
            // Click button negative event
            case R.id.btnNegative:
                FileUtil.writeLog(LOG_CLICK_NEGATIVE);
                mPresenter.onClickNegativeButton(txtAmount.getText().toString());
                break;
        }
    }

    /**
     * (1) Hiển thị ban đầu
     *      2. Thực hiện khởi tạo màn hình ban đầu
     * Show {@link Product} information on view
     *
     * @param product
     * @return nothing
     */
    @Override
    public void showProductDetail(Product product) {
        FileUtil.writeLog(LOG_SHOW_PRODUCT_DETAIL);
        txtName.setText(product.getProductName());
        String builder =
                getString(R.string.price) + AppConstants.DOUBLE_DOT
                        + CommonUtils.formatToCurrency(product.getPrice());
        txtPrice.setText(builder);
        txtInfo.setText(Html.fromHtml(product.getDescription()));
        txtAmount.setText(String.valueOf(DefaultValue.DEFAULT_AMOUNT));

        ProductLabel productLabel = product.getProductLabel();
        builder = AppConstants.LABEL + productLabel.getProductLabelName();
        txtProductLabel.setText(builder);

        ProductOrigin productOrigin = product.getProductOrigin();
        builder = AppConstants.ORGIGIN + productOrigin.getProductOriginName();
        txtProductOrigin.setText(builder);

        builder = AppConstants.QUANLITY + product.getQuanlityString();
        txtProductQuality.setText(builder);

        CommonUtils.loadImage(imgBackGround, product.getImage());
        CommonUtils.loadImage(imgSmallImage, product.getImage());
    }

    /**
     * (2) Xử lý nhấn button giỏ hàng
     * Open main activity and selected tab cart
     *
     * @return nothing
     */
    @Override
    public void openFragmentCart() {
        FileUtil.writeLog(LOG_OPEN_FRAGMENT_CART);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(KeyBundleConstant.FRAGMENT, AppConstants.CART_INDEX);
        startActivity(intent);
    }

    /**
     * Method was executed when product' amount was changed
     *
     * @param amount
     * @return nothing
     */
    @Override
    public void setAmount(String amount) {
        FileUtil.writeLog(LOG_SET_AMOUNT + amount);
        txtAmount.setText(amount);
    }

    /**
     * Method was executed when one of navigation items was clicked
     *
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FileUtil.writeLog(LOG_CLICK_HOME);
                onBackPressed();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        FileUtil.writeLog(LOG_ON_RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileUtil.writeLog(LOG_ON_PAUSE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileUtil.writeLog(LOG_ON_DESTROY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FileUtil.writeLog(LOG_ON_STOP);
    }
}
