package com.ptit.baobang.piospaapp.ui.activities.payment;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.BookingItem;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.data.local.helper.OrderHelper;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryStatus;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryType;
import com.ptit.baobang.piospaapp.data.model.OrderPaymentType;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Tax;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.CartItemProduct;
import com.ptit.baobang.piospaapp.data.network.model_request.CartItemService;
import com.ptit.baobang.piospaapp.data.network.model_request.CartShopping;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderBodyRequest;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;
import com.ptit.baobang.piospaapp.utils.InputUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PaymentPresenter extends BasePresenter implements IPaymentPresenter {

    private static String TAG = "PaymentPresenter";

    private IPaymentView mView;
    private Context mContext;
    List<CartProductItem> cartProductItems;
    List<CartServicePriceItem> cartServicePriceItems;

    PaymentPresenter(IPaymentView mView, Context context) {
        this.mView = mView;
        this.mContext = context;
    }

    @Override
    public void clickButtonNext(int currentStep, String name,
                                String phone, Province mProvince,
                                District mDistrict, Ward mWard,
                                String address, OrderDeliveryType mDeliveryType,
                                OrderPaymentType mPaymentType, Tax mTax) {
        switch (currentStep) {
            case 0:
                if (!checkDeliveryInfoInput(name, phone, mProvince,
                        mDistrict, mWard, address)) {
                    return;
                }
                break;
            case 1:
                if (!checkPaymentInput(mDeliveryType, mPaymentType)) {
                    return;
                }
                break;
            case 2:
                createOrder(name, phone, mProvince, mDistrict, mWard,
                        address, mDeliveryType, mPaymentType, mTax);
                break;
        }
        Cart cart = CartHelper.getCart();
        if (currentStep == 0 && cart.getProducts().size() == 0) {
            currentStep += 2;
        } else {
            currentStep++;
        }
        if (currentStep == 2) {
            computeTax(mDeliveryType, mTax);
        }
        mView.nextStep(currentStep);
    }

    public void computeTax(OrderDeliveryType mDeliveryType, Tax mTax) {

        int shipInt = 0;
        if (mDeliveryType != null) {
            shipInt = mDeliveryType.getPrice();
        }

        if (mTax != null) {
            Cart cart = CartHelper.getCart();

            BigDecimal total = cart.getTotalPrice();
            if (mTax.getType().equals(AppConstants.PECENT)) {
                total = total.add(total.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
            } else if (mTax.getType().equals(AppConstants.MONEY)) {
                total = total.add(new BigDecimal(mTax.getValue()));
            }
            String totalPrice = CommonUtils.formatToCurrency(cart.getTotalPrice());
            String ship = CommonUtils.formatToCurrency(shipInt);
            String payment = CommonUtils.formatToCurrency(total.add(BigDecimal.valueOf(shipInt)));
            mView.updateUIPaymentInfo(totalPrice, ship, payment);
        }

    }

    private void createOrder(String name, String phone, Province mProvince,
                             District mDistrict, Ward mWard, String address,
                             OrderDeliveryType mDeliveryType,
                             OrderPaymentType mPaymentType, Tax mTax) {

        String deliveyAddress = address + ", " + mWard.getType() + " " + mWard.getName()
                + "," + mDistrict.getType() + " " + mDistrict.getName()
                + ", " + mProvince.getType() + " " + mProvince.getName();

        Customer customer = SharedPreferenceUtils.getUser(mContext);
        Order order = new Order();
        order.setFullName(name);
        order.setPhone(phone);
        order.setCustomer(customer);
        order.setAddress(customer.getFullAddress());
        order.setAddressDelivery(deliveyAddress);
        order.setOrderPaymentType(mPaymentType);
        order.setOrderDeliveryType(mDeliveryType);
        if (mDeliveryType == null) {

            order.setDeliveryCost(0);
        } else {

            order.setDeliveryCost(mDeliveryType.getPrice());
        }
        order.setTax(mTax);
        // to get default value was define on database
        order.setOrderStatus(new OrderStatus());
        order.setOrderDeliveryStatus(new OrderDeliveryStatus());


        List<CartItemProduct> itemProducts = getCartItemProducts();
        List<CartItemService> itemServices = getCartItemServices();
        CartShopping cartShopping = new CartShopping();
        cartShopping.setCartItemServices(itemServices);
        cartShopping.setCartItemProducts(itemProducts);

        OrderBodyRequest orderBodyRequest = new OrderBodyRequest();
        orderBodyRequest.setOrder(order);
        orderBodyRequest.setCartShopping(cartShopping);
        Gson gson = new Gson();
        Log.e("JSON", gson.toJson(orderBodyRequest));
        mView.showLoading(mContext.getString(R.string.create_order));
        getCompositeDisposable().add(
                mApiService.createOrder(orderBodyRequest)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handleResponse(EndPoint<Order> orderEndPoint) {
        if (orderEndPoint.getStatusCode() == 200) {
            mView.doneStep();
            mView.hideLoading();
            Cart cart = CartHelper.getCart();
            cart.clear();

            saveOrderLocal(orderEndPoint.getData());


            mView.openOrderActivity();
        } else {
            mView.hideLoading(mContext.getString(R.string.create_order_failed), false);
            Log.e("Loi", orderEndPoint.getMessage());
        }
    }

    private void saveOrderLocal(Order data) {

        OrderRealm orderRealm = new OrderRealm(data, cartProductItems, cartServicePriceItems);
        OrderHelper.saveOrder(orderRealm);
    }

    private boolean checkPaymentInput(OrderDeliveryType mDeliveryType,
                                      OrderPaymentType mPaymentType) {
        if (mDeliveryType == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.delivery_type_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (mPaymentType == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.payment_type_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        return true;
    }

    private boolean checkDeliveryInfoInput(String name, String phone, Province mProvince, District mDistrict, Ward mWard, String address) {
        if (name == null || name.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_fullname_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (phone == null || phone.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_phone_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (!InputUtils.isValidPhone(phone)) {
            mView.showMessage(mContext.getString(R.string.message), mContext.getString(R.string.phone) + " " + phone + " " + mContext.getString(R.string.wrong), SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (mProvince == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_province_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (mDistrict == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_district_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (mWard == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_ward_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (address == null || address.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_specific_address_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        return true;
    }

    @Override
    public void clickTextViewProvince() {
        mView.openDialogProvince();
    }

    @Override
    public void clickTextViewDistrict(Province province) {
        mView.openDialogDistrict(province);
    }

    @Override
    public void clickTextViewWard(District mDistrict) {
        mView.openDialogWard(mDistrict);
    }

    @Override
    public void loadDeliveryType() {

        getCompositeDisposable().add(
                mApiService.getAllOrderDeliveryType()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleDeliveryTypeResponse, this::noneHandleError)
        );
    }

    private void noneHandleError(Throwable throwable) {

    }

    private void handleDeliveryTypeResponse(EndPoint<List<OrderDeliveryType>> listEndPoint) {
        List<OrderDeliveryType> deliveryTypes = listEndPoint.getData();
        mView.updateRVOrderDeliveryType(deliveryTypes);
    }

    @Override
    public void loadPaymentType() {
        getCompositeDisposable().add(
                mApiService.getAllOrderPaymentType()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleOrderPaymentTypeResponse, this::noneHandleError)
        );
    }

    private void handleOrderPaymentTypeResponse(EndPoint<List<OrderPaymentType>> listEndPoint) {
        List<OrderPaymentType> deliveryTypes = listEndPoint.getData();
        mView.updateRVOrderPaymentType(deliveryTypes);
    }

    @Override
    public void attachData(String name, String phone, Province mProvince,
                           District mDistrict, Ward mWard, String address,
                           OrderDeliveryType mDeliveryType,
                           OrderPaymentType mPaymentType) {
        mView.showData(name, phone, mProvince.getName(), mDistrict.getName(),
                mWard.getName(), address,
                mDeliveryType == null ? "" : mDeliveryType.getOrderDeliveryTypeName(),
                mPaymentType == null ? "" : mPaymentType.getOrderPaymentTypeName());
    }

    @Override
    public void loadCartItem() {
        cartProductItems = getCartProductItems();
        cartServicePriceItems = getCartServiceItems();
        mView.updateRVProduct(cartProductItems);
        mView.updateRVService(cartServicePriceItems);
    }

    @Override
    public void attachDataForInput(Context baseContext) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);
        mView.showDataForInput(customer);
    }

    @Override
    public void loadTax() {
        getCompositeDisposable().add(
                mApiService.getTax(AppConstants.TAX_ID)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleTaxResponse, this::noneHandleError)
        );
    }

    private void handleTaxResponse(EndPoint<Tax> taxEndPoint) {
        mView.setTax(taxEndPoint.getData());
    }

    private List<CartProductItem> getCartProductItems() {

        List<CartProductItem> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();
        Map<Product, Integer> itemMap = cart.getItemWithQuantityProduct();

        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            CartProductItem cartItem = new CartProductItem();
            cartItem.setProduct(entry.getKey());
            cartItem.setQuanlity(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    private List<CartServicePriceItem> getCartServiceItems() {
        List<CartServicePriceItem> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();

        Map<BookingItem, Integer> itemMap = cart.getItemWithQuantityServices();

        for (Map.Entry<BookingItem, Integer> entry : itemMap.entrySet()) {
            CartServicePriceItem cartItem = new CartServicePriceItem();
            cartItem.setBookingItem(entry.getKey());
            cartItem.setNumberCustomer(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    private List<CartItemProduct> getCartItemProducts() {
        List<CartItemProduct> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();
        Map<Product, Integer> itemMap = cart.getItemWithQuantityProduct();

        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            CartItemProduct cartItem = new CartItemProduct();
            cartItem.setProductId(entry.getKey().getProductId());
            cartItem.setNumber(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    private List<CartItemService> getCartItemServices() {
        List<CartItemService> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();

        Map<BookingItem, Integer> itemMap = cart.getItemWithQuantityServices();

        for (Map.Entry<BookingItem, Integer> entry : itemMap.entrySet()) {
            CartItemService cartItem = new CartItemService();
            cartItem.setProductId(entry.getKey().getServicePrice().getServicePriceId());
            //
            cartItem.setDateBooking(DateTimeUtils.formatDate(entry.getKey().getSelectedDate(), DateTimeUtils.DATE_PATTERN_DDMMYY));
            cartItem.setTimeBooking(DateTimeUtils.formatDate(entry.getKey().getSelectedDate(), DateTimeUtils.TIME_PATTERN));
            cartItem.setNumber(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
