package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.error.Error;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.DefaultValue;
import com.ptit.baobang.piospaapp.utils.FileUtil;
import com.ptit.baobang.piospaapp.utils.KeyBundleConstant;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Presenter màn hình chi tiết sản phẩm
 *
 * @version 1.0.1
 * @author BangNB
 */
public class ProductDetailPresenter extends BasePresenter implements IProductDetailPresenter {
    private final String TAG = "ProductDetailPresenter";
    private final String LOG_CONTRUCTOR = TAG + " contructor";
    String LOG_DO_ADD_CART = "do add cart";
    String LOG_DO_OPEN_CART = "do open cart";
    String LOG_GET_PRODUCT = "get product from intent";
    String LOG_DO_CLICK_POSITIVE = "Do click positive button";
    String LOG_DO_CLICK_NEGATIVE = "Do click negative button";
    String LOG_ERROR_CLICK_NEGATIVE = "Amount must be greater than 0";
    private IProductDetailView mView;
    private Context mContext;

    /**
     * The ProductDetailPresenter' constructor
     *
     * @param context           the Context of ProductDetailActivity
     * @param productDetailView the view of ProductDetailActivity
     */
    public ProductDetailPresenter(Context context, IProductDetailView productDetailView) {
        FileUtil.writeLog(LOG_CONTRUCTOR);
        this.mContext = context;
        this.mView = productDetailView;
    }

    /**
     * (5) Xử lý thêm giỏ hàng
     * Method was called when user click button "Thêm giỏ hàng"
     * Method used to add product to Cart
     * If product doesn't exist, it will add like a new product
     * If product exists, the product' amount will add to  "amount"
     *
     * @param product
     * @param amount  the amount of product
     */
    @Override
    public void onClickAddCart(Product product, String amount) {
        FileUtil.writeLog(LOG_DO_ADD_CART);
        Cart cart = CartHelper.getCart();
        cart.add(product, Integer.parseInt(amount));
        String message = mContext.getString(R.string.added)
                + AppConstants.SPACE_SYMBOL + product.getProductName();
        mView.showMessage(
                mContext.getString(R.string.message),
                message,
                SweetAlertDialog.SUCCESS_TYPE);
    }

    /**
     * (2) Xử lý nhấn button giỏ hàng
     * Method was called when user click FloatButton cart
     *
     * @return nothing
     */
    @Override
    public void onClickGoToCart() {
        FileUtil.writeLog(LOG_DO_OPEN_CART);
        mView.openFragmentCart();
    }

    /**
     * (1) Hiển thị ban đầu
     *      1. Lấy thông tin sản phẩm được truyền thông qua Intent
     * Get product was passed from {@link Intent}
     * Product was passed with {@link KeyBundleConstant}.PRODUCT_ID key.
     *
     * @param intent The Intent of ProductDetailActivity
     * @return {@link Product}
     */
    @Override
    public Product getProductFromBundle(Intent intent) {
        FileUtil.writeLog(LOG_GET_PRODUCT);
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return null;
        } else {
            return (Product) bundle.getSerializable(KeyBundleConstant.PRODUCT_ID);
        }
    }

    /**
     * (4) Xử lý tăng số lượng sản phẩm
     *      2. Xử lý tăng số lượng
     * The method was called when user clicked button positive
     * It used to increased product' amount to 1
     *
     * @param product
     * @param strAmount the text got from view
     * @return nothing
     */
    @Override
    public void onClickPositiveButton(Product product, String strAmount) {
        FileUtil.writeLog(LOG_DO_CLICK_POSITIVE);
        int amount = Integer.parseInt(strAmount);
        if (product.getAmount() == amount) {
            Log.e(TAG, Error.ERROR_PRODUCT_DETAIL_NOT_ENOUGH_AMOUNT.toString());
            mView.showMessage(
                    mContext.getString(R.string.message),
                    Error.ERROR_PRODUCT_DETAIL_NOT_ENOUGH_AMOUNT,
                    SweetAlertDialog.WARNING_TYPE);
            return;
        }
        mView.setAmount(String.valueOf(++amount));
    }

    /**
     * (3) Xử lý giảm số lượng sản phẩm
     *      2. Xử lý giảm số lượng
     * The method was called when user clicked button negative
     * It used to decreased product' amount to 1
     * If product's amount is greater than 1
     *
     * @param strAmount số lượng sản phẩm kiểu String
     */
    @Override
    public void onClickNegativeButton(String strAmount) {
        FileUtil.writeLog(LOG_DO_CLICK_NEGATIVE);
        int amount = Integer.parseInt(strAmount);

        if (amount > DefaultValue.DEFAULT_AMOUNT) {
            --amount;
        } else {
            FileUtil.writeLog(LOG_ERROR_CLICK_NEGATIVE);
        }
        mView.setAmount(String.valueOf(amount));
    }
}
