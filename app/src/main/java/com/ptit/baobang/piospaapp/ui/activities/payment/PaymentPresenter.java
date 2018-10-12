package com.ptit.baobang.piospaapp.ui.activities.payment;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.dto.CustomerInfoDTO;
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
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.CartItemProduct;
import com.ptit.baobang.piospaapp.data.network.model_request.CartShopping;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderBodyRequest;
import com.ptit.baobang.piospaapp.error.Error;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DefaultValue;
import com.ptit.baobang.piospaapp.utils.InputUtils;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;
import com.shuhart.stepview.StepView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PaymentPresenter extends BasePresenter implements IPaymentPresenter {

    private IPaymentView mView;
    private Context mContext;
    List<CartProductItem> cartProductItems;

    PaymentPresenter(IPaymentView mView, Context context) {
        this.mView = mView;
        this.mContext = context;
    }

    @Override
    public void clickButtonNext(StepView stepView, CustomerInfoDTO customerInfoDTO) {
        int currentStep = stepView.getCurrentStep();
        switch (currentStep) {
            case PaymentActivity.STEP_1_PAYMENT_TYPE:
                if (!checkPaymentTypeInput(customerInfoDTO)) {
                    return;
                }
                break;
            case PaymentActivity.STEP_2_ADDRESS:
                if (!checkDeliveryInfoInput(customerInfoDTO)) {
                    return;
                }
                break;
            case PaymentActivity.STEP_3_DELIVERY_TYPE:
                if (!checkDeliveryType(customerInfoDTO)) {
                    return;
                }
                break;
            case PaymentActivity.STEP_4_CONFIRM:
                createOrder(customerInfoDTO);
                break;
        }
        if (customerInfoDTO.getPaymentType().getOrderPaymentTypeId() == AppConstants.PAYMENT_TYPE_GET) {
            currentStep = stepView.getStepCount() - 1;
            computeTaxAndShip(customerInfoDTO.getDeliveryType(), customerInfoDTO.getTax());
        } else {
            currentStep++;
        }
        mView.nextStep(currentStep);
    }

    public void computeTaxAndShip(OrderDeliveryType mDeliveryType, Tax mTax) {

        int shipInt = 0;
        if (mDeliveryType != null) {
            shipInt = mDeliveryType.getPrice();
        }

        Cart cart = CartHelper.getCart();
        BigDecimal total = cart.getTotalPrice();
        mTax = null;
        if (mTax != null) {
            if (mTax.getType().equals(AppConstants.PECENT)) {
                total = total.add(total.multiply(BigDecimal.valueOf(mTax.getValue())).divide(BigDecimal.valueOf(AppConstants.HUNDRES_PERCENT)));
            } else if (mTax.getType().equals(AppConstants.MONEY)) {
                total = total.add(new BigDecimal(mTax.getValue()));
            }
        }
        String totalPrice = CommonUtils.formatToCurrency(cart.getTotalPrice());
        String ship = CommonUtils.formatToCurrency(shipInt);
        String payment = CommonUtils.formatToCurrency(total.add(BigDecimal.valueOf(shipInt)));
        mView.updateUIPaymentInfo(totalPrice, ship, payment);

    }

    private void createOrder(CustomerInfoDTO customerInfoDTO) {

        String deliveryAddress = getDeliveryAddress(customerInfoDTO);

        Customer customer = SharedPreferenceUtils.getUser(mContext);
        Order order = createOrderObject(customerInfoDTO, deliveryAddress, customer);

        List<CartItemProduct> itemProducts = getCartItemProducts();
        CartShopping cartShopping = new CartShopping();
        cartShopping.setCartItemProducts(itemProducts);

        OrderBodyRequest orderBodyRequest = new OrderBodyRequest();
        orderBodyRequest.setOrder(order);
        orderBodyRequest.setCartShopping(cartShopping);

        mView.showLoading(mContext.getString(R.string.create_order));
        getCompositeDisposable().add(
                mApiService.createOrder(orderBodyRequest)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
    }

    @NonNull
    private Order createOrderObject(CustomerInfoDTO customerInfoDTO, String deliveryAddress, Customer customer) {

        Order order = new Order();
        order.setFullName(customerInfoDTO.getName());
        order.setPhone(customerInfoDTO.getPhone());
        order.setCustomer(customer);
        order.setAddress(customer.getFullAddress());
        order.setAddressDelivery(deliveryAddress);
        order.setOrderPaymentType(customerInfoDTO.getPaymentType());
        order.setOrderDeliveryType(customerInfoDTO.getDeliveryType());
        if (customerInfoDTO.getDeliveryType() == null) {
            order.setDeliveryCost(DefaultValue.INT);
        } else {
            order.setDeliveryCost(customerInfoDTO.getDeliveryType().getPrice());
        }
        order.setTax(customerInfoDTO.getTax());
        // to get default value was define on database
        order.setOrderStatus(new OrderStatus());
        order.setOrderDeliveryStatus(new OrderDeliveryStatus());
        return order;
    }

    @NonNull
    private String getDeliveryAddress(CustomerInfoDTO customerInfoDTO) {
        return customerInfoDTO.getAddress()
                + AppConstants.COMMA_SYMBOL + AppConstants.SPACE_SYMBOL
                + customerInfoDTO.getWard().getType() + AppConstants.SPACE_SYMBOL + customerInfoDTO.getWard().getName()
                + AppConstants.COMMA_SYMBOL + AppConstants.SPACE_SYMBOL
                + customerInfoDTO.getDistrict().getType() + AppConstants.SPACE_SYMBOL + customerInfoDTO.getDistrict().getName()
                + AppConstants.COMMA_SYMBOL + AppConstants.SPACE_SYMBOL +
                customerInfoDTO.getProvince().getType() + AppConstants.SPACE_SYMBOL + customerInfoDTO.getProvince().getName();
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }

    private void handleResponse(EndPoint<Order> orderEndPoint) {
        if (orderEndPoint.getStatusCode() == AppConstants.SUCCESS_CODE) {
            mView.doneStep();
            mView.hideLoading();
            Cart cart = CartHelper.getCart();
            cart.clear();
            saveOrderLocal(orderEndPoint.getData());
            mView.openOrderActivity();
        } else {
            mView.hideLoading(orderEndPoint.getMessage(), false);
        }
    }

    private void saveOrderLocal(Order data) {
        OrderRealm orderRealm = new OrderRealm(data, cartProductItems);
        OrderHelper.saveOrder(orderRealm);
    }

    private boolean checkDeliveryType(CustomerInfoDTO customerInfoDTO) {
        if (customerInfoDTO.getDeliveryType() == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.delivery_type_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        return true;
    }

    private boolean checkPaymentTypeInput(CustomerInfoDTO customerInfoDTO) {
        if (customerInfoDTO.getPaymentType() == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.payment_type_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        return true;
    }

    private boolean checkDeliveryInfoInput(CustomerInfoDTO customerInfoDTO) {
        if (customerInfoDTO.getName() == null || customerInfoDTO.getName().isEmpty()) {
            mView.showMessage(mContext.getString(R.string.message), Error.ERROR_PAYMENT_NAME_EMPTY, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (customerInfoDTO.getPhone() == null || customerInfoDTO.getPhone().isEmpty()) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_phone_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (!InputUtils.isValidPhone(customerInfoDTO.getPhone())) {
            String message = mContext.getString(R.string.phone)
                    + AppConstants.SPACE_SYMBOL + customerInfoDTO.getPhone()
                    + AppConstants.SPACE_SYMBOL + mContext.getString(R.string.wrong);
            mView.showMessage(mContext.getString(R.string.message), message, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (customerInfoDTO.getProvince() == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_province_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (customerInfoDTO.getDistrict() == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_district_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (customerInfoDTO.getWard() == null) {
            mView.showMessage(mContext.getString(R.string.message), R.string.message_ward_empty, SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (customerInfoDTO.getAddress() == null || customerInfoDTO.getAddress().isEmpty()) {
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
    public void loadCartItem() {
        cartProductItems = getCartProductItems();
        mView.updateRVProduct(cartProductItems);
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
}
